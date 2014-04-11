package MessageAdapter.Implementation.Component;

import java.util.function.Consumer;

import DSPersistenceManager.Model.Message;
import DSPersistenceManager.Services.MessageDAO;
import MessageAdapter.Implementation.Usecases.ObtainMessageChunkFromDB;
import MessageAdapter.Implementation.Usecases.MessageAdapterThread;
import MessageAdapter.Implementation.Usecases.SubscriptionOrganization;
import MessageAdapter.Interface.IMessageAdapter;

/**
 * Created with IntelliJ IDEA.
 * User: Raphael
 * Date: 17.10.13
 * Time: 21:06
 * To change this template use File | Settings | File Templates.
 */

public class MessageAdapterComponent implements IMessageAdapter {

    private boolean messageThreadRunning = false;
    private Thread messageAcquisitionThread;
    MessageDAO messageDAO;

    //util:
    private ObtainMessageChunkFromDB obtainMessageChunkFromDB;
    private SubscriptionOrganization subscriptionOrganization;
    private MessageAdapterThread messageAdapterThread;

    //Constructor:
    public MessageAdapterComponent(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
        this.obtainMessageChunkFromDB = new ObtainMessageChunkFromDB(messageDAO, this.messageAdapterThread);
        this.subscriptionOrganization = new SubscriptionOrganization();
        this.messageAdapterThread = new MessageAdapterThread(this);
        this.messageAcquisitionThread = new Thread(this.messageAdapterThread);
    }

    public int obtainMessageChunkFromDB() {
        return obtainMessageChunkFromDB.obtainMessageChunkFromDB();
    }

    @Override
    public void obtainNewMessages() {
        if (messageThreadRunning) {
            messageAdapterThread.wakeUpThread();
        } else {
            messageThreadRunning = true;
            this.messageAcquisitionThread.start();
        }
    }

    @Override
    public void stopObtainingNewMessages() {
        messageAdapterThread.stopThread();
    }

    @Override
    public void addNewMessageChunkArrivedListener(Consumer<Iterable<Message>> c) {
        subscriptionOrganization.addNewMessageChunkArrivedListener(c);
    }

    @Override
    public void notifyListenersAboutNewMessageChunk(Iterable<Message> messageChunk) {
        this.subscriptionOrganization.notifyListenersAboutNewMessageChunk(messageChunk);
    }

    public Iterable<Message> getMessageChunk(){
        return obtainMessageChunkFromDB.getMessageChunk();
    }
}
