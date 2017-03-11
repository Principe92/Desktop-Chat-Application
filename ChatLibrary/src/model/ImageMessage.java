package model;

import java.io.File;

import factory.IImageHandler;

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
		// TODO Auto-generated method stub
		return null;
	}

}
