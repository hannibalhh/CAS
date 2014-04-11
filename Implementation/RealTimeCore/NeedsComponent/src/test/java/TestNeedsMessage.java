import org.apache.log4j.Logger;
import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.AkkaMessage;
import org.haw.cas.Adapters.AkkaAdapter.Interface.Messages.Needs.NeedsMessage;
import org.haw.cas.Adapters.PersistenceManager.Implementation.Exception.EntityAlreadyExistException;
import org.haw.cas.GlobalTypes.MessageInfo.Provenance;
import org.haw.cas.GlobalTypes.MessageInfo.TypeOfNeed;
import org.haw.cas.RealTimeCore.CommonTypes.Entities.GeoCoordinate;
import org.haw.cas.RealTimeCore.CommonTypes.Entities.Need;
import org.haw.cas.RealTimeCore.CommonTypes.Entities.User;
import org.haw.cas.RealTimeCore.UserComponent.Implementation.UserComponentMock;
import org.haw.cas.RealTimeCore.UserComponent.Interface.IUserComponent;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * User: Jason Wilmans
 * Date: 09.12.13
 * Time: 19:10
 */
public class TestNeedsMessage {

    private static final Logger logger = Logger.getLogger(TestNeedsMessage.class);
    private static int needs;
    private IUserComponent userComponent;
    private List<User> users;
    private Random random = new Random();

    @Before
    public void initializeTestUsers() throws EntityAlreadyExistException {
        userComponent = new UserComponentMock();

        users = new ArrayList<>();

        users.add(new User("testuser1"));
        users.add(new User("testuser2"));
        users.add(new User("testuser3"));

        users.forEach(u -> {
            try {
                userComponent.createUser(u);
            } catch (EntityAlreadyExistException e) {
                Assert.fail("Exception occurred while creating need: " + e);
            }
        });
    }

