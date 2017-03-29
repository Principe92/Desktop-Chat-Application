package type;

import javax.swing.JPanel;

public interface IMessage {

	void setData(String text);

	boolean IsType(String text);

	byte[] getData();
	
	MessageType getType();

	void setData(byte[] data);

	JPanel getMessagePanel(int x, int y);
}
 