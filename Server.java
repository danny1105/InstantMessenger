package chatMe;

import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Server extends JFrame{
	
	private JTextField userText;
	private JTextArea chatWindow;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private ServerSocket server;
	private Socket connection;
	
	//constructor
	public Server() {
		super("Dannys Instant Messenger");
		userText = new JTextField();
		userText.setEditable(false);
		userText.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					sendMessage(event.getActionCommand());
					userText.setText("");
				}
			}
		);
		add(userText, BorderLayout.NORTH);
		chatWindow = new JTextArea();
		add(new JScrollPane(chatWindow));
		setSize(300,150);
		setVisible(true);		
	}

	//set up and run the server
	public void startRunning() {
		try {
			server = new ServerSocket(6789, 100);
			while(true) {
				try {
					waitforConnection();
					setupStream();
					whileChatting();
				}catch(EOFException eofException) {
					showMessage("\n Server ended the connection! ");
				}finally {
					closeCrap();
					}
			}
		}catch(IOException ioException) {
			ioException.printStackTrace();
			}
	}
	
	//wait for connection, then displays connection information
	private void waitforConnection() throws IOException{
		showMessage("waiting for someone to connect...!!! \n");
		//setting up the socket
		connection = server.accept();
		showMessage(" Now connected to " +connection.getInetAddress().getHostName());
	}
	
	//get stream to send and recieve data
	private void setupStream() throws IOException{
		output = new ObjectOutputStream(connection.getOutputStream());
		output.flush();    //to clear leftover data
		input = new ObjectInputStream(connection.getInputStream());
		showMessage("\n Stream are now setup! \n");
	}
	
	//during the chat conversation
	private void whileChatting() throws IOException{
		String message = "You are now connected! ";
		sendMessage(message);
		ableToType(true);
		do {
			try {
				message = (String) input.readObject();
				showMessage("\n "+ message);
			}catch(ClassNotFoundException classNotFoundException){
				showMessage("\n Dunno what user typed ");
			}
		}while(!message.equals("CLIENT - END"));
	}
	
	//close streams and socket after done chatting
	private void closeCrap(){
		showMessage("\n Closing connection.... \n");
		ableToType(false);
		try {
			output.close();
			input.close();
			connection.close();
		}catch(IOException ioException){
			ioException.printStackTrace();
		}
	}
	
	//send a message to client
	private void sendMessage(String message) {
		try {
			output.writeObject("Danny - " + message);
			output.flush();
			showMessage("\n Danny - " + message);
		}catch(IOException ioException) {
			chatWindow.append("\n Error: Cannot send the message");
		}
	}
	
	//updates ChatWindow
	private void showMessage(final String text){
        //updates the part of GUI with new message
		SwingUtilities.invokeLater(
			new Runnable(){
				public void run(){
					chatWindow.append(text);
				}
			}
		);
	}
	
	//let the user type stuff into the chat
	//tof - true or false
	private void ableToType(final boolean tof){
		SwingUtilities.invokeLater(
			new Runnable(){
				public void run(){
					userText.setEditable(tof);
				}
			}
		);
	}
}









