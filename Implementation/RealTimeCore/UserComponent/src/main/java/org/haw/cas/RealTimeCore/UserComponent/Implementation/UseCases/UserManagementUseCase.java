package org.haw.cas.RealTimeCore.UserComponent.Implementation.UseCases;

import org.haw.cas.Adapters.AkkaAdapter.Interface.IAkkaAdapter;
import org.haw.cas.Adapters.PersistenceManager.Implementation.Exception.EntityAlreadyExistException;
import org.haw.cas.DataMiningTools.TextMining.DamerauLevenshtein;
import org.haw.cas.DataMiningTools.TextMining.IStringDistanceCalculator;
import org.haw.cas.RealTimeCore.CommonTypes.Entities.GeoCoordinate;
import org.haw.cas.RealTimeCore.CommonTypes.Entities.User;
import org.haw.cas.RealTimeCore.UserComponent.Implementation.UserDAO;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Created with IntelliJ IDEA.
 * User: j-wil_000
 * Date: 03.11.13
 * Time: 23:30
 * <p/>
 * This class implements the logic that revolves around the normal crud actions revolving around users.
 */
public class UserManagementUseCase {

    private final IAkkaAdapter akkaAdapter;
    private UserDAO userDAO;
    private IStringDistanceCalculator distanceCalculator;

    public UserManagementUseCase(IAkkaAdapter akkaAdapter) {
        this.akkaAdapter = akkaAdapter;
        userDAO = new UserDAO();
        distanceCalculator = new DamerauLevenshtein();
    }

    public Iterable<User> getAll() {
        return userDAO.getAll();
    }

    public void createUser(User user) throws EntityAlreadyExistException {
        userDAO.createNewUser(user);
    }

    public void update(User user) {
        userDAO.update(user);
    }

    public User resolveUser(String nick) {

        try {
            // iterates all users and tries to find the first, where distance between an alias and the nick is less then 2
            return userDAO.getAll().parallelStream()
                    .filter(u ->
                            u.getAliases()
                                    .stream()
                                    .filter(a -> distanceCalculator.calculateDistance(a, nick) < 2)
                                    .count() > 0
                    )
                    .findAny()
                    .get();
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    public GeoCoordinate getLatestPosition(User user) {
        return user.getLastPosition().getGeoCoordinate();
    }

    public Iterable<User> getAllUsersWithKnownPosition() {
             return userDAO.getAllUsersWithKnownPosition();
    }
}
