package org.haw.cas.Adapters.RTCToDataMinerAdapter.Interface.Messages;

import org.haw.cas.GlobalTypes.MessageInfo.TypeOfInfo;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: sebastian
 * Date: 30.10.13
 * Time: 14:21
 * To change this template use File | Settings | File Templates.
 */
public class CrevasseMessage extends InfoMessage {

    public CrevasseMessage(int certainty) {
        super(certainty);
        this.typeOfInfo = TypeOfInfo.Crevasse;
    }




}
