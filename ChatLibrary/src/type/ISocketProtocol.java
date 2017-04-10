package type;

public interface ISocketProtocol {

	byte[] getHandShake(IMessage msg);

	boolean isHandShake(byte[] data);

	MessageType getMessageType(byte[] data);

    String getSender(byte[] data);
}
