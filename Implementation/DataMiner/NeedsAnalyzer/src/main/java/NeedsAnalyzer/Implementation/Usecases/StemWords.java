package NeedsAnalyzer.Implementation.Usecases;

import org.haw.cas.TextMiner.Toolbox.Exceptions.GetWordStemsFailedException;
import org.haw.cas.TextMiner.Toolbox.WordStemmer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Jonas
 * Date: 11.12.13
 * Time: 16:03
 * To change this template use File | Settings | File Templates.
 */
public class StemWords {

    public static void main(String[] args) {
        System.out.println("Stemwords:");

        List<Map.Entry<String, String>> stems;

        List<String> words = new ArrayList<>();

        words.add("Haben");
        words.add("haben");
        words.add("habe");
        words.add("hatte");
        words.add("hätte");
        words.add("hast");
        words.add("hat");
        //haben

        words.add("bin");
        words.add("war");
        words.add("werde");
        words.add("sei");
        //sein


        words.add("brauche");
        words.add("brauchen");
        words.add("bräuchte");
        words.add("brauchte");
        words.add("braucht");
        words.add("brauchten");
        words.add("gebraucht");
        words.add("benötigte");
        words.add("benötigten");
        words.add("benötigt");
        words.add("benötige");
        words.add("benötigen");
        //brauchen, braucht, benötigen, benötig

        words.add("suchen");
        words.add("suche");
        words.add("suchte");
        words.add("suchten");
        words.add("gesucht");
        //suchen, sucht



        try {
            stems = WordStemmer.getWordStemsForWordList(words);
            for(Map.Entry<String,String> e: stems){
                System.out.println("Key " +  e.getKey() + " Value " + e.getValue());
            }
        } catch (GetWordStemsFailedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

}
