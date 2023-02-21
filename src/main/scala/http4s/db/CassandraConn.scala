package http4s.db

import fs2._
import cats.effect._
import com.ringcentral.cassandra4io.CassandraSession
import com.datastax.oss.driver.api.core.{CqlSession, CqlSessionBuilder}
import com.ringcentral.cassandra4io.cql.Reads
import com.ringcentral.cassandra4io.cql._
import doobie.WeakAsync.doobieWeakAsyncForAsync
import http4s.Models.User

import java.net.InetSocketAddress

object CassandraConn {

  implicit val userReads: Reads[User] = Reads.rowReads.map(row => User(row.getString("name"), row.getString("address")))
  val builder: CqlSessionBuilder = CqlSession
    .builder()
    .addContactPoint(InetSocketAddress.createUnresolved("localhost", 9042))
    .withAuthCredentials("cassandra", "cassandra")
    .withLocalDatacenter("datacenter1")
    .withKeyspace("mykeyspace")

  val makeSession: Resource[IO, CassandraSession[IO]] = CassandraSession.connect[IO](builder)

  def readUserById(name: String): IO[List[User]] = {
    makeSession.use { session =>
      cql"SELECT name, address FROM mykeyspace.user WHERE name = $name"
        .as[User]
        .select(session)
        .evalTap(i => IO(println(i))) // used for console printing only
        .compile.toList
    }
  }

  def readUsers(): IO[List[User]] = {
    makeSession.use { session =>
      cql"SELECT name, address FROM mykeyspace.user"
        .as[User]
        .select(session)
        .evalTap(i => IO(println(i))) // used for console printing only
        .compile.toList
    }
  }

  def insert(user: User, session: CassandraSession[IO]): IO[Boolean] = {
    cql"INSERT INTO mykeyspace.user (name, address) VALUES (${user.name}, ${user.address})"
      .execute(session)
  }

  def insertUser(user: User): IO[Boolean] = {
    makeSession.use { session =>
      insert(user, session)
    }
  }

  def insertUsers(user: List[User]): IO[List[Boolean]] = {
    makeSession.use { session =>
      val insertStmt = user.map(u => insert(u, session)).reduce((a,b) => a *> b)
      Stream
        .eval(insertStmt)
        .compile
        .toList
    }
  }
}
