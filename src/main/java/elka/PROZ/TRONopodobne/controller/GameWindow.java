package elka.PROZ.TRONopodobne.controller;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import elka.PROZ.TRONopodobne.view.MyPanel;

/**
 *Contains main method. Creates window, menu, and game ticker
 */
public class GameWindow extends JFrame {

	private MyPanel panel;
	private GameTicker ticker;
	/**
	 * main method creates game window in event dispatching thread
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new GameWindow());
	}

	public GameWindow() {
		this.setVisible(true);
		panel = new MyPanel();
		ticker = new GameTicker(panel);
		this.setTitle("TRONopodobne");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		new MainMenuController(this);
		this.add(panel);
		this.pack();
	}

	public GameTicker getTicker() {
		return ticker;
	}

	public MyPanel getPanel() {
		return panel;
	}

}