package main;

import factory.AbstractFactory;
import model.ChatDb;
import model.ChatManager;
import type.ILogger;
import type.ISocketProtocol;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        ILogger logger = AbstractFactory.getLogger();
        ISocketProtocol protocol = AbstractFactory.getProtocol();

        //  Main.getInstance().startClient();

        //If logged in then proceed to the app
        new App(logger, protocol, ChatDb.Instance(logger), ChatManager.instance());
    }
}
