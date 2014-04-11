package org.haw.cas.TextMiner.Toolbox;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.haw.cas.TextMiner.Toolbox.Exceptions.GetBaseFormResultsFailedException;

import java.util.*;
import java.util.concurrent.*;

/**
 * Created with IntelliJ IDEA.
 * User: Jan
 * Date: 12.11.13
 * Time: 18:11
 * To change this template use File | Settings | File Templates.
 */
public class WortschatzAccessExecutionHandler {

    private final int maxAccessNo;
    private ExecutorService executorService;
    private Queue<WortschatzAccessWorker> workers;


    WortschatzAccessExecutionHandler(int  maxAccessNo){
        this.maxAccessNo = maxAccessNo;
        this.workers = new ArrayBlockingQueue<WortschatzAccessWorker>(maxAccessNo);

        for (int i = 0; i < maxAccessNo; i++) {
            this.workers.add(new WortschatzAccessWorker(""));
        }

        this.executorService = Executors.newFixedThreadPool(this.maxAccessNo,
                new ThreadFactory() {
                    public Thread newThread(Runnable r) {
                        Thread t = new Thread(r);
                        t.setDaemon(true);
                        return t;
                    }
                });
    }

    /**
     * Gets the word-stem and POS-tag for a specified word.
     * It is highly advised to use getBaseformResults(List<String> words)
     * for higher performance
     * @param word
     * @return The stem and the POS-tag of the word.
     */
    public String[] getBaseformResult(String word) throws GetBaseFormResultsFailedException {
        String [] result = null;
        WortschatzAccessWorker worker;

        //worker = workers.remove();

        //worker.setWord(word);
        Future<String[]> futureResult = executorService.submit(new WortschatzAccessWorker(word));

        try {
            result = futureResult.get();
        } catch (Exception e) {
            throw new GetBaseFormResultsFailedException(e);
        }

        //workers.add(worker);
        return result;
    }

    /**
     * Gets the word-stem and POS-tag for the specified words.
     * @param words
     * @return The stem and POS-tag of all the words.
     */
    public List<String[]> getBaseformResults(List<String> words) throws GetBaseFormResultsFailedException {
        List<String[]> results = new LinkedList<>();
        List<WortschatzAccessWorker> usedWorkers = new LinkedList<>();
        List<Future<String[]>> futureResults = new ArrayList<>();

        // start workers for all words
        for(String word : words){
            WortschatzAccessWorker worker;

            //worker = workers.remove();

            //worker.setWord(word);
            //usedWorkers.add(worker);

            Future<String[]> futureResult = executorService.submit(new WortschatzAccessWorker(word));
            futureResults.add(futureResult);
        }

        //collect results from the workers and put Accesses back into the pool
        for(Future<String[]> futureResult : futureResults){
            try{
                // Wait 10 Seconds for the result.
                results.add(futureResult.get(10000, TimeUnit.MILLISECONDS));
            } catch ( Throwable e){
                throw new GetBaseFormResultsFailedException(e);
            }
        }

        //workers.addAll(usedWorkers);
        return results;

    }

    private class WortschatzAccessWorker implements Callable{

        private String word;
        private WortschatzAccessClient wortschatzAccessClient = null;

        WortschatzAccessWorker(String word){
            if (word == null) {
                throw new IllegalArgumentException("The given word must not be null!");
            }

            this.word = word;

        }

        public void setWord(String word){
            if (word == null) {
                throw new IllegalArgumentException("The given word must not be null!");
            }

            this.word = word;
        }

        public String[] call() throws Exception {
            try {
            String[] result = WortschatzAccessCache.getCachedResult(word);

            if(result == null){
                // Only try to match proper words in the first place.
                if(wortschatzAccessClient == null){
                    wortschatzAccessClient = new WortschatzAccessClient();
                }
                if(word.matches("[a-zA-ZäüöÄÖÜ]+")){
                    try {
                        result = wortschatzAccessClient.getBaseformResult(word);
                        WortschatzAccessCache.putResultIntoCache(word, result);
                    }
                    // English words will crash the wortschatzAccessClient for no apparent reason :(.
                    // Sometimes it will just crash if it feels like it...
                    catch (Exception e)
                    {
                        LogManager.getLogger(this.getClass()).warn("Accessing the Wortschatz database failed. The word was: '" + word + "' The exception was:\n" + ExceptionUtils.getStackTrace(e));

                        // Get a new client if the old one is corrupted, just to be sure...
                        this.wortschatzAccessClient = new WortschatzAccessClient();

                        result = new String[2];
                        result[0] = word;
                        //TODO: maybe change result[1] to appropriate value
                        result[1] = POSTagger.PosTag.UNRECOGNIZED.toString();
                    }
                }
                else
                {
                    result = new String[2];
                    result[0] = word;
                    //TODO: maybe change result[1] to appropriate value
                    result[1] = POSTagger.PosTag.UNRECOGNIZED.toString();
                }
            }

            return result;
            } catch (Exception e) {
                throw e;
            }
        }
    }
}
