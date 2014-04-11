package Implementation;

import org.haw.cas.Adapters.RTCToDataMinerAdapter.Interface.Messages.*;
import org.haw.cas.GlobalTypes.MessageInfo.Provenance;
import org.haw.cas.GlobalTypes.MessageInfo.TypeOfNeed;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Nils
 * Date: 27.11.13
 * Time: 15:47
 * To change this template use File | Settings | File Templates.
 */
public class MessageGenerator {


    private long id;

    public InformationMessage generateMessage() {
        List<InfoMessage> infoList = new ArrayList<>();


        infoList.add(new LocationMessage(8.0,50.0, 100));
        infoList.add(new LocationMessage(50.0,10.0, 100));
        infoList.add(new LocationMessage(randomize(10.039916), randomize(53.560705), 100));

        infoList.add(new CrevasseMessage(100));
        id = 0L;
        List<String> topics = new ArrayList<>();
        topics.add("how to mock!");
        topics.add("why so mockig");

        InformationMetadataMessage metaData = new InformationMetadataMessage(topics, new ArrayList<String>());

        return new InformationMessage("" + id++, "test 123 ", "Matthias Mock", infoList, LocalDateTime.now(), metaData, Provenance.Twitter);
    }

    public InformationMessage generateMessageWith2Locations() {
        List<InfoMessage> infoList = new ArrayList<>();

        infoList.add(new LocationMessage(53.0, 10, 70));
        infoList.add(new LocationMessage(10, 52, 72));
        infoList.add(new LocationMessage(10.039916, 53.560705, 70));
        infoList.add(new LocationMessage(7.0, 50, 0));

        infoList.add(new NeedMessage(TypeOfNeed.Food, 100));

        List<String> topics = new ArrayList<>();
        topics.add("how to mock!");
        topics.add("why so mockig");
        InformationMetadataMessage metaData = new InformationMetadataMessage(topics, new ArrayList<String>());

        return new InformationMessage(
                "" + id++,
                "this message is a test message and should appear in the golf von aden",
                "Matthias Mock",
                infoList,
                LocalDateTime.now(),
                metaData,
                Provenance.Twitter
        );
    }

    public InformationMessage generateMessageCrevasse() {
        List<InfoMessage> infoList = new ArrayList<>();

        infoList.add(new LocationMessage(10.039916, 53.560705, 100));


        List<String> topics = new ArrayList<>();
        topics.add("how to mock!");
        topics.add("why so mockig");
        InformationMetadataMessage metaData = new InformationMetadataMessage(topics, new ArrayList<String>());

        return new InformationMessage("" + id++, "test 1 2 3", "Matthias Mock", infoList, LocalDateTime.now(), metaData, Provenance.Twitter);
    }

    public InformationMessage generateMessageWithOneNeed() {
        List<InfoMessage> infoList = new ArrayList<>();

        infoList.add(new NeedMessage(TypeOfNeed.Food, 100));

        List<String> topics = new ArrayList<>();
        topics.add("how to mock!");
        topics.add("why so mockig");
        InformationMetadataMessage metaData = new InformationMetadataMessage(topics, new ArrayList<String>());

        return new InformationMessage("" + id++, "test 1 2 3", "Matthias Mock", infoList, LocalDateTime.now(), metaData, Provenance.Twitter);
    }

    public InformationMessage generateMessageWithEverything() {
        List<InfoMessage> infoList = new ArrayList<>();

        infoList.add(new CrevasseMessage(100));
        infoList.add(new NeedMessage(TypeOfNeed.Water, 100));
        infoList.add(new NeedMessage(TypeOfNeed.Shelter, 100));
        infoList.add(new NeedMessage(TypeOfNeed.MedicalCare, 100));
        infoList.add(new NeedMessage(TypeOfNeed.Helper, 100));
        infoList.add(new NeedMessage(TypeOfNeed.Food, 100));

        infoList.add(new LocationMessage(randomize(10.039916), randomize(53.560705), 100));

        List<String> topics = new ArrayList<>();
        topics.add("how to mock!");
        topics.add("why so mockig");
        InformationMetadataMessage metaData = new InformationMetadataMessage(topics, new ArrayList<String>());

        return new InformationMessage("" + id++, "test 1 2 3", "Matthias Mock", infoList, LocalDateTime.now(), metaData, Provenance.Twitter);

    }

    private double randomize(double input) {
        Random random = new Random();

        double result = input - (1 - random.nextDouble());


        return result;

    }

}
