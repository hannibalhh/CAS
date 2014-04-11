package org.haw.cas.Adapters.RTCToDataMinerAdapter.Interface.Messages;

import org.haw.cas.GlobalTypes.MessageInfo.Provenance;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * This class encapsulates a Message mined by the DataMiner. all information mined
 * by the dataminer is being send in the infos field as InfoMessages. InfoMessage is
 * an abstract class which needs to be extended by an actual implementation such as
 * LocationMessage.
 */
public class InformationMessage implements IDataMinerMessage {
    private String id;
    private String text;
    private String author;
    private List<InfoMessage> infos;
    private LocalDateTime publishTime;
    private InformationMetadataMessage metaData;
    private Provenance provenance;

    public InformationMessage(String id, String text, String author, List<InfoMessage> infos, LocalDateTime publishTime, InformationMetadataMessage metaData, Provenance provenance) {
        this.id = id;
        this.text = text;
        this.author = author;
        this.infos = infos;
        this.publishTime = publishTime;
        this.metaData = metaData;
        this.provenance = provenance;
    }

    public String getId() {
        return id;
    }
    public String getText() {
        return text;
    }
    public String getAuthor() {
        return author;
    }
    public List<InfoMessage> getInfos() {
        return Collections.unmodifiableList(infos);
    }
    public LocalDateTime getPublishTime() {
        return publishTime;
    }
    public InformationMetadataMessage getMetaData() {
        return metaData;
    }

    public Provenance getProvenance() {
        return provenance;
    }

    @Override
    public String toString() {
        String infosAsString = "\n";

        for(InfoMessage info : infos) {
            infosAsString += "  " + info.toString() + "\n";
        }

        return "InformationMessage{" +
                "id='" + id + '\'' +
                ", text='" + text + '\'' +
                ", author='" + author + '\'' +
                ", infos=" + infosAsString +
                ", publishTime=" + publishTime +
                ", metaData=" + metaData +
                ", provenance=" + provenance +
                '}';
    }
}
