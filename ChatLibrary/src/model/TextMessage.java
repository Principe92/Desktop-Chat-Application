package model;

import java.io.File;

import main.Util;
import type.IMessage;
import type.MessageType;

public class TextMessage implements IMessage {
	private String data;

	@Override
	public void setData(String text) {
		// TODO Auto-generated method stub
		this.data = text;
		
	}

	@Override
	public boolean IsType(String text) {
		return !(new File(text)).exists();
	}

	@Override
	public byte[] getData() {
		return data != null ? data.getBytes(Util.getEncoding()) : null;
	}

	@Override
	public MessageType getType() {
		return MessageType.TEXT;
	}

	@Override
	public void setData(byte[] data) {
		this.data = new String(data, Util.getEncoding());
	}
}
