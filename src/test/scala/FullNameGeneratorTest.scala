import org.scalatest.{FlatSpec, Matchers}
import qirun.utils._

class FullNameGeneratorTest extends FlatSpec with Matchers {

  "FullnameGenerator" should "return the next element in the iterator" in {

    val firstnames = CSVParser.read("src/test/resources/firstnames_samples.csv")
    val lastnames = CSVParser.read("src/test/resources/lastnames_samples.csv")

    val generator = FullNameGenerator(firstnames, lastnames)

    assert(generator.next() == ("Aaron", "Aaberg"))
    assert(generator.next() == ("Aaron", "Aaby"))
  }

  "FullnameGenerator" should "return the empty iterator when it reaches the last one" in {

    val empty1 = CSVParser.read("src/test/resources/empty.csv")
    val empty2 = CSVParser.read("src/test/resources/empty.csv")

    val generator = FullNameGenerator(empty1, empty2)

    assert(generator.next() == generator.iterator)
  }
}
