package model;

import main.Constant;
import main.Util;
import type.IMessage;
import type.ISocketProtocol;
import type.MessageType;
import type.ProtocolStateType;

public class SocketProtocol implements ISocketProtocol  {

	@Override
	public byte[] getHandShake(IMessage msg) {
		if (msg == null || msg.getData() == null) return null;
		
		String protocol = String.format("%d-%d-%d", ProtocolStateType.HANDSHAKE.getValue(), msg.getType().getValue(), msg.getData().length);
		return protocol.getBytes(Util.getEncoding());
	}

	@Override
	public boolean isHandShake(byte[] data) {
		if (data == null) return false;
		
		String val = new String(data, Util.getEncoding());
		String[] format = val.split(Constant.DELIMITER);
		
		if (format.length == 3){
			ProtocolStateType type = ProtocolStateType.UNKNOWN; 
			
			try{
				type = ProtocolStateType.getType(Integer.parseInt(format[0]));
				
			}catch(NumberFormatException e){
				
			}
			
			return type == ProtocolStateType.HANDSHAKE;
		}
		
		return false;
	}

	@Override
	public MessageType getMessageType(byte[] data) {
		String val = new String(data, Util.getEncoding());
		String[] format = val.split(Constant.DELIMITER);
		
		MessageType type = MessageType.UNKNOWN; 
		
		try{
			type = MessageType.getType(Integer.parseInt(format[1]));
			
		}catch(NumberFormatException e){
			
		}
		
		return type;
	}

}
