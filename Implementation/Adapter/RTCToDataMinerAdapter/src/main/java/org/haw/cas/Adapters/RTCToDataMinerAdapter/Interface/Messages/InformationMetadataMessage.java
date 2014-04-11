package org.haw.cas.Adapters.RTCToDataMinerAdapter.Interface.Messages;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: N3trunner
 * Date: 22.10.13
 * Time: 19:18
 * To change this template use File | Settings | File Templates.
 */
public class InformationMetadataMessage implements IDataMinerMessage {
    private List<String> topics;


    private List<String> pictureUrls;

    public List<String> getTopics() {
        return topics;
    }



    public List<String> getPictureUrls() {
        return pictureUrls;
    }

    public InformationMetadataMessage(List<String> topics, List<String> pictureUrls) {
        this.topics = topics;
        this.pictureUrls = pictureUrls;
    }
}
