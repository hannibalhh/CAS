package org.haw.cas.PlaceOfResidenceAnalyzer.Implementation.Usecases;

import DSPersistenceManager.Model.Geodata;
import DSPersistenceManager.Model.Message;
import DataTypes.DataTypeInterfaces.IInfo;
import DataTypes.Location;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.haw.cas.TextMiner.Toolbox.FileUtils;
import org.haw.cas.TextMiner.Toolbox.POSTagger;
import org.haw.cas.TextMiner.Toolbox.Tokenizer;

import java.io.File;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * User: Jan
 * Date: 16.10.13
 * Time: 17:08
 * To change this template use File | Settings | File Templates.
 */

public class AnalyzeChunkForLocations {
    private HashSet<String> cityNames;
    private GoogleMapsAchieveGeoData googleAccessUseCase;
    private Logger logger = LogManager.getLogger(AnalyzeChunkForLocations.class);
    private MapServiceInquiryValidator mapServiceInquiryValidator;
    private HashSet<String> streetNames;

    private final int europebonus = 1;
    private final int germanybonus = 2;
    private final int prepositionbonus = 3;
    private final int streetListbonus = 20;
    private final int cityListbonus = 10;


    public AnalyzeChunkForLocations(MapServiceInquiryValidator mapServiceInquiryValidator, GoogleMapsAchieveGeoData googleAccessUseCase) {
        this.mapServiceInquiryValidator = mapServiceInquiryValidator;
        this.googleAccessUseCase = googleAccessUseCase;
    }

    public List<IInfo> processMessage(Message message) {
        List<IInfo> result = new ArrayList<>();
        Map<String, Integer> certaintybonuses = new HashMap<>();

        logger.debug("Getting pos tags for words.");
        List<Map.Entry<String, POSTagger.PosTag>> posTags = org.haw.cas.TextMiner.Toolbox.POSTagger.getPosTagsFromText(message.getMessage());
        logger.debug("Got :" + posTags.size() + " pos tags.");
        if (posTags.size() < 1) return result;

        List<String> foundNames = new ArrayList<>();

        // Removed the names from the list, because the postagger produced a lot of false positives.
//        // Get all potential names from the message text. These might be names of locations.
//        List<String> foundNames = posTags
//                .parallelStream()
//                .filter(x -> x.getValue() == POSTagger.PosTag.NOMEN)
//                .map(x -> x.getKey())
//                .collect(Collectors.toList());


        // Get all street names from the list.
        logger.debug("Getting street names from text.");
        List<String> foundStreetNames = this.findStreetNamesInText(message.getMessage());
        for (String s : foundStreetNames) {
            certaintybonuses.put(s, streetListbonus);
        }
        foundNames.addAll(foundStreetNames);

        // Get all city names from the list.
        logger.debug("Getting city names from text.");
        List<String> foundCityNames = this.findCityNamesInText(message.getMessage());
        for (String s : foundCityNames) {
            certaintybonuses.put(s, cityListbonus);
        }


        foundNames.addAll(foundCityNames);


        // Remove all duplicates from the list.
        logger.debug("Removing duplicates in found names.");
        HashSet<String> foundNamesWithoutDuplicates = new HashSet<>();
        foundNamesWithoutDuplicates.addAll(foundNames);
        foundNames.clear();
        foundNames.addAll(foundNamesWithoutDuplicates);

        logger.debug("Computing certanity bonuses.");
        for (int i = 0; i < posTags.size() - 1; i++) {
            Entry<String, POSTagger.PosTag> entry = posTags.get(i);
            String place = entry.getKey();
            POSTagger.PosTag posTag = entry.getValue();
            if (posTag == POSTagger.PosTag.PRAEPOSITION) {
                String followingWord = posTags.get(i + 1).getKey();
                if (foundNames.contains(followingWord)) {
                    int oldbonus = 0;
                    if(certaintybonuses.get(posTags.get(i + 1).getKey()) != null){
                        oldbonus = certaintybonuses.get(posTags.get(i + 1).getKey());
                    }
                    certaintybonuses.put(posTags.get(i + 1).getKey(),oldbonus + prepositionbonus);
                } else if (i + 2 < posTags.size() && posTags.get(i + 1).getValue() == POSTagger.PosTag.STOPWORD && foundNames.contains(posTags.get(i + 2).getKey())) {
                    int oldbonus = 0;
                    if(certaintybonuses.get(posTags.get(i + 2).getKey()) != null){
                        oldbonus = certaintybonuses.get(posTags.get(i + 2).getKey());
                    }
                    certaintybonuses.put(posTags.get(i + 2).getKey(),oldbonus + prepositionbonus);
                }
            }
        }

        // Add the resulting Geodata to the results and compute certainty.
        // TODO: maybe process as Stream
        logger.debug("Generating Geodata...");
        double unknown = 0;
        for (Map.Entry<String, POSTagger.PosTag> posTag : posTags) {
            if (posTag.getValue().equals(POSTagger.PosTag.UNRECOGNIZED)) {
                unknown++;
            }
        }
        double knownWordsCertaintyCoeff = 0;
        if (posTags.size() > 0) {
            knownWordsCertaintyCoeff = (posTags.size() - unknown) / (posTags.size());
        }
        logger.debug("Found this possible loacations: " + foundNames);
        Map<String, Geodata> generatedGeodataMap = this.generateGeodataMapFromFoundNames(foundNames);
        logger.debug("Found this Geodata: " + generatedGeodataMap);
        Set<String> geodataMapkeys = generatedGeodataMap.keySet();


        int resultsCertainty = (int) ((100.0 - (geodataMapkeys.size() * 10.0)) * knownWordsCertaintyCoeff);
        if (resultsCertainty < 1) resultsCertainty = 1;

        for (String place : geodataMapkeys) {
            Integer individualCertaintybonus = certaintybonuses.get(place);
            if (individualCertaintybonus == null) {
                individualCertaintybonus = 0;
            }
            Geodata x = generatedGeodataMap.get(place);
            Location tmp = new Location(x.getLatitude(), x.getLongitude(), resultsCertainty);
            if (tmp.getLatitude() > 47.219568 && tmp.getLatitude() < 54.952386
                    && tmp.getLongitude() > 5.860405 && tmp.getLongitude() < 15.091552) {
                tmp.setCertainty(tmp.getCertainty() + germanybonus);
            } else if (tmp.getLatitude() > 38.891033 && tmp.getLatitude() < 64.736641
                    && tmp.getLongitude() > -11.352541 && tmp.getLongitude() < 31.713865) {
                tmp.setCertainty(tmp.getCertainty() + europebonus);
            }
            tmp.setCertainty(tmp.getCertainty() + individualCertaintybonus);
            IInfo info = tmp;
            result.add(info);
        }

        if (message.getGeodata() != null) {
            IInfo info = new Location(message.getGeodata().getLatitude(), message.getGeodata().getLongitude(), 200);
            result.add(info);
        }

        String foundLocationsAsString = "";
        for(IInfo loc : result) {
            foundLocationsAsString += loc + "; ";
        }

        LogManager.getLogger("FoundLocationsLogger").debug("Found these names in the text: " + generatedGeodataMap + "\n  The following locations were generated: " + foundLocationsAsString + "\n  In this message: " + message.getMessage());

        return result;
    }

