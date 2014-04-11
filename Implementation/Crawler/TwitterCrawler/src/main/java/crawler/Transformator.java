package crawler;

import DSPersistenceManager.Model.Geodata;
import DSPersistenceManager.Model.Message;
import DSPersistenceManager.Model.Trend;
import org.haw.cas.GlobalTypes.MessageInfo.Provenance;
import twitter4j.GeoLocation;
import twitter4j.Trends;
import twitterAccess.Tweet;

import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: sebastian
 * Date: 23.10.13
 * Time: 08:13
 * To change this template use File | Settings | File Templates.
 */
public class Transformator {
    public static List<Message> tweetsToMessage(List<Tweet> tweets) {
        List<Message> result = new LinkedList<Message>();
        for (Tweet currentTweet : tweets) {
            result.add(tweetToMessage(currentTweet));
        }
        return result;
    }

    private static Message tweetToMessage(Tweet tweet) {
        Message result = new Message();
        result.setAuthor(tweet.getUserName());
        result.setHashtags(tweet.getHashtags());
        result.setMessage(tweet.getText());
        result.setPictureUrl(tweet.getPictureUrl());
        //System.out.println("TweetTime: " + tweet.getTime());
        result.setPostTime(tweet.getTime());
        //System.out.println("ResultTime: " + result.getPostTime() );
        result.setGeodata(convertToGeodata(tweet.getGeolocation()));
        result.setProvenance(Provenance.Twitter);
        return result;
    }

    public static Trend trendsToTrend(Trends trends) {
        Trend result = new Trend();
        result.setCountry(trends.getLocation().getName());
        result.setWoeid(trends.getLocation().getWoeid());
        result.setPostTime(trends.getAsOf());
        result.setTrends(trendsToStringList(trends));
        result.setProvenance(Provenance.Twitter);
        return result;
    }

    private static List<String> trendsToStringList(Trends trends) {
        List<String> result = new LinkedList<String>();
        for (twitter4j.Trend trend : trends.getTrends()) {
            result.add(trend.getName());
        }
        return result;
    }

    private static Geodata convertToGeodata(GeoLocation location) {
        if (location != null) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            return new Geodata(latitude, longitude);
        } else {
            return null;
        }
    }
}
