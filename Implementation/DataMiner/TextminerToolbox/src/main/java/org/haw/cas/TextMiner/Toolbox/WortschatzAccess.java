package org.haw.cas.TextMiner.Toolbox;

import org.haw.cas.TextMiner.Toolbox.Exceptions.GetBaseFormResultsFailedException;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Jan
 * Date: 14.11.13
 * Time: 09:53
 * To change this template use File | Settings | File Templates.
 */
public class WortschatzAccess {

    public static final int cacheSize = 100000;
    public static final int noAccessClients = 30;

    private static WortschatzAccessExecutionHandler wortschatzAccessExecutionHandler = new WortschatzAccessExecutionHandler(noAccessClients);

    /**
     * Gets the word-stem and POS-tag for the specified words.
     * @param words
     * @return The stem and POS-tag of all the words.
     */
    public static List<String[]> getBaseformResults(List<String> words) throws GetBaseFormResultsFailedException {
        return wortschatzAccessExecutionHandler.getBaseformResults(words);
    }

    /**
     * Gets the word-stem and POS-tag for a specified word.
     * It is highly advised to use getBaseformResults(List<String> words)
     * for higher performance
     * @param word
     * @return The stem and the POS-tag of the word.
     */
    public static String[] getBaseformResult(String word) throws GetBaseFormResultsFailedException {
        return wortschatzAccessExecutionHandler.getBaseformResult(word);
    }


}
