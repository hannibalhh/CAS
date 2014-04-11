package NeedsAnalyzer.Implementation.Component;

import Coordinator.Component.CoordinatorComponent;
import Coordinator.Interface.ICoordinator;
import Coordinator.Interface.abstractClasses.AbstractAnalyzer;
import DSPersistenceManager.Model.Message;
import DataTypes.DataTypeInterfaces.IInfo;
import Interfaces.IAnalyzer;
import MessageAdapter.Interface.IMessageAdapter;
import NeedsAnalyzer.Implementation.Usecases.AnalyzeChunkForNeeds;
import NeedsAnalyzer.Implementation.Usecases.CreateWordVector;
import NeedsAnalyzer.Implementation.Usecases.NeuralNetNeeds;
import NeedsAnalyzer.Interface.INeedsAnalyzer;

import NeedsAnalyzer.Implementation.Usecases.AnalyzeChunkForNeeds.*;
import org.haw.cas.TextMiner.Toolbox.Exceptions.GetWordStemsFailedException;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Jonas
 * Date: 11.11.13
 * Time: 18:46
 * To change this template use File | Settings | File Templates.
 */
public class NeedsAnalyzerComponent extends AbstractAnalyzer implements INeedsAnalyzer{


    private AnalyzeChunkForNeeds analyzeChunkForNeeds;

    public NeedsAnalyzerComponent(IMessageAdapter messageAdapter, ICoordinator coordinator) {
        super(messageAdapter, coordinator);
        analyzeChunkForNeeds = new AnalyzeChunkForNeeds(new NeuralNetNeeds(0.4,CreateWordVector.getWordVector()));
    }

    @Override
    protected Map<Message, List<IInfo>> processMessages(Iterable<Message> messages) throws GetWordStemsFailedException {
        Map<Message, List<IInfo>> result = new HashMap<>();
        for(Message currentMessage : messages){
            result.put(currentMessage, analyzeChunkForNeeds.processMessage(currentMessage));
        }
        return result;
    }
}
