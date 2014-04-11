package org.haw.cas.TextMiner.Toolbox.Tests;

import org.haw.cas.TextMiner.Toolbox.WordEnvironment;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: sebastian
 * Date: 12.11.13
 * Time: 11:45
 * To change this template use File | Settings | File Templates.
 */
public class WordEnvironmentTest {
    @Test
    public void testGetSurroundingWords_singleKeyword(){
        List<String> textinTokens = Arrays.asList("Der", "Deich", "bricht", "rette", "sich", "wer", "kann", "alles", "unter", "wasser");
        Set<String> expected = new HashSet<>();
        expected.add("Der");
        expected.add("Deich");
        expected.add("bricht");
        expected.add("rette");
        Set<String> result = WordEnvironment.getSurroundingWords(2, "Deich", textinTokens);
        assertEquals(expected,result);
    }

    @Test
    public void testGetSurroundingWords_singleKeyword2(){
        List<String> textinTokens = Arrays.asList("Der","Deich","bricht","rette","sich","wer","kann","alles","unter","wasser");
        Set<String> expected = new HashSet<>();
        expected.add("wasser");
        expected.add("unter");
        expected.add("alles");
        Set<String> result = WordEnvironment.getSurroundingWords(2, "wasser", textinTokens);
        assertEquals(expected,result);
    }

    @Test
    public void testGetSurroundingWords_multipleKeywords(){
        List<String> textinTokens = Arrays.asList("Der","Deich","bricht","rette","sich","wer","kann","alles","unter","wasser");
        Set<String> expected = new HashSet<>();
        expected.add("wasser");
        expected.add("unter");
        expected.add("alles");
        expected.add("Der");
        expected.add("Deich");
        expected.add("bricht");
        expected.add("rette");
        expected.add("sich");
        Set<String> result = WordEnvironment.getSurroundingWords(2, Arrays.asList("bricht", "wasser"), textinTokens);
        assertEquals(expected,result);
    }

}
