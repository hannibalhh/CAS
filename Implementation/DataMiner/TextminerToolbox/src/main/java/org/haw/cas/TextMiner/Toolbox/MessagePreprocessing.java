package org.haw.cas.TextMiner.Toolbox;

/**
 * Created with IntelliJ IDEA.
 * User: sebastian
 * Date: 12.11.13
 * Time: 12:09
 * To change this template use File | Settings | File Templates.
 */
public class MessagePreprocessing {
    public static String removePunctation(String message){
        return message.replaceAll("[^a-zA-ZäÄöÖüÜß1-9 ]", "");
    }
}
