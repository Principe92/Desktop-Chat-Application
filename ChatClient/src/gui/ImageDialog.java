package gui;

import javax.swing.*;


public class ImageDialog {

    public ImageDialog(String path) {
        JOptionPane.showMessageDialog(null, new JLabel(new ImageIcon(path)), "", JOptionPane.PLAIN_MESSAGE, null);
    }

}
