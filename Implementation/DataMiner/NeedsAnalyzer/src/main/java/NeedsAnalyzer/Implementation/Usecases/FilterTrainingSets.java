package NeedsAnalyzer.Implementation.Usecases;

import org.haw.cas.TextMiner.Toolbox.Tokenizer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Jonas
 * Date: 03.12.13
 * Time: 00:33
 * To change this template use File | Settings | File Templates.
 */
public class FilterTrainingSets {


    public static void main(String[] args) {

        String[] keywords = new String[26];

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


        List<String> tweets = new ArrayList<>();   //Tweets mit positiven Hilfe Anfragen

        tweets.add("Herausfinden, wo Hilfe für die vom Hochwasser Betroffenen gebraucht wird, können Sie hier");
        tweets.add("Wer helfen möchte kann hier sehen wo aktuell Hilfe benötigt wird. #Hochwasser #Dresden");
        tweets.add("#Hochwasser in Süd- und Ostdeutschland: Hier können Freiwillige herausfinden, wo sie helfen können");
        tweets.add("Tolle Idee. Eine Karte zeigt an, wo in #Dresden Hilfe gebraucht wird.");
        tweets.add("Hochwasser-Hilfe-Netzwerk “hochwasser2013.de”");
        tweets.add("Freiwillige Helfer in den #Hochwasser Gebieten gesucht! Das Hilfe-Netzwerk hilft euch bei der Vernetzung:");
        tweets.add("Das #Hochwassser-Hilfe-Netzwerk füllt sich mit Hilfsangeboten! Mach auch Du mit! :)");
        tweets.add("Stadt #Halle bittet wegen #Hochwasser dringend um Hilfe im Bereich des Gimritzer Dammes. Sandabfüllstation ist am Hubertusplatz.");
        tweets.add("#Hochwasser Dringender Hinweis an Freiwillige Helfer: Bitte NICHT 112 anrufen. Dies behindert und blockiert den #Notruf und verzögert Hilfe");
        tweets.add("Wer in und um Passau helfen kann/will oder Hilfe braucht:");
        tweets.add("Südlich von #Leipzig ist das Entlastungsbauwerk Zitzschen geöffnet worden #Hochwasser");
        tweets.add("Falls jemand Hilfe organisiert: biete 2 kräftige Hände, heißen Tee und Durchhaltevermögen zwischen Wien und Melk. #hochwasser");
        tweets.add("Feuerwehr in #Wetterzeube im Burgenlandkreis braucht dringend Tauchpumpen. Einsatzleiter bittet um Hilfe im Kampf gegen #Hochwasser.");
        tweets.add("Wer in Bayern helfen möchte, kann seine Hilfe hier anbieten http://www.lsdenergy.com/helfer-bayern/");
        tweets.add("Der Studentenclub Bärenzwinger in #Dresden braucht eure Hilfe. Deshalb morgen früh ab 5:30h bitte beim Sandsäcke füllen helfen!");
        tweets.add("Auf Facebook gibts eine Infoseite zum Hochwasser für Infoaustausch & schnelle Hilfe (via @MartinThuer danke!!)");
        tweets.add("#Hochwasser MDR-THÜRINGEN-Studio in #Gera braucht Hilfe. Sandsack-Barrieren am Haus reichen noch nicht aus. Wer kann helfen?");
        tweets.add("Schnelle Hilfe gegen Hochwasser - interessant für Feuerwehren, THW und auch Privatleute:");
        tweets.add("Hochwasser-HILFE Tausende Menschen sind auf die Hilfe der Mitmenschen angewiesen sein! BITTE TEILEN!");
        tweets.add("FF Cattenstedt: 31.05. - 22:40 - TH -  Hochwasser,  nachb. Hilfe in Wienrode /Dekon-P+MTW+LF, 15 Kam.");
        tweets.add("Nun auch für #Bayern Helfer bei #Hochwasser können sich melden/kontaktiert werden, ");
        tweets.add("Wer in Bayern helfen möchte, kann seine Hilfe hier anbieten  #hochwasser #bayern");
        tweets.add("Helfer können sich nun in dieser Datenbank auch für Bayern eintragen!");
        tweets.add("#Hochwasser: Helfer können sich hier eintragen");
        tweets.add("Grimma sucht freiwillige Helfer ab Montag frueh! Kontakt: #Hochwasser");
        tweets.add("Kein Ende der steigenden Pegel abzusehen/ Helfer jederzeit willkommen » #Hochwasser");
        tweets.add("#Hochwasser: Freiwillige Helfer werden auch in Raguhn am Bahnhof, in Greppin am Sportplatz und in Altjeßnitz gesucht.");
        tweets.add("#Hochwasser: Die Stadt Bitterfeld sucht dringend freiwillige Helfer. Bitte bei der Feuerwehr in Jeßnitz melden.");
        tweets.add("Hochwasser: Zwickau sucht Helfer!: Hochwasser: Zwickau sucht Helfer! Zwickau (Sachsen) – Die Feuerwehr hat die...");
        tweets.add("Kein Ende der steigenden Pegel abzusehen/ Helfer jederzeit willkommen » #Hochwasser");
        tweets.add("#Walschleben braucht noch Helfer zum Sandsäcke befüllen. #Hochwasser.");


        List<String> tweetsNot = new ArrayList<>();   //Tweets mit negativen Hilfe Anfragen

        tweetsNot.add("Hochwasser, Sturm, Erdrutsch: Wie #Katastrophenhilfe in der Schweiz und von Caritas funktioniert. Wir auf SRF:");
        tweetsNot.add("Hochwasser in Deutschland: Jetzt kommt die Bundeswehr zu Hilfe...");
        tweetsNot.add("@zeitonline  #Hochwasser Schutz beginnt im #Bergwald ! Ein gesunder Wald kann 10 mal mehr Wasser speichern, deshalb Abschaffung der Jagd");
        tweetsNot.add("Bundeswehr kommt Hochwasserregionen zu Hilfe - Süddeutsche.de: UNTERNEHMEN-HEUTE.deBundeswehr kommt Hochwasser...");
        tweetsNot.add("Liebe Facebook-Fans," +
                "wir brauchen Eure Hilfe!" +
                "Wir planen ab ca. 16.30 Uhr eine Sondersendung zum Hochwasser." +
                "Wer...");
        tweetsNot.add("+++Scheuer: Seehofer und Merkel versichern Passau jegliche Hilfe bei Hochwasser-Katastrophe+++ " +
                "Ich habe MP...");
        tweetsNot.add("Nix mit WE. Ich werde mich dann gleich auf die Socken machen und das drohende Hochwasser begutachten. Ggfs. Hilfe...");
        tweetsNot.add("Hochwasser hält Helfer in Atem - Augsburger Allgemeine");
        tweetsNot.add("#Hochwasser: Respekt u Dank an die vielen Helfer, THW und die Feuerwehr.");
        tweetsNot.add("Beten wir für alle Menschen, die durch das Hochwasser großen Schaden erleiden; und für die unermüdlichen Helfer/-innen.");
        tweetsNot.add("Beim #Hochwasser in BaWü sind #Helfer am Wochenende zu mehr als 3000 Einsätzen ausgerückt. Ihnen vielen #Dank!");
        tweetsNot.add("der Dank geht an die Feuerwehr und alle Helfer, das Mitgefühl an alle Betroffenen! #Hochwasser");
        tweetsNot.add("Hochwasser in Teilen Deutschlands: Helfer arbeiten fieberhaft - Sonntag Starkregen");
        tweetsNot.add("Wer in Bayern helfen möchte, kann seine Hilfe hier anbieten http://www.lsdenergy.com/helfer-bayern/");
        tweetsNot.add("Das #Hochwasser zieht viele Schaulustige an. Nicht alle gucken nur, einige stehen den Helfern auch im Weg.");
        tweetsNot.add("Hochwasser in Region: Hunderte Helfer im Einsatz - Pforzheimer Zeitung");
        tweetsNot.add("Hochwasser in der Region: Helfer im Dauereinsatz: " +
                "            Hochwasser in Reutlingen." +
                "  FOTO:...");
        tweetsNot.add("Wenn ich sowas lese geht mir die Hutschnur hoch:@mzwebde: #Hochwasser in #Wetterzeube: Schaulustige behindern Helfer");
        tweetsNot.add("#Hochwasser in #Wetterzeube: Schaulustige behindern Helfer");
        tweetsNot.add("Danke an alle Ehrenamtlichen Helfer und beim DRK,Johanniter,THW,FW&FFW,Polizei und Heli Christoph beim Einsatz im Hochwasser in Thüringen!");
        tweetsNot.add("RT @LeLion_de: vielen Dank an die Feuerwehren und das THW ... ganz schön heftig heute");
        tweetsNot.add("Am Kloster #Weltenburg beginnen FW und Helfer damit, Hochwasser-Schutzwände aufzubauen. Die Lage wird kritisch.");
        tweetsNot.add("#de55x  #557bir  Hochwasser in Region: A8 unter Wasser, Straßen gesperrt - Pforzheimer Zeitung");
        tweetsNot.add("Wasser steigt weiter - Helfer sind im Dauereinsatz");
        tweetsNot.add("Hochwasser: Gefahr von Dammbruch in Pleidelsheim - Helfer sichern Dämme mit Sandsäcke");
        tweetsNot.add("RT @BertilStarkeHRA: Trotz der #Hochwasser Entspannung in #Sarstedt sind viele Helfer noch im Einsatz, zum...");
        tweetsNot.add("Die Pegel in Niedersachsen steigen weiter: Vielerorts kämpfen Helfer gegen das Hochwasser. Flüsse und Bäche tr...");
        tweetsNot.add("Manche Orte nur mit Booten erreichbar: Vielerorts kämpfen Helfer gegen das Hochwasser. Flüsse und Bäche treten...");
        tweetsNot.add("RT @Frankroth2012: Ein großes Lob für die ehrenamtlichen Helfer des Technischen Hilfswerkes, die am Schillerteich...");
        tweetsNot.add("Hochwasser in...: Helfer sind seit Sonntag im Dauereinsatz");
        tweetsNot.add("THW-Helfer bauen Notdeich gegen Hochwasser: Helfer des Technischen Hiflswerks (THW) haben es am Wochenende mit H...");
        tweetsNot.add("Hochwasser als Flucht-Helfer für Krokodile - BZ: BZHochwasser als Flucht-Helfer für KrokodileBZNun sind Krokodil...");
        tweetsNot.add("Das Hochwasser an Rhein und Mosel steigt und fällt zur Zeit. Doch die neusten Vorhersagen versetzt die Helfer in...");




        Map<String,Integer> map = new HashMap<String,Integer>();

        map = CreateWordVector.createVector(tester);

        int i = 0;
        for(Map.Entry<String,Integer> e: map.entrySet()){
            if(e.getValue() >= 3){
                //System.out.println("Key: " + e.getKey() + " Value: " + e.getValue());
                keywords[i++] = e.getKey();
            }
        }

        List<String> tokens;

        i = 0;
        int outp = 0;
        for(String s: tweetsNot){
            System.out.print("Tweet " + i++ + ": ");
            for (int j = 0; j < keywords.length; j++) {
                outp = 0;
                tokens = new ArrayList<>();
                tokens = Tokenizer.getTokenListFromText(s);
                for(String tkn: tokens){
                    if(tkn.equals(keywords[j])){
                        outp = 1;
                        break;
                    }
                }
                System.out.print("  "  + outp);
            }
            System.out.println("\n");
        }
    }

}
