package interfaces;

public interface IReadSocketListener {
	public void printToScreen(byte[] msg);

	public void close();
}
