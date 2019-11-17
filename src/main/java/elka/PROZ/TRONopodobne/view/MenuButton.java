package elka.PROZ.TRONopodobne.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.border.LineBorder;

import elka.PROZ.TRONopodobne.controller.GameWindow;

/**
 * Single button from main menu
 */
public class MenuButton extends JButton {
	/**
	 * represents location of button along y-axis. X-axis location is always the middle of frame length.
	 */
	private static int location = -250;

	/**
	 * Every new button positions itself below the previous one
	 * 
	 * @param s text on button
	 * @param f frame for the button to be added
	 * @param a action listener which handles clicks of the button
	 */
	public MenuButton(String s, GameWindow f, ActionListener a) {
		super(s);
		this.setFont(new Font("Arial", Font.PLAIN, 30));
		this.setBounds(f.getPanel().getResolution() / 2 - 100, f.getPanel().getResolution() / 2 + MenuButton.location,
				200, 50);
		this.setBorder(new LineBorder(Color.CYAN));
		this.setBackground(Color.BLACK);
		this.setForeground(Color.CYAN);
		f.add(this);
		f.getTicker().getButtons().add(this);
		this.addActionListener(a);
		MenuButton.location += 100;
	}
	/**
	 * changes location of next created button to the previous one's
	 */
	public static void MoveUpLocation() {
		MenuButton.location -= 100;
	}
}