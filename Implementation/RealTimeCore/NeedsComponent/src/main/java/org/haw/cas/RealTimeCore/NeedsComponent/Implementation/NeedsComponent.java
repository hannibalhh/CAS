package org.haw.cas.RealTimeCore.NeedsComponent.Implementation;

import org.apache.log4j.Logger;
import org.haw.cas.Adapters.AkkaAdapter.Interface.IAkkaAdapter;
import org.haw.cas.Adapters.RTCToDataMinerAdapter.Interface.IRTCAdapter;
import org.haw.cas.GlobalTypes.MessageInfo.TypeOfNeed;
import org.haw.cas.RealTimeCore.CommonTypes.Entities.Need;
import org.haw.cas.RealTimeCore.NeedsComponent.Interface.INeedsComponent;
import org.haw.cas.RealTimeCore.UserComponent.Implementation.UserComponent;
import org.haw.cas.RealTimeCore.UserComponent.Interface.IUserComponent;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: j-wil_000
 * Date: 27.10.13
 * Time: 01:02
 * <p/>
 * This is the interface of the Needs component.
 */
public class NeedsComponent implements INeedsComponent {

    private NeedsMessageTransformationUseCase needsMessageTransformationUseCase;
    private NeedsManagementUseCase needsManagementUseCase;
    private static final Logger logger = Logger.getLogger(NeedsComponent.class);

    public NeedsComponent(IRTCAdapter textMinerAdapter,
                          IAkkaAdapter akkaAdapter,
                          IUserComponent userComponent) {

        needsManagementUseCase = new NeedsManagementUseCase();
        this.needsMessageTransformationUseCase = new NeedsMessageTransformationUseCase(textMinerAdapter,
                akkaAdapter,
                userComponent,
                needsManagementUseCase);

        logger.debug("initialized");
    }

    @Override
    public void createNeed(Need need) {
         needsManagementUseCase.createNeed(need);
    }

    @Override
    public Collection<Need> getAllNeeds() {
      return needsManagementUseCase.getAll();
    }

    @Override
    public float getNeedCountWithPosition(TypeOfNeed type, LocalDateTime from, LocalDateTime to) {
        return needsManagementUseCase.getNeedCountWithPosition(type, from, to);
    }

    @Override
    public float getNeedCountWithoutPosition(TypeOfNeed type, LocalDateTime from, LocalDateTime to) {
        return needsManagementUseCase.getNeedCountWithoutPosition(type, from, to);
    }

    @Override
    public float getNeedCount(TypeOfNeed type, LocalDateTime from, LocalDateTime to) {
        return needsManagementUseCase.getNeedCount(type, from, to);
    }

    @Override
    public Collection<Need> getNeedsWithoutPosition(LocalDateTime from, LocalDateTime to) {
        return needsManagementUseCase.getNeedsWithoutPosition(from, to);
    }


}
