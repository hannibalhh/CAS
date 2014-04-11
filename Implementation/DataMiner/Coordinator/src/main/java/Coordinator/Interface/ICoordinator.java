package Coordinator.Interface;

import DataTypes.DataTypeInterfaces.IInfo;
import Interfaces.IAnalyzer;

import java.util.List;
import java.util.Map;

/**
 * User: Jason Wilmans
 * Date: 14.10.13
 * Time: 18:59
 */
public interface ICoordinator {
    public void startCoordinating();
    public void stopCoordinating();
    public void registerAnalyzerResultForCurrentChunk(IAnalyzer analyzer, Map<String, List<IInfo>> resultsForChunk); //String == id der analysierten Message
    public void registerAnalyzer(IAnalyzer analyzer);
}
