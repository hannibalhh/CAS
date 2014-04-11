package Implementation.Component;

import Coordinator.Component.CoordinatorComponent;
import Coordinator.Interface.abstractClasses.AbstractAnalyzer;
import CrevasseAnalyzer.Interface.FeatureVectorExtractor;
import CrevasseAnalyzer.Interface.ICrevasseAnalyzer;
import CrevasseAnalyzer.Interface.ICrevasseClassifier;
import DSPersistenceManager.Model.Message;
import DataTypes.Crevasse;
import DataTypes.DataTypeInterfaces.IInfo;
import Implementation.classifiers.CrevasseClassifierNeuralNet;
import Implementation.classifiers.FeatureVectorExtractor_SurroundingWords;
import Implementation.classifiers.SimpleCrevasseClassifier;
import MessageAdapter.Interface.IMessageAdapter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.haw.cas.TextMiner.Toolbox.NeuralNetworkWrapper;
import org.haw.cas.TextMiner.Toolbox.POSTagger;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: sebastian
 * Date: 30.10.13
 * Time: 13:26
 * To change this template use File | Settings | File Templates.
 */
public class CrevasseAnalyzerComponent extends AbstractAnalyzer implements ICrevasseAnalyzer{
    private ICrevasseClassifier crevasseClassifier;
    private static Logger logger = LogManager.getLogger(CrevasseAnalyzerComponent.class);

    public CrevasseAnalyzerComponent(IMessageAdapter messageAdapter, CoordinatorComponent coordinatorComponent){
        super(messageAdapter, coordinatorComponent);

        File crevasseNnetFile = null;
        try {
            crevasseNnetFile = new File("./CrevasseAnalyzer/resources/crevasseNet.nnet").getCanonicalFile();
            NeuralNetworkWrapper neuralNet = new NeuralNetworkWrapper(crevasseNnetFile);

            List<String> wordVector = CrevasseClassifierNeuralNet.getSampleWordVectorFromFile(
                    new File("./CrevasseAnalyzer/resources/wordVector.txt").getCanonicalFile());

            FeatureVectorExtractor vectorExtractor = new FeatureVectorExtractor_SurroundingWords(wordVector);

            this.crevasseClassifier = new CrevasseClassifierNeuralNet(neuralNet,vectorExtractor,0.4);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    protected Map<Message, List<IInfo>> processMessages(Iterable<Message> messages) {
        logger.debug("Got Message Chunk");
        Map<Message, List<IInfo>> result = new HashMap<>();
        for(Message currentMessage : messages){
            List<IInfo> currentResult = new ArrayList<>();
            currentResult.add(processMessage(currentMessage));

            result.put(currentMessage, currentResult);
        }
        return result;
    }

    private IInfo processMessage(Message message) {

        if(message.getMessage() == null){
            logger.debug("Got Message with null in field message: " + message);
            return null;
        }

        //calculate certainty
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

        int resultsCertainty = (int)((90.0)*knownWordsCertaintyCoeff);
        if(resultsCertainty < 1) resultsCertainty = 1;



        if(crevasseClassifier.crevasseReportIncluded(message.getMessage())){
            IInfo info =  new Crevasse(resultsCertainty);
            logger.debug("Classified as Crevasse: " + message);
            return info;
        }else{
            logger.debug("Classified as non Crevasse: " + message);
            return null;
        }
    }




}

