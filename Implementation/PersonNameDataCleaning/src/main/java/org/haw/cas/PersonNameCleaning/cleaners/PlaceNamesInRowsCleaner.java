package org.haw.cas.PersonNameCleaning.cleaners;

/**
 * Created with IntelliJ IDEA.
 * User: N3trunner
 * Date: 30.10.13
 * Time: 12:33
 * To change this template use File | Settings | File Templates.
 */
public class PlaceNamesInRowsCleaner implements ICleaner {
    @Override
    public String cleanInput(String input) {
        String lastCleaningResult = "";
        String currentCleaningResult = input;

        while(!lastCleaningResult.equals(currentCleaningResult)) {
            lastCleaningResult = currentCleaningResult;
            currentCleaningResult = currentCleaningResult.replaceAll("([a-zA-Zé]+),\\s([a-zA-Zé]+)","$1\n$2");
            currentCleaningResult = currentCleaningResult.replaceAll("([a-zA-Zé]+)\\s–\\s([a-zA-Zé]+)","$1\n$2");
            currentCleaningResult = currentCleaningResult.replaceAll("([a-zA-Zé]+)\\s/\\s([a-zA-Zé]+)","$1\n$2");
        }

        return currentCleaningResult;
    }
}
