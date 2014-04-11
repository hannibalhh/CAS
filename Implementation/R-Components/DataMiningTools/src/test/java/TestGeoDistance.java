import org.haw.cas.DataMiningTools.TextMining.HarversineFormula;
import org.haw.cas.DataMiningTools.TextMining.IGeoCoordinateDistanceCalculator;
import org.haw.cas.RealTimeCore.CommonTypes.Entities.GeoCoordinate;
import org.junit.Assert;
import org.testng.annotations.Test;

/**
 * Created with IntelliJ IDEA.
 * User: Nils
 * Date: 22.11.13
 * Time: 14:03
 * Test the distance calculation between to geo points.
 */


public class TestGeoDistance {



    @Test
    public void TestHarvensine (){
        IGeoCoordinateDistanceCalculator geoCoordinateDistanceCalculator = new HarversineFormula();
        double result = geoCoordinateDistanceCalculator.calculateDistance(new GeoCoordinate(50.0359, 005.4253), new GeoCoordinate(58.3838, 003.0412));

        Assert.assertSame(result, 968.9d);



    }



}
