import org.apache.log4j.Logger;
import org.haw.cas.Adapters.AkkaAdapter.Implementation.AkkaAdapterMock;
import org.haw.cas.Adapters.PersistenceManager.Implementation.Exception.EntityAlreadyExistException;
import org.haw.cas.Adapters.RTCToDataMinerAdapter.Implementation.RTCAdapterMock;
import org.haw.cas.GlobalTypes.MessageInfo.Provenance;
import org.haw.cas.GlobalTypes.MessageInfo.TypeOfNeed;
import org.haw.cas.RealTimeCore.CommonTypes.Entities.GeoCoordinate;
import org.haw.cas.RealTimeCore.CommonTypes.Entities.Need;
import org.haw.cas.RealTimeCore.CommonTypes.Entities.User;
import org.haw.cas.RealTimeCore.NeedsComponent.Implementation.NeedsComponent;
import org.haw.cas.RealTimeCore.NeedsComponent.Interface.INeedsComponent;
import org.haw.cas.RealTimeCore.UserComponent.Implementation.UserComponentMock;
import org.haw.cas.RealTimeCore.UserComponent.Interface.IUserComponent;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * User: Jason Wilmans
 * Date: 09.12.13
 * Time: 16:05
 * <p/>
 */

public class TestNeedsCRUDComponent {

    private static final Logger logger = Logger.getLogger(TestNeedsCRUDComponent.class);
    private INeedsComponent neeedsComponent;
    private IUserComponent userComponent;
    private List<User> users;
    private Random random = new Random();

    @Before
    public void resetNeedsComponent() {
        neeedsComponent = new NeedsComponent(
                new RTCAdapterMock(),
                new AkkaAdapterMock(),
                new UserComponentMock());
    }

    @Before
    public void initializeTestUsers() throws EntityAlreadyExistException {
        userComponent = new UserComponentMock();

        users = new ArrayList<>();

        users.add(new User("user1"));
        users.add(new User("user2"));
        users.add(new User("user3"));

        users.forEach(u -> {
            try {
                userComponent.createUser(u);
            } catch (EntityAlreadyExistException e) {
                Assert.fail("Exception occurred while creating need: " + e);
            }
        });
    }

    public Need getRandomNeed(boolean withGeoCoordinate) {
        return new Need(
                users.get(random.nextInt(users.size())),
                TypeOfNeed.values()[random.nextInt(TypeOfNeed.values().length)],
                withGeoCoordinate ? new GeoCoordinate(random.nextDouble() * 180, random.nextDouble() * 90) : null,
                LocalDateTime.now(),
                "testmessage",
                Provenance.values()[random.nextInt(Provenance.values().length)]);
    }

    @Test
    public void testNeedCreation() throws Exception {

        List<Need> needs = new ArrayList<>();

        int nrOfNeeds = 200000;

        for (int i = 0; i < nrOfNeeds; i++) {
            needs.add(getRandomNeed(random.nextBoolean()));
            neeedsComponent.createNeed(needs.get(i));
        }

        Assert.assertEquals("not all needs created", nrOfNeeds, neeedsComponent.getAllNeeds().size());

        Collection<Need> persistent = neeedsComponent.getAllNeeds();

        needs.parallelStream()
                .forEach(n -> Assert.assertTrue("asserted all created needs to be persisted, but one was not: ", persistent.contains(n)));
    }

    @Test
    public void testFindNeedsWithGeoCordinates() throws Exception {

        List<Need> needs = new ArrayList<>();

        int nrOfNeeds = 50;
        int nrOfNeedsWithPosition = 0;

        for (int i = 0; i < nrOfNeeds; i++) {

            if(i % 10 == 0) {
                needs.add(getRandomNeed(true));
                nrOfNeedsWithPosition++;
            }   else {
                needs.add(getRandomNeed(false));
            }

            neeedsComponent.createNeed(needs.get(i));
        }

        List<Need> retreived = needs.parallelStream()
                .filter(n -> n.getGeoCoordiante() != null)
                .collect(Collectors.toList());

        Assert.assertEquals("not the correct number of needs with position returned", nrOfNeedsWithPosition, retreived.size());
    }
}