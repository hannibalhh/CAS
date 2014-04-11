package org.haw.cas.Adapters.AkkaAdapter.Interface.Posts;

import org.haw.cas.Adapters.AkkaAdapter.Interface.Messages.IAkkaMessage;

/**
 * This class implements the functional message that embodies the GUI's request for no new posts without position.
 */
public class UpdatePostsMessage implements IAkkaMessage {

    @Override
    public byte[] getBytes() {
        return new byte[0];
    }
}
