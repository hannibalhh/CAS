package org.haw.cas.RealTimeCore.UserComponent.Implementation;


import org.apache.log4j.Logger;
import org.haw.cas.Adapters.PersistenceManager.Implementation.Exception.CantFoundEntityException;
import org.haw.cas.Adapters.PersistenceManager.Implementation.Exception.EntityAlreadyExistException;
import org.haw.cas.Adapters.PersistenceManager.Interface.AbstractDAO;
import org.haw.cas.RealTimeCore.CommonTypes.Entities.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * User: Nils
 * Date: 01.11.13
 * Time: 15:10
 * <p/>
 * This class is used to save and find Users.
 */
public class UserDAO extends AbstractDAO<User> {

    private Logger logger;

    public UserDAO() {
        super(User.class);
        logger = Logger.getLogger(this.getClass());
    }

    public void createNewUser(User user) throws EntityAlreadyExistException {
        create(user);
    }

    public void update(User user){

        try {
            super.update(user);
        } catch (CantFoundEntityException e) {
            e.printStackTrace();
        }
    }

    public Collection<User> getAll() {
        return super.getAll();
    }

    /**
     * Returns a collection of all users with the given alias.
     *
     * @return empty, if none found
     */
    public Collection<User> getUserByAlias(String alias) {

        Collection<User> results = new ArrayList<>();
        for (User user : getAll()) {
            for (String userAlias : user.getAliases()) {
                if (userAlias.equals(alias)) {
                    results.add(user);
                }
            }
        }

        return results;
    }

    public static void main(String[] args){
        UserDAO userDAO = new UserDAO();

        User u1 = new User("blarg");
        User u2 = new User("meh");

        try {
            System.out.println("save user");
            userDAO.createNewUser(u1);
            userDAO.createNewUser(u2);
            System.out.println("done");
        } catch (EntityAlreadyExistException e) {
            e.printStackTrace();
        }
        Collection<User> userList = userDAO.getAll();
        for(User user : userList){
            System.out.println("user: " + user);
        }

        u2.addAlias("peter");
        userDAO.update(u2);

        userList = userDAO.getAll();
        for(User user : userList){
            System.out.println("user: " + user);
        }
    }

    /**
     * Returns a list of all users with a known last position.
     * @return empty if none found, never null
     */
    public Iterable<User> getAllUsersWithKnownPosition() {
        List<User> users = super.getAll().stream()
                .filter(u -> u.getLastPosition() != null)
                .collect(Collectors.toList());

        return users;
    }
}
