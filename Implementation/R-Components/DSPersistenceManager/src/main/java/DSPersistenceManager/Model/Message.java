package DSPersistenceManager.Model;

//import javax.persistence.*;

import org.eclipse.persistence.nosql.annotations.Field;
import org.eclipse.persistence.nosql.annotations.NoSql;
import org.eclipse.persistence.nosql.annotations.DataFormatType;
import org.haw.cas.GlobalTypes.MessageInfo.Provenance;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@NamedQueries({@NamedQuery(name="Message.findAll", query="Select m from Message m") ,
        @NamedQuery(name="Message.findByAuthor", query="Select m from Message m where m.author = :author")})



@Entity
@NoSql(dataFormat=DataFormatType.MAPPED)
public class Message {
	@Id
    @GeneratedValue
	@Field(name="_id")
	private String id;

    @Enumerated(EnumType.ORDINAL)
    private Provenance provenance;
	private String author;
	private String message;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> hashtags;

    @Temporal(TemporalType.TIMESTAMP)
    private Date postTime;



    @Embedded
    private Geodata geodata;
    //@CollectionOfElements(targetElement = String.class)
    @ElementCollection(fetch = FetchType.EAGER,targetClass = String.class)
    private List<String> pictureUrls;
	
	public static final String findAll = "Message.findAll";
    public static final String findByAuthor = "Message.findByAuthor";
    public static final String findByHashtag = "Message.findByHashtag";

    public Message(){}

    public Message( String author, String message, List<String> hashtags, Date postTime,
                   Geodata geodata, List<String> pictureUrls, Provenance provenance) {
        this.author = author;
        this.message = message;
        this.hashtags = hashtags;
        this.postTime = postTime;
        this.geodata = geodata;
        this.pictureUrls = pictureUrls;
        this.provenance = provenance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getHashtags() {
        return hashtags;
    }

    public void setHashtags(List<String> hashtags) {
        this.hashtags = hashtags;
    }


    public Date getPostTime() {
        return postTime;
    }

    public void setPostTime(Date postTime) {
        this.postTime = postTime;
    }

    public Geodata getGeodata() {
        return geodata;
    }

    public void setGeodata(Geodata geodata) {
        this.geodata = geodata;
    }

    public List<String> getPictureUrl() {
        return pictureUrls;
    }

    public void setPictureUrl(List<String> pictureUrl) {
        this.pictureUrls = pictureUrl;
    }

    public Provenance getProvenance() {
        return provenance;
    }

    public void setProvenance(Provenance provenance) {
        this.provenance = provenance;
    }

    public boolean isValid(){
        if(this.getAuthor() == null) return false;
        if(this.getMessage() == null) return false;
        if(this.getPostTime() == null) return false;
        if(this.getHashtags() == null) return false;
        if(this.getProvenance() == null) return false;
        return true;
    }

    public String toString(){
		return "id: " + this.getId() + " --- Author: " + this.author + "--- Message: " + this.message; 
	}



}
