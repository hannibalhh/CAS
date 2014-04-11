package Implementation.classifiers;

import CrevasseAnalyzer.Interface.FeatureVectorExtractor;
import CrevasseAnalyzer.Interface.ICrevasseClassifier;
import Implementation.util.MessageCreatorFromFile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.haw.cas.TextMiner.Toolbox.MessagePreprocessing;
import org.haw.cas.TextMiner.Toolbox.NeuralNetworkWrapper;
import org.haw.cas.TextMiner.Toolbox.Tokenizer;
import org.haw.cas.TextMiner.Toolbox.WordEnvironment;

import java.io.*;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: sebastian
 * Date: 31.10.13
 * Time: 12:59
 * To change this template use File | Settings | File Templates.
 */
public class CrevasseClassifierNeuralNet extends AbstractCrevasseClassifier implements ICrevasseClassifier {
    private NeuralNetworkWrapper neuralNet;
    private double trueFalseSeperator;
    private FeatureVectorExtractor featureVectorExtractor;
    private static Logger logger = LogManager.getLogger(CrevasseClassifierNeuralNet.class);

    public CrevasseClassifierNeuralNet(NeuralNetworkWrapper neuralNet, FeatureVectorExtractor featureVectorExtractor, double trueFalseSeperator){
        this.neuralNet = neuralNet;
        this.trueFalseSeperator = trueFalseSeperator;
        this.featureVectorExtractor = featureVectorExtractor;
    }

    @Override
    public boolean crevasseReportIncluded(String message) {

        double[] wordVector = featureVectorExtractor.getFeatureVector(message);
        if(wordVector.length == 0) return false;


        return neuralNet.calculateOutput(wordVector)[0] > trueFalseSeperator;
    }

