package qirun.utils

import org.apache.logging.log4j.scala.Logging
import scala.io.Source
import java.sql.{Connection, DriverManager}
import com.typesafe.config.{Config, ConfigFactory}

object CSVParser extends Logging{

  lazy val config: Config = ConfigFactory.load("application.conf")
  private var connection: Connection = _

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

  def DBInsert(filePath: String, urlDB: String, header: Boolean=true) = {
    val data = read(filePath, header)

    try {
      connection = DBConfig()
      val statement = connection.createStatement
      val rs = statement.executeQuery("SELECT firstname, lastname, age FROM user")
      while (rs.next) {
        logger.info(s"firstname = ${rs.getString("firstname")}," +
          s" lastname = ${rs.getString("lastname")}," +
          s" age = ${rs.getInt("age")}")
      }
    } catch {
      case e: Exception => logger.error(e.getStackTrace)
    }
    connection.close()

    while (data.hasNext) {
      logger.info(s"${data.next()}")
    }
  }

  def DBConfig(): Connection = {
    val driver = "com.mysql.jdbc.Driver"
    Class.forName(driver)

    DriverManager.getConnection(
      config.getString("database.mysql.url"),
      config.getString("database.mysql.username"),
      config.getString("database.mysql.password"))
  }

  def createDB() =  {}

  def createTable() = {}

  def Insert() = {}
}