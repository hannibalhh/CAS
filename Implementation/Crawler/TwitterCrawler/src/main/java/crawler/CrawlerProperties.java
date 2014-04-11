package crawler;

import org.haw.cas.GlobalTypes.Settings.AppSettings;
import org.haw.cas.GlobalTypes.Settings.SettingException;

import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: sebastian Kai
 * Date: 17.10.13
 * Time: 10:46
 */
public class CrawlerProperties {
    private AppSettings settings;


    public CrawlerProperties() {
        settings = new AppSettings();
    }

    public List<String> getSearchTerms() {
        List<String> result = new LinkedList<String>();
        try {
            result = settings.getStringList("searchTerms");
        } catch (SettingException e) {
            e.printStackTrace();
        }
        return result;
    }

    public int getTrendsRateThreshold() {
        int result = 0;
        try {
            result = settings.getInt("remainingSearchRateToCrawlForTrends");
        } catch (SettingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return result;
    }

    public int getTrendsTimeThreshold() {
        int result = 0;
        try {
            result = settings.getInt("remainingSecondsTillRateRenew");
        } catch (SettingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return result;
    }

    public int getCrawlIntervalInSec() {
        int sec = 0;
        try {
            sec = settings.getInt("crawlInterval");
        } catch (SettingException e) {
            e.printStackTrace();
        }
        return sec;
    }

    public List<Integer> getWoeidList() {

        try {
            return settings.getIntList("woeidList");
        } catch (SettingException e) {
            e.printStackTrace();
        }
        return new LinkedList<Integer>();
    }
}
