package org.haw.cas.RealTimeCore.UserComponent.Implementation.UseCases;

import org.apache.log4j.Logger;
import org.haw.cas.Adapters.AkkaAdapter.Interface.Exceptions.ConfigurationException;
import org.haw.cas.Adapters.AkkaAdapter.Interface.Exceptions.TechnicalProblemException;
import org.haw.cas.Adapters.AkkaAdapter.Interface.IAkkaAdapter;
import org.haw.cas.Adapters.AkkaAdapter.Interface.Messages.Positions.UserPositionsMessage;
import org.haw.cas.Adapters.PersistenceManager.Implementation.Exception.EntityAlreadyExistException;
import org.haw.cas.Adapters.RTCToDataMinerAdapter.Interface.IRTCAdapter;
import org.haw.cas.Adapters.RTCToDataMinerAdapter.Interface.Messages.InfoMessage;
import org.haw.cas.Adapters.RTCToDataMinerAdapter.Interface.Messages.InformationMessage;
import org.haw.cas.Adapters.RTCToDataMinerAdapter.Interface.Messages.LocationMessage;
import org.haw.cas.DataMiningTools.TextMining.HarversineFormula;
import org.haw.cas.DataMiningTools.TextMining.IGeoCoordinateDistanceCalculator;
import org.haw.cas.GlobalTypes.MessageInfo.TypeOfInfo;
import org.haw.cas.RealTimeCore.CommonTypes.Entities.GeoCoordinate;
import org.haw.cas.RealTimeCore.CommonTypes.Entities.Position;
import org.haw.cas.RealTimeCore.CommonTypes.Entities.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: j-wil_000
 * Date: 04.11.13
 * Time: 23:49
 * <p/>
 * This class implements the logic, that is executed, if an information message is received by the RTC.
 */
public class InformationMessageReceivingUseCase {

    private final UserManagementUseCase userManagementUseCase;
    private final IAkkaAdapter akkaAdapter;
    private Logger logger = Logger.getLogger(InformationMessageReceivingUseCase.class);
    private IGeoCoordinateDistanceCalculator geoCoordinateDistanceCalculator;

    public InformationMessageReceivingUseCase(IRTCAdapter dataMinerAdapter, IAkkaAdapter akkaAdapter, UserManagementUseCase userManagementUseCase) {
        this.akkaAdapter = akkaAdapter;
        this.userManagementUseCase = userManagementUseCase;
        this.geoCoordinateDistanceCalculator = new HarversineFormula();
        dataMinerAdapter.subscribeForInformationMessage(InformationMessage.class, this::handleIncomingInformationMessage);
    }

    /**
     * Creates the user, if not already exists and updates its position, if possible.
     *
     * @param informationMessage
     */
    private void handleIncomingInformationMessage(InformationMessage informationMessage) {
        // retrieve the corresponding user (if someone with similar nick known, him, else create one)
        User user = getUser(informationMessage.getAuthor());
        List<Position> possibleNewPositions = findPossiblePositions(informationMessage);

        logger.debug("receiving user information");

        if (!possibleNewPositions.isEmpty()) {
            Position position = checkPositionPlausibility(possibleNewPositions, user);
            logger.debug("found a Position for user with id " + user.getId() +  " new Position is " + position.getGeoCoordinate());
            user.addPosition(position);
            userManagementUseCase.update(user);
            try {
                UserPositionsMessage msg = new UserPositionsMessage("broadcast", userManagementUseCase.getAll());
                logger.debug("trying to send new user positions to akka: " + msg);
                akkaAdapter.send(msg);
                logger.info("Found a new position for user. Sent UserPositionsMessage to Akka");
            } catch (TechnicalProblemException | ConfigurationException e) {
                logger.error("Was unable to send user updates to akka.", e);
            }
        }
    }

    /**
     * Returns the most likely new position of the given user for a given set of possibilities.
     * If a position was previously calculated for this user we assume that the position from the  possibleNewPositions
     * list with the smallest distance is the most like new position.
     *
     * @param possibleNewPositions list of options, not null
     * @param user the user, whose position is to be updated.
     * @return the new position for the user
     */
    private Position checkPositionPlausibility(List<Position> possibleNewPositions, User user) {
        //If a list with only one position is given this position is the new user position
        if (possibleNewPositions.size() == 1) return possibleNewPositions.iterator().next();

        Position newPosition = null;
        //if we previously calculated a position for the given user the position with the smallest distance to his last position will be chosen as the new distance
        if (user.hasPosition()) {
            logger.debug("user has last postion");

            Position usersLastPosition = user.getLastPosition();

            double distanceToLastPosition = Double.MAX_VALUE;

            for (Position p : possibleNewPositions) {
                double distanceBetweenPAndUsersLastPosition = geoCoordinateDistanceCalculator.calculateDistance(p.getGeoCoordinate(), usersLastPosition.getGeoCoordinate());
                if (distanceBetweenPAndUsersLastPosition < distanceToLastPosition) {
                    logger.debug("distanceToLastUserPostion was " + distanceToLastPosition + " new smaller distance is " + distanceBetweenPAndUsersLastPosition);
                    distanceToLastPosition = distanceBetweenPAndUsersLastPosition;
                    newPosition = p;
                }
            }

        }
        //if we never calculated a position for the given user the position with the highest certainty will be his new position.
        else
        {
            logger.debug("first postion for given user, try to find the most possible position");
            int highestCertainty = -1;
            for(Position p : possibleNewPositions){
                if(p.getCertainty() > highestCertainty){
                    logger.debug("postion with highest certanity is "+  p.getGeoCoordinate() + " certanity is " + p.getCertainty() );
                    highestCertainty = p.getCertainty();
                    newPosition = p;
                }
            }
        }

        logger.debug("new postion for user is " + newPosition.getGeoCoordinate());
        return newPosition;
    }

    /**
     * Tries to parse the possible locations from the message.
     *
     * @param informationMessage The message to examine. not null!
     * @return an empty list if no location was found.
     */
    private List<Position> findPossiblePositions(InformationMessage informationMessage) {

        List<Position> positionList = new ArrayList<>();
        List<InfoMessage> infoMessageList = informationMessage.getInfos();

        for (InfoMessage infoMessage : infoMessageList) {
            if (infoMessage.getType().equals(TypeOfInfo.Location)) {
                LocationMessage locationMessage = (LocationMessage) infoMessage;
                positionList.add(new Position(new GeoCoordinate(locationMessage.getLatitude(), locationMessage.getLongitude()),
                        informationMessage.getPublishTime(),
                        locationMessage.getCertainty(),
                        informationMessage.getProvenance(),
                        informationMessage.getText()
                ));
            }
        }

        return positionList;
    }

    /**
     * Gives a user for the given name. If a user with a similar (distance < 2) alias already exists, it will be used
     * <br/>
     * Else a new user with that alias will be created and returned.
     *
     * @param nick not null
     * @return a user that exists in the persistence
     */
    private User getUser(String nick) {

        User result = userManagementUseCase.resolveUser(nick);

        if (result == null) {

            result = new User(nick);

            try {
                userManagementUseCase.createUser(result);
                //System.out.println("in InformationMessageReceivingUseCase line 92: creating new User " + result + "user id is " + result.getId());
            } catch (EntityAlreadyExistException e) {
                logger.warn("user could not be created, because it already existed, but the previous check should have recognized that.");
            }
        }

        if (!result.getAliases().contains(nick)) {
            result.addAlias(nick);
        }

        return result;
    }
}