    @Test
    public void testNeedsMessageConversion() throws Exception {

        // create random needs
        List<Need> needs = new ArrayList<>();

        int nrOfNeeds = 20;

        for (int i = 0; i < nrOfNeeds; i++) {
            needs.add(getRandomNeed(true));
        }

        // encode the needs via protobuf
        NeedsMessage msg = new NeedsMessage("test1", needs);

        byte[] raw = msg.getBytes();

        // read it back from raw values
        AkkaMessage.AkkaMessageBuilder.Builder builder = AkkaMessage.AkkaMessageBuilder.newBuilder();

        builder.mergeFrom(raw);

        // test if a correct number of needs was serialized
        Assert.assertEquals("message type was not correct", AkkaMessage.AkkaMessageBuilder.MessageType.NeedsMessage, builder.getType());

        Assert.assertEquals("not all needs sent",
                needs.size(),
                builder.getNeeds().getAccomodationCount() +
                        builder.getNeeds().getDrinkCount() +
                        builder.getNeeds().getFoodCount() +
                        builder.getNeeds().getHelperCount() +
                        builder.getNeeds().getMedicamentCount()
        );

        // test if attributes of needs are correct. we can rely on the user name as identifier, since it is uniquely
        // generated in the getRandomNeed() method
        needs.parallelStream()
                .forEach(n -> {
                    String nick = n.getUser().getAliases().iterator().next();
                    switch (n.getType()) {
                        case Shelter:
                            logger.debug("checked an accommodation");
                            builder.getNeeds().getAccomodationList()
                                    .parallelStream()
                                    .forEach(a -> {
                                        if (nick.equals(a.getAuthor())) {
                                            Assert.assertEquals("geocoordinates(latitude) not serialized correctly",
                                                    (int) n.getGeoCoordiante().getLatitude(),
                                                    (int)a.getGeo().getLatitude());

                                            Assert.assertEquals("geocoordinates(longitude) not serialized correctly",
                                                    (int) n.getGeoCoordiante().getLongitude(),
                                                    (int)a.getGeo().getLongitude());

                                            Assert.assertEquals("creation time not serialized correctly",
                                                    n.getCreationTime().toEpochSecond(ZoneOffset.ofHours(1)),
                                                    a.getCreationTime());

                                            Assert.assertEquals("message not serialized correctly",
                                                    n.getMessage(),
                                                    a.getMessage());

                                            Assert.assertEquals("provenance not serialized correctly",
                                                    n.getProvenance(),
                                                    Provenance.valueOf(a.getProvenance()));
                                        }
                                    });
                            break;
                        case Water:
                            logger.debug("checked an accommodation");
                            builder.getNeeds().getDrinkList()
                                    .parallelStream()
                                    .forEach(a -> {
                                        if (nick.equals(a.getAuthor())) {
                                            Assert.assertEquals("geocoordinates(latitude) not serialized correctly",
                                                    (int) n.getGeoCoordiante().getLatitude(),
                                                    (int)a.getGeo().getLatitude());

                                            Assert.assertEquals("geocoordinates(longitude) not serialized correctly",
                                                    (int) n.getGeoCoordiante().getLongitude(),
                                                    (int)a.getGeo().getLongitude());

                                            Assert.assertEquals("creation time not serialized correctly",
                                                    n.getCreationTime().toEpochSecond(ZoneOffset.ofHours(1)),
                                                    a.getCreationTime());

                                            Assert.assertEquals("message not serialized correctly",
                                                    n.getMessage(),
                                                    a.getMessage());

                                            Assert.assertEquals("provenance not serialized correctly",
                                                    n.getProvenance(),
                                                    Provenance.valueOf(a.getProvenance()));
                                        }
                                    });
                            break;
                        case Food:
                            logger.debug("checked an accommodation");
                            builder.getNeeds().getFoodList()
                                    .parallelStream()
                                    .forEach(a -> {
                                        if (nick.equals(a.getAuthor())) {
                                            Assert.assertEquals("geocoordinates(latitude) not serialized correctly",
                                                    (int) n.getGeoCoordiante().getLatitude(),
                                                    (int)a.getGeo().getLatitude());

                                            Assert.assertEquals("geocoordinates(longitude) not serialized correctly",
                                                    (int) n.getGeoCoordiante().getLongitude(),
                                                    (int)a.getGeo().getLongitude());

                                            Assert.assertEquals("creation time not serialized correctly",
                                                    n.getCreationTime().toEpochSecond(ZoneOffset.ofHours(1)),
                                                    a.getCreationTime());

                                            Assert.assertEquals("message not serialized correctly",
                                                    n.getMessage(),
                                                    a.getMessage());

                                            Assert.assertEquals("provenance not serialized correctly",
                                                    n.getProvenance(),
                                                    Provenance.valueOf(a.getProvenance()));
                                        }
                                    });
                            break;
                        case Helper:
                            logger.debug("checked an accommodation");
                            builder.getNeeds().getHelperList()
                                    .parallelStream()
                                    .forEach(a -> {
                                        if (nick.equals(a.getAuthor())) {
                                            Assert.assertEquals("geocoordinates(latitude) not serialized correctly",
                                                    (int) n.getGeoCoordiante().getLatitude(),
                                                    (int)a.getGeo().getLatitude());

                                            Assert.assertEquals("geocoordinates(longitude) not serialized correctly",
                                                    (int) n.getGeoCoordiante().getLongitude(),
                                                    (int)a.getGeo().getLongitude());

                                            Assert.assertEquals("creation time not serialized correctly",
                                                    n.getCreationTime().toEpochSecond(ZoneOffset.ofHours(1)),
                                                    a.getCreationTime());

                                            Assert.assertEquals("message not serialized correctly",
                                                    n.getMessage(),
                                                    a.getMessage());

                                            Assert.assertEquals("provenance not serialized correctly",
                                                    n.getProvenance(),
                                                    Provenance.valueOf(a.getProvenance()));
                                        }
                                    });
                            break;
                        case MedicalCare:
                            logger.debug("checked an accommodation");
                            builder.getNeeds().getMedicamentList()
                                    .parallelStream()
                                    .forEach(a -> {
                                        if (nick.equals(a.getAuthor())) {
                                            Assert.assertEquals("geocoordinates(latitude) not serialized correctly",
                                                    (int) n.getGeoCoordiante().getLatitude(),
                                                    (int)a.getGeo().getLatitude());

                                            Assert.assertEquals("geocoordinates(longitude) not serialized correctly",
                                                    (int) n.getGeoCoordiante().getLongitude(),
                                                    (int)a.getGeo().getLongitude());

                                            Assert.assertEquals("creation time not serialized correctly",
                                                    n.getCreationTime().toEpochSecond(ZoneOffset.ofHours(1)),
                                                    a.getCreationTime());

                                            Assert.assertEquals("message not serialized correctly",
                                                    n.getMessage(),
                                                    a.getMessage());

                                            Assert.assertEquals("provenance not serialized correctly",
                                                    n.getProvenance(),
                                                    Provenance.valueOf(a.getProvenance()));
                                        }
                                    });
                            break;
                    }
                });
    }

    public Need getRandomNeed(boolean withGeoCoordinate) {
        needs++;
        return new Need(
                new User("test" + needs),
                TypeOfNeed.values()[random.nextInt(TypeOfNeed.values().length)],
                withGeoCoordinate ? new GeoCoordinate(random.nextDouble() * 180, random.nextDouble() * 90) : null,
                LocalDateTime.now(),
                "testmessage",
                Provenance.values()[random.nextInt(Provenance.values().length)]);
    }
}
