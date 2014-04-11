package crawler;

import DSPersistenceManager.Model.Message;
import DSPersistenceManager.Model.Trend;
import DSPersistenceManager.Services.MessageDAO;
import DSPersistenceManager.Services.TrendDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.haw.cas.GlobalTypes.MessageInfo.Provenance;
import org.haw.cas.GlobalTypes.Settings.AppSettings;
import twitter4j.*;
import twitterAccess.Tweet;
import twitterAccess.TwitterSearch;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: sebastian, Kai
 * Date: 17.10.13
 * Time: 10:28
 */
public class Crawler {
    static Logger logger = LogManager.getLogger(Crawler.class);
    private Map<String, Long> minIds = new HashMap<>();
    private TwitterSearch twitterSearch = new TwitterSearch();
    private CrawlerProperties properties = new CrawlerProperties();
    private TwitterFactory twitterFactory = new TwitterFactory();

    public Crawler() {
    }


    private void updateMinIds() {
        List<String> wordsCrawlingFor = findWordsCrawlingFor();
        for (String searchTerm : wordsCrawlingFor) {
            if (!(minIds.containsKey(searchTerm.toLowerCase()))) {
                minIds.put(searchTerm.toLowerCase(), 0l);
            }
        }
        //Remove Trends to crawl for
        if (!shouldTrendsBeCrawled()) {
            logger.info("### Trends should no longer be crawled and will be removed ###");
            Set<String> unusedSearchTerms = new HashSet<>(minIds.keySet());
            unusedSearchTerms.removeAll(wordsCrawlingFor);
            for (String elem : unusedSearchTerms) {
                logger.debug("Removing Trends: " + elem);
                minIds.remove(elem);
            }
        }
    }

    private void updateMinIds(String searchTerm, Long id) {
        this.minIds.put(searchTerm.toLowerCase(), id);
    }

    private List<String> findWordsCrawlingFor() {
        List<String> result = new ArrayList<>();
        //all Terms to lower case
        properties.getSearchTerms().forEach((String elem) -> {
            result.add(elem.toLowerCase());
        });
        return result;
    }

    public void crawl() {
        while (true) {
            logger.info("Begin Crawling for SearchTerms");
            //System.out.println("Begin Crawling for SearchTerms");
            if (shouldTweetsBeCrawled()) {
                crawlOnce();
            }
            logger.info("End Crawling for SearchTerms");
            if (shouldTrendsBeCrawled()) {
                logger.info("Begin Trend Crawling");
                crawlTrends();
                logger.info("EndTrendCrawling");
            } else {
                logger.info("No Trends Crawling");
            }
            try {
                Thread.sleep(properties.getCrawlIntervalInSec() * 1000);
            } catch (InterruptedException e) {
                logger.error("Cannot put thread to sleep", e);
            }
        }
    }

    private boolean shouldTrendsBeCrawled() {
        boolean result = false;
        Twitter twitter = twitterFactory.getInstance();
        try {
            Map<String, RateLimitStatus> rateLimitStatus = twitter.getRateLimitStatus();
            int trendRateLimit = rateLimitStatus.get("/trends/place").getRemaining();
            int remainingRateSearch = rateLimitStatus.get("/search/tweets").getRemaining();
            int remainingTimeSearch = rateLimitStatus.get("/search/tweets").getSecondsUntilReset();
            int rateThreshold = properties.getTrendsRateThreshold();
            int timeThreshold = properties.getTrendsTimeThreshold();
            logger.debug("Should Trends be crawled?");
            logger.debug("Rate Threshold: remaining(" + remainingRateSearch + ") > rateThreshold(" + rateThreshold + ")");
            logger.debug("Time Threshold: remaining(" + remainingTimeSearch + ") < timeThreshold(" + timeThreshold + ")");
            result = (trendRateLimit > 0) && (remainingRateSearch > rateThreshold) && (remainingTimeSearch < timeThreshold);
            logger.info("Trends will be crawled in next turn: " + result);
        } catch (TwitterException e) {
            logger.error("TwitterRateLimitStatus exceeded in shouldTrendsbeCrawled", e);
        }
        return result;
    }

    private boolean shouldTweetsBeCrawled() {
        boolean result = false;
        Twitter twitter = twitterFactory.getInstance();
        try {
            int remainingRateSearch = twitter.getRateLimitStatus().get("/search/tweets").getRemaining();
            result = remainingRateSearch > 0;
        } catch (TwitterException e) {
            logger.error("RateLimitStatus exceeded in shouldTweetsBeCrawled()",e);
        }
        logger.info("Should Tweets be crawled? " + result);
        return result;

    }


    private void crawlTrends() {
        Twitter twitter = twitterFactory.getInstance();
        List<Trend> trendsList = new LinkedList<>();
        List<Integer> woeidList = properties.getWoeidList();
        try {
            Trends trends;
            for (Integer woeid : woeidList) {
                trends = twitter.getPlaceTrends(woeid);
                logger.debug("Trends for: " + trends.getLocation().getName());
                trendsList.add(Transformator.trendsToTrend(trends));
            }
            pushTrendsToDB(trendsList);
            logger.debug("Put Trends into SearchTerm Map");
            for (Trend trend : trendsList) {
                for (String elem : trend.getTrends()) {
                    logger.debug("Trend: " + elem);
                    if (!minIds.containsKey(elem)) {
                        updateMinIds(elem, 0l);
                    }
                }
            }
        } catch (TwitterException e) {
            logger.error("Request Trend Rate Limit exceeded!",e);
        }
    }

