package Interfaces;

import DSPersistenceManager.Model.Message;

/**
 * Created with IntelliJ IDEA.
 * User: Raphael
 * Date: 20.10.13
 * Time: 19:40
 * To change this template use File | Settings | File Templates.
 */
public interface IAnalyzer {
    void onNewMessageChunkArrived(Iterable<Message> messageChunk);
    void startAnalyzer();
    void stopAnalyzer();
}
