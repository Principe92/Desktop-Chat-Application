package main;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JInternalFrame;

public class gui {

	private JFrame frame;
	private JTextField textField;

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
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnSubmit = new JButton("Send");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//send
				String text = textField.getText();
				if(text.isEmpty()) return;

				
				listener.sendText(text);
				displayMessage(text);
				clearText();
				
			}
		});
		
		btnSubmit.setBounds(311, 228, 97, 23);
		frame.getContentPane().add(btnSubmit);
		
		textField = new JTextField();
		textField.setBounds(33, 229, 254, 21);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		display = new JTextArea();
		
		display.setBounds(33, 10, 375, 208);
		
		ext_display = new JScrollPane(display);
		ext_display.setBounds(33,10,375,208);
		
		frame.getContentPane().add(ext_display);
		//frame.getContentPane().add(display);
		
		frame.setVisible(true);
	}

	protected void clearText() {
		// TODO Auto-generated method stub
		textField.setText("");
	}

	public void displayMessage(String message) {
		// TODO Auto-generated method stub
		display.append(String.format("%s\n", message));
	}
}
