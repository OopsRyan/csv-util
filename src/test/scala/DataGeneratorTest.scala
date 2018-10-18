import org.scalatest.{FlatSpec, Matchers}
import qirun.utils._

class DataGeneratorTest extends FlatSpec with Matchers {

  "FullnameGenerator" should "return the next element in the iterator" in {

    val firstnames = CSVParser.read("src/test/resources/firstnames_samples.csv")
    val lastnames = CSVParser.read("src/test/resources/lastnames_samples.csv")

    val generator = DataGenerator(firstnames, lastnames, 10, (1, 100))
    val user = generator.next().get
    assert(user.firstName == "Aaron" && user.lastName == "Aaberg")
  }

  "FullnameGenerator" should "return None when the given size is 0" in {

    val firstnames = CSVParser.read("src/test/resources/firstnames_samples.csv")
    val lastnames = CSVParser.read("src/test/resources/lastnames_samples.csv")

    val generator = DataGenerator(firstnames, lastnames, 0, (1, 100))
    val user = generator.next()
    assert(user.isEmpty)
  }

  "FullnameGenerator" should "return None when it reaches the last one" in {

    val empty1 = CSVParser.read("src/test/resources/empty.csv")
    val empty2 = CSVParser.read("src/test/resources/empty.csv")

    val generator = DataGenerator(empty1, empty2, 0, (1, 100))
    val user = generator.next()
    assert(user.isEmpty)
  }

  "FullnameGenerator" should "throw an exception when no data is generated" in {


  }
}