    public List<String> findCityNamesInText(String text) {
        List<String> tokens = Tokenizer.getTokenListFromText(text);
        List<String> matchList = new ArrayList<>();
        List<String> checkedAdvancedMatchList = new ArrayList<>();
        for(int i = 0; i < tokens.size(); i++){
            matchList.add(tokens.get(i));
            if(i +1 < tokens.size()){
                String str = String.format("%s %s",tokens.get(i),tokens.get(i+1));
                matchList.add(str);

            }
            if(i +2 < tokens.size()){
                String str = String.format("%s %s %s",tokens.get(i),tokens.get(i+1),tokens.get(i+2));
                matchList.add(str);

            }
        }

        for (String s : matchList) {
            if (isCityName(s)) {
                checkedAdvancedMatchList.add(s);
            }
        }

        logger.debug("Found these City Names: " + matchList);
        return checkedAdvancedMatchList;
    }

    /**
     * Gets all substrings from a text that may be a street address.
     *
     * @param text The text that will be searched for addresses.
     * @return A list of all potential street addresses found in the text.
     */
    public List<String> findStreetNamesInText(String text) {
        List<String> matchList = new ArrayList<>();
        List<String> matchList_withNumbers = new ArrayList<>();
        List<String> foundStreetNames = new ArrayList<>();
        Pattern regex = Pattern.compile("^(([a-zA-ZäöüÄÖÜ]\\D*)\\s+\\d+?\\s*.*)$"); //neu: ^(([a-zA-ZäöüÄÖÜ]\D*)\s+\d+?\s*.*)$     alt: [a-zA-ZäöüÄÖÜ]+(str.)?\s?[0-9]+(a-zA-ZäöüÄÖÜ)?
        Matcher regexMatcher = regex.matcher(text);
        while (regexMatcher.find()) {
            matchList.add(regexMatcher.group());
        }
        List<String> tokens = Tokenizer.getTokenListFromText(text);
        for(int i = 0; i < tokens.size(); i++){
            matchList.add(tokens.get(i));
            if(i +1 < tokens.size()){
                String str = String.format("%s %s",tokens.get(i),tokens.get(i+1));
                matchList.add(str);
                if(tokens.get(i+1).matches("[0-9]+.*"))    {
                    matchList_withNumbers.add(str);
                }
            }
            if(i +2 < tokens.size()){
                String str = String.format("%s %s %s",tokens.get(i),tokens.get(i+1),tokens.get(i+2));
                matchList.add(str);
                if(tokens.get(i+2).matches("[0-9]+.*"))    {
                    matchList_withNumbers.add(str);
                }
            }
            if(i +3 < tokens.size()){
                String str = String.format("%s %s %s %s",tokens.get(i),tokens.get(i+1),tokens.get(i+2),tokens.get(i+3));
                if(tokens.get(i+3).matches("[0-9]+.*"))    {
                    matchList_withNumbers.add(str);
                }
            }
        }
        List<String> checkedMatchList = new ArrayList<>();
        for (String s : matchList) {
            if (isStreetName(s)) {
                 checkedMatchList.add(s);
            }
        }
        for (String checkedString : checkedMatchList) {
            List<String> checkedWithNumbers = new ArrayList<>();
            for(String StringWithNumbers : matchList_withNumbers){
                if(StringWithNumbers.startsWith(checkedString)){
                    checkedWithNumbers.add(StringWithNumbers);
                }
            }
            if(!checkedWithNumbers.isEmpty()){
                foundStreetNames.addAll(checkedWithNumbers);
            } else {
                foundStreetNames.add(checkedString);
            }
        }


        logger.debug("Found these Street Names: " + checkedMatchList);
        return foundStreetNames;
    }

