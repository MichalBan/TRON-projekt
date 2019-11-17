package elka.PROZ.TRONopodobne.controller;

import java.awt.Point;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;

import elka.PROZ.TRONopodobne.model.DriverColor;
import elka.PROZ.TRONopodobne.view.ColorButton;
import elka.PROZ.TRONopodobne.view.MenuButton;

/**
 * Creates and contains menu for changing driver color
 */
public class ColorMenuController {
	private ArrayList<ColorButton> colorButtons = new ArrayList<ColorButton>();
	/**
	 * return to main menu button
	 */
	private MenuButton doneButton;
	private Point location;

	/**
	 * color buttons are placed in two grous for two players
	 */
	public ColorMenuController(GameWindow gw) {
		this.location = new Point(gw.getPanel().getResolution() / 3 - 100, gw.getPanel().getResolution() / 2 - 150);
		createColorButtonGroup(gw.getTicker().getControllers()[1], gw);
		location.x = location.x * 2 + 100;
		createColorButtonGroup(gw.getTicker().getControllers()[0], gw);
		doneButton = new MenuButton("Gotowe", gw, (e) -> {
		});
		doneButton.setVisible(false);
	}

	/**
	 *  creates set of 3 buttons for a player
	 * 
	 * @param dc controller of the players driver
	 * @param f  game window
	 */
	private void createColorButtonGroup(DriverController dc, GameWindow gw) {
		ButtonGroup bg = new ButtonGroup();
		createColorButton(dc, gw, bg, "Żółty", DriverColor.YELLOW);
		colorButtons.get(colorButtons.size() - 1).setSelected(true);
		location.y += 100;
		createColorButton(dc, gw, bg, "Cyjan", DriverColor.CYAN);
		location.y += 100;
		createColorButton(dc, gw, bg, "Czerwony", DriverColor.RED);
		location.y -= 200;
	}
	/**
	 * Creates a button with all properties
	 * @param dc controller of the driver whoose color changes
	 * @param f game window
	 * @param bg group connected with driver
	 * @param s text on button
	 * @param col color the button changes the driver to
	 */
	private void createColorButton(DriverController dc, JFrame f, ButtonGroup bg, String s, DriverColor col) {
		ColorButton c = new ColorButton(location, f, s, (e) -> dc.getDriver().setDriverColor(col));
		bg.add(c);
		colorButtons.add(c);
	}

	public ArrayList<ColorButton> getColorButtons() {
		return colorButtons;
	}

	public MenuButton getDoneButton() {
		return doneButton;
	}
}