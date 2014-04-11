package NeedsAnalyzer.Implementation.Usecases;

import org.haw.cas.TextMiner.Toolbox.Exceptions.GetWordStemsFailedException;
import org.haw.cas.TextMiner.Toolbox.Tokenizer;
import org.haw.cas.TextMiner.Toolbox.WordVectorGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Jonas
 * Date: 02.12.13
 * Time: 23:42
 * To change this template use File | Settings | File Templates.
 */
public class CreateWordVector {



    protected static Map<String,Integer> createVector(String trainingData){

        Map<String,Integer> map;

        try {
            map = WordVectorGenerator.getWordVectorFromText(trainingData);
            return map;
            /*for(Map.Entry<String,Integer> e: map.entrySet()){
                if(e.getValue() >= 3){
                    System.out.println("Key: " + e.getKey() + " Value: " + e.getValue());
                }
            }*/
        } catch (GetWordStemsFailedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            map = new ConcurrentHashMap<>();
            List<String> tokens = Tokenizer.getTokenListFromText(trainingData);


        }
        return null;
    }




    public static String[] getWordVector() {

        String tester = "Herausfinden, wo Hilfe für die vom Hochwasser Betroffenen gebraucht wird, können Sie hier"
                + "Wer helfen möchte kann hier sehen wo aktuell Hilfe benötigt wird. #Hochwasser #Dresden"
                + "#Hochwasser in Süd- und Ostdeutschland: Hier können Freiwillige herausfinden, wo sie helfen können"
                + "Tolle Idee. Eine Karte zeigt an, wo in #Dresden Hilfe gebraucht wird."
                + "Hochwasser-Hilfe-Netzwerk “hochwasser2013.de”"
                + "Freiwillige Helfer in den #Hochwasser Gebieten gesucht! Das Hilfe-Netzwerk hilft euch bei der Vernetzung:"
                + "Das #Hochwassser-Hilfe-Netzwerk füllt sich mit Hilfsangeboten! Mach auch Du mit! :)"
                + "Stadt #Halle bittet wegen #Hochwasser dringend um Hilfe im Bereich des Gimritzer Dammes. Sandabfüllstation ist am Hubertusplatz."
                + "#Hochwasser Dringender Hinweis an Freiwillige Helfer: Bitte NICHT 112 anrufen. Dies behindert und blockiert den #Notruf und verzögert Hilfe"
                + "Wer in und um Passau helfen kann/will oder Hilfe braucht:"
                + "Südlich von #Leipzig ist das Entlastungsbauwerk Zitzschen geöffnet worden #Hochwasser"
                + "Falls jemand Hilfe organisiert: biete 2 kräftige Hände, heißen Tee und Durchhaltevermögen zwischen Wien und Melk. #hochwasser"
                + "Feuerwehr in #Wetterzeube im Burgenlandkreis braucht dringend Tauchpumpen. Einsatzleiter bittet um Hilfe im Kampf gegen #Hochwasser."
                + "Wer in Bayern helfen möchte, kann seine Hilfe hier anbieten http://www.lsdenergy.com/helfer-bayern/"
                + "Der Studentenclub Bärenzwinger in #Dresden braucht eure Hilfe. Deshalb morgen früh ab 5:30h bitte beim Sandsäcke füllen helfen!"
                + "Auf Facebook gibts eine Infoseite zum Hochwasser für Infoaustausch & schnelle Hilfe (via @MartinThuer danke!!)"
                + "#Hochwasser MDR-THÜRINGEN-Studio in #Gera braucht Hilfe. Sandsack-Barrieren am Haus reichen noch nicht aus. Wer kann helfen?"
                + "Schnelle Hilfe gegen Hochwasser - interessant für Feuerwehren, THW und auch Privatleute:"
                + "Hochwasser-HILFE Tausende Menschen sind auf die Hilfe der Mitmenschen angewiesen sein! BITTE TEILEN!"
                + "FF Cattenstedt: 31.05. - 22:40 - TH -  Hochwasser,  nachb. Hilfe in Wienrode /Dekon-P+MTW+LF, 15 Kam."
                + "Nun auch für #Bayern Helfer bei #Hochwasser können sich melden/kontaktiert werden, "
                + "Wer in Bayern helfen möchte, kann seine Hilfe hier anbieten  #hochwasser #bayern"
                + "Helfer können sich nun in dieser Datenbank auch für Bayern eintragen!"
                + "#Hochwasser: Helfer können sich hier eintragen"
                + "Grimma sucht freiwillige Helfer ab Montag frueh! Kontakt: #Hochwasser"
                + "Kein Ende der steigenden Pegel abzusehen/ Helfer jederzeit willkommen » #Hochwasser"
                + "#Hochwasser: Freiwillige Helfer werden auch in Raguhn am Bahnhof, in Greppin am Sportplatz und in Altjeßnitz gesucht."
                + "#Hochwasser: Die Stadt Bitterfeld sucht dringend freiwillige Helfer. Bitte bei der Feuerwehr in Jeßnitz melden."
                + "Hochwasser: Zwickau sucht Helfer!: Hochwasser: Zwickau sucht Helfer! Zwickau (Sachsen) – Die Feuerwehr hat die..."
                + "Kein Ende der steigenden Pegel abzusehen/ Helfer jederzeit willkommen » #Hochwasser"
                + "#Walschleben braucht noch Helfer zum Sandsäcke befüllen. #Hochwasser.";

        Map<String,Integer> map;

        map = createVector(tester);

        int arrsize = 26;

        String[] keywords = new String[arrsize];

        //if(map == null){

            keywords[0] = "dringend";
            keywords[1] = "am";
            keywords[2] = "können";
            keywords[3] = "bei";
            keywords[4] = "freiwillige";
            keywords[5] = "zwickau";
            keywords[6] = "sein";
            keywords[7] = "bitten";
            keywords[8] = "wo";
            keywords[9] = "helfer";
            keywords[10] = "und";
            keywords[11] = "mögen";
            keywords[12] = "werden";
            keywords[13] = "helfen";
            keywords[14] = "im";
            keywords[15] = "hochwasser";
            keywords[16] = "wer";
            keywords[17] = "hilfe";
            keywords[18] = "hier";
            keywords[19] = "sandsack";
            keywords[20] = "braucht";
            keywords[21] = "dresden";
            keywords[22] = "feuerwehr";
            keywords[23] = "bayer";
            keywords[24] = "suchen";
            keywords[25] = "netzwerk";

        //}

        /*int i = 0;
        System.out.println("WordVector:");
        for(Map.Entry<String,Integer> e: map.entrySet()){
            if(e.getValue() >= 3){
                if(i < arrsize){
                    System.out.println("Key: " + e.getKey() + " Value: " + e.getValue());
                    keywords[i++] = e.getKey();
                    System.out.println(i);
                }else{
                    System.out.println("ARRAY FOR NEEDS IN NEURAL NET TOO SMALL");
                }
            }
        }*/
        return keywords;
    }
}
