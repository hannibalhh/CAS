package org.haw.cas.DataMiningTools.TextMining;

/**
 * User: Jason Wilmans
 * Date: 01.11.13
 * Time: 21:36
 *
 * This is the general interface of an algorithm, that is capable of calculating a distance between two strings.
 */
public interface IStringDistanceCalculator {

    /**
     * Returns an abstract measure of the distance between two strings.
     * @param string1 not null
     * @param string2 not null
     * @return the results vary depending on the slgorithm used.
     */
    public int calculateDistance(String string1, String string2);




}
