package org.haw.cas.RealTimeCore.NeedsComponent.Implementation;

import org.apache.log4j.Logger;
import org.haw.cas.Adapters.PersistenceManager.Implementation.Exception.EntityAlreadyExistException;
import org.haw.cas.GlobalTypes.MessageInfo.TypeOfNeed;
import org.haw.cas.RealTimeCore.CommonTypes.Entities.Need;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: Nils
 * Date: 04.11.13
 * Time: 23:50
 * To change this template use File | Settings | File Templates.
 */
public class NeedsManagementUseCase {

      private NeedsDAO needsDAO;
        private final static Logger logger = Logger.getLogger(NeedsManagementUseCase.class);

      public NeedsManagementUseCase(){
          this.needsDAO = new NeedsDAO();
      }

      public void createNeed(Need need){
          try {
              needsDAO.create(need);
              logger.debug("creating new need " + need);
          } catch (EntityAlreadyExistException e) {
              logger.warn("need " + need + "already exists");
          }
      }

    public Collection<Need> getAll() {
        return needsDAO.getAll();
    }

    public float getNeedCountWithPosition(TypeOfNeed type, LocalDateTime from, LocalDateTime to) {
        return needsDAO.getNeedCountWithPosition(type, from, to);
    }

    public float getNeedCountWithoutPosition(TypeOfNeed type, LocalDateTime from, LocalDateTime to) {
        return needsDAO.getNeedCountWithoutPosition(type, from, to);
    }

    public float getNeedCount(TypeOfNeed type, LocalDateTime from, LocalDateTime to) {
        return needsDAO.getNeedCount(type, from, to);
    }

    public Collection<Need> getNeedsWithoutPosition(LocalDateTime from, LocalDateTime to) {
        return needsDAO.getNeedsWithoutPosition(from, to);
    }
}
