package org.haw.cas.guiadapter;

import org.haw.cas.guiadapter.IGUIAdapter;
import org.haw.cas.guiadapter.data.config.Config;
import org.haw.cas.guiadapter.handler.IRTCGuiHandler;
import org.haw.cas.guiadapter.handler.RTCGuiHandler;

/**
 * 
 * Adapter for RTC. Sends Data to GUI.
 * 
 * @author alex
 * 
 */
public abstract class AbstractGUIAdapter implements IGUIAdapter {

	protected abstract Config config();
	protected IRTCGuiHandler guiHandler = new RTCGuiHandler(this,config());

	/**
	 * This method sends a message to all clients.
	 * 
	 * @param message
	 *            that should be send
	 */
	@Override
	public void sendMessage(byte[] message) {
		guiHandler.sendToAll(message);
	}

	/**
	 * This method has the incoming message as parameter
	 * and choose the rtc business methods and returns
	 * the answer as byte array
	 * 
	 * @return all data that are stored at that moment.
	 */
	@Override
	abstract public byte[] getMessage(byte[] request);
	
}