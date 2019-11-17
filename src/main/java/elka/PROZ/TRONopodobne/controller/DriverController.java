package elka.PROZ.TRONopodobne.controller;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;

import elka.PROZ.TRONopodobne.model.Direction;
import elka.PROZ.TRONopodobne.model.Driver;

/**
 * Controlls driver. Changes its direction based on key clics. Moves driver on
 * board. Gives images of trace and driver.
 */
public class DriverController implements KeyListener {

	private int codeUP;
	private int codeDOWN;
	private int codeLEFT;
	private int codeRIGHT;
	private Driver driver;
	private DriverImageAdjuster DIA;

	public DriverController(Driver d, int up, int down, int left, int right) {
		this.driver = d;
		codeUP = up;
		codeDOWN = down;
		codeLEFT = left;
		codeRIGHT = right;
		DIA = new DriverImageAdjuster(driver);
	}

	/**
	 * Clicking a key changes direction of driver. Changing into reverse of current
	 * direction is checked for and not allowed.
	 */
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if (codeUP(keyCode)) {
			driver.setDirection(driver.getReverseSteering().isActive() ? Direction.DOWN : Direction.UP);
		}
		if (codeDOWN(keyCode)) {
			driver.setDirection(driver.getReverseSteering().isActive() ? Direction.UP : Direction.DOWN);
		}
		if (codeLEFT(keyCode)) {
			driver.setDirection(driver.getReverseSteering().isActive() ? Direction.RIGHT : Direction.LEFT);
		}
		if (codeRIGHT(keyCode)) {
			driver.setDirection(driver.getReverseSteering().isActive() ? Direction.LEFT : Direction.RIGHT);
		}
	}

	/**
	 * Moves driver according to its direction
	 */
	public void drive() {
		driver.setPreviousDirection(driver.getDirection());
		if (driver.getDirection() == Direction.DOWN) {
			driver.setLocation(new Point(driver.getLocation().x, ++driver.getLocation().y));
		} else if (driver.getDirection() == Direction.UP) {
			driver.setLocation(new Point(driver.getLocation().x, --driver.getLocation().y));
		} else if (driver.getDirection() == Direction.LEFT) {
			driver.setLocation(new Point(--driver.getLocation().x, driver.getLocation().y));
		} else if (driver.getDirection() == Direction.RIGHT) {
			driver.setLocation(new Point(++driver.getLocation().x, driver.getLocation().y));
		}
	}
	/**
	 * @return image of driver with appropriate collor
	 */
	public ImageIcon getDriverImage() {
		return DIA.adjustDriver();
	}
	/**
	 * @return image of trace based on color and directions
	 */
	public ImageIcon getTraceImage() {
		return DIA.adjustTraceColor();
	}

	private boolean codeRIGHT(int keyCode) {
		return keyCode == codeRIGHT && driver.getPreviousDirection() != Direction.LEFT
				&& driver.getPreviousDirection() != Direction.RIGHT;
	}

	private boolean codeLEFT(int keyCode) {
		return keyCode == codeLEFT && driver.getPreviousDirection() != Direction.RIGHT
				&& driver.getPreviousDirection() != Direction.LEFT;
	}

	private boolean codeDOWN(int keyCode) {
		return keyCode == codeDOWN && driver.getPreviousDirection() != Direction.UP
				&& driver.getPreviousDirection() != Direction.DOWN;
	}

	private boolean codeUP(int keyCode) {
		return keyCode == codeUP && driver.getPreviousDirection() != Direction.DOWN
				&& driver.getPreviousDirection() != Direction.UP;
	}

	public Driver getDriver() {
		return driver;
	}

	public void keyTyped(KeyEvent e) {
	}

	public void keyReleased(KeyEvent e) {
	}

}