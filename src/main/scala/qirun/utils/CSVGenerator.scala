package qirun.utils

import java.io.{File, IOException, PrintWriter}
import org.apache.logging.log4j.scala.Logging

object CSVGenerator extends Logging {

  def writeOut(filePath: String, iterator: DataGenerator) = {
    val writer = new PrintWriter(new File(filePath))

    try {
      while (iterator.hasNext()) {
        val data = iterator.next()
        if (data.isDefined)
          writer.write(s"${data.get.toString}\n")
      }
      logger.info(s"File Successfully Generated")

    } catch {
      case io: IOException => logger.info(io.getMessage)
    }
    finally {
      writer.close()
    }
  }
}
