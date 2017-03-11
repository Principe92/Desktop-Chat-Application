package model;

public interface IMessage {

	void setData(String text);

	boolean IsType(String text);

	byte[] getData();

}
