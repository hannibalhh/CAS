package org.haw.cas.guiadapter;

/**
 * 
 * Adapter for communication between RTC and GUI. For Data-Structure use
 * ProtoBuf-Def! (TODO link..)
 * 
 * @author alex
 * 
 */

public interface IGUIAdapter {

	// message matching in Play-krams
	// RTC -> GUI
	public void sendMessage(byte[] message);

	// GUI -> RTC -> GUI
	public byte[] getMessage(byte[] request);

	// Future Stuff (?):

	// public void sendMedicineMessage(byte[] message);
	//
	// public void sendAssistantMessage(byte[] message);
	//
	// public void sendDrinkMessage(byte[] message);
	//
	// public void sendFoodMessage(byte[] message);
	//
	// public void sendHousingMessage(byte[] message);

}
