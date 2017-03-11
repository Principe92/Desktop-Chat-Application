package main;

public interface IClientThread {

	void sendMessageToSocket(String message);

	void startThread();

	int getClientId();

}
	