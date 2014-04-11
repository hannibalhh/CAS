package DataTypes.DataTypeInterfaces;

import org.haw.cas.Adapters.RTCToDataMinerAdapter.Interface.Messages.NeedMessage;
import org.haw.cas.GlobalTypes.MessageInfo.TypeOfNeed;

/**
 * Created with IntelliJ IDEA.
 * User: N3trunner
 * Date: 22.10.13
 * Time: 18:25
 * To change this template use File | Settings | File Templates.
 */
public interface INeed extends IInfo {


    public TypeOfNeed getTypeOfNeed();

    NeedMessage getMessage();
}
