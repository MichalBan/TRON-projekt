package tst;

import java.awt.Point;

import javax.swing.ImageIcon;

public class Driver {
	static enum Direction {
		DOWN, UP, LEFT, RIGHT
	};

	static enum DColor {
		YELLOW, CYAN, RED
	};

	static ImageIcon[] images = { new ImageIcon("PojazdZ.jpg"), new ImageIcon("liniaPoziomaZ.jpg"),
			new ImageIcon("liniaPionowaZ.jpg"), new ImageIcon("liniaDolLewoZ.jpg"), new ImageIcon("liniaDolPrawoZ.jpg"),
			new ImageIcon("liniaGoraLewoZ.jpg"), new ImageIcon("liniaGoraPrawoZ.jpg"), new ImageIcon("PojazdC.jpg"),
			new ImageIcon("liniaPoziomaC.jpg"), new ImageIcon("liniaPionowaC.jpg"), new ImageIcon("liniaDolLewoC.jpg"),
			new ImageIcon("liniaDolPrawoC.jpg"), new ImageIcon("liniaGoraLewoC.jpg"),
			new ImageIcon("liniaGoraPrawoC.jpg"), new ImageIcon("PojazdR.jpg"), new ImageIcon("liniaPoziomaR.jpg"),
			new ImageIcon("liniaPionowaR.jpg"), new ImageIcon("liniaDolLewoR.jpg"), new ImageIcon("liniaDolPrawoR.jpg"),
			new ImageIcon("liniaGoraLewoR.jpg"), new ImageIcon("liniaGoraPrawoR.jpg"), };

	Point location;
	Direction direction;
	Direction previousDirection;
	DColor driverColor = DColor.YELLOW;

	class BonusTimer {
		boolean active;
		int time;

		BonusTimer(boolean a, int t) {
			active = a;
			time = t;
		}
	}

	BonusTimer reverseSteering = new BonusTimer(false, 0);
	BonusTimer untouchable = new BonusTimer(false, 0);
	BonusTimer speed = new BonusTimer(false, 0);

	Driver(Point p, Direction k) {
		location = p;
		this.direction = k;
		this.previousDirection = k;
	}

}