    /**
     * Tries to match found names to geolocations.
     *
     * @param foundNames The potential names of locations that have been found in a text.
     * @return A list containing all locations that could be matched to the given names.
     */
    private Map<String, Geodata> generateGeodataMapFromFoundNames(List<String> foundNames) {
        Map<String, Geodata> geoMap = new HashMap<>();

        for (String place : foundNames) {
            // If the name of the place is in fact a name of a person, skip this one.
            if (!this.mapServiceInquiryValidator.checkValidityOfInquiry(place)) {
                continue;
            }

            Geodata location = this.getLocationForName(place);

            if (location != null) {
                geoMap.put(place, location);
            }
        }

        return geoMap;
    }

    /**
     * Gets a list of all known city names.
     *
     * @return
     */
    private HashSet<String> getCityNames() {
        if (cityNames == null) {
            cityNames = new HashSet<String>();
            cityNames.addAll(FileUtils.getEntriesFromTextFilesInPath("." + File.separator + "PlaceOfResidenceAnalyzer" + File.separator + "Data" + File.separator + "cities" + File.separator));
        }
        return cityNames;
    }

    /**
     * Tries to find a location with the given name.
     *
     * @param name The name of the location.
     * @return The geo-location of the found location if the search was succesful, otherwise null.
     */
    private Geodata getLocationForName(String name) {
        // Ask google for a location for the current name.
        GoogleMapsAchieveGeoData.GoogleMapsResponse response = this.googleAccessUseCase.obtainGeoData(name);

        if (response.exceptionDuringRequest == null) {
            return new Geodata(response.getLatitude(), response.getLongitude());
        }

        return null;
    }

    /**
     * Gets a list of all known city names.
     *
     * @return
     */
    private HashSet<String> getStreetNames() {
        if (streetNames == null) {
            streetNames = new HashSet<String>();
            streetNames.addAll(FileUtils.getEntriesFromTextFilesInPath("." + File.separator + "PlaceOfResidenceAnalyzer" + File.separator + "Data" + File.separator + "streets" + File.separator));
        }
        return streetNames;
    }

    /**
     * Returns true if the specified word is a city name, otherwise false.
     *
     * @param word
     * @return
     */
    private boolean isCityName(String word) {
        return getCityNames().contains(word.trim().toLowerCase());
    }

    /**
     * Returns true if the specified word is a city name, otherwise false.
     *
     * @param word
     * @return
     */
    private boolean isStreetName(String word) {
        return getStreetNames().contains(word.trim().toLowerCase());
    }
}
