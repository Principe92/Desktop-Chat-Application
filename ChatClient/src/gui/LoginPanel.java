package gui;

import main.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPanel extends JPanel implements ActionListener {

    private JTextField idField;
    private JPasswordField pwdField;
    private JButton btnLogin;
    private JButton btnClose;
    private Main main;

    public LoginPanel() {
        init();
        start();
        //	main = Main.getInstance();
    }

    private void init() {
        setLayout(null);

        JLabel lblId = new JLabel("ID");
        lblId.setBounds(12, 221, 40, 15);
        add(lblId);

        JLabel lblPwd = new JLabel("Passcode");
        lblPwd.setBounds(12, 246, 40, 15);
        add(lblPwd);

        idField = new JTextField();
        idField.setBounds(64, 218, 116, 21);
        add(idField);
        idField.setColumns(10);

        pwdField = new JPasswordField();
        pwdField.setBounds(64, 243, 116, 21);
        add(pwdField);
        pwdField.setColumns(10);

        btnLogin = new JButton("Login");
        btnLogin.setBounds(12, 274, 80, 37);
        add(btnLogin);

        btnClose = new JButton("Cancel");
        btnClose.setBounds(100, 274, 80, 37);
        add(btnClose);

        JPanel panel = new ImagePanel();
        panel.setBounds(0, 0, 195, 211);
        add(panel);
        //visibility issue
        panel.setVisible(true);

    }

    private void start() {
        pwdField.addActionListener(this);
        btnLogin.addActionListener(this);
        btnClose.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src == pwdField || src == btnLogin) {
            if (idField.getText().length() == 0) {
                JOptionPane.showMessageDialog(null, "Enter the ID");
            } else if (pwdField.getPassword().length == 0) {
                JOptionPane.showMessageDialog(null, "Enter the password");
            } else {
                //  main.requestLogin(idField.getText() + "," + String.valueOf(pwdField.getPassword()));
            }
        } else if (src == btnClose) {
            System.exit(0);
        }
    }

    private class ImagePanel extends JPanel {
        private Image img;

        private ImagePanel() {
            img = Toolkit.getDefaultToolkit().getImage("./login_in-512.png");
            MediaTracker tracker = new MediaTracker(this);
            try {
                tracker.addImage(img, 0);
                tracker.waitForAll();
                if (tracker.isErrorAny()) {
                    JOptionPane.showMessageDialog(null, "Image not ready yet");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Image not ready yet");
            }
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(img, 0, 0, getSize().width, getSize().height, this);
            setOpaque(false);
        }
    }
}
