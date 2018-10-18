package qirun.utils

case class FullNameGenerator(iterator: Iterator[(String, String)], n: Int) {

  //TODO: Limit check?
  /**
    * Extract the next element in the iterator
    * @return The next element if it exists. Otherwise, iterator
    */
  def next() = {
    if(iterator.hasNext) {
      iterator.next()
    } else iterator
  }
}

object FullNameGenerator {
  def apply(iterator1: Iterator[String], iterator2: Iterator[String], n: Int=0): FullNameGenerator = {
    val iterator = for (a <- iterator1; b <- iterator2) yield (a, b)

    new FullNameGenerator(iterator, n)
  }
}