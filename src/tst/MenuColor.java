package tst;

import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JFrame;

public class MenuColor {
	static ArrayList<ColorButton> colorButtons = new ArrayList<ColorButton>();
	static MenuButton doneButton;

	MenuColor(DriverControl[] controls, JFrame f) {
		createColorButtonGroup(controls[1], f);
		createColorButtonGroup(controls[0], f);
		doneButton = new MenuButton("Gotowe", f, (e) -> switchMenu());
		GameLoop.buttons.remove(GameLoop.buttons.size() - 1);
		doneButton.setVisible(false);
	}

	private void createColorButtonGroup(DriverControl dc, JFrame f) {
		ButtonGroup bg = new ButtonGroup();
		bg.add(new ColorButton(f, "¯ó³ty", (e) -> dc.changeColor(Driver.DColor.YELLOW)));
		colorButtons.get(colorButtons.size() - 1).setSelected(true);
		bg.add(new ColorButton(f, "Cyjan", (e) -> dc.changeColor(Driver.DColor.CYAN)));
		bg.add(new ColorButton(f, "Czerwony", (e) -> dc.changeColor(Driver.DColor.RED)));
	}

	private void switchMenu() {
		for (MenuButton p : GameLoop.buttons) {
			p.setVisible(true);
		}
		for (ColorButton p : MenuColor.colorButtons) {
			p.setVisible(false);
		}
		MenuColor.doneButton.setVisible(false);
	}
}