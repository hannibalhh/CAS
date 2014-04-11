package org.haw.cas.PlaceOfResidenceAnalyzer.Implementation.Usecases;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.haw.cas.TextMiner.Toolbox.WortschatzAccess;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Raphael
 * Date: 11.12.13
 * Time: 15:57
 * To change this template use File | Settings | File Templates.
 */
public class GoogleMapsCache {
    private final static String cacheLocation = "." + File.separator + "PlaceOfResidenceAnalyzer" + File.separator + "Data" + File.separator + "Cache" + File.separator + "mapsAccessCache.data";
    private final static int cacheWriteBackOnChangesCount = 10;

    private static final int maxNoEntrys = WortschatzAccess.cacheSize;
    private static Map<String,GoogleMapsAchieveGeoData.GoogleMapsResponse> cache = null;
    private static Logger logger = LogManager.getLogger(GoogleMapsCache.class);
    private static int currentChangesSinceLoadFromDisk = 0;


    private static Map<String,GoogleMapsAchieveGeoData.GoogleMapsResponse> getCache() {
        if (cache == null) {
            cache = new HashMap<String, GoogleMapsAchieveGeoData.GoogleMapsResponse>();
//            try {
//                inititializeCacheFromDisk();
//                logger.info("Initialized maps cache from disk. The current cache has " + cache.entrySet().size() + " entries.");
//            } catch (IOException e) {
//                logger.warn("Initializing maps cache from disk failed. If this is the first start of the application on this machine this is expected. Error message is:\n"
//                        + ExceptionUtils.getStackTrace(e));
//            } catch (ClassNotFoundException e) {
//                logger.warn("Initializing maps cache from disk failed: " + ExceptionUtils.getStackTrace(e));
//            }
        }

        return cache;
    }

    public synchronized static GoogleMapsAchieveGeoData.GoogleMapsResponse getCachedResult(String word){
        GoogleMapsAchieveGeoData.GoogleMapsResponse result = null;
        result = getCache().get(word);

        return result;
    }

    public synchronized static void putResultIntoCache(String word, GoogleMapsAchieveGeoData.GoogleMapsResponse result){
        getCache().put(word, result);

//        currentChangesSinceLoadFromDisk++;
//        if (currentChangesSinceLoadFromDisk >= cacheWriteBackOnChangesCount) {
//            currentChangesSinceLoadFromDisk = 0;
//            Thread writerThread = new Thread() {
//                public void run() {
//                    try {
//                        logger.debug("Writing changes to maps cache to disk. The cache currently contains: " + cache.entrySet().size());
//                        writeCacheToDisk();
//                    } catch (IOException e) {
//                        logger.warn("Writing maps cache to disk failed.:\n" + ExceptionUtils.getStackTrace(e));
//                    }
//                }
//            };
//            writerThread.start();
//        }
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

        cache = (Map<String, GoogleMapsAchieveGeoData.GoogleMapsResponse>) obj;
    }

    private synchronized static void writeCacheToDisk() throws IOException {

        File outputFile = new File(cacheLocation);

        if (!outputFile.exists()) {
            outputFile.getParentFile().mkdirs();
            logger.info("Maps Cache directorys created");
        }

        // Write to disk with FileOutputStream
        FileOutputStream f_out = new FileOutputStream(cacheLocation, false);

        // Write object with ObjectOutputStream
        ObjectOutputStream obj_out = new ObjectOutputStream(f_out);

        // Write object out to disk
        obj_out.writeObject(cache);
        logger.info("Maps Cache written to Disk at: " + cacheLocation);

        obj_out.flush();
        obj_out.close();
    }
}
