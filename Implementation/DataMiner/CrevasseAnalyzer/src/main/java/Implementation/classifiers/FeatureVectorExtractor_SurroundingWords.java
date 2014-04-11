package Implementation.classifiers;

import CrevasseAnalyzer.Interface.FeatureVectorExtractor;
import org.haw.cas.TextMiner.Toolbox.MessagePreprocessing;
import org.haw.cas.TextMiner.Toolbox.Tokenizer;
import org.haw.cas.TextMiner.Toolbox.WordEnvironment;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: sebastian
 * Date: 17.11.13
 * Time: 14:25
 * To change this template use File | Settings | File Templates.
 */
public class FeatureVectorExtractor_SurroundingWords implements FeatureVectorExtractor{
    private List<String> sampleWordVector;
    private int surroundingWordsCount;
    private List<String> keywords;

    public FeatureVectorExtractor_SurroundingWords(List<String> sampleWordVector){
        this.sampleWordVector = sampleWordVector;
        this.surroundingWordsCount = 3;
        this.keywords = Arrays.asList("deich", "deichbruch", "brechen");
    }

    public double[] getFeatureVector(String message){
        if(message==null){
            return new double[0];
        }
        List<String> tokens = Tokenizer.getTokenListFromText(MessagePreprocessing.removePunctation(message.toLowerCase()));

        tokens = processFormsOfDeich(tokens);
        tokens = processFormsOfDeichbruch(tokens);
        tokens = processFormsOfBrechen(tokens);

        Set<String> surroundingWords = WordEnvironment.getSurroundingWords(surroundingWordsCount, keywords, tokens);
        double[] result = new double[sampleWordVector.size()];

        if(!(containsAtLeatOneKeyword(tokens))) return new double[0];
        if(!(tokens.contains("deich")||tokens.contains("deichbruch")))return new double[0];

        int i = 0;
        for(String word : sampleWordVector){
            if(surroundingWords.contains(word)){
                result[i] = 1;
            }else{
                result[i] = 0;
            }
            i++;
        }
        String output = "";
        for(double d : result){
            output = output + d + " ";
        }
        return result;
    }


    private List<String> processFormsOfBrechen(List<String> tokens) {
        List<String> brechenForms = Arrays.asList("brechen", "brach", "bricht", "gebrochen", "gebrochenen", "brachen");
        return replaceWordForms(tokens, brechenForms, "brechen");
    }

    private List<String> processFormsOfDeichbruch(List<String> tokens) {
        List<String> DeichbruchForms = Arrays.asList("deichbruch", "deichbruchs", "deichbrüche", "deichbrüchen");
        return replaceWordForms(tokens, DeichbruchForms, "deichbruch");
    }

    private List<String> processFormsOfDeich(List<String> message) {
        List<String> DeichForms = Arrays.asList("deich", "deiche", "deichs", "deichen","damm","dämme", "deiches");
        return replaceWordForms(message, DeichForms, "deich");
    }

    private List<String> replaceWordForms(List<String> tokens, List<String> wordForms , String wordForm){
        List<String> result = new LinkedList<>();

        for(String token : tokens){
            if(wordForms.contains(token)){
                result.add(wordForm);
            }else{
                result.add(token);
            }
        }
        return result;
    }

    private boolean containsAtLeatOneKeyword(List<String> tokens) {
        for(String token : tokens){
            if(keywords.contains(token)){
                return true;
            }
        }
        return false;
    }
}
