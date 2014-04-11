package main;

import org.adapter.IGUIAdapter;
import org.handler.IRTCGuiHandler;
import org.handler.RTCGuiHandler;

/**
 * 
 * Adapter for RTC. Sends Data to GUI.
 * 
 * @author alex
 * 
 */

public class GUIAdapterImpl implements IGUIAdapter {

	private static IGUIAdapter adapter;
	private final IRTCGuiHandler guiHandler;

	/**
	 * Constructor.
	 */
	private GUIAdapterImpl() {
		super();

		this.guiHandler = new RTCGuiHandler(this);
	}

	/**
	 * Method that returns an Instance of this Adapter.
	 * 
	 * @return Instance of the Adapter
	 */
	public static IGUIAdapter getInstance() {
		if (adapter == null) {
			adapter = new GUIAdapterImpl();
		}

		return adapter;
	}

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
	 * This method calls
	 * 
	 * @return all data that are stored at that moment.
	 */
	@Override
	public byte[] getMessage(byte[] request) {
		// TODO build ProtoBuf krams from Data
		return "".getBytes();
	}
}
