package org.haw.cas.TextMiner.Toolbox.Tests;

import org.haw.cas.TextMiner.Toolbox.NeuralNetworkWrapper;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: sebastian
 * Date: 09.11.13
 * Time: 12:15
 * To change this template use File | Settings | File Templates.
 */
public class NeuralNetworkWrapperTest {

    @Test
    public void calculatingTest(){
        try {
            File trainedNetDescription = new File("./TextminerToolbox/resources/xplus1.nnet").getCanonicalFile();
            System.out.println(trainedNetDescription.getAbsolutePath());
            NeuralNetworkWrapper neuralNet = new NeuralNetworkWrapper(trainedNetDescription);
            double[] input = {0.15};
            assertEquals(0.25, neuralNet.calculateOutput(input)[0],0.01);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }




}
