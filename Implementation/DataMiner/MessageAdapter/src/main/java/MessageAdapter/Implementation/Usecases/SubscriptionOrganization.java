package MessageAdapter.Implementation.Usecases;

import DSPersistenceManager.Model.Message;

import java.util.ArrayList;
import java.util.function.Consumer;

/**
 * Created with IntelliJ IDEA.
 * User: Jonas
 * Date: 21.10.13
 * Time: 22:17
 * To change this template use File | Settings | File Templates.
 */
public class SubscriptionOrganization {

    protected ArrayList<Consumer<Iterable<Message>>> listeners;

    public SubscriptionOrganization(){
        this.listeners = new ArrayList<Consumer<Iterable<Message>>>();
    }

    public void addNewMessageChunkArrivedListener(Consumer<Iterable<Message>> c) {
        this.listeners.add(c);
    }

    public ArrayList<Consumer<Iterable<Message>>> getListeners(){
        return this.listeners;
    }

    public void notifyListenersAboutNewMessageChunk(Iterable<Message> messageChunk) {
        for (Consumer<Iterable<Message>> listener : this.listeners) {
            listener.accept(messageChunk);
        }
    }

}
