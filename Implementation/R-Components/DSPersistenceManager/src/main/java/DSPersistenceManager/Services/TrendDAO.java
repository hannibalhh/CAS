package DSPersistenceManager.Services;

import DSPersistenceManager.Model.Message;
import DSPersistenceManager.Model.Trend;
import org.haw.cas.GlobalTypes.Settings.AppSettings;
import org.haw.cas.GlobalTypes.Settings.SettingException;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: dude
 * Date: 14.11.13
 * Time: 15:16
 * To change this template use File | Settings | File Templates.
 */
public class TrendDAO extends AbstractDAO {
    private AppSettings appSettings ;

    public TrendDAO(){
        this.dbService = new DatabaseServices();
        this.appSettings = new AppSettings();
    }

    public Trend getTrendById(String id) {
        return this.getById(id,Trend.class);
    }

    public void create(Trend trend) {
        if(trend.isValid()) {
        super.create(trend);
        } else {
            throw new IllegalArgumentException("No valid Trend. Check Attributes for null");
        }

    }

    public void update(Trend trend) throws IllegalArgumentException{
        if(trend != null && trend.isValid()) {
            super.update(trend);
        }   else {
            throw new IllegalArgumentException("No valid Trend. Check Attributes for null");
        }

    }

    public void delete(Trend trend){
        super.delete(trend.getId(), Trend.class);
    }

    public Iterable<Trend> getAllTrends(){
        EntityManager em = dbService.getEntityManager();
        TypedQuery<Trend> query = em.createNamedQuery(Trend.findAll, Trend.class);
        List<Trend> result = query.getResultList();

        em.close();

        return result;
    }

    public Iterable<Trend> getTrend(int chunkSize){
        EntityManager em = dbService.getEntityManager();

        TypedQuery<Trend> query = em.createNamedQuery(Trend.findAll, Trend.class).setMaxResults(chunkSize);
        List<Trend> result = query.getResultList();
        try {
            if(appSettings.getBoolean("testing")) {
                for(Trend trend  : result) {
                    delete(trend);
                }
            }
        } catch (SettingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        em.close();

        return result;
    }
}
