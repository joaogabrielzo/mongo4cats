package mongo4cats

import cats.effect.{ExitCode, IO, IOApp}
import mongo4cats.client.{MongoClientF, MongoServerAddress}
import org.mongodb.scala.bson.collection.immutable.Document

class Application extends IOApp {

  val mongoAddress = MongoServerAddress("localhost", 27017)

  override def run(args: List[String]): IO[ExitCode] =
    MongoClientF.fromServerAddress[IO](mongoAddress).use { client =>
      for {
        db <- client.getDatabase("mydb")
        collection <- db.getCollection[Document]("mycoll")
      } yield ExitCode.Success
    }
}
