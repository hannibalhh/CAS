package DataTypes.DataTypeInterfaces;


import org.haw.cas.Adapters.RTCToDataMinerAdapter.Interface.Messages.LocationMessage;

/**
 * Created with IntelliJ IDEA.
 * User: Raphael
 * Date: 21.10.13
 * Time: 17:53
 * To change this template use File | Settings | File Templates.
 */
public interface ILocation extends IInfo {
    double getLongitude();

    double getLatitude();

    LocationMessage getMessage();

}
