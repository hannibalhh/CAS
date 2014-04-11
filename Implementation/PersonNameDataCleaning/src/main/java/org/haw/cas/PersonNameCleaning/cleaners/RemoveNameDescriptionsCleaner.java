package org.haw.cas.PersonNameCleaning.cleaners;

/**
 * Created with IntelliJ IDEA.
 * User: N3trunner
 * Date: 30.10.13
 * Time: 12:33
 * To change this template use File | Settings | File Templates.
 */
public class RemoveNameDescriptionsCleaner implements ICleaner {
    @Override
    public String cleanInput(String input) {
        return input.replaceAll("\\s\\s(\\s)+.+","\n");
    }
}
