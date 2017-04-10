package model;

import javax.swing.*;
import java.awt.*;

@SuppressWarnings("serial")
public class TextPanel extends JPanel {

    private final String text;
    private final int width;
    private int height;

    public TextPanel(String text, int width, int height) {
        //  setBorder(BorderFactory.createLineBorder(Color.black));

        this.text = text;
        this.width = width;
        this.height = height;
        this.setSize(new Dimension(width, height));
    }

    @Override
    protected void paintComponent(Graphics g) {
        //g.setColor(Color.BLACK);
        super.paintComponent(g);

        FontMetrics fm = g.getFontMetrics();
        int y = (height - fm.getHeight()) / 2;
        int x = fm.getMaxAdvance();

        System.out.println(String.format("%d %d %d %d", height, x, y, width));

        g.drawString(text, x, y);
    }
}
