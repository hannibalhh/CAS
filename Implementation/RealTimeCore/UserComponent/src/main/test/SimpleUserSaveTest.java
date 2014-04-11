import org.haw.cas.Adapters.RTCToDataMinerAdapter.Implementation.RTCAdapterComponent;
import org.haw.cas.Adapters.RTCToDataMinerAdapter.Interface.Exceptions.ConfigurationException;
import org.haw.cas.Adapters.RTCToDataMinerAdapter.Interface.Exceptions.TechnicalProblemException;
import org.haw.cas.Adapters.RTCToDataMinerAdapter.Interface.IRTCAdapter;
import org.haw.cas.Adapters.RTCToDataMinerAdapter.Interface.Messages.*;
import org.haw.cas.GlobalTypes.MessageInfo.Provenance;
import org.haw.cas.GlobalTypes.Settings.AppSettings;
import org.haw.cas.GlobalTypes.Settings.SettingException;
import org.haw.cas.RealTimeCore.CommonTypes.Entities.GeoCoordinate;
import org.haw.cas.RealTimeCore.CommonTypes.Entities.User;
import org.haw.cas.RealTimeCore.UserComponent.Implementation.UserDAO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * User: Jason Wilmans
 * Date: 01.11.13
 * Time: 19:56
 */


public class SimpleUserSaveTest {

    private UserDAO userDAO;
    private IRTCAdapter adapter;
    private AppSettings appSettings;

    @Before
    public void setUp() {
        userDAO = new UserDAO();
        adapter = new RTCAdapterComponent(false);
        appSettings = new AppSettings();



    }

    private InformationMessage generateTestInformationMessage(String author, GeoCoordinate geoCoordinate) {

        List<InfoMessage> infoList = new ArrayList<>();

        infoList.add(new LocationMessage(geoCoordinate.getLatitude(), geoCoordinate.getLongitude(), 100));

        List<String> topics = new ArrayList<>();
        topics.add("Unit test");
        InformationMetadataMessage metaData = new InformationMetadataMessage(topics, new ArrayList<String>());

        return new InformationMessage("unitTest informationMessage", "UnitTestMessage", author, infoList, LocalDateTime.now(), metaData, Provenance.Twitter);


    }


    @Test
    public void handleInformationMessageTest() {
        try {
            if (appSettings.getBoolean("testing")) {

                String testMessageAuthor = "Tobias Test";
                GeoCoordinate lactationOfAuthor = new GeoCoordinate(42.0, 10.0);

                adapter.send(generateTestInformationMessage(testMessageAuthor, lactationOfAuthor));

                Thread.sleep(10000);


                Collection<User> userCollection = userDAO.getAll();


                Assert.assertTrue(! userDAO.getUserByAlias(testMessageAuthor).isEmpty());

                List<User> testAuthors = new ArrayList<>( userDAO.getUserByAlias(testMessageAuthor));



                Assert.assertTrue( testAuthors
                        .stream()
                        .filter(u -> u.getLastPosition().getGeoCoordinate().equals(lactationOfAuthor))
                        .findAny()
                        .isPresent());


            }  else{
                System.err.println("Testing is false. Unittest will only run in testing mode, please chance your appsettings");
                Assert.assertTrue(false);
            }

        } catch (TechnicalProblemException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (ConfigurationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (SettingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }


}
