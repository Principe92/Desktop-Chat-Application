package model;

import java.io.File;

import main.Util;
import type.IImageHandler;
import type.IMessage;
import type.MessageType;

public class ImageMessage extends TextMessage implements IMessage {
	private byte[] data;
	private IImageHandler imageHandler;
	private String path;
	
	public ImageMessage(IImageHandler imageHandler) {
		this.imageHandler = imageHandler;
	}

	@Override
	public void setData(String text) {
		if (IsType(text)){
			
			if (imageHandler.loadImage(text)){
			 data =	imageHandler.getImageAsBytes();
			}
			
			this.path = text;
			
		}
	}

	@Override
	public boolean IsType(String text) {
		return new File(text).exists();
	}

	@Override
	public byte[] getData() {
		//return data;
		return this.path.getBytes(Util.getEncoding());
	}

	@Override
	public MessageType getType() {
		return MessageType.IMAGE;
	}

	@Override
	public void setData(byte[] data) {
		//this.data = data;
		this.path = new String(data, Util.getEncoding());
	}

//	@Override
//	public JPanel getMessagePanel(int x, int y) {
//		ImagePanel panel = new ImagePanel(this.data);
//		panel.setBounds(x, y, imageHandler.getImageWidth(), imageHandler.getImageHeight());
//		return panel;
//	}

}
