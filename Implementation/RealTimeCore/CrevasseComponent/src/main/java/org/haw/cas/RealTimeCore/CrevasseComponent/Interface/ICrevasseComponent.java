package org.haw.cas.RealTimeCore.CrevasseComponent.Interface;



import org.haw.cas.RealTimeCore.CommonTypes.Entities.Crevasse;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: sebastian
 * Date: 30.10.13
 * Time: 15:17
 * <p/>
 * Defines the available operations of the statistics component.
 */
public interface ICrevasseComponent {
    /**
     * Persistently saves the given need.
     * @param crevasse not null
     */
    void createCrevasse(Crevasse crevasse);

    /**
     * Returns a collection containing all crevasses ever registered.
     * @return empty if none found
     */
    public Collection<Crevasse> getAllCrevasses();

    /**
     * Same as the overload without parameters, with the difference that only crevasses in the time interval between
     * from and to are regarded. If from is not set, the earliest matches will be found, if to is not set, the upper
     * bound is now.
     * @param from (inclusive) start
     * @param to (exclusive) end, not null
     * @return empty if none found
     */
    public Collection<Crevasse> getAllCrevasses(LocalDateTime from, LocalDateTime to);

    /**
     * Same as the overload without parameters, with the difference that only crevasses in the time interval between
     * from and to are regarded. If from is not set, the earliest matches will be found, if to is not set, the upper
     * bound is now.
     * @param from (inclusive) start
     * @param to (exclusive) end, not null
     * @return empty if none found
     */
    public Collection<Crevasse> getCrevassesWithPositions(LocalDateTime from, LocalDateTime to);

    /**
     * Same as the overload without parameters, with the difference that only crevasses in the time interval between
     * from and to are regarded. If from is not set, the earliest matches will be found, if to is not set, the upper
     * bound is now.
     * @param from (inclusive) start
     * @param to (exclusive) end, not null
     * @return empty if none found
     */
    public Collection<Crevasse> getCrevassesWithoutPositions(LocalDateTime from, LocalDateTime to);

    /**
     * Returns the number of crevasses in the given time interval with a known position.
     * @param from not null
     * @param to not null
     * @return see description
     */
    int getCrevasseCountWithPosition(LocalDateTime from, LocalDateTime to);

    /**
     * Returns the number of crevasses in the given time interval with an unkown position.
     * @param from not null
     * @param to not null
     * @return see description
     */
    int getCrevasseCountWithoutPosition(LocalDateTime from, LocalDateTime to);

    /**
     * Returns the number of all crevasses in the given time interval.
     * @param from
     * @param to
     * @return
     */
    int getCrevasseCount(LocalDateTime from, LocalDateTime to);
}
