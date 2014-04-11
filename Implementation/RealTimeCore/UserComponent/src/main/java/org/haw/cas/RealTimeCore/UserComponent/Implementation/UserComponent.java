package org.haw.cas.RealTimeCore.UserComponent.Implementation;

import org.apache.log4j.Logger;
import org.haw.cas.Adapters.AkkaAdapter.Interface.IAkkaAdapter;
import org.haw.cas.Adapters.PersistenceManager.Implementation.Exception.EntityAlreadyExistException;
import org.haw.cas.Adapters.RTCToDataMinerAdapter.Interface.IRTCAdapter;
import org.haw.cas.RealTimeCore.CommonTypes.Entities.GeoCoordinate;
import org.haw.cas.RealTimeCore.CommonTypes.Entities.User;
import org.haw.cas.RealTimeCore.UserComponent.Implementation.UseCases.AkkaMessageReceivingUseCase;
import org.haw.cas.RealTimeCore.UserComponent.Implementation.UseCases.InformationMessageReceivingUseCase;
import org.haw.cas.RealTimeCore.UserComponent.Implementation.UseCases.UserManagementUseCase;
import org.haw.cas.RealTimeCore.UserComponent.Interface.IUserComponent;

/**
 * Created with IntelliJ IDEA.
 * User: j-wil_000
 * Date: 27.10.13
 * Time: 00:36
 *
 * This class represents the user component within the application core.
 */
public class UserComponent implements IUserComponent {

    private UserManagementUseCase userManagementUseCase;
    private InformationMessageReceivingUseCase informationMessageReceivingUseCase;
    private AkkaMessageReceivingUseCase akkaMessageReceivingUseCase;
    private static final Logger logger = Logger.getLogger(UserComponent.class);

    public UserComponent(IRTCAdapter dataMinerAdapter, IAkkaAdapter akkaAdapter) {
        userManagementUseCase = new UserManagementUseCase(akkaAdapter);
        informationMessageReceivingUseCase = new InformationMessageReceivingUseCase(dataMinerAdapter, akkaAdapter, userManagementUseCase);
        akkaMessageReceivingUseCase = new AkkaMessageReceivingUseCase(akkaAdapter, userManagementUseCase);
        logger.debug("initialized");
    }

    @Override
    public void createUser(User user) throws EntityAlreadyExistException {
        userManagementUseCase.createUser(user);
    }

    @Override
    public GeoCoordinate getLatestPosition(User user) {
        return userManagementUseCase.getLatestPosition(user);
    }

    @Override
    public User resolveUser(String nick) {
        return userManagementUseCase.resolveUser(nick);
    }
}
