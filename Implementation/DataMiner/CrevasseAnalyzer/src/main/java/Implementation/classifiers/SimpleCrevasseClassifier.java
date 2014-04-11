package Implementation.classifiers;

import CrevasseAnalyzer.Interface.ICrevasseClassifier;
import org.haw.cas.TextMiner.Toolbox.MessagePreprocessing;
import org.haw.cas.TextMiner.Toolbox.Tokenizer;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: sebastian
 * Date: 12.11.13
 * Time: 13:21
 * To change this template use File | Settings | File Templates.
 */
public class SimpleCrevasseClassifier extends AbstractCrevasseClassifier implements ICrevasseClassifier {

    @Override
    public boolean crevasseReportIncluded(String message) {
        List<String> tokens = Tokenizer.getTokenListFromText(MessagePreprocessing.removePunctation(message));
        return (containsDeich(tokens) && containsRelevantFormOfBrechen(tokens));
    }

    public boolean containsRelevantFormOfBrechen(List<String> tokens){
        List<String> relevantForms = Arrays.asList("bricht", "brechen", "brach", "brachen", "gebrochen", "gebrochenen");
        for(String currentToken : tokens){
            if(relevantForms.contains(currentToken)){
                return true;
            }
        }
        return false;
    }

    public boolean containsDeich(List<String> tokens){
        return tokens.contains("deich");
    }


}