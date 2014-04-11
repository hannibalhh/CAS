package org.haw.cas.TextMiner.Toolbox;


import de.uni_leipzig.wortschatz.webservice.client.baseform.BaseformClient;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.locks.Lock;

/**
 * Created with IntelliJ IDEA.
 * User: Jan
 * Date: 10.11.13
 * Time: 19:45
 * To change this template use File | Settings | File Templates.
 */
public class WortschatzAccessClient {

    private static long maxID = 0L;
    private final long ID;
    private BaseformClient baseformClient = null;
    private String[][] strResult;
    private Logger logger = LogManager.getLogger(WortschatzAccessClient.class);

    WortschatzAccessClient() {
        this.ID = maxID++;

        try {
            baseformClient = new BaseformClient();
        } catch (Exception e) {
            throw new RuntimeException("FATAL: BaseformeClient creation failed");
            //baseformClient = null;
        }
        if (baseformClient == null) return;

        baseformClient.setUsername("anonymous");
        baseformClient.setPassword("anonymous");
        baseformClient.setCorpus("de");

        int intCountIndex = baseformClient.getDBFields().length;
        strResult = new String[0][intCountIndex];
    }

    public synchronized String[] getBaseformResult(String word){
        String[] resu = new String[2];

        if (word.matches("[a-zA-ZäöüÄÖÜ]+")) {
            baseformClient.addParameter("Wort", word);

            try {
                baseformClient.execute();
            } catch (Exception e) {
                logger.warn("Baseform Client execution failed: " + ExceptionUtils.getStackTrace(e));
            }

            strResult = baseformClient.getResult();
            if (strResult != null && strResult.length > 0 && strResult[0] != null && strResult[0].length > 1) {
                resu[0] = strResult[0][0];
                resu[1] = strResult[0][1];

            } else {
                resu[0] = word;
                resu[1] = "";

            }
        } else {
            resu[0] = word;
            resu[1] = "";
        }

        return resu;
    }

    public long getID() {
        return ID;
    }

}
