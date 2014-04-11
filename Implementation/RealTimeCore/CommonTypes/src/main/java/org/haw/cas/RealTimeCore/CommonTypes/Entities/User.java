package org.haw.cas.RealTimeCore.CommonTypes.Entities;

import com.sun.istack.internal.Nullable;

import javax.persistence.*;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: j-wil_000
 * Date: 27.10.13
 * Time: 00:13
 * <p/>
 * This class implements the functional entity "Social Media User".
 */
@Entity
public class User implements IAbstractEntity {

    private static final long serialVersionUID = 35837L;
    @Id
    private long id;
    @ElementCollection
    private Set<String> aliases;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @Nullable
    private List<Position> positions;

    protected User() {}

    public User(String nick) {
        this.id = -1;
        this.aliases = new HashSet<>();
        this.aliases.add(nick);
        this.positions = new ArrayList<>();
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public void addAlias(String nick) {
        aliases.add(nick);
    }

    public void removeAlias(String nick) {
        if (aliases.size() > 1) {
            aliases.remove(nick);
        }
    }

    public Collection<String> getAliases() {
        return Collections.unmodifiableCollection(aliases);
    }

    /**
     * Saves the given position.
     *
     * @param position the given position
     */
    public void addPosition(Position position) {
        positions.add(position);
    }

    @Override
    public String toString() {

        String nicks = "";

        for (String alias : aliases) {
            nicks += alias + " ";
        }


        return "User{firstNick=" + aliases.toArray()[0] +
                " allNicks=" + nicks +
                ", positions=" + positions +
                '}';
    }

    /**
     * Returns the last known position of the user.
     *
     * @return null if not found
     */
    public Position getLastPosition() {
        return (positions.isEmpty()) ? null : positions.get(positions.size()-1);
    }

    public boolean hasPosition() {
        return !positions.isEmpty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (id != user.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
