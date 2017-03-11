package main;

public interface IClientListener {

	void sendMessage(String message, int senderId);

	void removeClient(int id);

}
