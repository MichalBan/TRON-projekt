package elka.PROZ.TRONopodobne.controller;

import javax.swing.JButton;

import elka.PROZ.TRONopodobne.view.ColorButton;
import elka.PROZ.TRONopodobne.view.MenuButton;

/**
 * Creates main menu, gives buttons their actions
 *
 */
public class MainMenuController {
	/**
	 * constructor also calls creation of color menu
	 */
	public MainMenuController(GameWindow gw) {
		ColorMenuController m = new ColorMenuController(gw);
		gw.getTicker().getButtons().remove(gw.getTicker().getButtons().size() - 1);
		m.getDoneButton().addActionListener((e) -> switchMenu(m, gw.getTicker()));
		MenuButton.MoveUpLocation();
		new MenuButton("Start", gw, (e) -> startGame(gw));
		MenuButton p = new MenuButton("Wyłącz bonus", gw, (e) -> {
		});
		p.addActionListener((e) -> switchBonus(gw.getTicker(), p));
		new MenuButton("Kolory graczy", gw, (e) -> switchMenu(m, gw.getTicker()));
		new MenuButton("Zmień tło", gw, (e) -> gw.getPanel().switchBg());
		new MenuButton("Wyjście", gw, (e) -> System.exit(0));
	}

	/**
	 * Starts game game ticker. If bonuses are active bonus ticker is also started.
	 * Hides menu. Driver controllers start listening to key clicks.
	 * 
	 */
	private void startGame(GameWindow gw) {
		gw.getPanel().addKeyListener(gw.getTicker().getControllers()[0]);
		gw.getPanel().addKeyListener(gw.getTicker().getControllers()[1]);
		for (JButton p : gw.getTicker().getButtons()) {
			p.setVisible(false);
		}
		gw.getTicker().restart();
		if (gw.getTicker().isBonusesActive())
			gw.getTicker().getTickerBonus().restart();
		gw.getPanel().requestFocusInWindow();
	}

	/**
	 * Enables or disables bonuses
	 */
	private void switchBonus(GameTicker ticker, MenuButton mb) {
		if (ticker.isBonusesActive()) {
			mb.setText("Załącz bonus");
			ticker.setBonusesActive(false);
		} else {
			mb.setText("Wyłącz bonus");
			ticker.setBonusesActive(true);
		}
	}
	
	/**
	 * hides main menu and displays color menu or vice versa.
	 * @param m Color menu to be hidden or displayed
	 */
	private void switchMenu(ColorMenuController m, GameTicker ticker) {
		for (MenuButton p : ticker.getButtons()) {
			p.setVisible(p.isVisible() ? false : true);
		}
		for (ColorButton p : m.getColorButtons()) {
			p.setVisible(p.isVisible() ? false : true);
		}
		m.getDoneButton().setVisible(m.getDoneButton().isVisible() ? false : true);
	}
}
