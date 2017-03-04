package main;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class gui {
	private JFrame frmChatapp;
	private JTextArea textField;

	private IGuiListener listener;
	private JTextArea display;
	
	private JScrollPane ext_display;


	
	/*
	 * Things to add:
	 * inviteButton
	 * exitBUtton
	 * whisper menu
	 * kicoff menu
	 * */
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
				//	gui window = new gui();
				//	window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @wbp.parser.entryPoint
	 */
	public gui(IGuiListener listener) {
		this.listener = listener;
		initialize();
		//client = Client.getInstance();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	
	private void initialize() {
		frmChatapp = setUpChatFrame();
		
		textField = addMessageField();
		frmChatapp.getContentPane().add(textField);
		
		JButton btnSubmit = addSendButton();
		frmChatapp.getContentPane().add(btnSubmit);
		
		ext_display = addMessageWindow();
		frmChatapp.getContentPane().add(ext_display);
		
		JLabel usr_lbl = addLabel();
		frmChatapp.getContentPane().add(usr_lbl);
		
		JLabel chat_lbl = addChatLabel();
		frmChatapp.getContentPane().add(chat_lbl);
		
		JScrollPane usr_scrpane = addAccountList();
		frmChatapp.getContentPane().add(usr_scrpane);
		
		JButton exitChatBtn = addExitButton();
		frmChatapp.getContentPane().add(exitChatBtn);
		
		JButton kickoutBtn = addKickoutButton();
		frmChatapp.getContentPane().add(kickoutBtn);
		
		frmChatapp.setVisible(true);
	}

	private JButton addKickoutButton() {
		JButton kickoutBtn = new JButton("Ban");
		kickoutBtn.setBounds(498, 229, 55, 23);
		return kickoutBtn;
	}

	private JButton addExitButton() {
		JButton exitChatBtn = new JButton("Exit");
		exitChatBtn.setBounds(420, 228, 62, 23);
		exitChatBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//exit the chatroom -- leave for now
			}
		});
		return exitChatBtn;
	}

	private JScrollPane addAccountList() {
		JScrollPane usr_scrpane = new JScrollPane();
		usr_scrpane.setBounds(420, 25, 120, 190);
		return usr_scrpane;
	}

	private JLabel addChatLabel() {
		JLabel chat_lbl = new JLabel("Chat");
		chat_lbl.setBounds(35, 10, 373, 13);
		return chat_lbl;
	}

	private JLabel addLabel() {
		JLabel usr_lbl = new JLabel("User Online");
		usr_lbl.setBounds(420, 6, 112, 21);
		return usr_lbl;
	}

	private JButton addSendButton() {
		JButton btnSubmit = new JButton("Send");
		btnSubmit.setBounds(311, 228, 97, 23);
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//send
				textField.requestFocus();
				sendText();
				return;
			}
		});
		return btnSubmit;
	}

	private JScrollPane addMessageWindow() {
		display = new JTextArea();
		display.setEditable(false);
		display.setBounds(33, 10, 375, 208);
		
		JScrollPane ext_display = new JScrollPane(display);
		ext_display.setBounds(33,25,375,190);
		
		return ext_display;
	}

	private JTextArea addMessageField() {
		JTextArea textField = new JTextArea();
		textField.setBounds(33, 229, 254, 21);
		textField.setColumns(10);
		textField.setLineWrap(true);
		textField.setWrapStyleWord(true);
		textField.addKeyListener(new KeyListener(){

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
				if (e.getKeyCode() == KeyEvent.VK_ENTER){
					//send
					sendText();
					return;
				}
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub	
			}

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
			}
		});
		
		return textField;
	}

	private JFrame setUpChatFrame() {
		JFrame frmChatapp = new JFrame();
		frmChatapp.setTitle("Chatapp");
		frmChatapp.setBounds(100, 100, 593, 299);
		frmChatapp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmChatapp.getContentPane().setLayout(null);
		JTextArea usr_account_list = new JTextArea();
		frmChatapp.getContentPane().add(usr_account_list);
		usr_account_list.setBounds(420, 26, 133, 188);
		
		// make the message box get focus
		frmChatapp.addWindowListener( new WindowAdapter() {
				  public void windowOpened( WindowEvent e ){
				        textField.requestFocus();
				    }
				}); 
		
		return frmChatapp;
	}
	
	private void sendText(){
		String text = textField.getText();
		if(text.isEmpty()) return;

		listener.sendText(text);
		displayMessage(text);
		clearText();
	}

	protected void clearText() {
		// TODO Auto-generated method stub
		textField.setText(null);
	}

	public void displayMessage(String message) {
		// TODO Auto-generated method stub
		display.append(String.format("%s\n", message));
	}
}
