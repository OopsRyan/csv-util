import org.scalatest.{FlatSpec, Matchers}
import qirun.utils._

class FullNameGeneratorTest extends FlatSpec with Matchers {

  val firstnames = CSVParser.read("src/test/resources/firstnames_samples.csv")
  val lastnames = CSVParser.read("src/test/resources/lastnames_samples.csv")

  val generator = FullNameGenerator(firstnames, lastnames)

  "FullnameGenerator" should "return the next element in the iterator" in {

    assert(generator.next() == ("Aaron", "Aaberg"))
    assert(generator.next() == ("Aaron", "Aaby"))
  }

  "FullnameGenerator" should "return the empty iterator when it reaches the last one" in {

    assert(generator.next() == ("Aaron", "Aaberg"))
    assert(generator.next() == ("Aaron", "Aaby"))
  }
}
