package MessageAdapter.Implementation.Usecases;

import DSPersistenceManager.Model.Geodata;
import DSPersistenceManager.Model.Message;
import DSPersistenceManager.Services.MessageDAO;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Jonas
 * Date: 21.10.13
 * Time: 22:06
 * To change this template use File | Settings | File Templates.
 */
public class ObtainMessageChunkFromDB {

    private MessageDAO medao;
    private List<Message> messageChunk;
    MessageAdapterThread messageAdapterThread;

    public ObtainMessageChunkFromDB(MessageDAO messageDAO, MessageAdapterThread messageAdapterThread){
        this.medao = messageDAO;
        this.messageAdapterThread = messageAdapterThread;
    }

    public int obtainMessageChunkFromDB() {

        int numberOfObtainedMessages = 0;
        int messageChunkSize = 30;
        Iterable<Message> temp = medao.getMessageChunk(messageChunkSize);
        Iterator<Message> iter = temp.iterator();

        messageChunk = new ArrayList<Message>();

        while (iter.hasNext()){
            Message m = iter.next();
            if(m.getPostTime() != null){
                messageChunk.add(m);
                numberOfObtainedMessages++;
            }
        }


        for(Message m: messageChunk){
            if(m.getAuthor()==null){
                m.setAuthor("");
            }
            if(m.getMessage()==null){
                m.setMessage("");
            }
            if(m.getId()==null){
                m.setId("");
            }
            if(m.getHashtags()==null){
                m.setHashtags(new ArrayList<String>());
            }
            /*if(m.getPictureUrl()==null){
                m.setPictureUrl(new ArrayList<String>());
            }*/
        }

        return numberOfObtainedMessages;
    }

    public Iterable<Message> getMessageChunk() {
        return messageChunk;
    }
}
