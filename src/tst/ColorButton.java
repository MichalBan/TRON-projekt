package tst;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JRadioButton;
import javax.swing.border.LineBorder;

class ColorButton extends JRadioButton {

	static Point location = new Point(GameLoop.resolution / 3 - 100, GameLoop.resolution / 2 - 150);

	ColorButton(JFrame f, String s, ActionListener a) {
		super(s);
		this.setFont(new Font("Arial", Font.PLAIN, 30));
		this.setBounds(location.x, location.y, 200, 50);
		this.setBorder(new LineBorder(Color.CYAN));
		this.setBorderPainted(true);
		this.setBackground(Color.BLACK);
		this.setForeground(Color.CYAN);
		this.addActionListener(a);
		MenuColor.colorButtons.add(this);
		f.add(this);
		this.setVisible(false);
		autoLocation();
	}

	private void autoLocation() {
		if (location.x == GameLoop.resolution / 3 - 100 && location.y == GameLoop.resolution / 2 + 50) {
			location.y -= 300;
			location.x = GameLoop.resolution * 2 / 3 - 100;
		}
		location.y += 100;
	}
}