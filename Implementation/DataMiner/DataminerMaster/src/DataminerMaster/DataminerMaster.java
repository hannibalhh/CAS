package DataminerMaster;


/**
 * Created with IntelliJ IDEA.
 * User: Jonas
 * Date: 20.10.13
 * Time: 21:49
 * To change this template use File | Settings | File Templates.
 */
public class DataminerMaster {

    public static void main(String [ ] args){
        MessageAdapter ma;
        //PlaceOfResidenceAnalyzer ana;
        //CoordinateMessageAdapterAndAnalyzers cor;

        ma = new MessageAdapter();
        //ana = new PlaceOfResidenceAnalyzer(ma);
        //cor = new CoordinateMessageAdapterAndAnalyzers(ma);

        Thread threadMa = new Thread(ma);
        //Thread threadAna = new Thread(ana);
        //Thread threadCor = new Thread(cor);

        threadMa.start();
        //threadAna.start();
        //threadCor.start();
    }

}
