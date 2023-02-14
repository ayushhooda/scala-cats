import cats.effect.{IO, IOApp}

object HelloWorld extends IOApp.Simple {
  override def run: IO[Unit] = IO.println("Hello, World!")
}
