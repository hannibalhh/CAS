package org.haw.cas.RealTimeCore.CrevasseComponent.Implementation;

import org.apache.log4j.Logger;
import org.haw.cas.Adapters.AkkaAdapter.Interface.IAkkaAdapter;
import org.haw.cas.Adapters.RTCToDataMinerAdapter.Interface.IRTCAdapter;
import org.haw.cas.RealTimeCore.CommonTypes.Entities.Crevasse;
import org.haw.cas.RealTimeCore.CrevasseComponent.Interface.ICrevasseComponent;
import org.haw.cas.RealTimeCore.UserComponent.Interface.IUserComponent;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.LinkedList;

/**
 * Created with IntelliJ IDEA.
 * User: sebastian, Jason
 * Date: 30.10.13
 * Time: 15:21
 * <p/>
 */
public class CrevasseComponent implements ICrevasseComponent {

    private final Logger logger = Logger.getLogger(CrevasseComponent.class);
    private final CrevasseManagementUseCase crevasseManagementUseCase;
    private final InformationMessageReceivingUseCase informationMessageReceivingUseCase;

    public CrevasseComponent(IRTCAdapter rtcAdapter, IAkkaAdapter akkaAdapter, IUserComponent userComponent){
        crevasseManagementUseCase = new CrevasseManagementUseCase();
        informationMessageReceivingUseCase = new InformationMessageReceivingUseCase(rtcAdapter, akkaAdapter, crevasseManagementUseCase, userComponent);
        logger.debug("initialized");
    }

    @Override
    public void createCrevasse(Crevasse crevasse) {
        crevasseManagementUseCase.create(crevasse);
    }

    @Override
    public Collection<Crevasse> getAllCrevasses() {
       return crevasseManagementUseCase.getAll();
    }

    @Override
    public Collection<Crevasse> getAllCrevasses(LocalDateTime from, LocalDateTime to) {
        return crevasseManagementUseCase.getAllCrevasses(from, to);
    }

    @Override
    public Collection<Crevasse> getCrevassesWithPositions(LocalDateTime from, LocalDateTime to) {
        return crevasseManagementUseCase.getCrevassesWithPositions(from, to);
    }

    @Override
    public Collection<Crevasse> getCrevassesWithoutPositions(LocalDateTime from, LocalDateTime to) {
        return crevasseManagementUseCase.getCrevassesWithoutPositions(from, to);
    }

    @Override
    public int getCrevasseCountWithPosition(LocalDateTime from, LocalDateTime to) {
        return crevasseManagementUseCase.getCrevasseCountWithPosition(from, to);
    }

    @Override
    public int getCrevasseCountWithoutPosition(LocalDateTime from, LocalDateTime to) {
        return crevasseManagementUseCase.getCrevasseCountWithoutPosition(from, to);
    }

    @Override
    public int getCrevasseCount(LocalDateTime from, LocalDateTime to) {
        return crevasseManagementUseCase.getCrevasseCount(from, to);
    }
}
