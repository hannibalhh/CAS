package Coordinator.Component;

import Coordinator.Interface.ICoordinator;
import Coordinator.UseCases.CoordinateMessageAdapterAndAnalyzers;
import DataTypes.DataTypeInterfaces.IInfo;
import Interfaces.IAnalyzer;

import MessageAdapter.Interface.IMessageAdapter;
import org.haw.cas.Adapters.RTCToDataMinerAdapter.Interface.IRTCAdapter;


import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Raphael
 * Date: 21.10.13
 * Time: 22:02
 * To change this template use File | Settings | File Templates.
 */
public class CoordinatorComponent implements ICoordinator{

    private Thread handlingThread;

    public CoordinateMessageAdapterAndAnalyzers handlingUseCase;

    public CoordinatorComponent(IMessageAdapter messageAdapter, IRTCAdapter rtcAdapter) {
        handlingUseCase = new CoordinateMessageAdapterAndAnalyzers(messageAdapter, rtcAdapter);
        handlingThread = new Thread(handlingUseCase);
    }

    @Override
    public void startCoordinating() {
        handlingThread.start();
    }

    @Override
    public void stopCoordinating() {
        handlingUseCase.stopCoordinating();
    }

    public void registerAnalyzerResultForCurrentChunk(IAnalyzer analyzer, Map<String, List<IInfo>> resultsForChunk) {
        handlingUseCase.registerAnalyzerResultForCurrentChunk(analyzer, resultsForChunk);
    }

    public void registerAnalyzer(IAnalyzer analyzer) {
        handlingUseCase.registerAnalyzer(analyzer);
    }

}
