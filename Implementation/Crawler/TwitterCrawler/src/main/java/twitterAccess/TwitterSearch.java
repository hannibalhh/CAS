package twitterAccess;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.haw.cas.GlobalTypes.Settings.AppSettings;
import org.haw.cas.GlobalTypes.Settings.SettingException;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterSearch {
private Twitter twitter;

	
	public TwitterSearch(){
		this.twitter = new TwitterFactory(new ConfigurationBuilder().build()).getInstance();

	}

    public List<Tweet> searchUsingMinId(String keyword, long minId){
        List<Tweet> tweetResult = new ArrayList<>();
        try {
            Query query = new Query(keyword);
            query.setCount(100);
            query.setSinceId(minId);
            QueryResult result;
            do {
                result = this.twitter.search(query);
                List<Status> tweets = result.getTweets();
                for (Status currentTweet : tweets) {
                    tweetResult.add(new Tweet(currentTweet));
                }
            } while ((query = result.nextQuery()) != null);
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to search tweets: " + te.getMessage());
        }
        return tweetResult;
    }
	
	
	public List<Tweet> search(String keyword){
		return searchUsingMinId(keyword,0);
	}
	
	
	public static void main(String args[]){
        AppSettings appSettings = new AppSettings();
        Twitter twitter = new TwitterFactory().getInstance();
        List<Trends> trendsList = new LinkedList();
        try {
            List<Integer> woeidList =  appSettings.getIntList("woeidList");
            Trends trends;

            for(Integer woeid:woeidList)  {
                trends = twitter.getPlaceTrends(woeid);
                System.out.println("Trends for: " + trends.getLocation().getName());
                for(int i = 1 ; i < trends.getTrends().length; i++) {
                    System.out.println(trends.getTrends()[i].getName());
                }
            }
        } catch (SettingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (TwitterException e) {
            e.getErrorMessage();
        }
	}
}


