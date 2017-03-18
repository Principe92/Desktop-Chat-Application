package model;

import java.io.File;

import type.IImageHandler;
import type.IMessage;
import type.MessageType;

public class ImageMessage implements IMessage {
	private byte[] data;
	private IImageHandler imageHandler;
	
	public ImageMessage(IImageHandler imageHandler) {
		this.imageHandler = imageHandler;
	}

	@Override
	public void setData(String text) {
		if (IsType(text)){
			
			if (imageHandler.loadImage(text)){
			 data =	imageHandler.getImageAsBytes();
			}
			
		}
	}

	@Override
	public boolean IsType(String text) {
		return new File(text).exists();
	}

	@Override
	public byte[] getData() {
		return data;
	}

	@Override
	public MessageType getType() {
		return MessageType.IMAGE;
	}

	@Override
	public void setData(byte[] data) {
		this.data = data;
	}

}
