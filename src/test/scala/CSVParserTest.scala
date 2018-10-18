import org.scalatest._
import qirun.utils._

class CSVParserTest extends FlatSpec with Matchers {

  "CSVParser" should "read data from the local file by the given path" in {
    val data = CSVParser.read("src/test/resources/lastnames_samples.csv")

    assert(data.nonEmpty)
  }

}