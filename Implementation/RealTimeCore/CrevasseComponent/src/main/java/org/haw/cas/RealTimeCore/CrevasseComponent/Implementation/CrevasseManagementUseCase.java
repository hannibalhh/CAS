package org.haw.cas.RealTimeCore.CrevasseComponent.Implementation;

import org.haw.cas.Adapters.PersistenceManager.Implementation.Exception.EntityAlreadyExistException;
import org.haw.cas.RealTimeCore.CommonTypes.Entities.Crevasse;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;

/**
 * User: Jason Wilmans
 * Date: 05.11.13
 * Time: 13:32
 * <p/>
 * This class implements all logic around the management of crevasses.
 */
class CrevasseManagementUseCase {
    CrevasseDAO crevasseDAO;

    CrevasseManagementUseCase() {
        crevasseDAO = new CrevasseDAO();
    }

    /**
     * Saves the given crevasse in the persistence.
     * @param crevasse not null
     */
    void create(Crevasse crevasse) {
        try {
            crevasseDAO.create(crevasse);
        } catch (EntityAlreadyExistException e) {
            System.err.println(new Date() + " - WARNING: Double crevasse entry: " + crevasse);
        }
    }

    /**
     * Returns a list of all crevasses.
     * @return empty, if none found
     */
    Collection<Crevasse> getAll() {
        return crevasseDAO.getAll();
    }

    public Collection<Crevasse> getAllCrevasses(LocalDateTime from, LocalDateTime to) {
        return crevasseDAO.getAllCrevasses(from, to);
    }

    public Collection<Crevasse> getCrevassesWithPositions(LocalDateTime from, LocalDateTime to) {
        return crevasseDAO.getCrevassesWithPositions(from, to);
    }

    public Collection<Crevasse> getCrevassesWithoutPositions(LocalDateTime from, LocalDateTime to) {
        return crevasseDAO.getCrevassesWithoutPositions(from, to);
    }

    public int getCrevasseCountWithPosition(LocalDateTime from, LocalDateTime to) {
        return crevasseDAO.getCrevasseCountWithPosition(from, to);
    }

    public int getCrevasseCountWithoutPosition(LocalDateTime from, LocalDateTime to) {
        return crevasseDAO.getCrevasseCountWithoutPosition(from, to);
    }

    public int getCrevasseCount(LocalDateTime from, LocalDateTime to) {
        return crevasseDAO.getCrevasseCount(from, to);
    }
}

