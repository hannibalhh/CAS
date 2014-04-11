package org.haw.cas.TextMiner.Toolbox;

import org.haw.cas.TextMiner.Toolbox.Exceptions.GetWordStemsFailedException;

import java.util.AbstractMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Analyzes words for their stems.
 */
public class WordStemmer {

    /**
     * Gets the word-stems for all recognized words in a string.
     * @param text
     * @return A list mapping all found words to their stems.
     */
    public static List<Map.Entry<String, String>> getWordStemsFromText(String text) throws GetWordStemsFailedException {
        return getWordStemsForWordList(Tokenizer.getTokenListFromText(text));
    }

    /**
     * Gets the word-stems for all words from a given word-list.
     * @param words
     * @return A list mapping all found words to their stems.
     */
    public static List<Map.Entry<String, String>> getWordStemsForWordList(List<String> words) throws GetWordStemsFailedException {
        List<Map.Entry<String, String>> stems = new LinkedList<Map.Entry<String, String>>();

        try {
            List<String[]> baseformResults = WortschatzAccess.getBaseformResults(words);

            if(words.size() != baseformResults.size()){
                throw new RuntimeException(String.format("%s %d","Fatal Wordschatz access error: lists have different sizes @ Wordstemmer",words.size() - baseformResults.size()));
            }
            for(int i = 0; i < words.size(); i++){
                if(baseformResults.get(i) != null){
                    stems.add(new AbstractMap.SimpleEntry<String, String>(words.get(i), baseformResults.get(i)[0]));
                } else{
                    stems.add(new AbstractMap.SimpleEntry<String, String>(words.get(i), words.get(i)));
                }
            }
        } catch (Exception e) {
            throw new GetWordStemsFailedException(e);
        }
        return stems;
    }
}
