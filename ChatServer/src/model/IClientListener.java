package model;

public interface IClientListener {

	void sendMessage(byte[] msg, int senderId);

	void removeClient(int id);

}
