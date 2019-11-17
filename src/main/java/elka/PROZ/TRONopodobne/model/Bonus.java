package elka.PROZ.TRONopodobne.model;

import java.awt.Point;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import elka.PROZ.TRONopodobne.controller.GameTicker;

/**
 * bonus is a square on board which can be picked up by driver to give a trimed
 * effect there are 3 types: reverse steering, speed and untouchable which
 * allows driver to move through line.
 */
public class Bonus {
	/**
	 * array of images of bonuses
	 */
	private ImageIcon[] images;
	private Point location;
	private JLabel icon;
	private PointType boardPoint;
	private PointType boardOnLinePoint;

	/**
	 * constructor cerates bonus ands puts it on board
	 * 
	 * @param p          polozenie na planszy
	 * @param board      plansza gry
	 * @param boardPoint reprezentacja rozdzaju bonusu
	 */
	public Bonus(Point p, GameTicker gt, PointType boardPoint) {
		String fs = File.separator;
		images = new ImageIcon[] { new ImageIcon("Images" + fs + "bonusOS.jpg"),
				new ImageIcon("Images" + fs + "bonusNT.png"), new ImageIcon("Images" + fs + "bonusS.jpg") };
		this.boardPoint = boardPoint;
		adjustOnLinePoint(boardPoint);
		this.location = p;
		gt.getBonuses().add(this);
		int i;
		if (boardPoint == PointType.REVERSE_STEERING)
			i = 0;
		else if (boardPoint == PointType.UNTOUCHABLE)
			i = 1;
		else
			i = 2;
		this.icon = new JLabel(images[i]);
		icon.setBounds(10 * (p.x - 1), 10 * (p.y - 1), 30, 30);
		putOnBoard(p, gt.getBoard());
	}
	/**
	 * bonus on board is 3x3 size
	 * @param p middle point of bonus
	 */
	private void putOnBoard(Point p, PointType[][] board) {
		for (int i = -1; i < 2; ++i) {
			for (int j = -1; j < 2; ++j) {
				if (board[p.x + i][p.y + j] == PointType.LINE) {
					board[p.x + i][p.y + j] = boardOnLinePoint;
				} else {
					board[p.x + i][p.y + j] = boardPoint;
				}
			}
		}
	}

	/**
	 * bonus can spawn on line and it is remembered
	 * 
	 * @param boardPoint type of bonus
	 */
	private void adjustOnLinePoint(PointType boardPoint) {
		if (boardPoint == PointType.REVERSE_STEERING) {
			boardOnLinePoint = PointType.REVERSE_STEERING_ON_LINE;
		} else if (boardPoint == PointType.UNTOUCHABLE) {
			boardOnLinePoint = PointType.UNTOUCHABLE_ON_LINE;
		} else if (boardPoint == PointType.SPEED) {
			boardOnLinePoint = PointType.SPEED_ON_LINE;
		} else {
			throw new IllegalArgumentException();
		}
	}

	public JLabel getIcon() {
		return icon;
	}

	public PointType getBoardOnLinePoint() {
		return boardOnLinePoint;
	}

	public Point getLocation() {
		return location;
	}

	public PointType getBoardPoint() {
		return boardPoint;
	}

}