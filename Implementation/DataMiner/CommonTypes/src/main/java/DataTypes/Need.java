package DataTypes;




import DataTypes.DataTypeInterfaces.INeed;
import org.haw.cas.Adapters.RTCToDataMinerAdapter.Interface.Messages.NeedMessage;
import org.haw.cas.GlobalTypes.MessageInfo.TypeOfNeed;

/**
 * Created with IntelliJ IDEA.
 * User: Malte Eckhoff
 * Date: 22.10.13
 * Time: 18:24
 * To change this template use File | Settings | File Templates.
 */
public class Need implements INeed {

    private TypeOfNeed typeOfNeed;

    private int certainty = 0;

    public int getCertainty() {
        return certainty;
    }

    public void setCertainty(int certainty) {
        this.certainty = certainty;
    }

    public TypeOfNeed getTypeOfNeed() {
        return typeOfNeed;
    }

    public Need(TypeOfNeed typeOfNeed) {
        this.typeOfNeed = typeOfNeed;
    }

    public Need(TypeOfNeed typeOfNeed, int certainty) {
        this.typeOfNeed = typeOfNeed;
        this.certainty = certainty;
    }

    @Override
    public NeedMessage getMessage() {
        return new NeedMessage(typeOfNeed,this.certainty);  //To change body of implemented methods use File | Settings | File Templates.
    }
    /*
    private NeedMessage.TypeOfNeed translateTypeOfNeed(TypeOfNeed need) {
        int needOrdinal = need.ordinal();
        return NeedMessage.TypeOfNeed.values()[needOrdinal];
    }
    */
}
