package org.haw.cas.PlaceOfResidenceAnalyzer.Implementation.Usecases;

import org.apache.commons.lang3.exception.ExceptionUtils;

/**
 * Created with IntelliJ IDEA.
 * User: Jonas
 * Date: 03.11.13
 * Time: 14:57
 * To change this template use File | Settings | File Templates.
 */
public class ApiTester {


    public static void main(String[] args) {
        GoogleMapsAchieveGeoData googleMapsAchieveGeoData = new GoogleMapsAchieveGeoData();

        String address = "Hamburg";

        GoogleMapsAchieveGeoData.GoogleMapsResponse response = googleMapsAchieveGeoData.obtainGeoData(address);

        if (response.getExceptionDuringRequest() == null) {
            System.out.println("Latitude: " + response.getLatitude() + "Longitude: " + response.getLongitude());
        } else {
            System.err.println("Error: " + ExceptionUtils.getStackTrace(response.getExceptionDuringRequest()));
        }
    }

}
