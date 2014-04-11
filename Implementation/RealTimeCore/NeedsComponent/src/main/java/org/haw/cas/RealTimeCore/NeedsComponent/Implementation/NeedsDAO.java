package org.haw.cas.RealTimeCore.NeedsComponent.Implementation;

import org.haw.cas.Adapters.PersistenceManager.Interface.AbstractDAO;
import org.haw.cas.GlobalTypes.MessageInfo.TypeOfNeed;
import org.haw.cas.RealTimeCore.CommonTypes.Entities.Need;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * User: Guest1
 * Date: 05.11.13
 * Time: 00:23
 * To change this template use File | Settings | File Templates.
 */
public class NeedsDAO extends AbstractDAO<Need> {

    protected NeedsDAO() {
        super(Need.class);
    }

    public float getNeedCountWithPosition(TypeOfNeed type, LocalDateTime from, LocalDateTime to) {
        return getAll().parallelStream()
                .filter(n -> n.getType() == type
                        && (n.getCreationTime().isAfter(from) || n.getCreationTime().isEqual(from))
                        && n.getCreationTime().isBefore(to)
                        && n.getGeoCoordiante() != null
                )
                .count();
    }

    public float getNeedCountWithoutPosition(TypeOfNeed type, LocalDateTime from, LocalDateTime to) {
        return getAll().parallelStream()
                .filter(n -> n.getType() == type
                        && (n.getCreationTime().isAfter(from) || n.getCreationTime().isEqual(from))
                        && n.getCreationTime().isBefore(to)
                        && n.getGeoCoordiante() == null
                )
                .count();
    }

    public float getNeedCount(TypeOfNeed type, LocalDateTime from, LocalDateTime to) {
        return getAll().parallelStream()
                .filter(n -> n.getType() == type
                        && (n.getCreationTime().isAfter(from) || n.getCreationTime().isEqual(from))
                        && n.getCreationTime().isBefore(to)
                )
                .count();
    }

    public Collection<Need> getNeedsWithoutPosition(LocalDateTime from, LocalDateTime to) {
        return getAll().parallelStream()
                .filter(n -> (n.getCreationTime().isAfter(from) || n.getCreationTime().isEqual(from))
                        && n.getCreationTime().isBefore(to)
                        && n.getGeoCoordiante() == null
                )
                .collect(Collectors.toList());
    }
}
