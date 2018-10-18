package qirun.utils

import scala.io.{BufferedSource, Source}

object CSVParser {

  /**
    * Read data from the local target file into buffer
    * @param filePath The local path of data file
    * @param header Indicate whether the header exists
    * @return Iterator[String]
    */
  def read(filePath: String, header: Boolean=true): Iterator[String] = {
    val iterator = Source.fromFile(filePath).getLines()

    if(header) iterator.drop(1) else iterator
  }
}