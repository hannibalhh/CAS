import org.haw.cas.DataMiningTools.TextMining.DamerauLevenshtein;
import org.haw.cas.DataMiningTools.TextMining.IStringDistanceCalculator;
import org.junit.Test;

/**
 * User: Jason Wilmans
 * Date: 04.11.13
 * Time: 12:00
 * </p>
 * Test the string distance calculation algorithms
 */
public class TestStringDistanceCalculators {

    @Test
    public void TestDamerauLevensthein() {
        IStringDistanceCalculator calculator = new DamerauLevenshtein();

        assert(calculator.calculateDistance("Test", "Test") == 0);

        assert(calculator.calculateDistance("test", "T3st") == 2);
    }
}
