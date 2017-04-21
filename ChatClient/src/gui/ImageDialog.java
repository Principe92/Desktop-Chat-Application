package gui;

import javax.swing.*;

/**
 * Created by okori on 21-Apr-17.
 */
public class ImageDialog {

    public ImageDialog(String path) {
        JOptionPane.showMessageDialog(null, new JLabel(new ImageIcon(path)), "", JOptionPane.PLAIN_MESSAGE, null);
    }

}
