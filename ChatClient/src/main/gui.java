package main;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import factory.MessageFactory;
import interfaces.IGuiListener;
import model.ImageFilter;
import type.ILogger;
import type.IMessage;

public class gui {
	private JFrame frmChatapp;
	private JTextArea textField;
	private final IGuiListener listener;
	private final ILogger logger;
	private JPanel display;
	private JScrollPane ext_display;
	private int lastX;
	private int lastY;

	public gui(IGuiListener listener, ILogger logger) {
		this.listener = listener;
		this.logger = logger;
		initialize();
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	
	private void initialize() {
		frmChatapp = setUpChatFrame();
		
		textField = addMessageField();
		
		JMenuBar menuBar = addMenuBar();
		frmChatapp.setJMenuBar(menuBar);
		
		JButton btnSubmit = addSendButton();
		frmChatapp.getContentPane().add(btnSubmit);
		
		ext_display = addMessageWindow();
		frmChatapp.getContentPane().add(ext_display);
		
	//	JLabel usr_lbl = addLabel();
	//	frmChatapp.getContentPane().add(usr_lbl);
		
	//	JLabel chat_lbl = addChatLabel();
	//	frmChatapp.getContentPane().add(chat_lbl);
		
	//	JScrollPane usr_scrpane = addAccountList();
	//	frmChatapp.getContentPane().add(usr_scrpane);
		
		//JButton exitChatBtn = addExitButton();
		//frmChatapp.getContentPane().add(exitChatBtn);
		
		//JButton kickoutBtn = addKickoutButton();
		//frmChatapp.getContentPane().add(kickoutBtn);
		
		frmChatapp.getContentPane().add(textField);
		frmChatapp.pack();
		frmChatapp.setVisible(true);
	}

	private JMenuBar addMenuBar() {
		JMenuBar bar = new JMenuBar();
		bar.add(Box.createHorizontalGlue());
		bar.setBorder(BorderFactory.createCompoundBorder(bar.getBorder(),BorderFactory.createEmptyBorder(4, 4, 4, 4)));
		bar.setSize(593, 200);
		bar.setBackground(Color.LIGHT_GRAY);
		
		// Attachment
		JButton item = new JButton("");
		item.setForeground(Color.blue);
		item.setToolTipText("Attach an image");
		item.setCursor(new Cursor(Cursor.HAND_CURSOR));
		item.setBorderPainted(false);
		item.setContentAreaFilled(false);
		item.setFocusPainted(false);
		item.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		item.setIcon(new ImageIcon("D:/Git/Desktop-Chat-Application/ChatClient/src/icons/ic_attachment_black_24dp.png"));
		bar.add(item);		
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JButton open = new JButton();
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new java.io.File(System.getProperty("user.home")));
				fileChooser.setDialogTitle("Attach an image");
				fileChooser.addChoosableFileFilter(new ImageFilter());
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				if (fileChooser.showOpenDialog(open) == JFileChooser.APPROVE_OPTION){
					textField.setText(fileChooser.getSelectedFile().getAbsolutePath());
				}
			}
		});
		
		// settings
		item = new JButton("");
		item.setForeground(Color.blue);
		item.setToolTipText("Settings");
		item.setCursor(new Cursor(Cursor.HAND_CURSOR));
		item.setBorderPainted(false);
		item.setContentAreaFilled(false);
		item.setFocusPainted(false);
		item.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		item.setIcon(new ImageIcon("D:/Git/Desktop-Chat-Application/ChatClient/src/icons/ic_settings_black_24dp.png"));
		bar.add(item);
		
		// exit
		item = new JButton("");
		item.setForeground(Color.blue);
		item.setToolTipText("Exit");
		item.setCursor(new Cursor(Cursor.HAND_CURSOR));
		item.setBorderPainted(false);
		item.setContentAreaFilled(false);
		item.setFocusPainted(false);
		item.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		item.setIcon(new ImageIcon("D:/Git/Desktop-Chat-Application/ChatClient/src/icons/ic_power_settings_new_black_24dp.png"));
		bar.add(item);
		
		return bar;
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
				//TODO: Exit chat room
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
		JButton item = new JButton("");
		item.setForeground(Color.blue);
		item.setToolTipText("Send");
		item.setCursor(new Cursor(Cursor.HAND_CURSOR));
		item.setBorderPainted(false);
		item.setContentAreaFilled(false);
		item.setFocusPainted(false);
		item.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		item.setIcon(new ImageIcon("D:/Git/Desktop-Chat-Application/ChatClient/src/icons/ic_send_black_24dp.png"));
		item.setBounds(380, 240, 97, 23);
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//send
				textField.requestFocus();
				sendText();
				return;
			}
		});
		return item;
	}

	private JScrollPane addMessageWindow() {
		display = new JPanel();
		display.setOpaque(true);
		//display.setEditable(false);
		//display.setBounds(33, 10, 600, 600);
		display.setLayout(new BoxLayout(display, BoxLayout.Y_AXIS));
		display.setBackground(new Color(130, 119, 23));
		
		JScrollPane ext_display = new JScrollPane(display);
		ext_display.setBounds(33,10, 600,600);
		
		
		return ext_display;
	}

	private JTextArea addMessageField() {
		JTextArea textField = new JTextArea();
		textField.setBounds(33, 229, 375, 40);
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
			public void keyReleased(KeyEvent e) {}

			@Override
			public void keyTyped(KeyEvent e) {}
		});
		
		return textField;
	}

	private JFrame setUpChatFrame() {
		JFrame frmChatapp = new JFrame();
		frmChatapp.setTitle("Chatapp");
	//	frmChatapp.setBounds(100, 100, 593, 299);
		frmChatapp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmChatapp.getContentPane().setLayout(new GridLayout(2, 1)); //new BoxLayout(frmChatapp.getContentPane(), BoxLayout.PAGE_AXIS));
		
		Dimension min = new Dimension(800,600);
		frmChatapp.setMinimumSize(min);

	    Toolkit toolkit = Toolkit.getDefaultToolkit();
	    Dimension max = toolkit.getScreenSize();
	    frmChatapp.setMaximumSize(max);
		
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
		
		if(text.isEmpty() || text.trim().isEmpty()) return;

		IMessage message = MessageFactory.getMessage(text.trim());
		
		if (message != null){
			try {
				listener.sendText(message);
			} catch (IOException e) {
				logger.logError(e);
			}
			
			displayMessage(message, Component.RIGHT_ALIGNMENT);
		}
		
		clearText();
	}


	protected void clearText() {
		textField.setText(null);
	}

	public void displayMessage(IMessage msg, float alignment) {
		JPanel pane = msg.getMessagePanel(lastX, lastY);
		pane.setBackground(Color.white);
		pane.setAlignmentX(alignment);
		display.add(pane, BoxLayout.X_AXIS);
		lastY += (pane.getHeight() + 10);
	}
	
	public void close() {
		frmChatapp.dispatchEvent(new WindowEvent(frmChatapp, WindowEvent.WINDOW_CLOSING));
	}
}
