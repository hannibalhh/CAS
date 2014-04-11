package org.haw.cas.PlaceOfResidenceAnalyzer.Implementation.Usecases;

/**
 * Created with IntelliJ IDEA.
 * User: Jonas
 * Date: 03.11.13
 * Time: 14:57
 * To change this template use File | Settings | File Templates.
 */

import com.google.common.base.Joiner;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.logging.log4j.Logger;
import org.haw.cas.PlaceOfResidenceAnalyzer.Implementation.Usecases.Exceptions.*;
import org.json.*;

import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.apache.logging.log4j.LogManager;

public class GoogleMapsAchieveGeoData {

    final String googleApiAddress = "http://maps.googleapis.com/maps/api/geocode/json";
    private Logger logger = LogManager.getLogger(GoogleMapsAchieveGeoData.class);
    private static BlockingQueue requestTokens = new ArrayBlockingQueue(10);
    private static Thread tokenProducer = null;

    public GoogleMapsAchieveGeoData() {
        if (tokenProducer == null) {
            tokenProducer = new Thread(new Runnable() {
                @Override
                public void run() {
                    while(true) {
                        if (requestTokens.remainingCapacity() > 0) {
                            requestTokens.add(new Object());
                        }

                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            logger.error("Error in TokenProducerThread: " + ExceptionUtils.getStackTrace(e));
                        }
                    }
                }
            });

            tokenProducer.setDaemon(true);
            tokenProducer.start();
        }
    }

    private static String encodeParams(final Map<String, String> params) {
        final String paramsUrl = Joiner.on('&').join(//key1=value1&key2=value2...
                Iterables.transform(params.entrySet(), input -> {
                    try {
                        final StringBuffer buffer = new StringBuffer();
                        buffer.append(input.getKey());//key=value
                        buffer.append('=');
                        buffer.append(URLEncoder.encode(input.getValue(), "utf-8"));//HTML 4.01
                        return buffer.toString();
                    } catch (final UnsupportedEncodingException e) {
                        throw new RuntimeException(e);
                    }
                }));
        return paramsUrl;
    }

    private static String readJson(final Reader rd) throws IOException {    //read JSON Format
        final StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    private static JSONObject sendRequest(final String url) throws IOException, JSONException {  //send request to gmaps
        final InputStream is = new URL(url).openStream();
        try {
            final BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            final String jsonText = readJson(rd);
            final JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }

    public GoogleMapsResponse obtainGeoData(String address) {
        if(GoogleMapsCache.getCachedResult(address)!= null){
            return GoogleMapsCache.getCachedResult(address);
        }

        final Map<String, String> params = Maps.newHashMap();
        Double lat = 0.0;
        Double lng = 0.0;
        boolean isPartialMatch = false;
        Throwable exceptionDuringRequest = null;

        params.put("sensor", "false");// GPS Sensor in this device
        params.put("address", address);
        params.put("region", "de");
        final String url = googleApiAddress + '?' + encodeParams(params);// generate parameters
        final JSONObject response;// response from google
        try {
            logger.debug("Waiting for a free slot for sending...");
            // Make sure we are only sending 10 messages per second on this client.
            requestTokens.take();

            logger.debug("Sending request to Google...");
            response = sendRequest(url);
            logger.debug("Got response from Google...");

            String status = response.getString("status");

            // Check id the request was a success. If not throw a matching exception.
            if (status.equals("OK") && response.getJSONArray("results").length() > 0) {
                JSONObject location = response.getJSONArray("results").getJSONObject(0);
                location = location.getJSONObject("geometry");
                location = location.getJSONObject("location");

                // If this is only a partial match we will not use the data.
                JSONObject match = response.getJSONArray("results").getJSONObject(0);
                if (match.has("partial_match")) {
                    isPartialMatch = true;
                }

                lng = location.getDouble("lng");// longitude
                lat = location.getDouble("lat");// latitude
            } else {
                // The response may contain a "error_message" field that contains more informations about the issue.
                // However BE ADVISED Google states that this field may be subject to change in future versions of the API.
                String message = "";
                if (response.has("error_message")) {
                    message = response.getString("error_message");
                }

                if (status.equals("ZERO_RESULTS")) {
                    throw new NoResultsFoundException(message);
                } else if (status.equals("OVER_QUERY_LIMIT")) {
                    throw new DailyQueryLimitReachedException(message);
                } else if (status.equals("REQUEST_DENIED")) {
                    throw new RequestDeniedException(message);
                } else if (status.equals("INVALID_REQUEST")) {
                    throw new InvalidRequestException(message);
                } else if (status.equals("UNKNOWN_ERROR")) {
                    throw new UnknownServerErrorException(message);
                } else {
                    throw new UnknownServerResponseException(message);
                }
            }
        } catch (Throwable e) {
            logger.warn("Failed to obtain data from the GoogleMapsService for the address '" + address + "'\nError: " + ExceptionUtils.getStackTrace(e));
            exceptionDuringRequest = e;
        }
        GoogleMapsResponse result = new GoogleMapsResponse(lat, lng, isPartialMatch, exceptionDuringRequest);
        GoogleMapsCache.putResultIntoCache(address, result);
        return result;
    }

    public class GoogleMapsResponse implements Serializable{
        Double latitude;
        Double longitude;
        Throwable exceptionDuringRequest = null;
        Boolean partialMatch = false;

        public Double getLatitude() {
            return latitude;
        }

        public Double getLongitude() {
            return longitude;
        }

        public Throwable getExceptionDuringRequest() {
            return exceptionDuringRequest;
        }

        public boolean isPartialMatch() {
            return partialMatch;
        }

        public GoogleMapsResponse(Double latitude, Double longitude, boolean partialMatch) {
            this.latitude = latitude;
            this.longitude = longitude;
            this.partialMatch = partialMatch;
        }

        public GoogleMapsResponse(Double latitude, Double longitude, boolean partialMatch, Throwable exceptionDuringRequest) {
            this.latitude = latitude;
            this.longitude = longitude;
            this.partialMatch = partialMatch;
            this.exceptionDuringRequest = exceptionDuringRequest;
        }
    }
}
