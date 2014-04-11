package org.haw.cas.Adapters.AkkaAdapter.Interface.Messages;

/**
 * Created with IntelliJ IDEA.
 * User: Chris
 * Date: 26.10.13
 * Time: 17:15
 * Marker Interface for all Messages
 */
public interface IAkkaMessage {

    /**
     * Returns a byte stream representation of this message.
     *
     * @return may be of length 0, never null
     */
    byte[] getBytes();
}
