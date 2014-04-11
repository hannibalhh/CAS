package org.haw.cas.Adapters.RTCToDataMinerAdapter.Implementation.builder.information;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.haw.cas.Adapters.RTCToDataMinerAdapter.Implementation.data.messageTyp.java.InformationMessage.InMessage;
import org.haw.cas.Adapters.RTCToDataMinerAdapter.Implementation.data.messageTyp.java.InformationMessage.InMetadataMessage;
import org.haw.cas.Adapters.RTCToDataMinerAdapter.Implementation.data.messageTyp.java.InformationMessage.Information;
import org.haw.cas.Adapters.RTCToDataMinerAdapter.Interface.Messages.*;
import org.haw.cas.GlobalTypes.MessageInfo.Provenance;
import org.haw.cas.GlobalTypes.MessageInfo.TypeOfNeed;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class BuildInformation {

    private static Logger logger = LogManager.getLogger(BuildInformation.class);

    /**
     * This method builds a InformationMessage from a ProtoBuf
     * Information-Message.
     *
     * @param m this should be converted
     * @return the new InformationMessage build from Information-Message
     * @throws ParseException
     */
    public static InformationMessage buildInformation(Information m)
            throws ParseException {

        List<InMessage> infoList = m.getInfoMessageList();
        List<InfoMessage> iList = new ArrayList<InfoMessage>();
        for (InMessage inMessage : infoList) {
            if (inMessage.getType() == (InMessage.TypeOfMessage.Crevasse)) {

                iList.add(new CrevasseMessage(inMessage.getCertainty()));
            } else if (inMessage.getType().equals(InMessage.TypeOfMessage.Need)) {


                iList.add(new NeedMessage(TypeOfNeed.values()[inMessage.getNeed().getNeedType().getNumber()-1], inMessage.getCertainty()));
            } else if (inMessage.getType() == (InMessage.TypeOfMessage.Location)) {

                iList.add(new LocationMessage(inMessage.getLocation().getLatitude(), inMessage.getLocation().getLongitude(), inMessage.getCertainty()));

            } else {
                logger.warn("FOUND UNKNOWN INFO MESSAGE: " + inMessage.getType());
            }
        }


        // build InformationMetadataMessage:
        InMetadataMessage imm = m.getInformationMetadataMessage();

        InformationMetadataMessage metaMessage = new InformationMetadataMessage(
                imm.getTopicsList(),
                imm.getPictureUrlsList());

        Provenance provenance = Provenance.values()[m.getProvenance().getNumber()-1];

        InformationMessage infoMessage = new InformationMessage(m.getId(),
                m.getText(), m.getAuthor(), iList, LocalDateTime.parse(m.getDate()),
                metaMessage, provenance);

        return infoMessage;
    }
}
