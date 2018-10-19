package qirun.utils

import org.apache.logging.log4j.scala.Logging

import scala.io.Source
import java.sql.{Connection, DriverManager, Statement}

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

  /**
    * Insert each line in the given file to the indicated DB by URL
    * @param filePath
    * @param urlDB
    * @param header determine whether or not to ignore the first row in the file
    */
  def DBInsert(filePath: String, urlDB: String, header: Boolean=true) = {
    val data = read(filePath, header)

    try {
      connection = DBConfig(urlDB)
      val statement = connection.createStatement

      createTable(statement)
      val count = insertData(data, statement)
      logger.info(s"############ Successfully insert ${count} rows..############")
      logger.info(s"###################### Query DB ####################")
      val rs = statement.executeQuery("SELECT firstname, lastname, age FROM user")
      while (rs.next) {
        logger.info(s"firstname = ${rs.getString("firstname")}," +
          s" lastname = ${rs.getString("lastname")}," +
          s" age = ${rs.getInt("age")}")
      }
    } catch {
      case e: Exception => logger.error(e.getMessage)
    }
    connection.close()
  }

  /**
    * Config DB connection by DBUrl and the params in app.conf
    * @param urlDB
    * @return
    */
  def DBConfig(urlDB: String): Connection = {
    val driver = "com.mysql.jdbc.Driver"
    Class.forName(driver)
    val urlPrefix = config.getString("database.mysql.urlPrefix")
    val args = urlDB.split(config.getString("database.urlSplit"))

    DriverManager.getConnection(urlPrefix + args(0), args(1), if(args.length > 2) args(2) else "")
  }

  /**
    * Create a table. Drop it first if it exists.
    * @param statement
    * @return
    */
  def createTable(statement: Statement) = {

    statement.executeUpdate("DROP TABLE user")

    val sql = "CREATE TABLE user (firstname VARCHAR(50), lastname VARCHAR(50), age INTEGER)"

    statement.executeUpdate(sql)
  }

  /**
    * Insert data line by line to the table
    * @param data
    * @param statement
    * @return The number of successfully executed rows
    */
  def insertData(data: Iterator[String], statement: Statement) = {
    var count = 0
//    data.foreach(s => s.split(",") match {
//
//    })


    while (data.hasNext) {
      data.next().split(",") match {
        case param: Array[String] if param.length == 3 =>
          statement.execute(s"INSERT INTO user values('${param(0)}', '${param(1)}', '${param(2)}')"); count+=1
        case e: Array[String] => throw new Exception("Some line is wrong...")
      }
    }

    count
  }
}