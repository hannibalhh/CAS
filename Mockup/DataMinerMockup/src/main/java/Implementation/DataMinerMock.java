package Implementation;

import org.haw.cas.Adapters.RTCToDataMinerAdapter.Implementation.RTCAdapterComponent;
import org.haw.cas.Adapters.RTCToDataMinerAdapter.Interface.Exceptions.ConfigurationException;
import org.haw.cas.Adapters.RTCToDataMinerAdapter.Interface.Exceptions.TechnicalProblemException;
import org.haw.cas.Adapters.RTCToDataMinerAdapter.Interface.IRTCAdapter;


/**
 * Created with IntelliJ IDEA.
 * User: Nils
 * Date: 05.11.13
 * Time: 15:31
 */
public class DataMinerMock {

    private MessageGenerator messageGenerator;
    private IRTCAdapter adapter;

    public DataMinerMock(MessageGenerator messageGenerator) {
        this.messageGenerator = messageGenerator;

        adapter = new RTCAdapterComponent(false);
    }

    public static void main(String[] args) {
        DataMinerMock dataMinerMock = new DataMinerMock(new MessageGenerator());
        int messageNumber = 1;

        for (int i = 0; i < messageNumber; i++) {
            dataMinerMock.send();
        }
    }

    public void send() {
        try {
            adapter.send(messageGenerator.generateMessageWith2Locations());
        } catch (TechnicalProblemException e) {
            e.printStackTrace();
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
    }
}
