package org.haw.cas.TextMiner.Toolbox.Tests;

import org.haw.cas.TextMiner.Toolbox.Tokenizer;
import org.haw.cas.TextMiner.Toolbox.WortschatzAccess;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.AbstractMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class WortschatzAccessTimeTest {
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    /**
     * Tests if the correct word vectors will be generated from specific inputs.
     * @throws Exception
     */
    @Test
    public void testGetWordVectorFromText() throws Exception {
        List<Map.Entry<String,Integer>> inputsAndExpectedResults = new LinkedList<>();

        // A simple case.
        inputsAndExpectedResults.add((new AbstractMap.SimpleEntry<String, Integer>("Ich habe einen Deichbruch an der Elbe erlebt.", 2000)));
        // A more complex case with an emoticon.
        inputsAndExpectedResults.add((new AbstractMap.SimpleEntry<String, Integer>("Am Berliner Tor gibt es eine Überflutung. Ich bin gerade am Berliner Tor :(.", 2000)));
        // A more complex case with numbers.
        inputsAndExpectedResults.add((new AbstractMap.SimpleEntry<String, Integer>("Am Berliner Tor 13 gibt es eine Überflutung. Ich bin gerade am Berliner Tor 07 :(.", 2000)));

        for(int i = 0; i < 10; i++){
            // A simple case.
            inputsAndExpectedResults.add((new AbstractMap.SimpleEntry<String, Integer>("Ich habe einen Deichbruch an der Elbe erlebt.", 100)));
            // A more complex case with an emoticon.
            inputsAndExpectedResults.add((new AbstractMap.SimpleEntry<String, Integer>("Am Berliner Tor gibt es eine Überflutung. Ich bin gerade am Berliner Tor :(.", 100)));
            // A more complex case with numbers.
            inputsAndExpectedResults.add((new AbstractMap.SimpleEntry<String, Integer>("Am Berliner Tor 13 gibt es eine Überflutung. Ich bin gerade am Berliner Tor 07 :(.", 100)));
            // A more mix of earlier messages
            inputsAndExpectedResults.add((new AbstractMap.SimpleEntry<String, Integer>("Ich habe einen Deichbruch es gibt eine Überflutung. Ich bin gerade an der Elbe :(.", 100)));
        }


        for(Map.Entry<String, Integer> sample : inputsAndExpectedResults) {
            long t1;
            long t2;
            boolean resu = false;

            t1 = System.currentTimeMillis();
            WortschatzAccess.getBaseformResults(Tokenizer.getTokenListFromText(sample.getKey()));
            t2 = System.currentTimeMillis();
            System.out.println(String.format("%d ms for: \"%s\" ",(t2-t1),sample.getKey()));

            assert (t2-t1) < sample.getValue() : String.format("too slow (%d > %d) on: \"%s\"",(t2-t1),sample.getValue(),sample.getKey());
        }
    }
}
