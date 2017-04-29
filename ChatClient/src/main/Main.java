package main;

import factory.AbstractFactory;
import gui.JoinDialog;
import gui.LoginPanel;
import model.ChatDb;
import model.ChatManager;
import type.ILogger;
import type.ISocketProtocol;

import javax.swing.*;
import java.io.IOException;

public class Main {

    private static final Main instance = new Main();
    private JoinDialog joinDialog;
    private App app;
    private LoginPanel loginPanel;
    private Client client;
    private Server server;


    private Main() {
        ILogger logger = AbstractFactory.getLogger();
        ISocketProtocol protocol = AbstractFactory.getProtocol();

        app = new App(logger, protocol, ChatDb.Instance(logger), ChatManager.instance());
    }

    public static void main(String[] args) throws IOException {
        ILogger logger = AbstractFactory.getLogger();
        ISocketProtocol protocol = AbstractFactory.getProtocol();

        //  Main.getInstance().startClient();

        //If logged in then proceed to the app
        new App(logger, protocol, ChatDb.Instance(logger), ChatManager.instance());
    }

    public static Main getInstance() {
        return instance;
    }


    private void startClient() {
        openLogin();
    }

    private void openLogin() {
        //Connect with socket
        try {
            loginPanel = new LoginPanel();
            //loginPanel.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "cannot connect to server");
        }
    }

    public void requestLogin(String string) {
        // TODO Auto-generated method stub

    }
}
