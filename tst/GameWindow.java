package tst;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.SwingUtilities;

/**
 * Zawiera funkcje main. Klasa reprezentujaca okienko gry. Odpowiada ona za
 * tworzenie Menu i rysowanie po ekranie
 */
public class GameWindow extends JFrame {
	final static int resolution = 1000;
	/**
	 * rozne elementy gry nakladaja sie na siebie, wiec sa rysowane w innych
	 * warstwach JLayeredPana
	 */
	static JLayeredPane panel = new JLayeredPane();
	GameTicker ticker = new GameTicker(resolution);
	static String background = "Images\\bg2.png";

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new GameWindow());
	}

	public GameWindow() {
		this.setTitle("TRONopodobne");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		createMenu();
		GameWindow.createBackground();
		GameWindow.panel.setPreferredSize(new Dimension(resolution, resolution));
		this.add(GameWindow.panel);
		this.pack();
	}

	/**
	 * Metoda tworzy wszystkie przyciski pojawiajace sie w menu zwyklym i zwiazanym
	 * z kolorem. Przyciskom od rzu przypisywane sa odpowiednie funkcjonalnosci.
	 */
	private void createMenu() {
		MenuColor m = new MenuColor(ticker.controllers, this,
				new Point(GameWindow.resolution / 3 - 100, GameWindow.resolution / 2 - 150));
		m.getDoneButton().addActionListener((e) -> switchMenu(m));
		GameTicker.buttons.remove(GameTicker.buttons.size() - 1);
		MenuButton.MoveUpLocation();
		new MenuButton("Start", this, (e) -> MenuButton.startGame(ticker));
		MenuButton p = new MenuButton("Wy³¹cz bonus", this, (e) -> {
		});
		p.addActionListener((e) -> p.switchBonus());
		new MenuButton("Kolory graczy", this, (e) -> switchMenu(m));
		new MenuButton("Zmieñ t³o", this, (e) -> MenuButton.switchBg());
		new MenuButton("Wyjœcie", this, (e) -> System.exit(0));
	}

	static void createBackground() {
		GameWindow.panel.removeAll();
		JLabel lBg = new JLabel(new ImageIcon(background));
		lBg.setBounds(0, 0, resolution, resolution);
		GameWindow.panel.add(lBg, Integer.valueOf(0));
	}

	/**
	 * metoda rysuje obrazek odpowiadajacy jednej kratce planszy
	 * 
	 * @param p  polozenie obiektu na planszy
	 * @param ic obrazek do narysowania
	 * @param i  warstwa JLayeredPana
	 */
	public static void draw(Point p, ImageIcon ic, Integer i) {
		JLabel lImage = new JLabel(ic);
		lImage.setBounds(10 * p.x, 10 * p.y, 10, 10);
		GameWindow.panel.add(lImage, i);
	}

	private void switchMenu(MenuColor m) {
		for (MenuButton p : GameTicker.buttons) {
			p.setVisible(p.isVisible() ? false : true);
		}
		for (ColorButton p : m.getColorButtons()) {
			p.setVisible(p.isVisible() ? false : true);
		}
		m.getDoneButton().setVisible(m.getDoneButton().isVisible() ? false : true);
	}

}