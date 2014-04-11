package MessageAdapter.Interface;

import DSPersistenceManager.Model.Message;

import java.util.List;
import java.util.function.Consumer;

/**
 * User: Jason Wilmans
 * Date: 14.10.13
 * Time: 19:00
 */
public interface IMessageAdapter {

    public void obtainNewMessages();

    public void stopObtainingNewMessages();

    public void addNewMessageChunkArrivedListener(Consumer<Iterable<Message>> c);

    public void notifyListenersAboutNewMessageChunk(Iterable<Message> messageChunk);
}
