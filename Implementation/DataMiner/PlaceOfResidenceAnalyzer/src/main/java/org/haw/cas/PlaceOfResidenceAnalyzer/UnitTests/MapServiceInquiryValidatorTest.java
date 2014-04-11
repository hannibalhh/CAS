package org.haw.cas.PlaceOfResidenceAnalyzer.UnitTests;

import org.eclipse.persistence.jpa.jpql.Assert;
import org.haw.cas.PlaceOfResidenceAnalyzer.Implementation.Usecases.MapServiceInquiryValidator;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Malte Eckhoff
 * Date: 31.10.13
 * Time: 11:21
 * To change this template use File | Settings | File Templates.
 */
public class MapServiceInquiryValidatorTest {
    private static MapServiceInquiryValidator inquiryValidator;

    @BeforeClass
    static void setUp() {
        inquiryValidator = new MapServiceInquiryValidator();
    }

    void tearDown() {

    }

    @Test
    void positiveTestCheckValidityOfInquiry() {
        List<String> testInputs = new ArrayList<String>();

        testInputs.add("peter");
        testInputs.add("hubert");
        testInputs.add("meier");
        testInputs.add("Peter");
        testInputs.add("Meier");
        testInputs.add("Müller");
        testInputs.add("Peter Meier");
        testInputs.add("Peter Zwirbel");

        for(String input : testInputs) {
            boolean result = inquiryValidator.checkValidityOfInquiry(input);
            Assert.isFalse(result, "Failed to recognize person name: " + input);
        }
    }

    @Test
    void negativeTestCheckValidityOfInquiry() {
        List<String> testInputs = new ArrayList<String>();

        testInputs.add("Herrenhausen");
        testInputs.add("Altona");
        testInputs.add("Ludwig-Erhard-Straße");
        testInputs.add("Hauptbahnhof");

        for(String input : testInputs) {
            boolean result = inquiryValidator.checkValidityOfInquiry(input);
            Assert.isTrue(result, "False positive on place name: " + input);
        }
    }
}
