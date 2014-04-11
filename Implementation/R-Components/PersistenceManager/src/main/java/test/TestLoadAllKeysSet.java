import junit.framework.Assert;
import org.haw.cas.Adapters.PersistenceManager.Implementation.DatabaseServices;
import org.haw.cas.RealTimeCore.CommonTypes.Entities.User;
import org.junit.Test;

import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Nils
 * Date: 02.12.13
 * Time: 11:18
 * To change this template use File | Settings | File Templates.
 */
public class TestLoadAllKeysSet

{

     @Test
     public void lLoadKeySetFromDB(){

         String mapName = User.class.getName();

         Map<Long,User> userMap = DatabaseServices.getNewEntityMap(mapName);

         User testUser1 = new User("Tobi Test");
         User testUser2 = new User("Torben Test");
         User testUser3 = new User("Tohmas Test");

         testUser1.setId(1);
         testUser2.setId(2);
         testUser3.setId(3);


         userMap.put(testUser1.getId(),testUser1);
         userMap.put(testUser2.getId(),testUser2);
         userMap.put(testUser3.getId(),testUser3);

         Set<Long> firstset = userMap.keySet();

         DatabaseServices.Shutdown(this.getClass());

         userMap = DatabaseServices.getNewEntityMap(mapName);


         Set<Long> secondSet = userMap.keySet();

         Assert.assertEquals(firstset,secondSet);


    }


}
