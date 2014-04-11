package NeedsAnalyzer.Implementation.Usecases;

import org.haw.cas.TextMiner.Toolbox.NeuralNetworkWrapper;
import org.haw.cas.TextMiner.Toolbox.Tokenizer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Jonas
 * Date: 03.12.13
 * Time: 12:27
 * To change this template use File | Settings | File Templates.
 */
public class NeuralNetNeeds {

    private NeuralNetworkWrapper neuralNet;
    private double trueFalseSeperator;
    private String[] keywords;
    private int featureVectorSize = 26;

    public NeuralNetNeeds(double tfSeparator, String[] keywords){

        try {
            File needsHelperNnetFile = new File("./NeedsAnalyzer/src/resources/FloodHelper.nnet").getCanonicalFile();
            this.neuralNet = new NeuralNetworkWrapper(needsHelperNnetFile);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        this.trueFalseSeperator = tfSeparator;
        this.keywords = keywords;
    }

    public double[] getFeatureVector(String msg){
        if(msg == null){
            return null;
        }

        double[] fVector = new double[featureVectorSize];

        double output;
        List<String> tokens = new ArrayList<>();
        tokens = Tokenizer.getTokenListFromText(msg);

        if(keywords.length != featureVectorSize) return null; //Error in keywords-size

        for (int j = 0; j < keywords.length; j++) {
            output = 0.0;
            for(String tkn: tokens){
                if(tkn.equals(keywords[j])){
                    output = 1.0;
                    break;
                }
            }
            fVector[j] = output;
        }
        return fVector;
    }

    public Boolean calcOutput(double[] fVector){
        /*System.out.println();
        for (int i = 0; i < 26; i++) {
            System.out.print(fVector[i] + " ");
        }*/
        //System.out.println("calcOutput: " + calcOutput);
        return neuralNet.calculateOutput(fVector)[0] > trueFalseSeperator;
    }

}
