package org.haw.cas.RealTimeCore.CrevasseComponent.Implementation;

import org.haw.cas.Adapters.PersistenceManager.Interface.AbstractDAO;
import org.haw.cas.RealTimeCore.CommonTypes.Entities.Crevasse;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * User: Jason Wilmans
 * Date: 05.11.13
 * Time: 13:26
 * <p/>
 * This class handles the crud operations for crevasses and more complex queries.
 */
class CrevasseDAO extends AbstractDAO<Crevasse> {
    CrevasseDAO() {
        super(Crevasse.class);
    }

    public Collection<Crevasse> getAllBetween(LocalDateTime from, LocalDateTime to) {
        Stream<Crevasse> stream;

        if(from != null && to != null) {
            stream = getAll().parallelStream()
                    .filter(c -> c.getCreationTime().isBefore(to)
                                && (c.getCreationTime().isAfter(from) || c.getCreationTime().isEqual(from)));
        } else if(from == null && to == null) {
            stream = getAll().parallelStream();
        } else if(from == null) {
            stream = getAll().parallelStream()
            .filter(c -> c.getCreationTime().isBefore(to));
        } else {
            stream = getAll().parallelStream()
                    .filter(c -> c.getCreationTime().isAfter(from));
        }

        return stream.collect(Collectors.toList());
    }

    public Collection<Crevasse> getAllCrevasses(LocalDateTime from, LocalDateTime to) {
        return getAll().parallelStream()
                .filter(c ->
                        (c.getCreationTime().isAfter(from) || c.getCreationTime().isEqual(from))
                                && c.getCreationTime().isBefore(to)
                )
                .collect(Collectors.toList());
    }

    public Collection<Crevasse> getCrevassesWithPositions(LocalDateTime from, LocalDateTime to) {
        return getAll().parallelStream()
                .filter(c ->
                        (c.getCreationTime().isAfter(from) || c.getCreationTime().isEqual(from))
                                && c.getCreationTime().isBefore(to)
                                && c.getGeoCoordiante() != null
                )
                .collect(Collectors.toList());
    }

    public Collection<Crevasse> getCrevassesWithoutPositions(LocalDateTime from, LocalDateTime to) {
        return getAll().parallelStream()
                .filter(c ->
                        (c.getCreationTime().isAfter(from) || c.getCreationTime().isEqual(from))
                                && c.getCreationTime().isBefore(to)
                                && c.getGeoCoordiante() == null
                )
                .collect(Collectors.toList());
    }

    public int getCrevasseCountWithPosition(LocalDateTime from, LocalDateTime to) {
        return (int)getAll().parallelStream()
                .filter(c ->
                        (c.getCreationTime().isAfter(from) || c.getCreationTime().isEqual(from))
                        && c.getCreationTime().isBefore(to)
                        && c.getGeoCoordiante() != null
                )
                .count();
    }

    public int getCrevasseCountWithoutPosition(LocalDateTime from, LocalDateTime to) {
        return (int)getAll().parallelStream()
                .filter(c ->
                        (c.getCreationTime().isAfter(from) || c.getCreationTime().isEqual(from))
                                && c.getCreationTime().isBefore(to)
                                && c.getGeoCoordiante() == null
                )
                .count();
    }

    public int getCrevasseCount(LocalDateTime from, LocalDateTime to) {
        return (int)getAll().parallelStream()
                .filter(c ->
                        (c.getCreationTime().isAfter(from) || c.getCreationTime().isEqual(from))
                                && c.getCreationTime().isBefore(to)
                )
                .count();
    }
}
