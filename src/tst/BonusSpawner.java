package tst;

import java.awt.Point;
import java.util.concurrent.ThreadLocalRandom;
/**
 * klasa typu factory
 * sluzy do tworzenia nowych bonusow na planszy
 * polozenie i typ bonusu jest losowe
 */
public class BonusSpawner {
	static void createBonusForBoard(PointType[][] board) {
		int t = ThreadLocalRandom.current().nextInt(1, 4);
		int x = ThreadLocalRandom.current().nextInt(1, GameTicker.boardSize - 1);
		int y = ThreadLocalRandom.current().nextInt(1, GameTicker.boardSize - 1);
		switch (t) {
		case 1:
			new Bonus(new Point(x, y), board, PointType.REVERSE_STEERING);
			break;
		case 2:
			new Bonus(new Point(x, y), board, PointType.UNTOUCHABLE);
			break;
		case 3:
			new Bonus(new Point(x, y), board, PointType.SPEED);
			break;
		}
	}
}
