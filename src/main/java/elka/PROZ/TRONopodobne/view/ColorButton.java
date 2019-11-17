package elka.PROZ.TRONopodobne.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JRadioButton;
import javax.swing.border.LineBorder;

/**
 * Single radio button which appears in driver color choice menu
 */
public class ColorButton extends JRadioButton {
	/**
	 * 
	 * @param location Location of button in frame
	 * @param f frame the button is added to
	 * @param s text on the button
	 * @param a action listener which handles clicks of the button
	 */
	public ColorButton(Point location, JFrame f, String s, ActionListener a) {
		super(s);
		this.setFont(new Font("Arial", Font.PLAIN, 30));
		this.setBounds(location.x, location.y, 200, 50);
		this.setBorder(new LineBorder(Color.CYAN));
		this.setBorderPainted(true);
		this.setBackground(Color.BLACK);
		this.setForeground(Color.CYAN);
		this.addActionListener(a);
		f.add(this);
		this.setVisible(false);
	}
}