package org.haw.cas.Adapters.RTCToDataMinerAdapter.Interface.Messages;

import org.haw.cas.GlobalTypes.MessageInfo.TypeOfInfo;

/**
 * Marker class for extracted Information belonging to an InformationMessage
 */
public abstract class InfoMessage implements IDataMinerMessage {

    protected TypeOfInfo typeOfInfo;
    protected int certainty;

    public InfoMessage(int certainty){
        this.certainty = certainty;

    }

    public TypeOfInfo getType(){
        return typeOfInfo;
    }

    public int getCertainty() {
        return certainty;
    }



}
