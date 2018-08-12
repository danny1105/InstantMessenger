package chatMeClient;

import javax.swing.JFrame;

public class ClientTest {
	public static void main(String[] args) {
		Client maddy;
		maddy = new Client("127.0.0.1");
		maddy.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		maddy.startRunning();
	}
}
