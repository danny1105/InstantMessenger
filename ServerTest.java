package chatMe;

import javax.swing.JFrame;

public class ServerTest {
		public static void main(String[] args) {
			Server danny = new Server();
			danny.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			danny.startRunning();
		}
}
