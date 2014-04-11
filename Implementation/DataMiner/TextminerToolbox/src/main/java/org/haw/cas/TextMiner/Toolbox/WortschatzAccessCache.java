package org.haw.cas.TextMiner.Toolbox;

import java.io.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.collections.map.LRUMap;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: Jan
 * Date: 14.11.13
 * Time: 09:16
 * To change this template use File | Settings | File Templates.
 */
public class WortschatzAccessCache {

    private final static String cacheLocation = "." + File.separator + "TextminerToolbox" + File.separator + "Data" + File.separator + "Cache" + File.separator + "wortschatzAccessCache.data";
    private final static int cacheWriteBackOnChangesCount = 10;

    private static final int maxNoEntrys = WortschatzAccess.cacheSize;
    private static Map<String,String[]> cache = null;
    private static Logger logger = LogManager.getLogger(WortschatzAccessCache.class);
    private static int currentChangesSinceLoadFromDisk = 0;


    private static Map<String,String[]> getCache() {
        if (cache == null) {
            cache = new HashMap<String, String[]>();

            try {
                inititializeCacheFromDisk();
                logger.info("Initialized wortschatz cache from disk. The current cache has " + cache.entrySet().size() + " entries.");
            } catch (IOException e) {
                logger.warn("Initializing cache from disk failed. If this is the first start of the application on this machine this is expected. Error message is:\n"
                        + ExceptionUtils.getStackTrace(e));
            } catch (ClassNotFoundException e) {
                logger.warn("Initializing cache from disk failed: " + ExceptionUtils.getStackTrace(e));
            }
        }

        return cache;
    }

    public synchronized static String[] getCachedResult(String word){
        String[] result = null;
            result = getCache().get(word);

        return result;
    }

    public synchronized static void putResultIntoCache(String word, String[] result){
        getCache().put(word, result);

        currentChangesSinceLoadFromDisk++;
        if (currentChangesSinceLoadFromDisk >= cacheWriteBackOnChangesCount) {
            currentChangesSinceLoadFromDisk = 0;
            Thread writerThread = new Thread() {
                public void run() {
                    try {
                        logger.debug("Writing changes to wortschatz cache to disk. The cache currently contains: " + cache.entrySet().size());
                        writeCacheToDisk();
                    } catch (IOException e) {
                        logger.warn("Writing cache to disk failed.:\n" + ExceptionUtils.getStackTrace(e));
                    }
                }
            };
            writerThread.start();
        }
    }

    public static int getCacheSize(){
        return maxNoEntrys;
    }

    private synchronized static void inititializeCacheFromDisk() throws IOException, ClassNotFoundException {
        // Read from disk using FileInputStream
        FileInputStream f_in = new
                FileInputStream(cacheLocation);

        // Read object using ObjectInputStream
        ObjectInputStream obj_in =
                new ObjectInputStream (f_in);

        // Read an object
        Object obj = obj_in.readObject();

        cache = (Map<String, String[]>) obj;
    }

    private synchronized static void writeCacheToDisk() throws IOException {
        File outputFile = new File(cacheLocation);

        if (!outputFile.exists()) {
            outputFile.getParentFile().mkdirs();
        }

        // Write to disk with FileOutputStream
        FileOutputStream f_out = new FileOutputStream(cacheLocation, false);

        // Write object with ObjectOutputStream
        ObjectOutputStream obj_out = new ObjectOutputStream(f_out);

        // Write object out to disk
        obj_out.writeObject(cache);
    }
}
