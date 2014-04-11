package org.haw.cas.RealTimeCore.NeedsComponent.Implementation;

import org.apache.log4j.Logger;
import org.haw.cas.Adapters.AkkaAdapter.Interface.Exceptions.ConfigurationException;
import org.haw.cas.Adapters.AkkaAdapter.Interface.Exceptions.TechnicalProblemException;
import org.haw.cas.Adapters.AkkaAdapter.Interface.IAkkaAdapter;
import org.haw.cas.Adapters.AkkaAdapter.Interface.Messages.Needs.NeedsMessage;
import org.haw.cas.Adapters.AkkaAdapter.Interface.Messages.Needs.UpdateNeedsMessage;
import org.haw.cas.Adapters.RTCToDataMinerAdapter.Interface.IRTCAdapter;
import org.haw.cas.Adapters.RTCToDataMinerAdapter.Interface.Messages.InfoMessage;
import org.haw.cas.Adapters.RTCToDataMinerAdapter.Interface.Messages.InformationMessage;
import org.haw.cas.Adapters.RTCToDataMinerAdapter.Interface.Messages.LocationMessage;
import org.haw.cas.Adapters.RTCToDataMinerAdapter.Interface.Messages.NeedMessage;
import org.haw.cas.GlobalTypes.MessageInfo.TypeOfInfo;
import org.haw.cas.RealTimeCore.CommonTypes.Entities.GeoCoordinate;
import org.haw.cas.RealTimeCore.CommonTypes.Entities.Need;
import org.haw.cas.RealTimeCore.CommonTypes.Entities.User;
import org.haw.cas.RealTimeCore.UserComponent.Interface.IUserComponent;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Created with IntelliJ IDEA.
 * User: Nils, Jason
 * Date: 05.11.13
 * Time: 00:03
 * <p/>
 * This class implements the logic that is exceuted, when messages arrive from the akka adapter or the text miner.
 */
public class NeedsMessageTransformationUseCase {

    private final IAkkaAdapter akkaAdapter;
    private IUserComponent userComponent;
    private NeedsManagementUseCase needsManagementUseCase;
    private Logger logger = Logger.getLogger(this.getClass());

    public NeedsMessageTransformationUseCase(IRTCAdapter textMinerAdapter,
                                             IAkkaAdapter akkaAdapter,
                                             IUserComponent userComponent,
                                             NeedsManagementUseCase needsManagementUseCase) {
        this.akkaAdapter = akkaAdapter;
        this.userComponent = userComponent;
        this.needsManagementUseCase = needsManagementUseCase;

        textMinerAdapter.subscribeForInformationMessage(InformationMessage.class, this::handleIncomingInformationMessage);
        akkaAdapter.subscribeForMessage(UpdateNeedsMessage.class, this::handleIncomingUpdateNeedsMessage);
    }

    /**
     * Answers with a list of needs.
     *
     * @param updateNeedsMessage not null
     */
    private void handleIncomingUpdateNeedsMessage(UpdateNeedsMessage updateNeedsMessage) {
        logger.debug("Received UpdateNeedsMessage.");
        sendCurrentNeeds(updateNeedsMessage.getSender());
    }

    /**
     * Sends a message with the given sender to the Akka server
     *
     * @param sender not null
     */
    private void sendCurrentNeeds(String sender) {
        try {
            Collection<Need> sendList = needsManagementUseCase.getAll().parallelStream()
                    .filter(n -> n.getGeoCoordiante() != null)
                    .collect(Collectors.toList());

            //cant build protomesasge with an empty list
            if (!sender.isEmpty()) {
                NeedsMessage msg = new NeedsMessage(sender, sendList);
                akkaAdapter.send(msg);
                logger.info("Sent UpdateNeedsMessage to Akka");

                if(logger.isDebugEnabled()){
                    String sendingNeedsString="";
                    for(Need n : sendList){
                        sendingNeedsString += n + ", ";
                    }
                    logger.debug("The following needs are send to the GUI: " + sendingNeedsString);

                }

            }
        } catch (TechnicalProblemException | ConfigurationException e) {
            e.printStackTrace();
        }
    }

    /**
     * Check the incoming information message for a needs part,
     * convert and save it if found.
     *
     * @param message the incoming information message. not null
     */
    private void handleIncomingInformationMessage(InformationMessage message) {
        logger.debug("NeedsComponent receives an information message.");
        boolean showable = false;
        //check, if the incoming message contains a needs component (else irrelevant -> do nothing)
        for (InfoMessage info : message.getInfos()) {
            if (info.getType() == TypeOfInfo.Need) {
                Need need = getNeedFromInformationMessage((NeedMessage) info, message);
                needsManagementUseCase.createNeed(need);
                showable |= need.getGeoCoordiante() != null;
            }
        }

        if(showable) {
            sendCurrentNeeds("needs");
        }
    }

    /**
     * Extracts the need entity from a information message and the corresponding informationmessage
     *
     * @param needs              not null
     * @param informationMessage not null
     * @return not null
     */
    private Need getNeedFromInformationMessage(NeedMessage needs, InformationMessage informationMessage) {
        logger.debug(" informationMessage contains a need ");
        User user = userComponent.resolveUser(informationMessage.getAuthor());

        GeoCoordinate position = null;
        LocationMessage mostLikelyLocation = null;
        LocationMessage locationMessage;
        int highestCertainty = Integer.MIN_VALUE;

        List<InfoMessage> infoMessageList = informationMessage.getInfos();

        for (InfoMessage infoMessage : infoMessageList) {
            if (infoMessage.getType() == TypeOfInfo.Location) {
                locationMessage = (LocationMessage) infoMessage;
                if(highestCertainty < locationMessage.getCertainty()){
                    logger.debug(highestCertainty + " < " + locationMessage.getCertainty());
                    mostLikelyLocation = locationMessage;
                    highestCertainty = locationMessage.getCertainty();
                }

            }
        }

        if (mostLikelyLocation != null) {
            position = new GeoCoordinate(mostLikelyLocation.getLatitude(), mostLikelyLocation.getLongitude());
            logger.debug("new position provenance is an informationMessage " + position);
        } else if (user.hasPosition()) {
            logger.debug("informationMessage does not contain a position, take last userPosition as position for the new need");
            position = user.getLastPosition().getGeoCoordinate();
        }

        if (informationMessage.getProvenance() == null) {
            logger.error("NeedsMessageTransformationUseCase: provenance ist null!");
        } else {
            logger.debug("NeedsMessageTransformationUseCase: provenance ist " + informationMessage.getProvenance());
        }
        logger.debug("the new needs message type is " + needs.getTypeOfNeed());
        Need need = new Need(user, needs.getTypeOfNeed(), position, informationMessage.getPublishTime(), informationMessage.getText(), informationMessage.getProvenance());

        return need;
    }
}