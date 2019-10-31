package tst;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;

public class DriverControl implements KeyListener {
	enum ImageSet {
		YELLOW(0), CYAN(7), RED(14);

		private final int index;

		private ImageSet(int value) {
			this.index = value;
		}
	};

	int codeUP;
	int codeDOWN;
	int codeLEFT;
	int codeRIGHT;
	Driver d;

	DriverControl(Driver d, int up, int down, int left, int right) {
		this.d = d;
		codeUP = up;
		codeDOWN = down;
		codeLEFT = left;
		codeRIGHT = right;
	}

	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if (codeUP(keyCode)) {
			d.direction = d.reverseSteering.active ? Driver.Direction.DOWN : Driver.Direction.UP;
		}
		if (codeDOWN(keyCode)) {
			d.direction = d.reverseSteering.active ? Driver.Direction.UP : Driver.Direction.DOWN;
		}
		if (codeLEFT(keyCode)) {
			d.direction = d.reverseSteering.active ? Driver.Direction.RIGHT : Driver.Direction.LEFT;
		}
		if (codeRIGHT(keyCode)) {
			d.direction = d.reverseSteering.active ? Driver.Direction.LEFT : Driver.Direction.RIGHT;
		}
	}

	void drive() {
		d.previousDirection = d.direction;
		if (d.direction == Driver.Direction.DOWN) {
			++d.location.y;
		} else if (d.direction == Driver.Direction.UP) {
			--d.location.y;
		} else if (d.direction == Driver.Direction.LEFT) {
			--d.location.x;
		} else if (d.direction == Driver.Direction.RIGHT) {
			++d.location.x;
		}
	}

	ImageIcon adjustTraceColor() {
		if (d.driverColor == Driver.DColor.YELLOW) {
			return adjustTrace(ImageSet.YELLOW.index);
		} else if (d.driverColor == Driver.DColor.CYAN) {
			return adjustTrace(ImageSet.CYAN.index);
		} else if (d.driverColor == Driver.DColor.RED) {
			return adjustTrace(ImageSet.RED.index);
		}
		throw new IllegalArgumentException();
	}

	private ImageIcon adjustTrace(int i) {
		if (d.direction == Driver.Direction.DOWN) {
			return adjustTraceDOWN(i);
		} else if (d.direction == Driver.Direction.UP) {
			return adjustTraceUP(i);
		} else if (d.direction == Driver.Direction.LEFT) {
			return adjustTraceLEFT(i);
		} else {
			return adjustTraceRIGHT(i);
		}
	}

	private ImageIcon adjustTraceRIGHT(int i) {
		if (d.previousDirection == Driver.Direction.DOWN) {
			d.previousDirection = Driver.Direction.RIGHT;
			return Driver.images[i + 6];
		} else if (d.previousDirection == Driver.Direction.UP) {
			d.previousDirection = Driver.Direction.RIGHT;
			return Driver.images[i + 4];
		} else {
			return Driver.images[i + 1];
		}
	}

	private ImageIcon adjustTraceLEFT(int i) {
		if (d.previousDirection == Driver.Direction.DOWN) {
			d.previousDirection = Driver.Direction.LEFT;
			return Driver.images[i + 5];
		} else if (d.previousDirection == Driver.Direction.UP) {
			d.previousDirection = Driver.Direction.LEFT;
			return Driver.images[i + 3];
		} else {
			return Driver.images[i + 1];
		}
	}

	private ImageIcon adjustTraceUP(int i) {
		if (d.previousDirection == Driver.Direction.LEFT) {
			d.previousDirection = Driver.Direction.UP;
			return Driver.images[i + 6];
		} else if (d.previousDirection == Driver.Direction.RIGHT) {
			d.previousDirection = Driver.Direction.UP;
			return Driver.images[i + 5];
		} else {
			return Driver.images[i + 2];
		}
	}

	private ImageIcon adjustTraceDOWN(int i) {
		if (d.previousDirection == Driver.Direction.LEFT) {
			d.previousDirection = Driver.Direction.DOWN;
			return Driver.images[i + 4];
		} else if (d.previousDirection == Driver.Direction.RIGHT) {
			d.previousDirection = Driver.Direction.DOWN;
			return Driver.images[i + 3];
		} else {
			return Driver.images[i + 2];
		}
	}

	ImageIcon adjustDriver() {
		if (d.driverColor == Driver.DColor.YELLOW) {
			return Driver.images[ImageSet.YELLOW.index];
		} else if (d.driverColor == Driver.DColor.CYAN) {
			return Driver.images[ImageSet.CYAN.index];
		} else if (d.driverColor == Driver.DColor.RED) {
			return Driver.images[ImageSet.RED.index];
		}
		throw new IllegalArgumentException();
	}

	void changeColor(Driver.DColor col) {
		d.driverColor = col;
	}

	private boolean codeRIGHT(int keyCode) {
		return keyCode == codeRIGHT && d.previousDirection != Driver.Direction.LEFT
				&& d.previousDirection != Driver.Direction.RIGHT;
	}

	private boolean codeLEFT(int keyCode) {
		return keyCode == codeLEFT && d.previousDirection != Driver.Direction.RIGHT
				&& d.previousDirection != Driver.Direction.LEFT;
	}

	private boolean codeDOWN(int keyCode) {
		return keyCode == codeDOWN && d.previousDirection != Driver.Direction.UP
				&& d.previousDirection != Driver.Direction.DOWN;
	}

	private boolean codeUP(int keyCode) {
		return keyCode == codeUP && d.previousDirection != Driver.Direction.DOWN
				&& d.previousDirection != Driver.Direction.UP;
	}

	public void keyTyped(KeyEvent e) {
	}

	public void keyReleased(KeyEvent e) {
	}

}