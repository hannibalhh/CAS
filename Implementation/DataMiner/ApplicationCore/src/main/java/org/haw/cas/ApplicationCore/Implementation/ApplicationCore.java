package org.haw.cas.ApplicationCore.Implementation;

import org.haw.cas.ApplicationCore.Interface.IDataMiner;
import Coordinator.Interface.ICoordinator;
import CrevasseAnalyzer.Interface.ICrevasseAnalyzer;
import MessageAdapter.Interface.IMessageAdapter;
import NeedsAnalyzer.Interface.INeedsAnalyzer;
import org.haw.cas.PlaceOfResidenceAnalyzer.Interface.IPlaceOfResidenceAnalyzer;
import org.haw.cas.Adapters.RTCToDataMinerAdapter.Interface.IRTCAdapter;

/**
 * Created with IntelliJ IDEA.
 * User: Raphael
 * Date: 21.10.13
 * Time: 19:59
 * To change this template use File | Settings | File Templates.
 */
public class ApplicationCore implements IDataMiner {
    private ICoordinator coordinator;
    private IPlaceOfResidenceAnalyzer placeOfResidenceAnalyzer;
    private ICrevasseAnalyzer crevasseAnalyzer;
    private INeedsAnalyzer needsAnalyzer;
    private IMessageAdapter messageAdapter;
    private IRTCAdapter rtcAdapterComponent;

    public ApplicationCore(ICoordinator coordinatorComponent, IPlaceOfResidenceAnalyzer placeOfResidenceAnalyzer, ICrevasseAnalyzer crevasseAnalyzer, INeedsAnalyzer needsAnalyzer, IMessageAdapter messageAdapterComponent, IRTCAdapter rtcAdapterComponent) {
        this.coordinator = coordinatorComponent;
        this.placeOfResidenceAnalyzer = placeOfResidenceAnalyzer;
        this.crevasseAnalyzer = crevasseAnalyzer;
        this.needsAnalyzer =  needsAnalyzer;
        this.messageAdapter = messageAdapterComponent;
        this.rtcAdapterComponent = rtcAdapterComponent;
    }


    @Override
    public void startAll() {
        this.coordinator.startCoordinating();
        this.messageAdapter.obtainNewMessages();
        this.placeOfResidenceAnalyzer.startAnalyzer(); //Todo: wenn semaphor behoben wieder einfuegen
        this.crevasseAnalyzer.startAnalyzer();
        this.needsAnalyzer.startAnalyzer();
    }

    @Override
    public void stopAll() {
        this.messageAdapter.stopObtainingNewMessages();
        this.coordinator.stopCoordinating();
        this.placeOfResidenceAnalyzer.stopAnalyzer(); //Todo: wenn semaphor behoben wieder einfuegen
        this.crevasseAnalyzer.stopAnalyzer();
        this.needsAnalyzer.stopAnalyzer();
    }
}
