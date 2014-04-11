package org.haw.cas.guiadapter.handler.sender;

public interface IRTCGuiSender {

	public void openChannel(String host, Integer port, String queueName);

	public void closeChannel();

	public Boolean sendMessage(byte[] byteArr);

}
