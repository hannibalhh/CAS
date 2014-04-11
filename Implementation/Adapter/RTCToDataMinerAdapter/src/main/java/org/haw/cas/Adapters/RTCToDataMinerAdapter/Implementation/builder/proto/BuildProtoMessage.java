package org.haw.cas.Adapters.RTCToDataMinerAdapter.Implementation.builder.proto;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.haw.cas.Adapters.RTCToDataMinerAdapter.Implementation.data.messageTyp.java.InformationMessage.InMessage;
import org.haw.cas.Adapters.RTCToDataMinerAdapter.Implementation.data.messageTyp.java.InformationMessage.InMetadataMessage;
import org.haw.cas.Adapters.RTCToDataMinerAdapter.Implementation.data.messageTyp.java.InformationMessage.Information;
import org.haw.cas.Adapters.RTCToDataMinerAdapter.Implementation.data.messageTyp.java.InformationMessage.Information.Builder;
import org.haw.cas.Adapters.RTCToDataMinerAdapter.Implementation.data.messageTyp.java.InformationMessage.LoMessage;
import org.haw.cas.Adapters.RTCToDataMinerAdapter.Implementation.data.messageTyp.java.NeedInfoMessage;
import org.haw.cas.Adapters.RTCToDataMinerAdapter.Interface.Messages.InfoMessage;
import org.haw.cas.Adapters.RTCToDataMinerAdapter.Interface.Messages.InformationMessage;
import org.haw.cas.Adapters.RTCToDataMinerAdapter.Interface.Messages.LocationMessage;
import org.haw.cas.Adapters.RTCToDataMinerAdapter.Interface.Messages.NeedMessage;
import org.haw.cas.GlobalTypes.MessageInfo.Provenance;
import org.haw.cas.GlobalTypes.MessageInfo.TypeOfInfo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class BuildProtoMessage {

    private static Logger logger = LogManager.getLogger(BuildProtoMessage.class);

	/**
	 * This method builds a Protobuf-Information-Message from an
	 * InformationMessage-Object.
	 * 
	 * @param m
	 *            should be converted
	 * @return the new Protobuf-Message
	 */
	public static Information buildProtoInformation(InformationMessage m) {
		Builder infoMessageBuilder = Information.newBuilder();
                infoMessageBuilder.setId(m.getId()).setText(m.getText()).setAuthor(m.getAuthor());
                                                //TODO USE MESSAGE
		for (InfoMessage im : m.getInfos()) {
            if (im.getType() == TypeOfInfo.Crevasse) {
                infoMessageBuilder.addInfoMessage(InMessage.newBuilder()
                        .setType(InMessage.TypeOfMessage.Crevasse)
                        .setCertainty(im.getCertainty())
                );

                      //  .setType(InMessage.MessageType.CrevasseInfoMessage).setMessage(""));
            } else if (im.getType() == TypeOfInfo.Need) {
                NeedMessage needsMessage = (NeedMessage) im;

                infoMessageBuilder.addInfoMessage(InMessage.newBuilder()
                        .setType(InMessage.TypeOfMessage.Need)
                        .setNeed(
                                NeedInfoMessage.NeedsInfoMessage.newBuilder()

                                        .setNeedType(NeedInfoMessage.NeedsInfoMessage.NeedType.valueOf(needsMessage.getTypeOfNeed().getNumber()))
                                        .setMessage(m.getText())

                        )

                        .setCertainty(im.getCertainty())
                );
                      //  .setType(InMessage.MessageType.NeedInfoMessage).setMessage(""));
            }else if(im.getType() == TypeOfInfo.Location){
                LocationMessage locationMessage = (LocationMessage) im;

                infoMessageBuilder.addInfoMessage(
                        InMessage.newBuilder()
                                .setType(InMessage.TypeOfMessage.Location)
                                .setLocation(
                                        LoMessage.newBuilder()
                                                .setLongitude(locationMessage.getLongitude())
                                                .setLatitude(locationMessage.getLatitude()))
                                .setCertainty(im.getCertainty())
                );
            }

            else {
                logger.debug("FOUND NOT EXPECTED INFO TYPE");

            }
        }
        logger.debug("All INFO MESSAGES BUILT");
        // build InformationMetadataMessage
		org.haw.cas.Adapters.RTCToDataMinerAdapter.Implementation.data.messageTyp.java.InformationMessage.InMetadataMessage.Builder immBuilder = InMetadataMessage
				.newBuilder();
        logger.debug("BUILDING META DATA");
		immBuilder.addAllTopics(m.getMetaData().getTopics());

		//for (int i = 0; i < m.getMetaData().getTopics().size(); i++) {
        //    immBuilder.setTopics(i, m.getMetaData().getTopics().get(i));
		//}

        @Deprecated
        /*
		immBuilder.setLocationMessage(LoMessage.newBuilder()
				.setLongitude(m.getMetaData().getLocation().getLongitude())
				.setLatitude(m.getMetaData().getLocation().getLatitude())
				.build());
        */
        //TODO: LASST DIE SCHEISSE FUNKTIONIEREN .. DRECKS PICTURE URL IST KEIN BYTE ARRAY!!!! FUCK YOU HIBERNATE! (keine picture urls momentan)
        List<String> pictureUrl = m.getMetaData().getPictureUrls();
        //List<String> pictureUrl = new ArrayList<>(); //fuck you byte array


        immBuilder.addAllPictureUrls(pictureUrl);
        logger.debug("FINISHED BUILDING META DATA");
        logger.debug("BUILDING DATE");
		//DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		String date = m.getPublishTime().toString();

		infoMessageBuilder.setDate(date).setInformationMetadataMessage(immBuilder);
        logger.debug("FINISHED BUILDING DATE");


        logger.debug("BUILDING CERTAINTY");


        logger.debug("FINISHED BUILDING CERTAINTY");

        logger.debug("BUILDING PROVENANCE");
        if(m.getProvenance() == Provenance.Twitter){
            infoMessageBuilder.setProvenance(Information.Provenance.Twitter);
        }else if(m.getProvenance() == Provenance.Unknown){
            infoMessageBuilder.setProvenance(Information.Provenance.Unknown);
        } else {
            logger.debug("FOUND NOT EXPECTED PROVENANCE TYPE");
        }
        logger.debug("FINISHED BUILDING PROVENANCE");

		return infoMessageBuilder.build();
	}
}
