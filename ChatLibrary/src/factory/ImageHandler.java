package factory;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import type.IImageHandler;

public class ImageHandler implements IImageHandler {
	
	private BufferedImage image;

	@Override
	public boolean loadImage(String text) {
		try{
			
			image = ImageIO.read(new File(text));
			return true;
			
		}catch(IOException exception){
			
			image = null;
			return false;
		}
	}

	@Override
	public byte[] getImageAsBytes() {

		if (image != null){
			 WritableRaster raster = image.getRaster();
			 DataBufferByte data   = (DataBufferByte) raster.getDataBuffer();

			 return ( data.getData() );
		}
		
		return null;
	}

	@Override
	public int getImageWidth() {
		return image != null ? image.getWidth() : 0;
	}

	@Override
	public int getImageHeight() {
		return image != null ? image.getHeight() : 0;
	}

}
