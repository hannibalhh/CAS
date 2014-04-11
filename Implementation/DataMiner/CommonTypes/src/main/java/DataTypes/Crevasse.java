package DataTypes;

import DataTypes.DataTypeInterfaces.ICrevasse;
import org.haw.cas.Adapters.RTCToDataMinerAdapter.Interface.Messages.CrevasseMessage;
import org.haw.cas.Adapters.RTCToDataMinerAdapter.Interface.Messages.InfoMessage;

/**
 * Created with IntelliJ IDEA.
 * User: sebastian
 * Date: 30.10.13
 * Time: 14:28
 * To change this template use File | Settings | File Templates.
 */
public class Crevasse implements ICrevasse {

    private int certainty = 0;

    public Crevasse() {
    }

    public Crevasse(int certainty) {
        this.certainty = certainty;
    }

    public int getCertainty() {
        return certainty;
    }

    public void setCertainty(int certainty) {
        this.certainty = certainty;
    }


    public InfoMessage getMessage() {
        return new CrevasseMessage(this.certainty);
    }

    @Override
    public String toString() {
        return "Crevasse{}";
    }
}
