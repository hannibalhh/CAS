package org.haw.cas.Adapters.RTCToDataMinerAdapter.Interface.Messages;

import org.haw.cas.GlobalTypes.MessageInfo.TypeOfInfo;
import org.haw.cas.GlobalTypes.MessageInfo.TypeOfNeed;

/**
 * Created with IntelliJ IDEA.
 * User: Malte Eckhoff
 * Date: 22.10.13
 * Time: 19:17
 * To change this template use File | Settings | File Templates.
 */
public class NeedMessage extends InfoMessage {


    private TypeOfNeed typeOfNeed;

    @Override
    public TypeOfInfo getType() {
        return typeOfInfo;
    }


    public TypeOfNeed getTypeOfNeed() {
        return typeOfNeed;
    }

    public NeedMessage(TypeOfNeed typeOfNeed, int certainty) {
        super(certainty);
        this.typeOfNeed = typeOfNeed;
        this.typeOfInfo = TypeOfInfo.Need;
    }
}
