package DataTypes.DataTypeInterfaces;

import org.haw.cas.Adapters.RTCToDataMinerAdapter.Interface.Messages.InformationMessage;
import org.haw.cas.GlobalTypes.MessageInfo.Provenance;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: N3trunner
 * Date: 21.10.13
 * Time: 16:54
 * To change this template use File | Settings | File Templates.
 */
public interface IInformation {
    String getId();

    String getText();

    String getAuthor();

    List<IInfo> getInfos();

    Date getPublishTime();

    Provenance getProvenance();

    IInformationMetadata getMetaData();

    InformationMessage getMessage();
}
