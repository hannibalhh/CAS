package DSPersistenceManager.Model;

import org.eclipse.persistence.nosql.annotations.DataFormatType;
import org.eclipse.persistence.nosql.annotations.Field;
import org.eclipse.persistence.nosql.annotations.NoSql;
import org.haw.cas.GlobalTypes.MessageInfo.Provenance;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: dude
 * Date: 14.11.13
 * Time: 15:09
 * To change this template use File | Settings | File Templates.
 */
@NamedQueries({@NamedQuery(name="Trend.findAll", query="Select t from Trend t") ,
        @NamedQuery(name="Message.findByCountry", query="Select t from Trend t where t.country = :country")})



@Entity
@NoSql(dataFormat= DataFormatType.MAPPED)
public class Trend {
    @Id
    @GeneratedValue
    @Field(name="_id")
    private String id;

    private String country;
    private Provenance provenance;

    @Temporal(TemporalType.DATE)
    private Date postTime;

    private Integer woeid;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> trends;


    public static final String findAll = "Trend.findAll";
    public static final String findByCountry= "Trend.findByCountry";

    public Trend(){}

    public Trend(String country, Date postTime, Integer woeid, List<String> trends, Provenance provenance) {
        this.country = country;
        this.postTime = postTime;
        this.woeid = woeid;
        this.trends = trends;
        this.provenance = provenance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Provenance getProvenance() {
        return provenance;
    }

    public void setProvenance(Provenance provenance) {
        this.provenance = provenance;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Date getPostTime() {
        return postTime;
    }

    public void setPostTime(Date postTime) {
        this.postTime = postTime;
    }

    public Integer getWoeid() {
        return woeid;
    }

    public void setWoeid(Integer woeid) {
        this.woeid = woeid;
    }

    public List<String> getTrends() {
        return trends;
    }

    public void setTrends(List<String> trends) {
        this.trends = trends;
    }

    public boolean isValid() {
        if(this.getTrends() == null) return false;
        if(this.getCountry() == null) return false;
        if(this.getWoeid() == null) return false;
        if(this.getPostTime() == null) return false;
      return true;
    }

    @Override
    public String toString() {
        return "Trend{" +
                "id='" + id + '\'' +
                ", country='" + country + '\'' +
                ", postTime=" + postTime +
                ", woeid=" + woeid +
                ", trends=" + trends +
                '}';
    }
}
