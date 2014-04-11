package org.haw.cas.RealTimeCore.ApplicationCore.Interface;


import org.apache.log4j.*;
import org.apache.log4j.helpers.DateLayout;
import org.apache.log4j.net.SocketAppender;
import org.haw.cas.Adapters.AkkaAdapter.Implementation.AkkaAdapterComponent;
import org.haw.cas.Adapters.AkkaAdapter.Interface.IAkkaAdapter;
import org.haw.cas.Adapters.PersistenceManager.Implementation.Exception.EntityAlreadyExistException;
import org.haw.cas.Adapters.RTCToDataMinerAdapter.Implementation.RTCAdapterComponent;
import org.haw.cas.Adapters.RTCToDataMinerAdapter.Interface.IRTCAdapter;
import org.haw.cas.GlobalTypes.Settings.AppSettings;
import org.haw.cas.GlobalTypes.Settings.SettingException;
import org.haw.cas.RealTimeCore.ApplicationCore.Implementation.ApplicationCoreComponent;
import org.haw.cas.RealTimeCore.CommonTypes.Entities.GeoCoordinate;
import org.haw.cas.RealTimeCore.CommonTypes.Entities.User;
import org.haw.cas.RealTimeCore.CrevasseComponent.Implementation.CrevasseComponent;
import org.haw.cas.RealTimeCore.CrevasseComponent.Interface.ICrevasseComponent;
import org.haw.cas.RealTimeCore.NeedsComponent.Implementation.NeedsComponent;
import org.haw.cas.RealTimeCore.NeedsComponent.Interface.INeedsComponent;
import org.haw.cas.RealTimeCore.UserComponent.Implementation.UserComponent;
import org.haw.cas.RealTimeCore.UserComponent.Interface.IUserComponent;
import org.haw.cas.StatisticsComponent.Implementation.StatisticsComponent;
import org.haw.cas.StatisticsComponent.Interface.IStatisticsComponent;
import org.picocontainer.defaults.DefaultPicoContainer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

/**
 * User: Jason Wilmans
 * Date: 22.10.13
 * Time: 19:50
 * <p/>
 * This factory encapsulates the assembly of the RealTimeCore's Application core. It resolves the dependencies between
 * the core's components and initiates them in the correct order. For now, there is only the productive core. It may
 * also be used to build other configurations of the application core, e.g. with mocked components for testing.
 */
public class ApplicationCoreFactory {

    /**
     * Returns a normal fully initialized application core, containing all components.
     *
     * @return never null
     */
    public static IApplicationCore getApplicationCore() {

        //start log4j with a standard configuration and the log level set in the app's settings
        AppSettings settings = new AppSettings();

        try {
            //BasicConfigurator.configure();
            //BasicConfigurator.configure(new FileAppender(new TTCCLayout("HH:MM:SS"), "./logs/" + LocalDateTime.now().toString() + ".log"));

            SocketAppender sa = new SocketAppender(
                    InetAddress.getByName(settings.getString("elasticSearchServerAddress")),
                    settings.getInt("elasticSearchServerPort"));

            sa.setName("Logstashlogger");
            sa.setLayout(new PatternLayout("%d %-5p [%c{1}] %m%n"));
            sa.setThreshold(Level.DEBUG);
            sa.activateOptions();

            FileAppender fa = new FileAppender();
            fa.setName("FileLogger");
            fa.setFile("./logs/" + LocalDate.now() + ".log");
            fa.setLayout(new PatternLayout("%d %-5p [%c{1}] %m%n"));
            fa.setThreshold(Level.DEBUG);
            fa.setAppend(true);
            fa.activateOptions();

            ConsoleAppender ca = new ConsoleAppender();
            ca.setName("ConsoleAppender");
            ca.setLayout(new PatternLayout("%d %-5p [%c{1}] %m%n"));
            ca.activateOptions();

            //active logging for appenders, if told so in the console
            if (settings.getBoolean("logToFile")) Logger.getRootLogger().addAppender(fa);
            if (settings.getBoolean("logToConsole")) Logger.getRootLogger().addAppender(ca);
            if (settings.getBoolean("logToLogstash")) Logger.getRootLogger().addAppender(sa);

            Logger.getRootLogger()
                    .setLevel(
                            Level.toLevel(
                                    settings.getString("logLevel")
                            )
                    );

        } catch (SettingException e) {
            Logger.getLogger(ApplicationCoreFactory.class).error(e.getMessage());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        //Register all application core parts and let the container resolve their dependencies
        DefaultPicoContainer container = new DefaultPicoContainer();

        container.registerComponentImplementation(IAkkaAdapter.class, AkkaAdapterComponent.class);
        container.registerComponentInstance(IRTCAdapter.class, new RTCAdapterComponent(true));
        container.registerComponentImplementation(IApplicationCore.class, ApplicationCoreComponent.class);
        container.registerComponentImplementation(IUserComponent.class, UserComponent.class);
        container.registerComponentImplementation(INeedsComponent.class, NeedsComponent.class);
        container.registerComponentImplementation(ICrevasseComponent.class, CrevasseComponent.class);
        container.registerComponentImplementation(IStatisticsComponent.class, StatisticsComponent.class);

        return (IApplicationCore) container.getComponentInstanceOfType(ApplicationCoreComponent.class);
    }
}