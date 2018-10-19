package qirun.utils

import com.typesafe.config.{Config, ConfigFactory}

case class User(firstName: String, lastName: String, age: Int) {

  @Override
  override def toString: String = s"${firstName},${lastName},${age}"
}

object Main {

  lazy val config: Config = ConfigFactory.load("application.conf")

  def main(args: Array[String]): Unit = {

    val randomAgeRange = (config.getInt("randomAge.lower"), config.getInt("randomAge.upper"))

    val dataSizeRange = (config.getInt("dataSize.lower"), config.getInt("dataSize.upper"))

    validateArguments(args, dataSizeRange)

    val outputPath = args(1)
    val dataGenerator = initializeDataGenerator(args(0).toInt, randomAgeRange)

    // Generate CSV datafile
    val header = "firstname,lastname,age"
    CSVGenerator.writeOut(outputPath, dataGenerator, Option(header))

    // Parse the generated file and insert each line of it to Database
    CSVParser.DBInsert(outputPath, args(2))
  }

  /**
    * Validate if the given data size is in the range
    * @param args
    * @param dataSizeRange
    */
  private def validateArguments(args: Array[String], dataSizeRange: (Int, Int)) = {

    if (!args(0).forall(_.isDigit)) throw new IllegalArgumentException("The first Argument should be a number")

    if (args(0).toInt <= dataSizeRange._1 || args(0).toInt >= dataSizeRange._2)
      throw new IllegalArgumentException(s"The size of data should be in the range " +
        s"(${dataSizeRange._1}, ${dataSizeRange._2})(exclusive)")
  }

  /**
    * Load the raw data file
    * @param size
    * @param range
    * @return
    */
  private def initializeDataGenerator(size: Int, range: (Int, Int)) = {
    val firstnames = CSVParser.read("src/main/resources/firstnames_samples.csv")
    val lastnames = CSVParser.read("src/main/resources/lastnames_samples.csv")

    DataGenerator(firstnames, lastnames, size, range)
  }
}