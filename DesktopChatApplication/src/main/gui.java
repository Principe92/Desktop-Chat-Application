package main;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JInternalFrame;

public class gui {

	private JFrame frame;
	private JTextArea messageView;

	private IGuiListener listener;
	private JTextArea display;


	
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
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnSubmit = new JButton("Send");
		btnSubmit.setBounds(311, 228, 97, 23);
		frame.getContentPane().add(btnSubmit);
		
		messageView = new JTextArea();
		messageView.addKeyListener(new KeyListener(){

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
		
		
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//send
				messageView.requestFocus();
				sendText();
				return;
			}
		});
		
		
		messageView.setBounds(33, 229, 254, 21);
		messageView.setColumns(10);
		messageView.setLineWrap(true);
		messageView.setWrapStyleWord(true);
		
		display = new JTextArea();
		display.setEditable(false);
		display.setBounds(33, 10, 375, 208);
		JScrollPane pane = new JScrollPane(display);
		pane.setBounds(33, 229, 254, 21);
		
		
		frame.getContentPane().add(messageView);
		frame.getContentPane().add(pane);
		frame.setVisible(true);
		
		// make the message box get focus
		frame.addWindowListener( new WindowAdapter() {
		    public void windowOpened( WindowEvent e ){
		        messageView.requestFocus();
		    }
		}); 
	}
	
	private void sendText(){
		String text = messageView.getText();
		if(text.isEmpty()) return;

		
		listener.sendText(text);
		displayMessage(text);
		clearText();
	}

	protected void clearText() {
		// TODO Auto-generated method stub
		messageView.setText(null);
	}

	public void displayMessage(String message) {
		// TODO Auto-generated method stub
		display.append(String.format("%s\n", message));
	}
}
