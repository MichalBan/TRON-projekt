package tst;

import java.awt.Point;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
/**
 * klasa reprezentuje menu zwiazane z wyborem koloru gracza
 */
public class MenuColor {
	private ArrayList<ColorButton> colorButtons = new ArrayList<ColorButton>();
	/**
	 * przycisk powrotu do glownego menu
	 */
	private MenuButton doneButton;
	private Point location;

	public MenuColor(DriverController[] controls, JFrame f, Point location) {
		this.location = location;
		createColorButtonGroup(controls[1], f);
		location.x = location.x * 2 + 100;
		createColorButtonGroup(controls[0], f);
		doneButton = new MenuButton("Gotowe", f, (e) -> {
		});
		doneButton.setVisible(false);
	}
	/**\
	 * metoda tworzy zestaw 3 przyciskow stanowiacych alternatywe rozlaczna koloru gracza
	 * @param dc controller zwiazany z danym graczem
	 * @param f okienko gry
	 */
	private void createColorButtonGroup(DriverController dc, JFrame f) {
		ButtonGroup bg = new ButtonGroup();
		createColorButton(dc, f, bg, "¯ó³ty", Driver.DColor.YELLOW);
		colorButtons.get(colorButtons.size() - 1).setSelected(true);
		location.y += 100;
		createColorButton(dc, f, bg, "Cyjan", Driver.DColor.CYAN);
		location.y += 100;
		createColorButton(dc, f, bg, "Czerwony", Driver.DColor.RED);
		location.y -= 200;
	}

	private void createColorButton(DriverController dc, JFrame f, ButtonGroup bg, String s, Driver.DColor col) {
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