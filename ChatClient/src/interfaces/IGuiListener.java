package interfaces;

import java.io.IOException;

public interface IGuiListener {

	void sendText(byte[] msg) throws IOException;

}
