package org.haw.cas.Adapters.PersistenceManager.Implementation;


import com.hazelcast.config.*;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.apache.log4j.Logger;
import org.haw.cas.GlobalTypes.Settings.AppSettings;
import org.haw.cas.GlobalTypes.Settings.SettingException;
import org.haw.cas.RealTimeCore.CommonTypes.Entities.IAbstractEntity;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.List;
import java.util.Map;


/**
 * Created with IntelliJ IDEA.
 * User: Nils
 * Date: 01.11.13
 * Time: 14:55
 * <p/>
 * This class is used to read the hibernate settings.
 */
public class DatabaseServices {

    private static final Logger logger = Logger.getLogger(DatabaseServices.class);
    private static HazelcastInstance entityHazelcastInstance;
    private static String serverName;
    private static AppSettings appSettings = new AppSettings();

    public DatabaseServices() {
    }

    private static HazelcastInstance getHazelcastInstance() {
        if (entityHazelcastInstance == null) {
            try {
                if (appSettings.getBoolean("testing") || !appSettings.getBoolean("HazelcastLookForServer")) {
                    entityHazelcastInstance = Hazelcast.newHazelcastInstance();
                } else {
                    entityHazelcastInstance = Hazelcast.newHazelcastInstance(getdHazelcastConfig());
                }
            } catch (SettingException e) {
                e.printStackTrace();
            }

        }

        return entityHazelcastInstance;
    }

    public static <V extends IAbstractEntity> Map<Long, V> getNewEntityMap(String mapName) {
        getHazelcastInstance().getConfig().addMapConfig(getEntityMapConfig(mapName));
        return getHazelcastInstance().getMap(mapName);
    }

    public static <K extends IAbstractEntity> Map<Class<K>, IdClassMapper> getNewIdEntityMap(String mapName) {
        getHazelcastInstance().getConfig().addMapConfig(getIdMapConfig(mapName));
        return getHazelcastInstance().getMap(mapName);
    }

    /**
     * Builds the prepared Hazelcast config without the Mapconfig. For building the Mapconfig a specific parameter
     * is needed for building the Mapconfig, this part will be added when the map is requested.
     *
     * @return
     */
    private static Config getdHazelcastConfig() {
        AppSettings appSettings = new AppSettings();
        Config config = new Config();

        //Name of the Server node
        try {
            if (appSettings.getBoolean("testing")) {
                serverName = appSettings.getString("TestServerNode");
            } else {
                serverName = appSettings.getString("HazelcastServerNode");
            }

            //NetworkConfig
            //Disable Multicast
            MulticastConfig multicastConfig = new MulticastConfig();
            multicastConfig.setEnabled(false);

            //set Server address for TCP connection
            TcpIpConfig tcpIpConfig = new TcpIpConfig();
            tcpIpConfig.setEnabled(true);
            tcpIpConfig.addMember(serverName);


            //Wraps the join and tcp config
            JoinConfig joinConfig = new JoinConfig();
            joinConfig.setMulticastConfig(multicastConfig);
            joinConfig.setTcpIpConfig(tcpIpConfig);

            InterfacesConfig interfacesConfig = new InterfacesConfig();
            interfacesConfig.setEnabled(true);

            // add all nics to possible hosting interfaces
            try {


                List<NetworkInterface> nics = Collections.list(NetworkInterface.getNetworkInterfaces());
                nics.forEach(n ->
                        n.getInterfaceAddresses().forEach(address -> {
                            if (address.getAddress().getHostAddress().startsWith("141")) {
                                interfacesConfig.addInterface(address.getAddress().getHostAddress());
                            }

                        }
                        )
                );

            } catch (SocketException e) {
                e.printStackTrace();
            }


            //finally set the network config
            NetworkConfig networkConfig = new NetworkConfig();
            networkConfig.setInterfaces(interfacesConfig);
            networkConfig.setPortAutoIncrement(true);
            networkConfig.setJoin(joinConfig);


            //put the map and the networking in the main config
            config.setNetworkConfig(networkConfig);

        } catch (SettingException e) {
            e.printStackTrace();
        }

        return config;
    }

    /**
     * Returns the standard mapconfig and adds an new map to the known Hazelcastmaps
     *
     * @param mapName the name of the new map
     * @return pre configured mapconfig with new mapName
     */
    private static MapConfig getMapConfig(String mapName) {

        // use near cache if needed
        NearCacheConfig nearCacheConfig = new NearCacheConfig();
//        nearCacheConfig.setMaxSize(1000)
//                .setMaxIdleSeconds(120)
//                .setTimeToLiveSeconds(1800);

        //MapConfig that is wrapping the MapStoreConfig
        MapConfig mapConfig = new MapConfig();

        mapConfig.setName(mapName);
//        mapConfig.setBackupCount(0);
//        mapConfig.setTimeToLiveSeconds(300);
//        mapConfig.setNearCacheConfig(nearCacheConfig);

        return mapConfig;
    }


    /**
     * returns an new Hazelcast mapConfig and adds the a new MapConfig to the HazelcastInstance if HazelPersistence
     * is enabled in Appsettings. If not the mapName is added to the mapConfig and a default MapStoreConfig is used
     * (default MapStoreConfig = disabled)
     *
     * @param mapName the name of the new map
     * @return
     */
    private static MapConfig getIdMapConfig(String mapName) {


        MapConfig mapConfig = getMapConfig(mapName);


        try {
            if (appSettings.getBoolean("HazelcastPersistence")) {

                //generate MapStoreConfig
                MapStoreConfig mapStoreConfig = new MapStoreConfig();
                //enables to persist the map

                mapStoreConfig.setEnabled(true);
                //mapStoreConfig.setClassName(EntityMapStoreImplementation.class.getName());
                mapStoreConfig.setClassName(IdMapStore.class.getName());
                mapStoreConfig.setImplementation(new IdMapStore());

                mapStoreConfig.setWriteDelaySeconds(0);
                mapConfig.setMapStoreConfig(mapStoreConfig);

            }
        } catch (SettingException e) {
            e.printStackTrace();
        }

        //enables to persist the map


        return mapConfig;
    }

    /**
     * returns an new Hazelcast mapConfig and adds the a new MapConfig to the HazelcastInstance if HazelPersistence
     * is enabled in Appsettings. If not the mapName is added to the mapConfig and a default MapStoreConfig is used
     * (default MapStoreConfig = disabled)
     *
     * @param mapName the name of the new map
     * @return
     */
    private static MapConfig getEntityMapConfig(String mapName) {
        MapConfig mapConfig = getMapConfig(mapName);

        try {
            if (appSettings.getBoolean("HazelcastPersistence")) {
                //generate MapStoreConfig
                MapStoreConfig mapStoreConfig = new MapStoreConfig();
                //enables to persist the map

                mapStoreConfig.setEnabled(true);
                //mapStoreConfig.setClassName(EntityMapStoreImplementation.class.getName());

                mapStoreConfig.setImplementation(new EntityMapStore(Class.forName(mapName)));

                mapStoreConfig.setWriteDelaySeconds(0);
                mapConfig.setMapStoreConfig(mapStoreConfig);
            }


        } catch (ClassNotFoundException e) {
            logger.fatal("DAO initialized incorrectly. Expected valid class name as map name but got: " + mapName, e);

        } catch (SettingException e) {
            e.printStackTrace();
        }
        return mapConfig;

    }

    public static void Shutdown(Class clazz) {
        logger.warn("Shutdown was called from an external class " + clazz.getSimpleName() + " HazelcastInstance is shutting down now!");
        entityHazelcastInstance.shutdown();

    }


}
