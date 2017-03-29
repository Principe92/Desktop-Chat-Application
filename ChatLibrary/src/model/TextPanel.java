package model;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class TextPanel extends JPanel {

	private final String text;
	private final int width;
	private int height;
	
	public TextPanel(String text, int width, int height){
		setBorder(BorderFactory.createLineBorder(Color.black));
		
		this.text = text;
		this.width = width;
		this.height = height;
	}
	
	@Override
	protected void paintComponent(Graphics g){
		//g.setColor(Color.BLACK);
		super.paintComponent(g);
		
		FontMetrics fm = g.getFontMetrics();
		int y = (height - fm.getHeight())/2;
		
		

	    g.drawString(text, 0, 20);
	}
}
