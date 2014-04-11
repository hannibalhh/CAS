package DataTypes.DataTypeInterfaces;

import org.haw.cas.Adapters.RTCToDataMinerAdapter.Interface.Messages.InfoMessage;

/**
 * Created with IntelliJ IDEA.
 * User: N3trunner
 * Date: 21.10.13
 * Time: 16:45
 * To change this template use File | Settings | File Templates.
 */
public interface IInfo {

    public void setCertainty(int certainty);
    public int getCertainty();
    public InfoMessage getMessage();
}
