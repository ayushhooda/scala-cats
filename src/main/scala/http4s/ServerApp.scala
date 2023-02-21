package http4s

import cats.data.Kleisli
import cats.effect._
import com.comcast.ip4s._
import org.http4s.ember.server._
import org.http4s.implicits._
import org.http4s.{Request, Response, server}

object ServerApp extends IOApp {

  // Add all types of api routes services here
  val httpApp: Kleisli[IO, Request[IO], Response[IO]] = server.Router(
    "/" -> Routes.helloWorldService
  ).orNotFound

  def run(args: List[String]): IO[ExitCode] = {
    EmberServerBuilder
      .default[IO]
      .withHost(ipv4"0.0.0.0")
      .withPort(port"8081")
      .withHttpApp(httpApp)
      .build
      .use(_ => IO.never)
      .as(ExitCode.Success)
  }

}