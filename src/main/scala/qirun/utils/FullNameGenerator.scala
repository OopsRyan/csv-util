package qirun.utils

case class FullNameGenerator[A](iterator: Iterator[A], n: Int) {

  private var count = 0

  /**
    * Extract the next element in the iterator
    * @return The next element if it exists. Otherwise, iterator
    */
  def next() = {
    if(iterator.hasNext && count < n) {
      count += 1
      iterator.next()
    } else None
  }
}

object FullNameGenerator {
  /**
    * Create a iterator containing all combinations if two iterators provided.
    * Otherwise, initialize with the given one.
    * @param iterator1 Firstnames in this case
    * @param iterator2 Lastnames in this case
    * @param size The number of combinations to be generated
    * @tparam A The type of elements in iterator1
    * @tparam B The type of elements in iterator2
    * @return The case object with the combinations
    */
  def apply[A, B](iterator1: Iterator[A], iterator2: Iterator[B], size: Int): FullNameGenerator[(A, B)] = {
    val iterator = for (a <- iterator1; b <- iterator2) yield (a, b)

    new FullNameGenerator[(A, B)](iterator, size)
  }

  def apply[A](iterator1: Iterator[A], size: Int): FullNameGenerator[A] = {
    new FullNameGenerator[A](iterator1, size)
  }
}