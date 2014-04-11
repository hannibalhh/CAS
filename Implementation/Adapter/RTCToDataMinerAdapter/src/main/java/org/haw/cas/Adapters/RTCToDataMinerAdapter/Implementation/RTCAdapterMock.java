package org.haw.cas.Adapters.RTCToDataMinerAdapter.Implementation;

import org.haw.cas.Adapters.RTCToDataMinerAdapter.Interface.Exceptions.ConfigurationException;
import org.haw.cas.Adapters.RTCToDataMinerAdapter.Interface.Exceptions.TechnicalProblemException;
import org.haw.cas.Adapters.RTCToDataMinerAdapter.Interface.IRTCAdapter;
import org.haw.cas.Adapters.RTCToDataMinerAdapter.Interface.Messages.IDataMinerMessage;
import org.haw.cas.Adapters.RTCToDataMinerAdapter.Interface.Messages.InformationMessage;

import java.util.function.Consumer;

/**
 * User: Jason Wilmans
 * Date: 09.12.13
 * Time: 17:00
 * <p/>
 * Returns a mock of the rtc adapter that does nothing except printing messages.
 */
public class RTCAdapterMock implements IRTCAdapter {
    @Override
    public void send(InformationMessage information) throws TechnicalProblemException, ConfigurationException {
        System.out.println("pretending to send an information " + information);
    }

    @Override
    public <M extends IDataMinerMessage> void subscribeForInformationMessage(Class<M> messageType, Consumer<M> messagelistener) {
        System.out.println("registering " + messagelistener + " for messages of type " + messageType + "Psyched! not really...");
    }
}
