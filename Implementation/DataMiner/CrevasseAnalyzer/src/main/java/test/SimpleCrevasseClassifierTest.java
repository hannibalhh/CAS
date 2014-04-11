package test;

import Implementation.classifiers.SimpleCrevasseClassifier;
import org.haw.cas.TextMiner.Toolbox.MessagePreprocessing;
import org.haw.cas.TextMiner.Toolbox.Tokenizer;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: sebastian
 * Date: 31.10.13
 * Time: 13:29
 * To change this template use File | Settings | File Templates.
 */
public class SimpleCrevasseClassifierTest {

    @Test
    public void testTokenizing1(){
        SimpleCrevasseClassifier classifier = new SimpleCrevasseClassifier();

        String test1 = "Deichbruch in Hamburg. Alle Häuser stehen unter Wasser. :-(".toLowerCase();
        List<String> expectedTest1 = Arrays.asList("deichbruch", "in", "hamburg", "alle", "häuser", "stehen", "unter", "wasser");

        List<String> computed = Tokenizer.getTokenListFromText(MessagePreprocessing.removePunctation(test1));
        assertEquals(computed, expectedTest1);
    }

    @Test
    public void testTokenizingGermanLetters(){
        SimpleCrevasseClassifier classifier = new SimpleCrevasseClassifier();
        String test1 = "Füße ändern Über Öder öder  ".toLowerCase();
        List<String> expectedTest1 = Arrays.asList("füße", "ändern", "über", "öder", "öder");
        List<String> computed = Tokenizer.getTokenListFromText(MessagePreprocessing.removePunctation(test1));
        assertEquals(computed, expectedTest1);
    }

    @Test
    public void testContainsRelevantFormOfBrechen(){
        SimpleCrevasseClassifier classifier = new SimpleCrevasseClassifier();

        List<String> test1 = Tokenizer.getTokenListFromText(MessagePreprocessing.removePunctation("Der Deich bricht!!!!".toLowerCase()));
        assertEquals(true, classifier.containsRelevantFormOfBrechen(test1));

        List<String> test2 = Tokenizer.getTokenListFromText(MessagePreprocessing.removePunctation("Ich stehe auf dem Deich!!!!".toLowerCase()));
        assertEquals(false,classifier.containsRelevantFormOfBrechen(test2));
    }

    @Test
    public void testContainsDeich(){
        SimpleCrevasseClassifier classifier = new SimpleCrevasseClassifier();

        List<String> test1 = Tokenizer.getTokenListFromText(MessagePreprocessing.removePunctation("Der Deich bricht!!!!".toLowerCase()));
        assertEquals(true,classifier.containsDeich(test1));

        List<String> test2 = Tokenizer.getTokenListFromText(MessagePreprocessing.removePunctation("Ich stehe auf dem...!!!!".toLowerCase()));
        assertEquals(false,classifier.containsDeich(test2));
    }






}
