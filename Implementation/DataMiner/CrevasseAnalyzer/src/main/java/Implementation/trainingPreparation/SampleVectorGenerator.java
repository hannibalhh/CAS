package Implementation.trainingPreparation;

import Implementation.classifiers.CrevasseClassifierNeuralNet;
import Implementation.util.MessageCreatorFromFile;
import org.haw.cas.TextMiner.Toolbox.MessagePreprocessing;
import org.haw.cas.TextMiner.Toolbox.Tokenizer;
import org.haw.cas.TextMiner.Toolbox.WordEnvironment;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: sebastian
 * Date: 12.11.13
 * Time: 12:29
 * To change this template use File | Settings | File Templates.
 */
public class SampleVectorGenerator {
    public static void generateSampleVector(File fileOfAllTweets, List<String> keywords, int minWordCount) throws IOException {
        List<String> result = MessageCreatorFromFile.readFile(fileOfAllTweets);
        Map<String,Integer> wordCount = new HashMap<>();

        for(String message : result){
            List<String> tokens = Tokenizer.getTokenListFromText(MessagePreprocessing.removePunctation(message.toLowerCase()));
            Set<String> surroundingWords = WordEnvironment.getSurroundingWords(3, keywords, tokens);
            for(String word : surroundingWords){
                if(wordCount.containsKey(word)){
                    int newCount = wordCount.get(word)+1;
                    wordCount.put(word,newCount);
                }else{
                    wordCount.put(word,1);
                }
            }
        }
        for(Map.Entry<String,Integer> e : wordCount.entrySet()){
            if(e.getValue() >= minWordCount){
                System.out.println(e.getKey() + " : " + e.getValue());
            }
        }

    }

    private static List<String> StringArrayToList(String[] tokens) {
        List<String> tokenList = new ArrayList<>();
        for(String token : tokens){
            tokenList.add(token);
        }
        return tokenList;
    }

    public static void main(String args[]) throws IOException {
        List<String> keywords = Arrays.asList("Deich", "Deichbruch", "brechen", "gebrochen", "gebrochenen", "brach", "bricht");
        File allTweets = new File("../../allTweets.txt").getCanonicalFile();
        generateSampleVector(allTweets,keywords,2);
    }
}
