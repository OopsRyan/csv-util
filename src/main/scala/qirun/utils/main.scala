package qirun.utils

import com.typesafe.config.{Config, ConfigFactory}

case class User(firstName: String, lastName: String, age: Int) {

  @Override
  override def toString: String = s"${firstName},${lastName},${age}"
}

object CSVMain {

  lazy val config: Config = ConfigFactory.load("application.conf")

  def main(args: Array[String]): Unit = {

    val randomAgeRange = (config.getInt("randomAge.lower"), config.getInt("randomAge.upper"))

    val dataSizeRange = (config.getInt("dataSize.lower"), config.getInt("dataSize.upper"))

    validateArguments(args, dataSizeRange)

    val outputPath = args(1)
    val dataGenerator = initializeDataGenerator(args(0).toInt, randomAgeRange)

    CSVGenerator.writeOut(outputPath, dataGenerator)
  }

  private def validateArguments(args: Array[String], dataSizeRange: (Int, Int)) = {

    if (!args(0).forall(_.isDigit)) throw new IllegalArgumentException("The first Argument should be a number")

    if (args(0).toInt <= dataSizeRange._1 || args(0).toInt >= dataSizeRange._2)
      throw new IllegalArgumentException(s"The size of data should be in the range " +
        s"(${dataSizeRange._1}, ${dataSizeRange._2})(exclusive)")
  }

  private def initializeDataGenerator(size: Int, range: (Int, Int)) = {
    val firstnames = CSVParser.read("src/test/resources/firstnames_samples.csv")
    val lastnames = CSVParser.read("src/test/resources/lastnames_samples.csv")

    DataGenerator(firstnames, lastnames, size, range)
  }
}