package org.haw.cas.RealTimeCore.UserComponent.Interface;

import org.haw.cas.Adapters.PersistenceManager.Implementation.Exception.EntityAlreadyExistException;
import org.haw.cas.RealTimeCore.CommonTypes.Entities.GeoCoordinate;
import org.haw.cas.RealTimeCore.CommonTypes.Entities.User;

/**
 * Created with IntelliJ IDEA.
 * User: j-wil_000
 * Date: 27.10.13
 * Time: 00:26
 *
 * This is the interface of the user component. The user component manages users and their positions.
 */
public interface IUserComponent {

    /**
     * Persistently saves the user.
     * @param user not null
     */
    public void createUser(User user) throws EntityAlreadyExistException;

    /**
     * Returns the user's last known position.
     * @param user the user to look for.
     * @return null, if no data available
     */
    public GeoCoordinate getLatestPosition(User user);

    /**
     * Returns the user with the given nick.
     * @param nick the user's alias, not null
     * @return null, if none found
     */
    User resolveUser(String nick);
}
