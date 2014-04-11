package twitterAccess;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import twitter4j.GeoLocation;
import twitter4j.HashtagEntity;
import twitter4j.MediaEntity;
import twitter4j.Status;

public class Tweet {
	private Status tweet;
	
	public Tweet(Status status){
		this.tweet = status;
	}
	
	public Long getId() {
		return this.tweet.getId();
	}

	public String getText() {
		return this.tweet.getText();
	}

	public List<String> getHashtags() {
		List<String> result = new ArrayList<String>();
		HashtagEntity[] hashTags = this.tweet.getHashtagEntities();
		for(HashtagEntity currentHashTag : hashTags){
			result.add(currentHashTag.getText());
		}
		return result;
	}
	
	public Date getTime(){
		return this.tweet.getCreatedAt();
	}

    public List<String> getPictureUrl(){
        List<String> result = new LinkedList<String>();
        MediaEntity[] mediaEntities = this.tweet.getMediaEntities();
        for(MediaEntity currentEntity : mediaEntities){
            if(currentEntity.getType().equals("photo")){
                result.add(currentEntity.getMediaURL());
            }
        }
        return result;
    }




	public GeoLocation getGeolocation() {
		return this.tweet.getGeoLocation();
	}

	public String getUserName() {
		return this.tweet.getUser().getScreenName();
		
	}

	
	
	
	

}
