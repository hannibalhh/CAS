package DataTypes;

import DataTypes.DataTypeInterfaces.IInformationMetadata;
import DataTypes.DataTypeInterfaces.ILocation;
import org.haw.cas.Adapters.RTCToDataMinerAdapter.Interface.Messages.InformationMetadataMessage;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Malte Eckhoff
 * Date: 21.10.13
 * Time: 16:44
 * To change this template use File | Settings | File Templates.
 */
public class InformationMetadata implements IInformationMetadata {
    private List<String> topics;



    private List<String> pictureUrls;

    @Override
    public List<String> getTopics() {
        return topics;
    }

    @Override
    public List<String> getPictureUrls() {
        return pictureUrls;
    }

    public InformationMetadata(List<String> topics, List<String> pictureUrls) {
        this.topics = topics;
        this.pictureUrls = pictureUrls;
    }

    @Override
    public InformationMetadataMessage getMessage() {

        return new InformationMetadataMessage(this.getTopics(), this.getPictureUrls());
    }
}
