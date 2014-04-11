package org.haw.cas.RealTimeCore.UserComponent.Implementation.UseCases;

import org.apache.log4j.Logger;
import org.haw.cas.Adapters.AkkaAdapter.Interface.Exceptions.ConfigurationException;
import org.haw.cas.Adapters.AkkaAdapter.Interface.Exceptions.TechnicalProblemException;
import org.haw.cas.Adapters.AkkaAdapter.Interface.IAkkaAdapter;
import org.haw.cas.Adapters.AkkaAdapter.Interface.Messages.Positions.UpdateUserPositionsMessage;
import org.haw.cas.Adapters.AkkaAdapter.Interface.Messages.Positions.UserPositionsMessage;
import org.haw.cas.DataMiningTools.TextMining.HarversineFormula;
import org.haw.cas.DataMiningTools.TextMining.IGeoCoordinateDistanceCalculator;
import org.haw.cas.RealTimeCore.CommonTypes.Entities.User;
import org.haw.cas.RealTimeCore.UserComponent.Implementation.UserDAO;

import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: j-wil_000
 * Date: 22.11.13
 * Time: 19:09
 * <p/>
 * This class implements the logic that is executed
 */
public class AkkaMessageReceivingUseCase {

    private final IAkkaAdapter akkaAdapter;
    private final UserManagementUseCase userManagement;
    private final UserDAO userDAO;
    private final Logger logger = Logger.getLogger(AkkaMessageReceivingUseCase.class);
    private final IGeoCoordinateDistanceCalculator distanceCalculator;

    public AkkaMessageReceivingUseCase(IAkkaAdapter akkaAdapter, UserManagementUseCase userManagement) {
        this.akkaAdapter = akkaAdapter;
        this.userManagement = userManagement;
        this.userDAO = new UserDAO();
        this.distanceCalculator = new HarversineFormula();

        akkaAdapter.subscribeForMessage(UpdateUserPositionsMessage.class, this::handleIncomingUpdateUserPositionsMessage);
    }

    /**
     * Handles incoming UpdateUserPositionsMessages. Answers in the direction of Akka with a list of all users with
     * positions as specified in the UpdateUserPositionsMessage.
     * @param userPositionsMessage not null
     */
    public void handleIncomingUpdateUserPositionsMessage(UpdateUserPositionsMessage userPositionsMessage) {

        /* find all users with a position within the circle described by center and radius of the
        updateUserPositionsMessage and return them. */

        List<User> users = new LinkedList<>();
        userManagement.getAllUsersWithKnownPosition().forEach(u -> {
            if(u.getLastPosition().getDate().isAfter(userPositionsMessage.getOldest())
                && distanceCalculator.calculateDistance(u.getLastPosition().getGeoCoordinate(), userPositionsMessage.getCenter())
                    <= userPositionsMessage.getRadius()) {
                users.add(u);
            }
        });

        try {
            akkaAdapter.send(new UserPositionsMessage(userPositionsMessage.getRequestId(), users));
            logger.info("Sent UserPositionsMessage to Akka.");
        } catch (TechnicalProblemException | ConfigurationException e) {
            logger.error("An error occurred while sending a UserPositionsUpdate to Akka. See exception for details"
            + e.getLocalizedMessage());
        }
    }
}
