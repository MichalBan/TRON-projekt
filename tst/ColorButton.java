package tst;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JRadioButton;
import javax.swing.border.LineBorder;
/**
 * klasa reprezentuje przycisk do wyboru koloru gracza
 * tworza one alternatywy rozlaczne dla kazdego gracza
 */
public class ColorButton extends JRadioButton {
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