    public static void main(String args[]) throws IOException {
        File crevasseNnetFile = new File("./CrevasseAnalyzer/resources/crevasseNet.nnet").getCanonicalFile();
        System.out.println(crevasseNnetFile.getAbsolutePath());
        NeuralNetworkWrapper neuralNet = new NeuralNetworkWrapper(crevasseNnetFile);

        List<String> wordVector = CrevasseClassifierNeuralNet.getSampleWordVectorFromFile(
                new File("./CrevasseAnalyzer/resources/wordVector.txt").getCanonicalFile());

        FeatureVectorExtractor vectorExtractor = new FeatureVectorExtractor_SurroundingWords(wordVector);

        ICrevasseClassifier classifier = new CrevasseClassifierNeuralNet(neuralNet,vectorExtractor,0.4);


        System.out.println("");
        System.out.println("------------------- all false ones --------------------------------------");
        System.out.println("");

        File falseMessagesfile = new File("./CrevasseAnalyzer/resources/deichbrueche_false.txt").getCanonicalFile();
        List<String> allFalseMessages = MessageCreatorFromFile.readFile(falseMessagesfile);
        for(String falseMessage : allFalseMessages){
            if(classifier.crevasseReportIncluded(falseMessage)){
                System.out.println(falseMessage);
            }

        }

        System.out.println("gesamt: " + allFalseMessages.size());

        System.out.println("");
        System.out.println("------------------- all right ones --------------------------------------");
        System.out.println("");

        File trueMessagesfile = new File("./CrevasseAnalyzer/resources/deichbrueche.txt").getCanonicalFile();
        List<String> alltrueMessages = MessageCreatorFromFile.readFile(trueMessagesfile);
        for(String trueMessage : alltrueMessages){
            if(!(classifier.crevasseReportIncluded(trueMessage))){
                System.out.println(trueMessage);
            }

        }

        List<String> rights = new LinkedList<>();
        rights.add("In Draschwitz ist im #Hochwasser ein Deich gebrochen. Grund: Wurzeln, die den Deich durchzogen und geschädigt haben. http://bit.ly/11tXSSU ");
        rights.add("Hochwasser-Protokoll: Deich gebrochen – Fischbeck überflutet http://bit.ly/18xDchI");
        rights.add("#de99x #996soem   Deich gebrochen – Fischbeck überflutet - DIE WELT http://goo.gl/fb/bXjtN ");
        rights.add("Auch der Deich in Hohengöhren gebrochen, jetzt kommt es für Schönhausen von 2 Seiten :-/ #Hochwasser");
        rights.add("Angst vor Elbeflut im Norden – Deich im Kreis Stendal gebrochen http://www.antizensur.de/angst-vor-elbeflut-im-norden-deich-im-kreis-stendal-gebrochen/ …");
        rights.add("+++ Das Hochwasser im Ticker +++ - LIVE!: Weiterer Deich gebrochen – Orte evakuiert: Der Norden Deutschlands k... http://adf.ly/QPTQp ");
        rights.add("Deich in Hamburg gebrochen #hawtest");
        rights.add("Fischbeck hat den Kampf gegen das Elbe #Hochwasser verloren: der Deich ist in der Nacht gebrochen. Wassermassen fließen in Wohngebiete.#Flut");
        rights.add("Hochwasser: Deich bei Osterhofen gebrochen http://www.rosenheim24.de/news/bayern/hochwasser-deggendorf-deich-osterhofen-gebrochen-2942216.html …");
        rights.add("Zunächst keine Gefahr - Deich in Grießen gebrochen http://bit.ly/c8LWlP");
        rights.add("Bei Hamburg ist ein Deich gebrochen. Feuerwehr überfordert...");
        rights.add("Bei Hamburg ist ein Deich gebrochen! +HAWTest 1");

        List<String> wrongs = new LinkedList<>();
        wrongs.add("„Hätten die Biber weiter gegraben, wäre der Deich gebrochen.“ So ein Quark. http://www.svz.de/artikel/artikel/zwei-geschuetzte-biber-aus-sorge-um-deiche-geschossen.html …");
        wrongs.add("Tja Leute. Die Lage ist ätzend. Wir sitzen in der Nähe von Fischbeck, wo vor 2 Tagen der Deich gebrochen ist. Seit");
        wrongs.add("Deich an der Saalemündung in #Breitenhagen geschlossen, war am 9. Juni gebrochen. Mobiles Schöpfwerk aufgebaut. #Hochwasser");
        wrongs.add("Der im Sommer gebrochene Deich in Hamburg ist nun wieder geschlossen");
        wrongs.add("#Hochwasseraktuell - Korrektur: Der Deich bei Osterhofen im #LandkreisDeggendorf ist noch nicht gebrochen http://bit.ly/deichbruch");
        wrongs.add("Falschmeldung: Der Deich in Hamburg ist nicht gebrochen. ");
        wrongs.add("ie Witwe des ermordeten Augsburger Polizisten Mathias Vieth hat ihr Schweigen gebrochen. http://sz.de/1.1835107 ");
        wrongs.add("Und immer dran denken: Alle Herzen sind unschuldig, bis sie das erste Mal gebrochen werden.");
        wrongs.add("Ein bisschen gebrochen nach der Nachspeise.  Hat aber keiner gemerkt.  #halligallin pic.twitter.com/3xGZPSn9Nv");
        wrongs.add("Der #NSA Skandal ist erst aufgeklärt, wenn einige, die ihren Amtseid gebrochen haben dafür in den Knast gehen.");
        wrongs.add("So @ExWuschel hat mir mein Herz gebrochen");
        wrongs.add("Und dann habe ich gerade eine Tür aus der Büroküche gebrochen und denke mir, geil, Gin verleiht Flügel.");
        wrongs.add("\"Lieber Papa, heute habe ich einem Kind den Arm gebrochen!\" http://instagram.com/p/hYiZ0GtIZp/ ");
        wrongs.add("Während sich im dunklen Treppenhaus bevorzugt das Genick gebrochen wird, zeichne ich mit Filzstift den verblassten Ausschlag virtuos nach.");
        wrongs.add("Übrigens bricht damit jetzt das BluRay-Zeitalter bei mir an.");
        System.out.println("------------------- Test rights --------------------------------------");
        for(String s : rights){
            System.out.println(s);
            System.out.println(classifier.crevasseReportIncluded(s));
        }

        System.out.println("------------------- Test wrongs --------------------------------------");
        for(String s : wrongs){
            System.out.println(s);
            System.out.println(classifier.crevasseReportIncluded(s));
        }






    }



}
