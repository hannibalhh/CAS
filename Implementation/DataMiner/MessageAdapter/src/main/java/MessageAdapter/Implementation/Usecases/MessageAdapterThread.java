package MessageAdapter.Implementation.Usecases;

import DSPersistenceManager.Model.Message;
import MessageAdapter.Implementation.Component.MessageAdapterComponent;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * Created with IntelliJ IDEA.
 * User: Jonas
 * Date: 21.10.13
 * Time: 22:53
 * To change this template use File | Settings | File Templates.
 */
public class MessageAdapterThread implements Runnable {
    private boolean running = true;
    private Semaphore sleep = new Semaphore(0);
    MessageAdapterComponent messageAdapterComponent;
    private Iterable<Message> messageChunk;
    private Logger logger = LogManager.getLogger(MessageAdapterThread.class);
    private Random random = new Random();

    public MessageAdapterThread(MessageAdapterComponent mAC){
        this.messageAdapterComponent = mAC;
    }

    @Override
    public void run() {
        while(running){
            try {
                if (messageAdapterComponent.obtainMessageChunkFromDB() > 0) {
                    // The message adapter has obtained messages from the db. Inform the listeners.
                    messageChunk = messageAdapterComponent.getMessageChunk();
                    messageAdapterComponent.notifyListenersAboutNewMessageChunk(messageChunk);
                    logger.info("The MessageAdapter has notified all listeners about a newly arrived message chunk and will now sleep.");
                    sleep.acquire();
                } else {
                    // There are currently no messages in the db. We will sleep for a randomized time and try again later.
                    long secondsToSleep = random.nextInt(10000);
                    logger.info("There are no new messages in the DB. The MessageAdapter will sleep for "
                            + (secondsToSleep/1000)
                            + " seconds (This is randomly generated sleep time) before trying to obtain a message chunk again.");
                    Thread.sleep(secondsToSleep);
                }
            } catch (InterruptedException e) {
                logger.error("Error while fetching messages: " + ExceptionUtils.getStackTrace(e));
            }
        }
    }

    public void wakeUpThread(){
        if (this.sleep.availablePermits() == 0) {
            this.sleep.release();
        } else {
            logger.warn("Message adapter Thread already running.");
        }
    }

    public void stopThread() {
        this.running = false;
        this.wakeUpThread();
    }
}
