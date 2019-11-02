package tst;

import java.awt.Point;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import tst.PointType;

/**
 * Klasa Bonus reprezentuje pojawiaj¹cy siê na planszy bonus który po
 * podniesieniu daje graczowi czasowy modyfikator
 * ma on rozmiar 3x3 na planszy
 * wystepuje w 3 odmianach, reprezentowanych polami na planszy
 */
public class Bonus {
	private static ImageIcon[] images = { new ImageIcon("Images\\bonusOS.jpg"), new ImageIcon("Images\\bonusNT.png"),
			new ImageIcon("Images\\bonusS.jpg") };

	private Point location;
	private JLabel icon;
	private PointType boardPoint;
	private PointType boardOnLinePoint;
/**
 * Kostruktor od razu zaznacza bonus na planszy i rysuje go w okienu
 * @param p polozenie na planszy
 * @param board plansza gry
 * @param boardPoint reprezentacja rozdzaju bonusu
 */
	Bonus(Point p, PointType[][] board, PointType boardPoint) {
		this.boardPoint = boardPoint;
		adjustOnLinePoint(boardPoint);
		this.location = p;
		GameTicker.bonuses.add(this);
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
/**
 * jesli pod Bonusem znajduje sie linia to jest to zapamietane
 * @param boardPoint polozenie Bonusu
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
		GameWindow.panel.add(icon, Integer.valueOf(1));
		return icon;
	}
	/**
	 * metoda usuwa Bonus znajduj¹cy siê na danym polu planszy
	 * @param p polozenie punktu planszy
	 * @param board plansza gry
	 */
	static void removeBonus(Point p, PointType[][] board) {
		for (Bonus b : GameTicker.bonuses) {
			if (Math.abs(b.location.x - p.x) < 2 && Math.abs(b.location.y - p.y) < 2) {
				GameWindow.panel.remove(b.icon);
				GameWindow.panel.repaint();
				b.removeBonusBoard(board);
				GameTicker.bonuses.remove(b);
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

	public JLabel getIcon() {
		return icon;
	}

	public PointType getBoardOnLinePoint() {
		return boardOnLinePoint;
	}

}