package tst;

import java.awt.Point;

/**
 * klasa reprezentujaca postac gracza
 * nie zawiera logiki tylko dane
 */
public class Driver {
	static enum Direction {
		DOWN, UP, LEFT, RIGHT
	};

	static enum DColor {
		YELLOW, CYAN, RED
	};

	private Point location;
	private Direction direction;
	private Direction previousDirection;
	private DColor driverColor = DColor.YELLOW;

	/**
	 * Driver przechowuje informacje o wszystkich aktywnych na nim bonusach
	 */
	class BonusTimer {
		boolean active;
		int time;

		BonusTimer(boolean a, int t) {
			active = a;
			time = t;
		}
	}

	private BonusTimer reverseSteering = new BonusTimer(false, 0);
	private BonusTimer untouchable = new BonusTimer(false, 0);
	private BonusTimer speed = new BonusTimer(false, 0);

	Driver(Point p, Direction k) {
		location = p;
		this.direction = k;
		this.previousDirection = k;
	}

	public Point getLocation() {
		return location;
	}

	public void setLocation(Point location) {
		this.location = location;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public Direction getPreviousDirection() {
		return previousDirection;
	}

	public void setPreviousDirection(Direction previousDirection) {
		this.previousDirection = previousDirection;
	}

	public DColor getDriverColor() {
		return driverColor;
	}

	public void setDriverColor(DColor driverColor) {
		this.driverColor = driverColor;
	}

	public BonusTimer getReverseSteering() {
		return reverseSteering;
	}

	public BonusTimer getUntouchable() {
		return untouchable;
	}

	public BonusTimer getSpeed() {
		return speed;
	}

}