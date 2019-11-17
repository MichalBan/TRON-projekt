package elka.PROZ.TRONopodobne.model;

import java.awt.Point;

/**
 * represents player in game. Controlled by DriverController
 */
public class Driver {

	private Point location;
	private Direction direction;
	private Direction previousDirection;
	private DriverColor driverColor = DriverColor.YELLOW;

	/**
	 * Driver stores information about all his active bonuses
	 */
	private BonusTimer reverseSteering = new BonusTimer(false, 0);
	private BonusTimer untouchable = new BonusTimer(false, 0);
	private BonusTimer speed = new BonusTimer(false, 0);

	public Driver(Point p, Direction k) {
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

	public DriverColor getDriverColor() {
		return driverColor;
	}

	public void setDriverColor(DriverColor driverColor) {
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