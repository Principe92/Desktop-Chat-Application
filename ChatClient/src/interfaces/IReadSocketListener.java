package interfaces;

import type.IMessage;

public interface IReadSocketListener {
	public void printToScreen(IMessage msg);

	public void close();
}
