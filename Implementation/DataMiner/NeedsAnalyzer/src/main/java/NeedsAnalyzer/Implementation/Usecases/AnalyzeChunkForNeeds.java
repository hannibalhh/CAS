package NeedsAnalyzer.Implementation.Usecases;


import DSPersistenceManager.Model.Message;
import DataTypes.DataTypeInterfaces.IInfo;
import DataTypes.DataTypeInterfaces.INeed;
import DataTypes.Need;
import org.haw.cas.TextMiner.Toolbox.*;
import org.haw.cas.GlobalTypes.MessageInfo.TypeOfNeed;
import org.haw.cas.TextMiner.Toolbox.Exceptions.GetWordStemsFailedException;

import java.util.*;


/**
 * Created with IntelliJ IDEA.
 * User: Jonas
 * Date: 11.11.13
 * Time: 20:09
 * To change this template use File | Settings | File Templates.
 */
public class AnalyzeChunkForNeeds {


    NeuralNetNeeds neuralNetNeeds;
    private int fVectorSize = 26;

    public AnalyzeChunkForNeeds(NeuralNetNeeds neuralNetNeeds){
        this.neuralNetNeeds = neuralNetNeeds;
    }

    public List<IInfo> processMessage(Message message) {

        List<IInfo> ret = new ArrayList<>();

        String msgString = message.getMessage();

    /*    Map<String, Integer> wordvector;

        wordvector = WordVectorGenerator.getWordVectorFromText(msgString);*/

        List<Map.Entry<String, String>> stemmed;

        try{
            stemmed = WordStemmer.getWordStemsFromText(msgString);
        }catch (GetWordStemsFailedException e){
            List<String> tokenized = Tokenizer.getTokenListFromText(message.getMessage());
            stemmed = new LinkedList<Map.Entry<String, String>>();
            for(String s: tokenized){
                stemmed.add(new AbstractMap.SimpleEntry<String, String>(s,s));
            }
            //only tokenized
        }

        boolean helper = false;
        boolean medicine = false;
        boolean shelter = false;
        boolean thirsty = false;
        boolean hungry = false;

        boolean haben = false;
        boolean sein = false;
        boolean brauchen = false;
        boolean suchen = false;

        boolean kein = false;

        for(Map.Entry<String,String> entry: stemmed){
            //First keys are stemwords for other matches
            if(entry.getValue().equals("haben") || entry.getValue().equals("Haben") || entry.getValue().equals("Habe") || entry.getValue().equals("Hatte") || entry.getValue().equals("Hat")){
                haben = true;
            }
            if(entry.getValue().equals("sein") || entry.getValue().equals("Sein") || entry.getValue().equals("Bin") || entry.getValue().equals("Sei") || entry.getValue().equals("War")){
                sein = true;
            }
            if(entry.getValue().equals("brauchen") || entry.getValue().equals("braucht") || entry.getValue().equals("benötigen") || entry.getValue().equals("benötig") || entry.getValue().equals("Brauchen") || entry.getValue().equals("Brauche") || entry.getValue().equals("gebraucht") || entry.getValue().equals("Gebraucht")){
                brauchen = true;
            }
            if(entry.getValue().equals("suchen") || entry.getValue().equals("sucht") || entry.getValue().equals("Suche") || entry.getValue().equals("Sucht") || entry.getValue().equals("Suchen")){
                suchen = true;
            }
        }

        if(haben || sein || brauchen || suchen){
            for(Map.Entry<String,String> entry: stemmed){
                if((entry.getValue().equals("helfen")   //Stemword
                        || entry.getValue().equals("Hilfe")
                        || entry.getValue().equals("Helfer")) && !kein){  //if the helper isnt negated
                    if(!helper){
                        //Neural Net:
                        double[] featureVector;

                        featureVector = neuralNetNeeds.getFeatureVector(message.getMessage());

                        if(neuralNetNeeds.calcOutput(featureVector)){
                            ret.add(new Need(TypeOfNeed.Helper));
                            helper = true;
                        }
                    }
                }
                if(entry.getValue().equals("kein") || entry.getValue().equals("Kein") || entry.getValue().equals("keine") || entry.getValue().equals("Keine") || entry.getValue().equals("keinen") || entry.getValue().equals("Keinen")){
                    kein = true;
                }else{
                    kein = false;
                }
            }
        }


        if(haben){
            for(Map.Entry<String,String> entry: stemmed){
                if((entry.getValue().equals("Medizin") || entry.getValue().equals("Medikament") || entry.getValue().equals("Medikamente") || entry.getValue().equals("medizin") || entry.getValue().equals("medikament") || entry.getValue().equals("medikamente")) && !kein){  //only if not negated before
                    if(!medicine){
                        ret.add(new Need(TypeOfNeed.MedicalCare));
                        medicine = true;
                    }
                }
                if((entry.getValue().equals("Durst") || entry.getValue().equals("durst")) && !kein){
                    if(!thirsty){
                        ret.add(new Need(TypeOfNeed.Water));
                        thirsty = true;
                    }
                }
                if((entry.getValue().equals("Hunger") || entry.getValue().equals("hunger")) && !kein){
                    if(!hungry){
                        ret.add(new Need(TypeOfNeed.Food));
                        hungry = true;
                    }
                }
                if((entry.getValue().equals("kein") || entry.getValue().equals("Kein") || entry.getValue().equals("keine") || entry.getValue().equals("Keine") || entry.getValue().equals("keinen") || entry.getValue().equals("Keinen"))){
                    kein = true;
                }else{
                    kein = false;
                }
            }
        }

        if(sein){
            for(Map.Entry<String,String> entry: stemmed){
                if((entry.getValue().equals("krank") || entry.getValue().equals("Krank"))  && !kein ){
                    if(!medicine){
                        ret.add(new Need(TypeOfNeed.MedicalCare));
                        medicine = true;
                    }
                }
                if((entry.getValue().equals("obdachlos") || entry.getValue().equals("heimatlos") || entry.getValue().equals("Obdachlos") || entry.getValue().equals("Heimatlos")) && !kein){
                    if(!shelter){
                        ret.add(new Need(TypeOfNeed.Shelter));
                        shelter = true;
                    }
                }
                if((entry.getValue().equals("durstig") || entry.getValue().equals("Durstig")) && !kein){
                    if(!thirsty){
                        ret.add(new Need(TypeOfNeed.Water));
                        thirsty = true;
                    }
                }
                if((entry.getValue().equals("hungrig") || entry.getValue().equals("Hungrig")) && !kein){
                    if(!hungry){
                        ret.add(new Need(TypeOfNeed.Food));
                        hungry = true;
                    }
                }
                if((entry.getValue().equals("nicht") || entry.getValue().equals("Nicht"))){
                    kein = true;
                }else{
                    kein = false;
                }
            }
        }

        if(brauchen || suchen){
            for(Map.Entry<String,String> entry: stemmed){
                if((entry.getValue().equals("Medizin") || entry.getValue().equals("Medikament") || entry.getValue().equals("Versorgung") || entry.getValue().equals("medizin") || entry.getValue().equals("medikament") || entry.getValue().equals("versorgung")  || entry.getValue().equals("medikamente") || entry.getValue().equals("Medikamente")) && !kein){
                    if(!medicine){
                        ret.add(new Need(TypeOfNeed.MedicalCare));
                        medicine = true;
                    }
                }
                if((entry.getValue().equals("Übernachtungsmöglichkeit") || entry.getValue().equals("Unterkunft") || entry.getValue().equals("Bleibe") || entry.getValue().equals("übernachtungsmöglichkeit") || entry.getValue().equals("unterkunft") || entry.getValue().equals("bleibe") || entry.getValue().equals("Unterkünfte") || entry.getValue().equals("unterkünfte")) && !kein){
                    if(!shelter){
                        ret.add(new Need(TypeOfNeed.Shelter));
                        shelter = true;
                    }
                }
                if((entry.getValue().equals("trinken") || entry.getValue().equals("Trinken") || entry.getValue().equals("Wasser") || entry.getValue().equals("wasser") || entry.getValue().equals("Getränke") || entry.getValue().equals("getränke") || entry.getValue().equals("Getränk") || entry.getValue().equals("getränk")) && !kein){
                    if(!thirsty){
                        ret.add(new Need(TypeOfNeed.Water));
                        thirsty = true;
                    }
                }
                if((entry.getValue().equals("essen") || entry.getValue().equals("Essen") || entry.getValue().equals("Nahrungsmittel") || entry.getValue().equals("nahrungsmittel") || entry.getValue().equals("Lebensmittel") || entry.getValue().equals("lebensmittel")) && !kein){
                    if(!hungry){
                        ret.add(new Need(TypeOfNeed.Food));
                        hungry = true;
                    }
                }
                if((entry.getValue().equals("kein") || entry.getValue().equals("Kein") || entry.getValue().equals("keine") || entry.getValue().equals("Keine") || entry.getValue().equals("keinen") || entry.getValue().equals("Keinen"))){
                    kein = true;
                }else{
                    kein = false;
                }
            }
        }

        //Set certainties
        //TODO: maybe process as Stream
        List<Map.Entry<String, POSTagger.PosTag>> posTags = org.haw.cas.TextMiner.Toolbox.POSTagger.getPosTagsFromText(message.getMessage());
        double unknown = 0;
        for(Map.Entry<String,POSTagger.PosTag> posTag : posTags  ){
            if(posTag.getValue().equals(POSTagger.PosTag.UNRECOGNIZED )){
                unknown++;
            }
        }
        double knownWordsCertaintyCoeff = 0;
        if(posTags.size() > 0){
            knownWordsCertaintyCoeff = (posTags.size()-unknown)/(posTags.size());
        }

        int resultsCertainty = (int)((100.0-(ret.size()*10.0))*knownWordsCertaintyCoeff);
        if(resultsCertainty < 1) resultsCertainty = 1;

        for(IInfo need : ret){
            need.setCertainty(resultsCertainty);
        }

        return ret;
    }
}
