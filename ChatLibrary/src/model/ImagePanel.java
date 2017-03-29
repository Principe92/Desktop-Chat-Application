package model;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ImagePanel extends JPanel {

	private String path;
	private BufferedImage image;
	
	public ImagePanel(String path){
		this.path = path;
		loadImage();
	}
	
	public ImagePanel(byte[] image){
		try {
			this.image = ImageIO.read(new ByteArrayInputStream(image));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void loadImage() {
		 try {                
	          image = ImageIO.read(new File(path));
	       } catch (IOException e) {
	    	   e.printStackTrace();
	       }
	}
	
	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);
    }
}
