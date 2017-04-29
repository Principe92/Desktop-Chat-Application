package gui;

import main.Constant;
import main.Util;

import javax.swing.*;
import java.awt.*;

/**
 * Created by okori on 05-Apr-17.
 */
public class ImageButton extends JButton {
    public ImageButton(String hoverText, String icon) {
        super(Constant.EMPTY);
        setForeground(Color.blue);
        setToolTipText(hoverText);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setBorderPainted(false);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        setIcon(new ImageIcon(this.getClass().getResource(Util.fillIconPath(icon))));
    }
}
