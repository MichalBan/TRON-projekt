package tst;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;
import javax.swing.border.LineBorder;

/**
 * Klasa reprezentuje pojedynczy przycisk w glownym menu i przycisk wyjsca z
 * menu koloru. Zawiera takze metody przyciskow z menu glownego.
 *
 */
public class MenuButton extends JButton {

	private static int location = -250;

	/**
	 * Kazdy kolejny przycisk sam sie pozycjonuje na srodku ekranu pod poprzednimi
	 * 
	 * @param s napis na przycisku
	 * @param f okno do ktorego przycisk jest podpiety
	 * @param a ActionListener zwiazany z nacisnieciem przycisku
	 */
	public MenuButton(String s, JFrame f, ActionListener a) {
		super(s);
		this.setFont(new Font("Arial", Font.PLAIN, 30));
		this.setBounds(GameWindow.resolution / 2 - 100, GameWindow.resolution / 2 + MenuButton.location, 200, 50);
		this.setBorder(new LineBorder(Color.CYAN));
		this.setBackground(Color.BLACK);
		this.setForeground(Color.CYAN);
		f.add(this);
		GameTicker.buttons.add(this);
		this.addActionListener(a);
		MenuButton.location += 100;
	}

	/**
	 * Metoda rozpoczyna krecenie sie petli gry. Podlacza keyListenery nieaktywne w
	 * menu. Ukrywa menu.
	 * 
	 */
	static void startGame(GameTicker ticker) {
		GameWindow.panel.addKeyListener(ticker.controllers[0]);
		GameWindow.panel.addKeyListener(ticker.controllers[1]);
		for (JButton p : GameTicker.buttons) {
			p.setVisible(false);
		}
		ticker.restart();
		if (GameTicker.bonusesActive)
			ticker.tickerBonus.restart();
		GameWindow.panel.requestFocusInWindow();
	}
	/**
	 * metoda rotacyjnie zmienia tlo gry
	 */
	static void switchBg() {
		if (GameWindow.background == "bg2.png") {
			GameWindow.background = "bg3.jpg";
		} else if (GameWindow.background == "bg.jpg") {
			GameWindow.background = "bg2.png";
		} else if (GameWindow.background == "bg3.jpg") {
			GameWindow.background = "bg.jpg";
		}
		GameWindow.createBackground();
	}
	/**
	 * metoda zalacza lub wylacza bonusy
	 */
	public void switchBonus() {
		if (GameTicker.bonusesActive) {
			this.setText("Za³¹cz bonus");
			GameTicker.bonusesActive = false;
		} else {
			this.setText("Wy³¹cz bonus");
			GameTicker.bonusesActive = true;
		}
	}

	public static void MoveUpLocation() {
		MenuButton.location -= 100;
	}
}