package Implementation.classifiers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: sebastian
 * Date: 14.11.13
 * Time: 14:37
 * To change this template use File | Settings | File Templates.
 */
public class AbstractCrevasseClassifier {
    public static List<String> getSampleWordVectorFromFile(File sampleWordVector){
        List<String> result = new LinkedList<>();
        try {
            BufferedReader in = new BufferedReader(new FileReader(sampleWordVector));
            String line;
            while ((line = in.readLine()) != null) {
                result.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
