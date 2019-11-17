package elka.PROZ.TRONopodobne.controller;

import java.awt.Point;
import java.util.concurrent.ThreadLocalRandom;

import elka.PROZ.TRONopodobne.model.Bonus;
import elka.PROZ.TRONopodobne.model.PointType;

/**
 * factory class creates bonus with random location and type. Removes bonus too.
 */
public class BonusSpawner {
	/**
	 * creates bonus. Is static, called as utility tool.
	 * @param gt
	 */
	public static void createBonus(GameTicker gt) {
		int t = ThreadLocalRandom.current().nextInt(1, 4);
		int x = ThreadLocalRandom.current().nextInt(1, gt.getBoardSize() - 1);
		int y = ThreadLocalRandom.current().nextInt(1, gt.getBoardSize() - 1);
		Bonus b;
		switch (t) {
		case 1:
			b = new Bonus(new Point(x, y), gt, PointType.REVERSE_STEERING);
			break;
		case 2:
			b = new Bonus(new Point(x, y), gt, PointType.UNTOUCHABLE);
			break;
		default:
			b = new Bonus(new Point(x, y), gt, PointType.SPEED);
			break;
		}
		gt.getPanel().drawBonus(b.getIcon());
	}

	/**
	 * 
	 * finds bonus on given location and removes from board
	 * @param p     location of bonus
	 * @param board the board
	 */
	public static void removeBonus(Point p, GameTicker gt) {	
		for (int i = gt.getBonuses().size()-1; i>=0; --i) {
			if (Math.abs(gt.getBonuses().get(i).getLocation().x - p.x) < 2 && Math.abs(gt.getBonuses().get(i).getLocation().y - p.y) < 2) {
				gt.getPanel().removeBonusImage(gt.getBonuses().get(i).getIcon());
				removeBonusBoard(gt.getBoard(), gt.getBonuses().get(i));
				gt.getBonuses().remove(gt.getBonuses().get(i));
				break;
			}
		}
	}

	private static void removeBonusBoard(PointType[][] board, Bonus b) {
		for (int i = -1; i < 2; ++i) {
			for (int j = -1; j < 2; ++j) {
				if (bonusOnLine(board, b, i, j)) {
					board[b.getLocation().x + i][b.getLocation().y + j] = PointType.LINE;
				}else if(bonusNotOnLine(board, b, i, j)) {
					board[b.getLocation().x + i][b.getLocation().y + j] = PointType.NOTHING;
				}
			}
		}
	}
	
	private static boolean bonusNotOnLine(PointType[][] board, Bonus b, int i, int j) {
		return board[b.getLocation().x + i][b.getLocation().y + j] == b.getBoardPoint();
	}

	private static boolean bonusOnLine(PointType[][] board, Bonus b, int i, int j) {
		return board[b.getLocation().x + i][b.getLocation().y + j] == b.getBoardOnLinePoint();
	}
}
