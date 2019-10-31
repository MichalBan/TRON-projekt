package tst;

import java.awt.Point;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import tst.PointType;

public class Bonus {
	static ImageIcon[] images = { new ImageIcon("bonusOS.jpg"), new ImageIcon("bonusNT.png"),
			new ImageIcon("bonusS.jpg") };

	Point location;
	JLabel icon;
	PointType boardPoint;
	PointType boardOnLinePoint;

	Bonus(Point p, PointType[][] board, PointType boardPoint) {
		this.boardPoint = boardPoint;
		adjustOnLinePoint(boardPoint);
		this.location = p;
		GameLoop.bonuses.add(this);
		this.icon = drawBonus(this.location);
		putOnBoard(p, board, boardPoint);
	}

	private void putOnBoard(Point p, PointType[][] board, PointType boardPoint) {
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

	private JLabel drawBonus(Point p) {
		int i;
		if (boardPoint == PointType.REVERSE_STEERING)
			i = 0;
		else if (boardPoint == PointType.UNTOUCHABLE)
			i = 1;
		else
			i = 2;
		JLabel icon = new JLabel(Bonus.images[i]);
		icon.setBounds(10 * (p.x - 1), 10 * (p.y - 1), 30, 30);
		GameLoop.panel.add(icon, new Integer(1));
		return icon;
	}

	static void removeBonus(Point p, PointType[][] board) {
		for (Bonus b : GameLoop.bonuses) {
			if (Math.abs(b.location.x - p.x) < 2 && Math.abs(b.location.y - p.y) < 2) {
				GameLoop.panel.remove(b.icon);
				GameLoop.panel.repaint();
				b.removeBonusBoard(board);
				GameLoop.bonuses.remove(b);
				break;
			}
		}
	}

	private void removeBonusBoard(PointType[][] board) {
		for (int i = -1; i < 2; ++i) {
			for (int j = -1; j < 2; ++j) {
				if (bonusOnLine(board, i, j)) {
					board[this.location.x + i][this.location.y + j] = PointType.LINE;
				} else {
					board[this.location.x + i][this.location.y + j] = PointType.NOTHING;
				}
			}
		}
	}

	private boolean bonusOnLine(PointType[][] board, int i, int j) {
		return board[this.location.x + i][this.location.y + j] == PointType.REVERSE_STEERING_ON_LINE
				|| board[this.location.x + i][this.location.y + j] == PointType.UNTOUCHABLE_ON_LINE
				|| board[this.location.x + i][this.location.y + j] == PointType.SPEED_ON_LINE;
	}

}