package org.haw.cas.TextMiner.Toolbox;

import org.haw.cas.TextMiner.Toolbox.Exceptions.GetWordStemsFailedException;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Generates a word-vector from text.
 */
public class WordVectorGenerator {
    /**
     * Gets a word-vector from the specified text with all stopwords removed. Also all words will be in their stemmed form.
     * @param text The text from which the word vector will be extracted.
     * @return A map mapping the stem of the word to the number of occurences in the specified text.
     */
    public static Map<String, Integer> getWordVectorFromText(String text) throws GetWordStemsFailedException {
        // Tokenize the text first
        List<String> tokens = Tokenizer.getTokenListFromText(text);

        // Get the stems for all words
        List<Map.Entry<String,String>> wordStems = WordStemmer.getWordStemsForWordList(tokens);

        // Get the pos-tags for the stemmed words
        List<Map.Entry<String,POSTagger.PosTag>> wordTags = POSTagger.getPosTagsForWordList(wordStems.parallelStream().map(x -> x.getValue()).collect(Collectors.toList()));

        // Filter all stemmed words that are stopwords. Only the non stopword stemmed words remain.
        List<String> nonStopWordStemmedWords = wordTags.parallelStream().filter(x -> x.getValue() != POSTagger.PosTag.STOPWORD).map(x -> x.getKey()).collect(Collectors.toList());

        // Generate the word vector by counting the occurrences of the words.
        Map<String, Integer> wordVector = new ConcurrentHashMap<>();
        nonStopWordStemmedWords.parallelStream().forEach(
            word -> {
                if (wordVector.containsKey(word)) {
                    wordVector.put(word, wordVector.get(word) + 1);
                } else {
                    wordVector.put(word, 1);
                }
            });
        return wordVector;
    }
}
