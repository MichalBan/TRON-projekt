package elka.PROZ.TRONopodobne.view;

import java.awt.Dimension;
import java.awt.Point;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

/**
 * Draws all images inside frame on different layers
 */
public class MyPanel extends JLayeredPane {

	private final int resolution;
	private int bgIndex;
	private ImageIcon[] background;

	public MyPanel() {
		super();
		String fs = File.separator;
		background = new ImageIcon[] { new ImageIcon("Images" + fs + "bg2.png"),
				new ImageIcon("Images" + fs + "bg3.jpg"), new ImageIcon("Images" + fs + "bg.jpg") };
		bgIndex = 0;
		resolution = 1000;
		this.setPreferredSize(new Dimension(resolution, resolution));
		createBackground();
	}

	/**
	 * draws image of a single point on the board
	 * 
	 * @param p  location on board
	 * @param ic image to be drawn
	 * @param i  JLayeredPane's layer index 
	 */
	public void draw(Point p, ImageIcon ic, Integer i) {
		JLabel lImage = new JLabel(ic);
		lImage.setBounds(10 * p.x, 10 * p.y, 10, 10);
		this.add(lImage, i);
		this.moveToFront(lImage);
		this.repaint();
	}
	/**
	 * draws bonus image
	 * @param ico JLabel with image
	 */
	public void drawBonus(JLabel ico) {
		this.add(ico, Integer.valueOf(1));
		this.repaint();
	}
	/**
	 * removes image of bonus
	 * @param l JLabel with image to be removed
	 */
	public void removeBonusImage(JLabel ico) {
		this.remove(ico);
		this.repaint();
	}

	/**
	 * alternately changes game's background
	 */
	public void switchBg() {
		if (bgIndex == 0) {
			bgIndex = 1;
		} else if (bgIndex == 1) {
			bgIndex = 2;
		} else if (bgIndex == 2) {
			bgIndex = 0;
		}
		createBackground();
	}
	/**
	 * draws background from image array
	 */
	
	public void createBackground() {
		this.removeAll();
		JLabel lBg = new JLabel(background[bgIndex]);
		lBg.setBounds(0, 0, resolution, resolution);
		this.add(lBg, Integer.valueOf(0));
		this.repaint();
	}

	public int getResolution() {
		return resolution;
	}

	public int getBgIndex() {
		return bgIndex;
	}
}
