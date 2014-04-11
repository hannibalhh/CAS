package org.haw.cas.PlaceOfResidenceAnalyzer.Implementation.Usecases;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;

/**
 * Filters the incoming inquiries to the MapService.
 * Most of the inquiries that are not a name of a place will be filtered out here.
 */
public class MapServiceInquiryValidator {

    private HashSet<String> personNames;
    private Logger logger = LogManager.getLogger(MapServiceInquiryValidator.class);

    public MapServiceInquiryValidator() {
        personNames = new HashSet<String>();

        File nameDirectory = new File("." + File.separator + "PlaceOfResidenceAnalyzer" + File.separator + "Data" + File.separator + "PersonNames");

        try {
            logger.debug("Reading lists of person names from: " + nameDirectory.getCanonicalPath());
        } catch (IOException e) {
            throw new ExceptionInInitializerError(e);
        }

        File[] nameFiles = getAllTxtFilesInDirectory(nameDirectory);

        for(File nameFile : nameFiles) {
            try {
                Scanner scanner =  new Scanner(nameFile);
                while (scanner.hasNextLine()){
                    String nextLine = scanner.nextLine();
                    personNames.add(nextLine.toLowerCase());
                }
            } catch (FileNotFoundException e) {
                throw new ExceptionInInitializerError(e);
            }
        }
    }

    /**
     * Checks if the specified name is a valid location. Names of persons will be filtered out.
     * @param locationName The name of the presumed location.
     * @return true - If the name seems to be a valid location, otherwise false.
     */
    public boolean checkValidityOfInquiry(String locationName) {


        if (this.personNames.contains(locationName.toLowerCase())) {
            return false;
        }

        return true;
    }

    /**
     * Gets a list of all text files in the specified directory.
     * @param directory
     * @return
     */
    private static File[] getAllTxtFilesInDirectory( File directory){
        return directory.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String filename)
            { return filename.endsWith(".txt"); }
        } );
    }
}
