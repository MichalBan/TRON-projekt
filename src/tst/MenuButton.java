package tst;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;
import javax.swing.border.LineBorder;

public class MenuButton extends JButton {

	static int location = -250;

	MenuButton(String s, JFrame f, ActionListener a) {
		super(s);
		this.setFont(new Font("Arial", Font.PLAIN, 30));
		this.setBounds(GameLoop.resolution / 2 - 100, GameLoop.resolution / 2 + MenuButton.location, 200, 50);
		this.setBorder(new LineBorder(Color.CYAN));
		this.setBackground(Color.BLACK);
		this.setForeground(Color.CYAN);
		f.add(this);
		GameLoop.buttons.add(this);
		this.addActionListener(a);
		MenuButton.location += 100;
	}

	/**
	 * <b>lol</b>
	 * bbb
	 * @param ticker
	 * @param tickerBonus
	 * @param drivers
	 */
	static void startGame(Timer ticker, Timer tickerBonus, DriverControl[] drivers) {
		GameLoop.panel.addKeyListener(drivers[0]);
		GameLoop.panel.addKeyListener(drivers[1]);
		for (JButton p : GameLoop.buttons) {
			p.setVisible(false);
		}
		ticker.restart();
		if (GameLoop.bonusesActive)
			tickerBonus.restart();
		GameLoop.panel.requestFocusInWindow();
	}

	static void switchMenuColor() {
		for (MenuButton p : GameLoop.buttons) {
			p.setVisible(false);
		}
		for (ColorButton p : MenuColor.colorButtons) {
			p.setVisible(true);
		}
		MenuColor.doneButton.setVisible(true);
	}

	static void switchBg() {
		if (GameLoop.background == "bg2.png") {
			GameLoop.background = "bg3.jpg";
		} else if (GameLoop.background == "bg.jpg") {
			GameLoop.background = "bg2.png";
		} else if (GameLoop.background == "bg3.jpg") {
			GameLoop.background = "bg.jpg";
		}
		GameLoop.createBackground();
	}

	void switchBonus() {
		if (GameLoop.bonusesActive) {
			this.setText("Za��cz bonus");
			GameLoop.bonusesActive = false;
		} else {
			this.setText("Wy��cz bonus");
			GameLoop.bonusesActive = true;
		}
	}
}