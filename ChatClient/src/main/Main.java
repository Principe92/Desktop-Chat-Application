package main;

import factory.AbstractFactory;
import model.ChatDb;
import model.ChatManager;
import type.ILogger;
import type.ISocketProtocol;

import gui.JoinDialog;
import gui.LoginPanel;
import main.App;
import main.Client;
import main.Server;

import java.io.IOException;

public class Main {
	
	private static final Main instance = new Main();
	private JoinDialog joinDialog;
	private App app;
	private LoginPanel loginPanel;
	private Client client;
	private Server server;
	

    public static void main(String[] args) throws IOException {   	
        ILogger logger = AbstractFactory.getLogger();
        ISocketProtocol protocol = AbstractFactory.getProtocol();
        
        Main.getInstance().startClient();
        
        
        
        //If logged in then proceed to the app
        new App(logger, protocol, ChatDb.Instance(logger), ChatManager.instance());
    }
    
    private static Main getInstance() {
    	return instance;
    }
    
    private void startClient() {
    	openLogin();
    }
    
    private void openLogin(){
    	//Connect with socket 
    }
}
