package org.haw.cas.TextMiner.Toolbox;


import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.*;

/**
 * Used to analyze text for their POS-tags.
 */
public class POSTagger {

    private static HashSet<String> stopwords;
    private static HashSet<String> prepositions;
    private static Logger logger = LogManager.getLogger(POSTagger.class);

    /**
     * Gets a list of all known stopwords names.
     *
     * @return
     */
    private static HashSet<String> getStopwords() {
        if (stopwords == null) {
            stopwords = new HashSet<String>();
            stopwords.addAll(FileUtils.getEntriesFromTextFilesInPath("." + File.separator + "TextminerToolbox" + File.separator + "Data" + File.separator + "stopwords" + File.separator));
        }
        return stopwords;
    }

    /**
     * Gets a list of all known stopwords names.
     *
     * @return
     */
    private static HashSet<String> getPrepositions() {
        if (prepositions == null) {
            prepositions = new HashSet<String>();
            prepositions.addAll(FileUtils.getEntriesFromTextFilesInPath("." + File.separator + "TextminerToolbox" + File.separator + "Data" + File.separator + "prepositions" + File.separator));
        }
        return prepositions;
    }

    /**
     * Gets the POS-Tag for a WortschatzAccessresult.
     *
     * @param result
     * @return
     */
    private static PosTag getPosTagForResult(String[] result) {
        if (result == null || result.length < 2) {
            return PosTag.UNRECOGNIZED;
        }
        PosTag posTag = null;
        switch (result[1]) {
            case "N":
                posTag = PosTag.NOMEN;
                break;
            case "V":
                posTag = PosTag.VERB;
                break;
            case "A":
                posTag = PosTag.ADJEKTIV;
                break;
            case "-":
                posTag = PosTag.STOPWORD;
                break;
            default:
                posTag = PosTag.UNRECOGNIZED;
                break;
        }
        return posTag;

    }

    /**
     * Gets the POS-Tags for all recognized words in a string.
     *
     * @param text
     * @return An ordered list mapping all found words to POS-tags.
     */
    public static List<Map.Entry<String, PosTag>> getPosTagsFromText(String text) {
        List<String> tokens = Tokenizer.getTokenListFromText(text);
        return getPosTagsForWordList(tokens);
    }

    /**
     * Gets the POS-Tags for all words from a given word-list.
     *
     * @param words
     * @return An ordered list mapping all words to POS-tags.
     */
    public static List<Map.Entry<String, PosTag>> getPosTagsForWordList(List<String> words) {
        List<Map.Entry<String, PosTag>> posTags = new ArrayList<>();

        // Optimization: Determines if the posTags list has been initialized from the pos tags online service.
        // The service will only be queried, if one word cannot be tagged locally.
        boolean posTaggerOnlineServiceQueried = false;
        // If the posTagger online service isn't available for whatever reason, only the local word lists will be used.
        boolean posTaggerOnlineServiceAvailable = true;
        List<String[]> baseformResults = null;

        for (int i = 0; i < words.size(); i++) {
            String word = words.get(i);

            if (isPreposition(word)) {
                posTags.add(new AbstractMap.SimpleEntry<String, PosTag>(word, PosTag.PRAEPOSITION));
            } else if (isStopWord(word)) {
                posTags.add(new AbstractMap.SimpleEntry<String, PosTag>(word, PosTag.STOPWORD));
            } else {
                // We cannot tag the word locally. We need to use the pos tagging online service.
                PosTag posTag = PosTag.UNRECOGNIZED;

                // If the posTagger online service is available, we will use it to tag this word.
                if (posTaggerOnlineServiceAvailable) {
                    if (!posTaggerOnlineServiceQueried) {
                        // The posTagger online service has not been queried yet. We will now initialize the local pos tags list.
                        posTaggerOnlineServiceQueried = true;

                        try {
                            baseformResults = WortschatzAccess.getBaseformResults(words);

                            if (words.size() != baseformResults.size()) {
                                throw new RuntimeException(String.format("%s %d", "Fatal error in PosTagger: The WortschaftzAccess client did not return results for all words.", words.size() - baseformResults.size()));
                            }
                        } catch (Throwable e) {
                            // An error occured while we tried to access the pos tagger online service. We will not use it further in this run.
                            logger.warn("The PosTagging online service is not available. Pos tagging will continue to work, but at drastically reduced capabilities.\nError message was: " + ExceptionUtils.getStackTrace(e));
                            posTaggerOnlineServiceAvailable = false;
                        }
                    }

                    if (posTaggerOnlineServiceAvailable) {
                        posTag = getPosTagForResult(baseformResults.get(i));
                    }
                }

                posTags.add(new AbstractMap.SimpleEntry<String, PosTag>(word, posTag));
            }
        }
        return posTags;
    }

    /**
     * Returns true if the specified word is a stopword, otherwise false.
     *
     * @param word The word that will be checked.
     * @return True if the given word is a stopword, otherwise false.
     */
    private static boolean isStopWord(String word) {
        return getStopwords().contains(word.toLowerCase());
    }



    /**
     * Returns true if the specified word is a preposition, otherwise false.
     *
     * @param word
     * @return
     */
    private static boolean isPreposition(String word) {
        return getPrepositions().contains(word.toLowerCase());
    }

    /**
     * All recognized POS-tags.
     */
    public enum PosTag {

        UNRECOGNIZED,
        STOPWORD,
        NOMEN,
        VERB,
        ADJEKTIV,
        ADVERB,
        PRONOMEN,
        PRAEPOSITION,
        KONJUNKTION,
        NUMERALE,
        ARTIKEL,
        INTERJEKTION

    }

}
