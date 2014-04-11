package org.haw.cas.PersonNameCleaning.cleaners;

/**
 * Created with IntelliJ IDEA.
 * User: N3trunner
 * Date: 30.10.13
 * Time: 12:37
 * To change this template use File | Settings | File Templates.
 */
public class NameVariationsCleaner implements ICleaner {
    @Override
    public String cleanInput(String input) {
        return input.replaceAll("([a-zA-Zé]+)\\(([a-zA-Zé])\\)", "$1\n$1$2");
    }
}
