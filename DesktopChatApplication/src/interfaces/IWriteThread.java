package interfaces;

public interface IWriteThread {
	void end();
	
	void sendToSocket(String text);

} 
