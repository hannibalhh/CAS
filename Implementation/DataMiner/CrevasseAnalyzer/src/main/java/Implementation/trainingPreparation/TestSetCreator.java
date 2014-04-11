package Implementation.trainingPreparation;

import CrevasseAnalyzer.Interface.FeatureVectorExtractor;
import CrevasseAnalyzer.Interface.ICrevasseClassifier;
import Implementation.classifiers.CrevasseClassifierNeuralNet;
import Implementation.classifiers.FeatureVectorExtractor_SurroundingWords;
import Implementation.util.MessageCreatorFromFile;
import org.haw.cas.TextMiner.Toolbox.NeuralNetworkWrapper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: sebastian
 * Date: 12.11.13
 * Time: 14:12
 * To change this template use File | Settings | File Templates.
 */
public class TestSetCreator {
    BufferedWriter output;

    public TestSetCreator(File trainingResults){
        try {
            output = Files.newBufferedWriter(trainingResults.toPath(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void writeTrainingFile(File positivesFile, File negativesFile, FeatureVectorExtractor featureVectorExtractor){
        MessageCreatorFromFile messageCreator = new MessageCreatorFromFile();
        List<String> positives =  messageCreator.readFile(positivesFile);
        List<String> negatives =  messageCreator.readFile(negativesFile);

        try{
            processMessagesAndWriteToFile(featureVectorExtractor, positives,1);
            processMessagesAndWriteToFile(featureVectorExtractor, negatives,0);
            output.close();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    private void processMessagesAndWriteToFile(FeatureVectorExtractor featureVectorExtractor, List<String> positives, int value) throws IOException {
        double[] wordVector;
        for(String current : positives){
            wordVector =  featureVectorExtractor.getFeatureVector(current);
            System.out.println("WordvectorLength: "+ wordVector.length);

            double[] wordVectorWithResult = new double[wordVector.length+1];
            wordVectorWithResult[wordVectorWithResult.length-1] = value;

            int i = 0;
            for(double currentDouble : wordVector){
                wordVectorWithResult[i] = currentDouble;
                i++;
            }

            System.out.println("Gesamtlaenge: " + wordVectorWithResult.length);
            output.write(createTrainingString(wordVectorWithResult));
            output.newLine();
        }
    }

    public String createTrainingString(double[] wordVector) {
        System.out.println("WritingWordVector: " +  wordVector);
        StringBuffer temp = new StringBuffer();
        for(int i = 0; i < wordVector.length-1;i++){
            temp.append(String.valueOf(wordVector[i]));
            temp.append(" ");
        }
        temp.append(wordVector[wordVector.length-1]);
        System.out.println("Result: " + temp.toString());
        return temp.toString();
    }

    public static void main(String args[]) throws IOException {
        List<String> sampleWordVector = CrevasseClassifierNeuralNet.getSampleWordVectorFromFile(
                new File("./CrevasseAnalyzer/resources/wordVector.txt").getCanonicalFile());

        System.out.println(sampleWordVector);
        FeatureVectorExtractor featureVectorExtractor = new FeatureVectorExtractor_SurroundingWords(sampleWordVector);



        File trainingSet = new File("./CrevasseAnalyzer/resources/trainingSet.txt").getCanonicalFile();
        File positivesFile = new File("./CrevasseAnalyzer/resources/deichbrueche.txt").getCanonicalFile();
        File negativesFile = new File("./CrevasseAnalyzer/resources/deichbrueche_false.txt").getCanonicalFile();
        System.out.println(trainingSet);
        TestSetCreator creator = new TestSetCreator(trainingSet);
        creator.writeTrainingFile(positivesFile,negativesFile,featureVectorExtractor);
    }
}
