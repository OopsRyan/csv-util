package qirun.utils

import scala.util.Random

case class DataGenerator(iterator: Iterator[(String, String)], n: Int, range: (Int, Int)) {

  private var count = 0
  private val randomSeed = new Random()

  /**
    * Extract the next element in the iterator
    * @return The next element if it exists. Otherwise, iterator
    */
  def next(): Option[User] = {
    if(hasNext) {
      count += 1
      val tuple2 = iterator.next()
      Option(User(tuple2._1, tuple2._2, getRandomNumber(range)))
    } else None
  }

  def hasNext = iterator.hasNext && count < n

  /**
    * get a random number from the given range(exclusive)
    * @param range
    * @return
    */
  private def getRandomNumber(range: (Int, Int)): Int = {
    range._1 + randomSeed.nextInt(range._2 - range._1)
  }
}

object DataGenerator {
  /**
    * Create a iterator containing all combinations if two iterators provided.
    * Otherwise, initialize with the given one.
    * @param iterator1 Firstnames in this case
    * @param iterator2 Lastnames in this case
    * @param size The number of combinations to be generated
    * @return The case object with the combinations
    */
  def apply(iterator1: Iterator[String], iterator2: Iterator[String],
                     size: Int, range: (Int, Int)) = {
    val iterator = for (a <- iterator1; b <- iterator2) yield (a, b)

    new DataGenerator(iterator, size, range)
  }
}