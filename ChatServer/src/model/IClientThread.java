package model;

public interface IClientThread {

	void sendMessageToSocket(byte[] message);

	void startThread();

	int getClientId();

	Client getClient();

}
	