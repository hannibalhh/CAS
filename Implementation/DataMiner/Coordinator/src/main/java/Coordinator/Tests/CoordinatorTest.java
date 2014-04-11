package Coordinator.Tests;

import Coordinator.Component.CoordinatorComponent;
import Coordinator.Interface.ICoordinator;
import Coordinator.Interface.abstractClasses.AbstractAnalyzer;
import DSPersistenceManager.Model.Message;
import DataTypes.DataTypeInterfaces.IInfo;
import Interfaces.IAnalyzer;
import MessageAdapter.Interface.IMessageAdapter;
import org.haw.cas.Adapters.RTCToDataMinerAdapter.Interface.Exceptions.ConfigurationException;
import org.haw.cas.Adapters.RTCToDataMinerAdapter.Interface.Exceptions.TechnicalProblemException;
import org.haw.cas.Adapters.RTCToDataMinerAdapter.Interface.IRTCAdapter;
import org.haw.cas.Adapters.RTCToDataMinerAdapter.Interface.Messages.IDataMinerMessage;
import org.haw.cas.Adapters.RTCToDataMinerAdapter.Interface.Messages.InformationMessage;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.*;
import java.util.function.Consumer;

/**
 * Created with IntelliJ IDEA.
 * User: N3trunner
 * Date: 03.12.13
 * Time: 17:37
 * To change this template use File | Settings | File Templates.
 */
public class CoordinatorTest {
    private CoordinatorComponent coordinator;
    private IMessageAdapter messageAdapter;
    private List<IAnalyzer> analyzers;
    private static final int numberOfAnalyzers = 10;

    @Before
    public void setup() {
        messageAdapter = new MessageAdapterMock();
        coordinator = new CoordinatorComponent(messageAdapter, new RTCAdapterMock());
        analyzers = new ArrayList<>();

        for(int i = 0; i < numberOfAnalyzers; i++) {
            IAnalyzer analyzer = new MockAnalyzer(messageAdapter, coordinator);
            analyzer.startAnalyzer();
            analyzers.add(analyzer);
        }
    }

    @Test
    public void coordinationTest() {
        coordinator.startCoordinating();
        messageAdapter.obtainNewMessages();

        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class MockAnalyzer extends AbstractAnalyzer {

        private Random rand = new Random();

        protected MockAnalyzer(IMessageAdapter messageAdapter, ICoordinator coordinator) {
            super(messageAdapter, coordinator);
        }

        @Override
        protected Map<Message, List<IInfo>> processMessages(Iterable<Message> messages) throws Throwable {
            Thread.sleep(rand.nextInt(1000));

            Map<Message, List<IInfo>> result = new HashMap<>();

            messages.forEach(message -> result.put(message, new ArrayList<IInfo>()));

            return result;
        }
    }

    private class MessageAdapterMock implements IMessageAdapter {

        private Random random = new Random();

        private List<Consumer<Iterable<Message>>> listeners = new ArrayList<>();

        @Override
        public void obtainNewMessages() {
            try {
                Thread.sleep(random.nextInt(3000));
            } catch (InterruptedException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }

            this.notifyListenersAboutNewMessageChunk(new ArrayList<Message>());
        }

        @Override
        public void stopObtainingNewMessages() {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void addNewMessageChunkArrivedListener(Consumer<Iterable<Message>> c) {
            listeners.add(c);
        }

        @Override
        public void notifyListenersAboutNewMessageChunk(Iterable<Message> messageChunk) {
            for (Consumer<Iterable<Message>> listener : listeners) {
                listener.accept(messageChunk);
            }
        }
    }

    private class RTCAdapterMock implements IRTCAdapter {
        Random random = new Random();

        @Override
        public void send(InformationMessage information) throws TechnicalProblemException, ConfigurationException {
            try {
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }

        @Override
        public <M extends IDataMinerMessage> void subscribeForInformationMessage(Class<M> messageType, Consumer<M> messagelistener) {
            //To change body of implemented methods use File | Settings | File Templates.
        }
    }
}
