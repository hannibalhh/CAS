package DataTypes;

import DataTypes.DataTypeInterfaces.IInfo;
import DataTypes.DataTypeInterfaces.IInformation;
import org.haw.cas.Adapters.RTCToDataMinerAdapter.Interface.Messages.InfoMessage;
import org.haw.cas.Adapters.RTCToDataMinerAdapter.Interface.Messages.InformationMessage;
import org.haw.cas.GlobalTypes.MessageInfo.Provenance;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: N3trunner
 * Date: 21.10.13
 * Time: 16:42
 * To change this template use File | Settings | File Templates.
 */
public class Information implements IInformation {

    private String id;

    private Provenance provenance;


    private String text;

    private String author;

    private List<IInfo> infos;

    private Date publishTime;

    private InformationMetadata metaData;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public String getAuthor() {
        return author;
    }

    @Override
    public List<IInfo> getInfos() {
        return Collections.unmodifiableList(infos);
    }

    @Override
    public Date getPublishTime() {
        return publishTime;
    }

    @Override
    public InformationMetadata getMetaData() {
        return metaData;
    }

    @Override
    public Provenance getProvenance() {
        return provenance;
    }

    @Override
    public InformationMessage getMessage() {
        List<InfoMessage> infoMessageList = new ArrayList<>();
        for(IInfo i: infos){
            infoMessageList.add(i.getMessage());
        }
        InformationMessage informationMessage = new InformationMessage(this.getId(), this.getText(), this.getAuthor(), infoMessageList,
                LocalDateTime.ofInstant(Instant.ofEpochMilli(getPublishTime().getTime()), ZoneOffset.ofHours(1)),
        this.getMetaData().getMessage(), this.getProvenance());
        return informationMessage;
    }

    public Information(String id, String text, String author, List<IInfo> infos, Date publishTime, InformationMetadata metaData, Provenance provenance) {
        this.id = id;
        this.text = text;
        this.author = author;
        this.infos = infos;
        this.publishTime = publishTime;
        this.metaData = metaData;
        this.provenance = provenance;
    }

}
