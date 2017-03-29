package model;

import java.awt.Dimension;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JPanel;

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

	@Override
	public JPanel getMessagePanel(int x, int y) {
		TextPanel panel = new TextPanel(this.data, this.data.length(), 20);
		panel.setMaximumSize(new Dimension(this.data.length() + 20, 50));
		return panel;
	}
}
