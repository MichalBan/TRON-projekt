package tst;

import java.awt.Point;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;

public class MenuColor {
	ArrayList<ColorButton> colorButtons = new ArrayList<ColorButton>();
	MenuButton doneButton;
	Point location;

	public MenuColor(DriverController[] controls, JFrame f, Point location, ArrayList<MenuButton> buttons) {
		this.location = location;
		createColorButtonGroup(controls[1], f);
		location.x = location.x * 2 + 100;
		createColorButtonGroup(controls[0], f);
		doneButton = new MenuButton("Gotowe", f, (e) -> {
		});
		doneButton.setVisible(false);
	}

	private void createColorButtonGroup(DriverController dc, JFrame f) {
		ButtonGroup bg = new ButtonGroup();
		createColorButton(dc, f, bg, "¯ó³ty", Driver.DColor.YELLOW);
		colorButtons.get(colorButtons.size()-1).setSelected(true);
		location.y += 100;
		createColorButton(dc, f, bg, "Cyjan", Driver.DColor.CYAN);
		location.y += 100;
		createColorButton(dc, f, bg, "Czerwony", Driver.DColor.RED);
		location.y -= 200;
	}

	private void createColorButton(DriverController dc, JFrame f, ButtonGroup bg, String s, Driver.DColor col) {
		ColorButton c = new ColorButton(location, f, s, (e) -> dc.changeColor(col));
		bg.add(c);
		colorButtons.add(c);
	}

}