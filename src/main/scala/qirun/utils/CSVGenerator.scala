package qirun.utils

import java.io.{File, IOException, PrintWriter}
import org.apache.logging.log4j.scala.Logging

object CSVGenerator extends Logging {

  /**
    * Write out the data in iterator to the given file
    * @param filePath
    * @param iterator
    */
  def writeOut(filePath: String, iterator: DataGenerator, header: Option[String]) = {
    val writer = new PrintWriter(new File(filePath))

    try {
      if(header.isDefined) writer.write(s"${header.get}\n")
      while (iterator.hasNext) {
        val data = iterator.next()
        if (data.isDefined)
          writer.write(s"${data.get.toString}\n")
      }
      logger.info(s"File Successfully Generated")

    } catch {
      case io: IOException => logger.error(io.getStackTrace)
    }
    finally {
      writer.close()
    }
  }
}
