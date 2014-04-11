package training;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: sebastian
 * Date: 10.11.13
 * Time: 12:52
 * To change this template use File | Settings | File Templates.
 */
public class MessageCreator {
    public static void readFile(){
        try {
            File file = new File("../../../");
            System.out.println(file.getAbsolutePath());
//            BufferedReader in = new BufferedReader(new FileReader("text.txt"));
//            String zeile = null;
//            while ((zeile = in.readLine()) != null) {
//                System.out.println("Gelesene Zeile: " + zeile);
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        readFile();
    }
}
