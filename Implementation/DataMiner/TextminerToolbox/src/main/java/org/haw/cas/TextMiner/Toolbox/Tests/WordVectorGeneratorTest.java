package org.haw.cas.TextMiner.Toolbox.Tests;

import junit.framework.Assert;
import org.haw.cas.TextMiner.Toolbox.WordVectorGenerator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: N3trunner
 * Date: 11.11.13
 * Time: 12:52
 * To change this template use File | Settings | File Templates.
 */
public class WordVectorGeneratorTest {
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
        Map<String, Map<String, Integer>> inputsAndExpectedResults = new HashMap<>();

        // A simple case.
        inputsAndExpectedResults.put("Ich habe einen Deichbruch an der Elbe erlebt.", new HashMap<String, Integer>() {
            {
                put("Ich", 1);
                put("Deichbruch", 1);
                put("Elbe", 1);
                put("erlebt", 1);
            }
        });

        // A more complex case with an emoticon.
        inputsAndExpectedResults.put("Am Berliner Tor gibt es eine Überflutung. Ich bin gerade am Berliner Tor :(.", new HashMap<String, Integer>() {
            {
                put("Berliner", 2);
                put("Tor", 2);
                put("Überflutung", 1);
                put("Ich", 1);
                put("geben", 1);
                put("gerade", 1);
                put(":(", 1);
            }
        });


        for(Map.Entry<String, Map<String, Integer>> sample : inputsAndExpectedResults.entrySet()) {
            Assert.assertEquals(sample.getValue(), WordVectorGenerator.getWordVectorFromText(sample.getKey()));
        }
    }
}
