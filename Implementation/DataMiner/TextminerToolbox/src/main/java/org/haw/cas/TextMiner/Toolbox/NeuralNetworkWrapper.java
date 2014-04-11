package org.haw.cas.TextMiner.Toolbox;




import org.neuroph.core.NeuralNetwork;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: sebastian
 * Date: 09.11.13
 * Time: 09:08
 * To change this template use File | Settings | File Templates.
 */


public class NeuralNetworkWrapper {
    private NeuralNetwork neuralNetwork;

    public NeuralNetworkWrapper(File nnetFile){
        neuralNetwork = NeuralNetwork.createFromFile(nnetFile);
    }

    public double[] calculateOutput(double[] input){
        if(input.length != neuralNetwork.getInputsCount()){
            throw new IllegalArgumentException("Number of InputNeurons and Number if inputs must be equal");
        }

        neuralNetwork.setInput(input);
        neuralNetwork.calculate();
        return neuralNetwork.getOutput();
    }

//    private double[] scaleOutput(double[] output) {
//        double[] result = new double[output.length];
//        for(int i = 0; i < output.length; i++){
//            result[i] = output[i];
//        }
//        return result;
//    }
//
//    private double[] scaleInput(double[] input) {
//        double[] result = new double[input.length];
//        for(int i = 0; i < input.length; i++){
//            result[i] = (input[i] - minInput)/(maxInput - minInput);
//        }
//        return result;
//    }

}
