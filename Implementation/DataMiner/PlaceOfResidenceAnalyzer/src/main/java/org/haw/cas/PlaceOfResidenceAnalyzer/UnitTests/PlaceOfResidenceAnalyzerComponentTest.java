package org.haw.cas.PlaceOfResidenceAnalyzer.UnitTests;

import Coordinator.Interface.ICoordinator;
import DSPersistenceManager.Model.Geodata;
import DSPersistenceManager.Model.Message;
import DataTypes.DataTypeInterfaces.IInfo;
import DataTypes.Location;
import Interfaces.IAnalyzer;
import MessageAdapter.Interface.IMessageAdapter;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.haw.cas.GlobalTypes.MessageInfo.Provenance;
import org.haw.cas.PlaceOfResidenceAnalyzer.Implementation.Component.PlaceOfResidenceAnalyzerComponent;
import org.haw.cas.PlaceOfResidenceAnalyzer.Implementation.Usecases.GoogleMapsAchieveGeoData;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.Semaphore;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * User: N3trunner
 * Date: 27.11.13
 * Time: 21:14
 * To change this template use File | Settings | File Templates.
 */
public class PlaceOfResidenceAnalyzerComponentTest {
    PlaceOfResidenceAnalyzerComponent placeOfResidenceAnalyzer;
    CoordinatorMock coordinatorMock;
    Logger logger = LogManager.getLogger(PlaceOfResidenceAnalyzerComponentTest.class);

    @Before
    public void setUp() throws Exception {
        coordinatorMock = new CoordinatorMock();

        placeOfResidenceAnalyzer = new PlaceOfResidenceAnalyzerComponent(new MessageAdapterMock(), coordinatorMock);
        placeOfResidenceAnalyzer.startAnalyzer();
    }

    @After
    public void tearDown() throws Exception {
        placeOfResidenceAnalyzer.stopAnalyzer();
    }

    @Test
    public void testProcessMessages() throws Exception {
        HashMap<Message, List<Location>> testMessagesAndExpectedResults = new HashMap<>();

        // Basic city name testing
        testMessagesAndExpectedResults.put(getTestMessage("Ich sitze in Hamburg.", new Geodata(53.5510846,9.9936818)), this.getLocationsForNames(new String[]{"Hamburg"}));
        testMessagesAndExpectedResults.put(getTestMessage("Ich sitze in Berlin.", new Geodata(52.5200066,13.404954)), this.getLocationsForNames(new String[]{"Berlin"}));

        // Testing recognition of streets.
        testMessagesAndExpectedResults.put(getTestMessage("Am Heidenkampsweg 17 ist Hochwasser.", new Geodata(53.5509004,10.0262398)), this.getLocationsForNames(new String[]{"Heidenkampsweg 17"}));

        // Testing recognition of exiting geotag.
        testMessagesAndExpectedResults.put(getTestMessage("Mein Gorilla hat ne Villa im Zoo.", new Geodata(100,200)), new ArrayList<Location>() {{add(new Location(100, 200));}});

        // Testing if on false positives.
        testMessagesAndExpectedResults.put(getTestMessage("Ich bin ein Gorilla.", new Geodata(1,1)), new ArrayList<Location>());

        List<Message> messageChunk = new ArrayList<Message>();
        messageChunk.addAll(testMessagesAndExpectedResults.keySet());

        placeOfResidenceAnalyzer.onNewMessageChunkArrived(messageChunk);
        Map<String, List<IInfo>> analyzerResult = coordinatorMock.getLastResultOfAnalyzer();

        Iterator<Map.Entry<Message, List<Location>>> iterator = testMessagesAndExpectedResults.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<Message, List<Location>> nextElement = iterator.next();
            List<IInfo> currentResult = analyzerResult.get(nextElement.getKey().getId());
            List<Location> expectedResult = nextElement.getValue();

            try {
                Assert.assertTrue(checkIfListContainsElements(currentResult, expectedResult));

                List<Location> expectedResultsWithCertainities = currentResult.stream().filter(x -> expectedResult.contains(x)).map(x -> (Location) x).collect(Collectors.toList());

                // Check if the expected results have the highest certainity from the returned results.
                Assert.assertTrue(checkIfExpectedLocationsHaveHighestCertainity(currentResult, expectedResultsWithCertainities));
            } catch (AssertionError e) {
                System.err.println("Assertion failed: Message Text: " + nextElement.getKey().getMessage());
                throw e;
            }
        }
    }

    private boolean checkIfExpectedLocationsHaveHighestCertainity(List<IInfo> currentResult, List<Location> expectedResult) {
        List<IInfo> resultsWorkingCopy = new ArrayList<>(currentResult);
        resultsWorkingCopy.removeAll(expectedResult);

        return expectedResult.stream().allMatch(expected -> resultsWorkingCopy.stream().allMatch(actual -> expected.getCertainty() > actual.getCertainty()));
    }

    private boolean checkIfListContainsElements(List list, List elements) {
        return elements.stream().allMatch(x -> list.contains(x));
    }

    private List<Location> getLocationsForNames(String[] locations) {
        List<Location> result = new ArrayList<>();

        for (String loc : locations) {
            Location resultFromGoogle = getLocationForName(loc);

            if (resultFromGoogle != null) {
                result.add(resultFromGoogle);
            }
        }

        return result;
    }

    private synchronized Location getLocationForName(String name) {
        GoogleMapsAchieveGeoData maps = new GoogleMapsAchieveGeoData();
        GoogleMapsAchieveGeoData.GoogleMapsResponse response = maps.obtainGeoData(name);
        if (response.getExceptionDuringRequest() == null) {
            logger.error("Failed to get location from GoogleMaps for name: '" + name + "' Error: " + ExceptionUtils.getStackTrace(response.getExceptionDuringRequest()));
            return null;
        }
        return new Location(response.getLatitude(), response.getLongitude());
    }

    private Message getTestMessage(String message, Geodata location) {
        Message newMessage = new Message("Testman", message, new ArrayList<String>(), new Date(), location, new ArrayList<String>(), Provenance.Twitter);

        newMessage.setId(UUID.randomUUID().toString());

        return newMessage;
    }
}

class CoordinatorMock implements ICoordinator {

    private Semaphore waitHandle = new Semaphore(0);
    private Map<String, List<IInfo>> lastResult = null;

    public Map<String, List<IInfo>> getLastResultOfAnalyzer() throws InterruptedException {
        waitHandle.acquire();

        return lastResult;
    }

    @Override
    public void startCoordinating() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void stopCoordinating() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void registerAnalyzerResultForCurrentChunk(IAnalyzer analyzer, Map<String, List<IInfo>> resultsForChunk) {
        lastResult = resultsForChunk;
        waitHandle.release();
    }

    @Override
    public void registerAnalyzer(IAnalyzer analyzer) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}

class MessageAdapterMock implements IMessageAdapter {

    @Override
    public void obtainNewMessages() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void stopObtainingNewMessages() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void addNewMessageChunkArrivedListener(Consumer<Iterable<Message>> c) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void notifyListenersAboutNewMessageChunk(Iterable<Message> messageChunk) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
