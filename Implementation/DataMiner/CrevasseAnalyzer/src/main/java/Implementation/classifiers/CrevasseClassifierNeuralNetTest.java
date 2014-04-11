package Implementation.classifiers;

import CrevasseAnalyzer.Interface.FeatureVectorExtractor;
import CrevasseAnalyzer.Interface.ICrevasseClassifier;
import org.haw.cas.TextMiner.Toolbox.NeuralNetworkWrapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * Created with IntelliJ IDEA.
 * User: sebastian
 * Date: 14.11.13
 * Time: 14:53
 * To change this template use File | Settings | File Templates.
 */
public class CrevasseClassifierNeuralNetTest {
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCrevasseReportIncluded1() throws Exception {
        NeuralNetworkWrapper neuralNetMock = Mockito.mock(NeuralNetworkWrapper.class);
        double[] wishedNNetResult = {1.0};
        when(neuralNetMock.calculateOutput(any(double[].class))).thenReturn(wishedNNetResult);

        List<String> sampleWordVector = Arrays.asList("Deich","gebrochen");
        FeatureVectorExtractor featureVectorExtractor = new FeatureVectorExtractor_SurroundingWords(sampleWordVector);

        ICrevasseClassifier classifier = new CrevasseClassifierNeuralNet(neuralNetMock,featureVectorExtractor,0.99);

        assertEquals(true, classifier.crevasseReportIncluded("Deich gebrochen")); //unabhaenig von Eingabe, da Netz gemockt
    }

    @Test
    public void testCrevasseReportIncluded2() throws Exception {
        NeuralNetworkWrapper neuralNetMock = Mockito.mock(NeuralNetworkWrapper.class);
        double[] wishedNNetResult = {0.712};
        when(neuralNetMock.calculateOutput(any(double[].class))).thenReturn(wishedNNetResult);

        List<String> sampleWordVector = Arrays.asList("Deich","gebrochen"); //eigentlich irrelevant da Netz gemockt
        FeatureVectorExtractor featureVectorExtractor = new FeatureVectorExtractor_SurroundingWords(sampleWordVector);

        ICrevasseClassifier classifier = new CrevasseClassifierNeuralNet(neuralNetMock,featureVectorExtractor,0.9);

        assertEquals(false, classifier.crevasseReportIncluded("Deich nicht gebrochen")); //unabhaenig von Eingabe, da Netz gemockt
    }

}
