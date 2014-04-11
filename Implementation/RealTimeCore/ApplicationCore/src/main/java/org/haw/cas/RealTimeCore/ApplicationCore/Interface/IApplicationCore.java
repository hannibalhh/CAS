package org.haw.cas.RealTimeCore.ApplicationCore.Interface;

import org.haw.cas.RealTimeCore.CrevasseComponent.Interface.ICrevasseComponent;
import org.haw.cas.Adapters.AkkaAdapter.Interface.IAkkaAdapter;
import org.haw.cas.Adapters.RTCToDataMinerAdapter.Interface.Messages.IDataMinerMessage;
import org.haw.cas.RealTimeCore.NeedsComponent.Interface.INeedsComponent;
import org.haw.cas.RealTimeCore.UserComponent.Interface.IUserComponent;

import java.util.function.Consumer;

/**
 * User: Jason Wilmans
 * Date: 22.10.13
 * Time: 19:50
 *
 * This interface is the entry point for the RealTimeCore's application core.
 *
 * NOTE: All the methods in this interface are thread-safe. there is no need to worry about synchronizing.
 */
public interface IApplicationCore extends IUserComponent, INeedsComponent, IAkkaAdapter, ICrevasseComponent {

    /**
     * @see org.haw.cas.Adapters.RTCToDataMinerAdapter.Interface.IRTCAdapter
     * @param messageType
     * @param messagelistener
     * @param <M>
     */
    public <M extends IDataMinerMessage> void subscribeForInformationMessage(Class<M> messageType, Consumer<M> messagelistener);

}
