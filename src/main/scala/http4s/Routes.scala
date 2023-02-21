package http4s

import cats.effect.IO
import http4s.Models.User
import http4s.db.CassandraConn
import io.circe.generic.auto._
import org.http4s._
import org.http4s.circe.CirceEntityCodec.circeEntityEncoder
import org.http4s.circe.CirceEntityDecoder.circeEntityDecoder
import org.http4s.dsl.io._

object Routes {

  val helloWorldService: HttpRoutes[IO] = HttpRoutes.of[IO] {
    case GET -> Root / "hello" =>
      Ok("hello")
    case GET -> Root / "users" =>
      CassandraConn.readUsers().flatMap(Ok(_))
    case GET -> Root / "user" / id =>
      CassandraConn.readUserById(id).flatMap(Ok(_))
    case req@POST -> Root / "user" =>
      req.as[User].flatMap(user => CassandraConn.insertUser(user).flatMap(Ok(_)))
    case req@POST -> Root / "users" =>
      req.as[List[User]].flatMap(users => CassandraConn.insertUsers(users).flatMap(Ok(_)))
    case _ =>
      IO(Response(Status.Ok))
  }

}