    private void crawlOnce() {
        List<Message> allMessages = new LinkedList<>();
        List<Tweet> tweets;
        long minId;
        updateMinIds();
        for (Map.Entry<String, Long> entry : this.minIds.entrySet()) {
            tweets = twitterSearch.searchUsingMinId(entry.getKey(), entry.getValue());
            logger.info("gefundene Tweets für " + entry.getKey() + ": " + tweets.size());
            if (tweets.size() > 0) {
                minId = findMinId(tweets);
                updateMinIds(entry.getKey(), minId);
            }
            allMessages.addAll(Transformator.tweetsToMessage(tweets));
        }
        logger.info("#### Push to DB ####");
        logger.debug("Pushing " + allMessages.size() + " Messages to DB");
        pushMessagesToDB(allMessages);
        logger.info("Everything Pushed\n");
    }

    private void pushMessagesToDB(List<Message> messages) {
        MessageDAO messageDAO = new MessageDAO();
        for (Message message : messages) {
            messageDAO.createMessage(message);
        }
    }

    private void pushTrendsToDB(List<DSPersistenceManager.Model.Trend> trends) {
        TrendDAO trendDAO = new TrendDAO();
        for (Trend trend : trends) {
            trendDAO.create(trend);
        }
    }

    private long findMinId(List<Tweet> tweets) {
        long result = 0;
        for (Tweet tweet : tweets) {
            if (tweet.getId() > result) {
                result = tweet.getId();
            }
        }
        return result;
    }

    public static void main(String args[]) {

        AppSettings settings = new AppSettings();

     //Crawling

        MessageDAO messageDAO = new MessageDAO();

       Message message1 = new Message("verfassungklage","Das Hochwasser-Hilfe-Netzwerk Hier können sie als Betroffener des Jahrhunderthochwassers Hilfsgesuche einstellen. http://hochwasser.piratenpartei-bayern.de",new LinkedList<String>(Arrays.asList()),new Date(),null, new LinkedList<>(Arrays.asList("http://hochwasser.piratenpartei-bayern.de")), Provenance.Twitter);
       Message message2 = new Message("MedTest","Helfer in Horst gesucht um Medikamente auszuteilen!",new LinkedList<String>(Arrays.asList()),new Date(),null, new LinkedList<>(Arrays.asList("pic.twitter.com/dgbKz1UyaI")), Provenance.Twitter);

        // System.out.println("MessageTime: " + message.getPostTime());

       // System.out.println("MessagTimeAfter: " + messageDAO.getMessageById("5270F6A29DB6FD24B7D96903").getGeodata());
        // messageDAO.createMessage(message);

         List<Message> testMessageList = new LinkedList<>();

       Message message3 = new Message("SchulzStep","Deichbaustelle in #Zangenberg Zeitz. 60 Bundeswehrsoldaten und freiwillig Helfer versuchen Deichbruch zu verhindern. pic.twitter.com/dgbKz1UyaI",new LinkedList<String>(Arrays.asList("#Zangenberg")),new Date(),null, new LinkedList<>(Arrays.asList("pic.twitter.com/dgbKz1UyaI")), Provenance.Twitter);
       Message message4 = new Message("FranzJuergen","In Draschwitz ist im #Hochwasser ein Deich gebrochen. Grund: Wurzeln, die den Deich durchzogen und geschädigt haben. http://bit.ly/11tXSSU",new LinkedList<String>(Arrays.asList("#Hochwasser")),new Date(),null, new LinkedList<>(Arrays.asList("http://bit.ly/11tXSSU")), Provenance.Twitter);
       Message message5 = new Message("JustSylt","Orkan Xaver: Deichbruch auf Sylt – Dach der Nordseeklinik abegdeckt http://fb.me/1LK15zUTe ",new LinkedList<String>(),new Date(),null, new LinkedList<>(Arrays.asList("http://fb.me/1LK15zUTe")), Provenance.Twitter);
           //NEED
       Message message6 = new Message("DeichretterEV","Wir brauchen Hilfe bei Fischbeck da der Deich gebrochen ist. pic.twitter.com/dgbKz1UyaI",new LinkedList<String>(Arrays.asList()),new Date(),null, new LinkedList<>(Arrays.asList("pic.twitter.com/dgbKz1UyaI")), Provenance.Twitter);
       Message message7 = new Message("MedTest","Helfer in Horst gesucht um Medikamente auszuteilen!",new LinkedList<String>(Arrays.asList()),new Date(),null, new LinkedList<>(Arrays.asList("pic.twitter.com/dgbKz1UyaI")), Provenance.Twitter);
       testMessageList.add(message1);
       testMessageList.add(message2);
       testMessageList.add(message3);
       testMessageList.add(message4);
       testMessageList.add(message5);
       testMessageList.add(message6);
       testMessageList.add(message7);
         for(Message message:testMessageList) {
             System.out.println("Message pushed: " + message);
             messageDAO.createMessage(message);
         }

      //  messageDAO.createMessage(message1);
        // Message message = new Message("dpa","Deich bei Fischbeck wieder geschlossen http://bit.ly/13fU21F  #Deichbruch #Fischbeck #Hochwasser",new LinkedList<String>(Arrays.asList("#Fischbeck","#Hochwasser","Deichbruch")),new Date(),null, new LinkedList<>(Arrays.asList("http://bit.ly/13fU21F")), Provenance.Twitter);

        Crawler crawler = new Crawler();
        crawler.crawl();
    }
}
