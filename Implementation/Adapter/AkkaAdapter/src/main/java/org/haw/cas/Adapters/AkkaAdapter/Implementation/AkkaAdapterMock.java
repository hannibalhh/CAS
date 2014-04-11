package org.haw.cas.Adapters.AkkaAdapter.Implementation;

import org.haw.cas.Adapters.AkkaAdapter.Interface.IAkkaAdapter;
import org.haw.cas.Adapters.AkkaAdapter.Interface.Messages.IAkkaMessage;

import java.util.function.Consumer;

/**
 * User: Jason Wilmans
 * Date: 09.12.13
 * Time: 16:58
 * <p/>
 * Returns a mock of the akka adapter that does nothing except printing messages.
 */
public class AkkaAdapterMock implements IAkkaAdapter {
    @Override
    public void send(IAkkaMessage message) throws org.haw.cas.Adapters.AkkaAdapter.Interface.Exceptions.TechnicalProblemException, org.haw.cas.Adapters.AkkaAdapter.Interface.Exceptions.ConfigurationException {
        System.out.println("pretending to send an akka message " + message);
    }

    @Override
    public <M extends IAkkaMessage> void subscribeForMessage(Class<M> messageType, Consumer<M> messageListener) {
        System.out.println("registering " + messageListener + " for messages of type " + messageType + "Psyched! not really...");
    }
}
