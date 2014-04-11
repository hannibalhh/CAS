package org.haw.cas.RealTimeCore.ApplicationCore.Implementation;

import org.apache.log4j.Logger;
import org.haw.cas.Adapters.AkkaAdapter.Interface.Exceptions.ConfigurationException;
import org.haw.cas.Adapters.AkkaAdapter.Interface.Exceptions.MessageTypeNotSubscribableException;
import org.haw.cas.Adapters.AkkaAdapter.Interface.Exceptions.TechnicalProblemException;
import org.haw.cas.Adapters.AkkaAdapter.Interface.IAkkaAdapter;
import org.haw.cas.Adapters.AkkaAdapter.Interface.Messages.IAkkaMessage;
import org.haw.cas.Adapters.PersistenceManager.Implementation.Exception.EntityAlreadyExistException;
import org.haw.cas.Adapters.RTCToDataMinerAdapter.Interface.IRTCAdapter;
import org.haw.cas.Adapters.RTCToDataMinerAdapter.Interface.Messages.IDataMinerMessage;
import org.haw.cas.GlobalTypes.MessageInfo.TypeOfNeed;
import org.haw.cas.RealTimeCore.ApplicationCore.Interface.IApplicationCore;
import org.haw.cas.RealTimeCore.CommonTypes.Entities.Crevasse;
import org.haw.cas.RealTimeCore.CommonTypes.Entities.GeoCoordinate;
import org.haw.cas.RealTimeCore.CommonTypes.Entities.Need;
import org.haw.cas.RealTimeCore.CommonTypes.Entities.User;
import org.haw.cas.RealTimeCore.CrevasseComponent.Interface.ICrevasseComponent;
import org.haw.cas.RealTimeCore.NeedsComponent.Interface.INeedsComponent;
import org.haw.cas.RealTimeCore.UserComponent.Interface.IUserComponent;
import org.haw.cas.StatisticsComponent.Interface.IStatisticsComponent;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.concurrent.Semaphore;
import java.util.function.Consumer;

/**
 * User: Jason Wilmans
 * Date: 22.10.13
 * Time: 19:51
 * <p/>
 * This component implements the RealTimeCore's application core. This is the main entry point for any user of its functions.
 */
public class ApplicationCoreComponent implements IApplicationCore {

    /* What we are doing here, is delegating the calls to the application core in such a way, that only one thread at a
       time can access the same component when writing. We'll see how that works out :).
     */

    private final INeedsComponent needsComponent;
    private final ICrevasseComponent crevasseComponent;
    private final Logger logger = Logger.getLogger(ApplicationCoreComponent.class);
    private IStatisticsComponent statisticsComponent;
    private Semaphore userComponentMutex;
    private Semaphore needsComponentMutex;
    private Semaphore crevasseComponentMutex;
    private IAkkaAdapter akkaAdapter;
    private IRTCAdapter dataMinerAdapter;
    private IUserComponent userComponent;


    public ApplicationCoreComponent(IAkkaAdapter akkaAdapter,
                                    IRTCAdapter dataMinerAdapter,
                                    IUserComponent userComponent,
                                    INeedsComponent needsComponent,
                                    ICrevasseComponent crevasseComponent,
                                    IStatisticsComponent statisticsComponent) throws MessageTypeNotSubscribableException {
        this.akkaAdapter = akkaAdapter;
        this.dataMinerAdapter = dataMinerAdapter;
        this.userComponent = userComponent;
        this.needsComponent = needsComponent;
        this.crevasseComponent = crevasseComponent;
        this.statisticsComponent = statisticsComponent;
        this.crevasseComponentMutex = new Semaphore(1, true);
        this.userComponentMutex = new Semaphore(1, true);
        this.needsComponentMutex = new Semaphore(1, true);
        logger.debug("initialized");
    }

    public void createNeed(Need need) {
        try {
            needsComponentMutex.acquire();

            needsComponent.createNeed(need);

            needsComponentMutex.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Collection<Need> getAllNeeds() {
        return needsComponent.getAllNeeds();
    }

    @Override
    public float getNeedCountWithPosition(TypeOfNeed type, LocalDateTime from, LocalDateTime to) {
        return needsComponent.getNeedCountWithPosition(type, from, to);
    }

    @Override
    public float getNeedCountWithoutPosition(TypeOfNeed type, LocalDateTime from, LocalDateTime to) {
        return needsComponent.getNeedCountWithoutPosition(type, from, to);
    }

    @Override
    public float getNeedCount(TypeOfNeed type, LocalDateTime from, LocalDateTime to) {
        return needsComponent.getNeedCount(type, from, to);
    }

    @Override
    public Collection<Need> getNeedsWithoutPosition(LocalDateTime from, LocalDateTime to) {
        return needsComponent.getNeedsWithoutPosition(from, to);
    }

    public void createUser(User user) throws EntityAlreadyExistException {
        try {
            userComponentMutex.acquire();

            userComponent.createUser(user);

            userComponentMutex.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public User resolveUser(String nick) {
        return userComponent.resolveUser(nick);
    }

    public GeoCoordinate getLatestPosition(User user) {
        try {
            userComponentMutex.acquire();

            return userComponent.getLatestPosition(user);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            userComponentMutex.release();
        }

        return null;
    }

    public void send(IAkkaMessage message) throws TechnicalProblemException, ConfigurationException {
        akkaAdapter.send(message);
    }

    public <M extends IAkkaMessage> void subscribeForMessage(Class<M> messageType, Consumer<M> messageListener) {
        akkaAdapter.subscribeForMessage(messageType, messageListener);
    }

    public <M extends IDataMinerMessage> void subscribeForInformationMessage(Class<M> messageType, Consumer<M> messagelistener) {
        dataMinerAdapter.subscribeForInformationMessage(messageType, messagelistener);
    }

    @Override
    public void createCrevasse(Crevasse crevasse) {
        try {
            crevasseComponentMutex.acquire();

            crevasseComponent.createCrevasse(crevasse);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            crevasseComponentMutex.release();
        }
    }

    @Override
    public Collection<Crevasse> getAllCrevasses() {
        return crevasseComponent.getAllCrevasses();
    }

    @Override
    public Collection<Crevasse> getAllCrevasses(LocalDateTime from, LocalDateTime to) {
        return crevasseComponent.getAllCrevasses(from, to);
    }

    @Override
    public Collection<Crevasse> getCrevassesWithPositions(LocalDateTime from, LocalDateTime to) {
        return crevasseComponent.getCrevassesWithPositions(from, to);
    }

    @Override
    public Collection<Crevasse> getCrevassesWithoutPositions(LocalDateTime from, LocalDateTime to) {
        return crevasseComponent.getCrevassesWithoutPositions(from, to);
    }

    @Override
    public int getCrevasseCountWithPosition(LocalDateTime from, LocalDateTime to) {
        return crevasseComponent.getCrevasseCountWithPosition(from, to);
    }

    @Override
    public int getCrevasseCountWithoutPosition(LocalDateTime from, LocalDateTime to) {
        return crevasseComponent.getCrevasseCountWithoutPosition(from, to);
    }

    @Override
    public int getCrevasseCount(LocalDateTime from, LocalDateTime to) {
        return crevasseComponent.getCrevasseCount(from, to);
    }
}
