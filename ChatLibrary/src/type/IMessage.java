package type;

public interface IMessage {

	void setData(String text);

	boolean IsType(String text);

	byte[] getData();
	
	MessageType getType();

	void setData(byte[] data);

}
