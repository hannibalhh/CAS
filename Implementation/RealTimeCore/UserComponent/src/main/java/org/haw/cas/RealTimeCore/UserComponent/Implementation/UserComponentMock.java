package org.haw.cas.RealTimeCore.UserComponent.Implementation;

import org.haw.cas.Adapters.PersistenceManager.Implementation.Exception.EntityAlreadyExistException;
import org.haw.cas.RealTimeCore.CommonTypes.Entities.GeoCoordinate;
import org.haw.cas.RealTimeCore.CommonTypes.Entities.User;
import org.haw.cas.RealTimeCore.UserComponent.Interface.IUserComponent;

import java.util.HashMap;
import java.util.Map;

/**
 * User: Jason Wilmans
 * Date: 09.12.13
 * Time: 16:52
 * <p/>
 * This class is a non-persistent, but fully functional, mock of the user component.
 */
public class UserComponentMock implements IUserComponent {

    private static long userCount;
    private Map<Long, User> users = new HashMap<>();

    @Override
    public void createUser(User user) throws EntityAlreadyExistException {
        if(users.containsKey(user.getId())) {
            throw new EntityAlreadyExistException(user);
        }

        userCount++;
        users.put(userCount, user);
    }

    @Override
    public GeoCoordinate getLatestPosition(User user) {
        return user.getLastPosition() != null ? user.getLastPosition().getGeoCoordinate() : null;
    }

    @Override
    public User resolveUser(String nick) {
        return users.values().parallelStream()
                .filter(u -> u.getAliases().contains(nick))
                .findAny()
                .get();
    }
}
