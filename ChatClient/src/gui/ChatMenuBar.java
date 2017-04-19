package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicMenuBarUI;
import java.awt.*;

/**
 * Created by okori on 07-Apr-17.
 */
public class ChatMenuBar extends JMenuBar {

    public ChatMenuBar(Color bg) {
        add(Box.createHorizontalGlue());
        setBorder(BorderFactory.createCompoundBorder(getBorder(), new EmptyBorder(4, 4, 4, 4)));
        setUI(new BasicMenuBarUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                g.setColor(bg);
                g.fillRect(0, 0, c.getWidth(), c.getHeight());
            }
        });
    }
}
