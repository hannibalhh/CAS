package org.haw.cas.TextMiner.Toolbox.Tests;

import org.haw.cas.TextMiner.Toolbox.MessagePreprocessing;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: sebastian
 * Date: 12.11.13
 * Time: 12:12
 * To change this template use File | Settings | File Templates.
 */
public class MessagePreprocessingTest {

    @Test
    public void removePunctation1(){
        String message1 = "Der Fischbecker Deich brach vor 6 Wochen... Jetzt alles gut :-)";
        String expected1 = "Der Fischbecker Deich brach vor 6 Wochen Jetzt alles gut ";
        assertEquals(expected1, MessagePreprocessing.removePunctation(message1));
    }

    @Test
    public void removePunctation2(){
        String message1 = "Über Änderungen ?! Hallo. es ßöäü?)(5";
        String expected1 = "Über Änderungen  Hallo es ßöäü5";
        assertEquals(expected1, MessagePreprocessing.removePunctation(message1));
    }
}
