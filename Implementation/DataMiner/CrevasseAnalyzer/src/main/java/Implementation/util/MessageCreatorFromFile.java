package Implementation.util;

import Implementation.classifiers.CrevasseClassifierNeuralNet;
import org.haw.cas.TextMiner.Toolbox.MessagePreprocessing;
import org.haw.cas.TextMiner.Toolbox.Tokenizer;
import org.haw.cas.TextMiner.Toolbox.WordEnvironment;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;


/**
 * Created with IntelliJ IDEA.
 * User: sebastian
 * Date: 10.11.13
 * Time: 12:52
 * To change this template use File | Settings | File Templates.
 */
public class MessageCreatorFromFile {

    public static List<String> readFile(File file){
        List<String> result = new LinkedList<>();
        try {
            //BufferedReader in = new BufferedReader(new FileReader(file));
            BufferedReader in = Files.newBufferedReader(file.toPath(), StandardCharsets.UTF_8);
            String zeile = null;
            String message = "";
            while ((zeile = in.readLine()) != null) {
                if(zeile.equals("")){
                    result.add(message);
                    message = "";
                }else{
                    message = message + zeile;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) throws IOException {
        File file = new File("../../allTweets.txt").getCanonicalFile();
        List<String> result = readFile(file);
        List<String> keywords = Arrays.asList("Deich", "Deichbruch", "brechen", "gebrochen", "gebrochenen", "brach", "bricht");
        Map<String,Integer> wordCount = new HashMap<>();

        for(String message : result){
            List<String> tokenList = Tokenizer.getTokenListFromText(MessagePreprocessing.removePunctation(message.toLowerCase()));

            Set<String> surroundingWords = WordEnvironment.getSurroundingWords(3, keywords, tokenList);
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
            if(e.getValue() >= 3){
                //System.out.println(e.getKey() + " : " + e.getValue());
            }
        }
    }
}
