package org.haw.cas.ApplicationCore.Implementation;

import NeedsAnalyzer.Implementation.Component.NeedsAnalyzerComponent;
import org.haw.cas.ApplicationCore.Implementation.ApplicationCore;
import org.haw.cas.ApplicationCore.Interface.IDataMiner;
import Coordinator.Component.CoordinatorComponent;
import DSPersistenceManager.Services.MessageDAO;
import Implementation.Component.CrevasseAnalyzerComponent;
import MessageAdapter.Implementation.Component.MessageAdapterComponent;
import org.haw.cas.GlobalTypes.Settings.AppSettings;
import org.haw.cas.GlobalTypes.Settings.SettingException;
import org.haw.cas.PlaceOfResidenceAnalyzer.Implementation.Component.PlaceOfResidenceAnalyzerComponent;
import org.haw.cas.Adapters.RTCToDataMinerAdapter.Implementation.RTCAdapterComponent;
import org.picocontainer.MutablePicoContainer;
import org.picocontainer.defaults.DefaultPicoContainer;


/**
 * User: Jason Wilmans
 * Date: 14.10.13
 * Time: 18:59
 */
public class ApplicationCoreFactory {

    public static IDataMiner getApplicationCore() {
        MutablePicoContainer pico = new DefaultPicoContainer();
        pico.registerComponentImplementation(MessageDAO.class);
        pico.registerComponentImplementation(RTCAdapterComponent.class);
        pico.registerComponentImplementation(CoordinatorComponent.class);
        pico.registerComponentImplementation(MessageAdapterComponent.class);
        pico.registerComponentImplementation(PlaceOfResidenceAnalyzerComponent.class);
        pico.registerComponentImplementation(CrevasseAnalyzerComponent.class);
        pico.registerComponentImplementation(NeedsAnalyzerComponent.class);

        pico.registerComponentImplementation(ApplicationCore.class);

        return (IDataMiner) pico.getComponentInstanceOfType(ApplicationCore.class);
    }

}
