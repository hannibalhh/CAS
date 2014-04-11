package org.haw.cas.RealTimeCore.CrevasseComponent.Implementation;

import org.apache.log4j.Logger;
import org.haw.cas.Adapters.AkkaAdapter.Interface.Exceptions.ConfigurationException;
import org.haw.cas.Adapters.AkkaAdapter.Interface.Exceptions.TechnicalProblemException;
import org.haw.cas.Adapters.AkkaAdapter.Interface.IAkkaAdapter;
import org.haw.cas.Adapters.AkkaAdapter.Interface.Messages.Crevasses.CrevassesMessage;
import org.haw.cas.Adapters.RTCToDataMinerAdapter.Interface.IRTCAdapter;
import org.haw.cas.Adapters.RTCToDataMinerAdapter.Interface.Messages.CrevasseMessage;
import org.haw.cas.Adapters.RTCToDataMinerAdapter.Interface.Messages.InfoMessage;
import org.haw.cas.Adapters.RTCToDataMinerAdapter.Interface.Messages.InformationMessage;
import org.haw.cas.Adapters.RTCToDataMinerAdapter.Interface.Messages.LocationMessage;
import org.haw.cas.GlobalTypes.MessageInfo.TypeOfInfo;
import org.haw.cas.RealTimeCore.CommonTypes.Entities.Crevasse;
import org.haw.cas.RealTimeCore.CommonTypes.Entities.GeoCoordinate;
import org.haw.cas.RealTimeCore.CommonTypes.Entities.User;
import org.haw.cas.RealTimeCore.UserComponent.Interface.IUserComponent;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * User: Sebastian, Jason Wilmans
 * Date: 05.11.13
 * Time: 13:24
 * <p/>
 * This class implements the logic of checking incoming information messages for crevasse entries.
 */
class InformationMessageReceivingUseCase {

    private final IAkkaAdapter akkaAdapter;
    private final CrevasseManagementUseCase crevasseManagementUseCase;
    private final IUserComponent userComponent;
    private final Logger logger = Logger.getLogger(InformationMessageReceivingUseCase.class);

    InformationMessageReceivingUseCase(
            IRTCAdapter rtcAdapter,
            IAkkaAdapter akkaAdapter,
            CrevasseManagementUseCase crevasseManagementUseCase,
            IUserComponent userComponent) {

        this.akkaAdapter = akkaAdapter;
        this.crevasseManagementUseCase = crevasseManagementUseCase;
        this.userComponent = userComponent;
        rtcAdapter.subscribeForInformationMessage(InformationMessage.class, this::handleIncomingInformationMessage);
    }

    private void handleIncomingInformationMessage(InformationMessage informationMessage) {

        // TODO: til now all messages are needs-messages  or crevasse
        List<InfoMessage> infos = informationMessage.getInfos();

        LocationMessage locationMessage = null;
        LocationMessage mostLikelyLocation = null;
        CrevasseMessage crevasseMessage = null;
        int highestCertainty = Integer.MIN_VALUE;


        for (InfoMessage info : infos) {
            //TODO: without cast!!! Stupid to get object and then ask the object if it is true!!!
            if (info.getType() == (TypeOfInfo.Crevasse)) {
                crevasseMessage = (CrevasseMessage) info;
            }
            if (info.getType() == TypeOfInfo.Location) {
                locationMessage = (LocationMessage) info;
                if(highestCertainty < locationMessage.getCertainty()){
                    mostLikelyLocation = locationMessage;
                    highestCertainty = locationMessage.getCertainty();
                }
            }
        }

        if (crevasseMessage != null && mostLikelyLocation != null) {
            Crevasse crevasse = extractCrevasse(informationMessage, crevasseMessage, mostLikelyLocation);

            crevasseManagementUseCase.create(crevasse);
            CrevassesMessage message = new CrevassesMessage(new LinkedList(crevasseManagementUseCase.getAll()));

            try {
                logger.debug("Sending crevasse messag to akka: " + message);
                akkaAdapter.send(message);
                logger.info("Sent CrevassesMessage to Akka");
            } catch (TechnicalProblemException | ConfigurationException e) {
                logger.error("Unable to send CrevasseMessage to Akka Server." + message, e);
            }
        }

    }

    private Crevasse extractCrevasse(InformationMessage informationMessage, CrevasseMessage crevasseMessage, LocationMessage locationMessage) {

        User user = userComponent.resolveUser(informationMessage.getAuthor());


        GeoCoordinate geoCoordinate = new GeoCoordinate((float) locationMessage.getLatitude(), (float) locationMessage.getLongitude());

        return new Crevasse(user, geoCoordinate, informationMessage.getPublishTime(), informationMessage.getText(), informationMessage.getProvenance());

    }
}
