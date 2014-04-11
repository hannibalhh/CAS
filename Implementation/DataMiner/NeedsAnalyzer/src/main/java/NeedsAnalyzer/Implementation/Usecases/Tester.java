package NeedsAnalyzer.Implementation.Usecases;

import DSPersistenceManager.Model.Message;
import DataTypes.DataTypeInterfaces.IInfo;
import DataTypes.DataTypeInterfaces.INeed;
import DataTypes.Need;
import org.haw.cas.TextMiner.Toolbox.Exceptions.GetWordStemsFailedException;
import org.haw.cas.TextMiner.Toolbox.POSTagger;
import org.haw.cas.TextMiner.Toolbox.Tokenizer;
import org.haw.cas.TextMiner.Toolbox.WordStemmer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Jonas
 * Date: 11.11.13
 * Time: 21:33
 * To change this template use File | Settings | File Templates.
 */
public class Tester {



    public static void main(String[] args) {

        AnalyzeChunkForNeeds analyzeChunkForNeeds = new AnalyzeChunkForNeeds(new NeuralNetNeeds(0.4,CreateWordVector.getWordVector()));

        System.out.println("NeedsAnalyzer Test Start");


        String tester = "Ich bin gestern in bester Laune mit der Bahn gefahren. Keine Hilfe war für mein vollständig befriedigendes Vergnügen von Nöten.";
        String tester2 = "Hilfe Helfer helfen hilf half";
        String tester3 = "Ich habe fürchterlichen Durst";
        String tester4 = "Ich bin sehr hungrig";
        String tester5 = "Wir brauchen Hilfe am Blabla Platz um mit Sandsack einen Deich zu flicken";
        String tester6 = "Herausfinden, wo Hilfe für die vom Hochwasser Betroffenen gebraucht wird, können Sie hier";

        List<Map.Entry<String, POSTagger.PosTag>> postags;

        postags = POSTagger.getPosTagsFromText(tester);

        for(Map.Entry<String, POSTagger.PosTag> e: postags){
            System.out.println("Key: " + e.getKey() + " Value:" + e.getKey());
        }


        Message msg1 = new Message();
        msg1.setMessage(tester);
        Message msg2 = new Message();
        msg2.setMessage(tester2);
        Message msg3 = new Message();
        msg3.setMessage(tester3);
        Message msg4 = new Message();
        msg4.setMessage(tester4);

        Message msg5 = new Message();
        msg5.setMessage(tester5);
        Message msg6 = new Message();
        msg6.setMessage(tester6);




        INeed iInfo;

        List<IInfo> list = new ArrayList<>();
        List<IInfo> list2 = new ArrayList<>();
        List<IInfo> list3 = new ArrayList<>();
        List<IInfo> list4 = new ArrayList<>();
        List<IInfo> list5 = new ArrayList<>();
        List<IInfo> list6 = new ArrayList<>();

        long zstVorher;
        long zstNachher;

        long zst1;
        long zst2;
        long zst3;

        zstVorher = System.currentTimeMillis();

            list = analyzeChunkForNeeds.processMessage(msg1);
            zst1 = System.currentTimeMillis();

            list2 = analyzeChunkForNeeds.processMessage(msg2);
            zst2 = System.currentTimeMillis();

            list3 = analyzeChunkForNeeds.processMessage(msg3);
            zst3 = System.currentTimeMillis();

            list4 = analyzeChunkForNeeds.processMessage(msg4);
            list5 = analyzeChunkForNeeds.processMessage(msg5);
            list6 = analyzeChunkForNeeds.processMessage(msg6);
            zstNachher = System.currentTimeMillis();

            System.out.println("Zeit benötigt: ");
            System.out.println("String 1: " + (zst1 - zstVorher) + "ms");
            System.out.println("String 2: " + (zst2 - zst1) + "ms");
            System.out.println("String 3: " + (zst3 - zst2) + "ms");
            System.out.println("String 4: " + (zstNachher - zst3) + "ms");
            System.out.println("Insgesamt: " + (double)(zstNachher - zstVorher)/1000 + "s");






        if(!list.isEmpty()){
            for(IInfo obj: list){
                iInfo = (INeed)obj;
                System.out.println("1: " + iInfo.getTypeOfNeed());
            }
        }
        if(!list2.isEmpty()){
            for(IInfo obj: list2){
                iInfo = (INeed)obj;
                System.out.println("2: " + iInfo.getTypeOfNeed());
            }
        }
        if(!list3.isEmpty()){
            for(IInfo obj: list3){
                iInfo = (INeed)obj;
                System.out.println("3: " + iInfo.getTypeOfNeed());
            }
        }
        if(!list4.isEmpty()){
            for(IInfo obj: list4){
                iInfo = (INeed)obj;
                System.out.println("4: " + iInfo.getTypeOfNeed());
            }
        }
        if(!list5.isEmpty()){
            for(IInfo obj: list5){
                iInfo = (INeed)obj;
                System.out.println("5: " + iInfo.getTypeOfNeed());
            }
        }
        if(!list6.isEmpty()){
            for(IInfo obj: list6){
                iInfo = (INeed)obj;
                System.out.println("6: " + iInfo.getTypeOfNeed());
            }
        }

    }

}
