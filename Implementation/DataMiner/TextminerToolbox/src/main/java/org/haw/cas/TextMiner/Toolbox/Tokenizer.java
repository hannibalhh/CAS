package org.haw.cas.TextMiner.Toolbox;

import java.util.List;
import java.util.function.Predicate;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.StringTokenizer;

/**
 * Builds token-lists from a specified text.
 */
public class Tokenizer {
    /**
     * Builds a list of tokens from the specified text.
     * @param text
     * @return
     */
    public static List<String> getTokenListFromText(String text) {
        return getTokenListFromText(text," .!?,#:;()~'");
    }

    /**
     * Builds a list of tokens from the specified text using the given delimiter.
     * @param text , delimiters
     * @return
     */
    public static List<String> getTokenListFromText(String text, String delimiters) {
        List<String> tokenList = new ArrayList<>();
        if(text != null){
            StringTokenizer tokenizer = new StringTokenizer(text,delimiters);
            while(tokenizer.hasMoreTokens()){
                tokenList.add(tokenizer.nextToken());
            }
        }
        return tokenList;
    }
}