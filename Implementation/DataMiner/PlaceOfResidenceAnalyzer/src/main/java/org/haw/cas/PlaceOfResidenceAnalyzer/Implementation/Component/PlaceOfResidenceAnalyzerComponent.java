package org.haw.cas.PlaceOfResidenceAnalyzer.Implementation.Component;

import Coordinator.Component.CoordinatorComponent;
import Coordinator.Interface.ICoordinator;
import Coordinator.Interface.abstractClasses.AbstractAnalyzer;
import DSPersistenceManager.Model.Message;
import DataTypes.DataTypeInterfaces.IInfo;
import MessageAdapter.Interface.IMessageAdapter;
import org.haw.cas.PlaceOfResidenceAnalyzer.Implementation.Usecases.AnalyzeChunkForLocations;
import org.haw.cas.PlaceOfResidenceAnalyzer.Implementation.Usecases.GoogleMapsAchieveGeoData;
import org.haw.cas.PlaceOfResidenceAnalyzer.Implementation.Usecases.MapServiceInquiryValidator;
import org.haw.cas.PlaceOfResidenceAnalyzer.Interface.IPlaceOfResidenceAnalyzer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Jan
 * Date: 24.10.13
 * Time: 19:39
 * To change this template use File | Settings | File Templates.
 */
public class PlaceOfResidenceAnalyzerComponent extends AbstractAnalyzer implements IPlaceOfResidenceAnalyzer {
    public AnalyzeChunkForLocations useCaseAnalyzeChunkForLocations = null;
    private MapServiceInquiryValidator mapServiceInquiryValidator;
    private GoogleMapsAchieveGeoData googleMapsAchieveGeoData;

    public PlaceOfResidenceAnalyzerComponent(IMessageAdapter messageAdapter, ICoordinator coordinatorComponent){
        super(messageAdapter, coordinatorComponent);
        this.mapServiceInquiryValidator = new MapServiceInquiryValidator();
        this.googleMapsAchieveGeoData = new GoogleMapsAchieveGeoData();
        this.useCaseAnalyzeChunkForLocations = new AnalyzeChunkForLocations(this.mapServiceInquiryValidator, this.googleMapsAchieveGeoData);
    }

    @Override
    protected Map<Message, List<IInfo>> processMessages(Iterable<Message> messages) {
        Map<Message, List<IInfo>> result = new HashMap<>();
        for(Message currentMessage : messages){
            result.put(currentMessage, useCaseAnalyzeChunkForLocations.processMessage(currentMessage));
        }
        return result;
    }
}
