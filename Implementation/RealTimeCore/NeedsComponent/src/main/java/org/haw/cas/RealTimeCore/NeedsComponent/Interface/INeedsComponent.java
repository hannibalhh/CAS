package org.haw.cas.RealTimeCore.NeedsComponent.Interface;

import org.haw.cas.GlobalTypes.MessageInfo.TypeOfNeed;
import org.haw.cas.RealTimeCore.CommonTypes.Entities.Need;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: j-wil_000
 * Date: 27.10.13
 * Time: 00:10
 * <p/>
 * This class represents the needs component within the application core.
 */
public interface INeedsComponent {

    /**
     * Persistently saves the given need.
     *
     * @param need not null
     */
    void createNeed(Need need);

    /**
     * Returns a collection containing all needs ever registered.
     *
     * @return empty, if no
     */
    public Collection<Need> getAllNeeds();

    /**
     * Returns the number of needs, with the given type and within the time interval between from (inclusive) and to (exclusive), that have a
     * known position.
     * @param from not null
     * @param to not null
     * @return see description
     */
    float getNeedCountWithPosition(TypeOfNeed type, LocalDateTime from, LocalDateTime to);

    /**
     * Returns the number of needs, with the given type and within the time interval between from (inclusive) and to (exclusive), that have an
     * unknown position.
     * @param from not null
     * @param to not null
     * @return see description
     */
    float getNeedCountWithoutPosition(TypeOfNeed type, LocalDateTime from, LocalDateTime to);

    /**
     * Returns the number of needs, with the given type and within the time interval between from (inclusive) and to (exclusive).
     * @param from not null
     * @param to not null
     * @return see description
     */
    float getNeedCount(TypeOfNeed type, LocalDateTime from, LocalDateTime to);

    /**
     * Returns a collection of needs, that have no attached position and were reported between from  and to
     * .
     * @param from (inclusive) start of time interval, not null
     * @param to (exsclusive) end of time interval, not null
     * @return empty, if none found
     */
    public Collection<Need> getNeedsWithoutPosition(LocalDateTime from, LocalDateTime to);
}
