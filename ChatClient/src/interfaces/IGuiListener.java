package interfaces;

import java.io.IOException;

import type.IMessage;

public interface IGuiListener {

	void sendText(IMessage message) throws IOException;

}
