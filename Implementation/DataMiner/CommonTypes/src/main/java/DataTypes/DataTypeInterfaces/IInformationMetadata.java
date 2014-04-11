package DataTypes.DataTypeInterfaces;

import org.haw.cas.Adapters.RTCToDataMinerAdapter.Interface.Messages.InformationMetadataMessage;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: N3trunner
 * Date: 21.10.13
 * Time: 16:55
 * To change this template use File | Settings | File Templates.
 */
public interface IInformationMetadata {
    List<String> getTopics();

    List<String> getPictureUrls();

    InformationMetadataMessage getMessage();
}
