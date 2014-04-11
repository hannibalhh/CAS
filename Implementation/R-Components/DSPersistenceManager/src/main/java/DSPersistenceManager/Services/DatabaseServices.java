package DSPersistenceManager.Services;

import org.haw.cas.GlobalTypes.Settings.AppSettings;
import org.haw.cas.GlobalTypes.Settings.SettingException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DatabaseServices {
	private static EntityManagerFactory factory;
    private static AppSettings appSettings = new AppSettings();
	
	public DatabaseServices(){
	}
	
	public EntityManager getEntityManager(){
        try {
            if(factory == null && !appSettings.getBoolean("testing")){
                DatabaseServices.factory = Persistence.createEntityManagerFactory("a");
            } else if(factory == null && appSettings.getBoolean("testing")){
                DatabaseServices.factory = Persistence.createEntityManagerFactory("DSPersictenceTest");
            }
            return factory.createEntityManager();
        } catch (SettingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return factory.createEntityManager();
    }
}
