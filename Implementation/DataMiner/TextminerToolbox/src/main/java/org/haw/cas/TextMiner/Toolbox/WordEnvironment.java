package org.haw.cas.TextMiner.Toolbox;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: sebastian
 * Date: 12.11.13
 * Time: 11:41
 * To change this template use File | Settings | File Templates.
 */
public class WordEnvironment {
    public static Set<String> getSurroundingWords(int n, List<String> keywords, List<String> textInTokens){
        Set<String> result = new HashSet<>();
        for(String currentKeyword : keywords){
            result.addAll(getSurroundingWords(n,currentKeyword,textInTokens));
        }
        return result;
    }

    public static Set<String> getSurroundingWords(int n, String keyword, List<String> textInTokens){
        Set<String> result = new HashSet<>();
        int indexOfKeyword = textInTokens.indexOf(keyword);
        if(indexOfKeyword > -1){
            int leftBorder = indexOfKeyword - n < 0 ? 0 : indexOfKeyword -n;
            int rightBorder = indexOfKeyword + n >= textInTokens.size() ? textInTokens.size()-1 : indexOfKeyword + n;

            for(int i = leftBorder; i <= rightBorder; i++){
                result.add(textInTokens.get(i));
            }
        }
        return result;
    }
}
