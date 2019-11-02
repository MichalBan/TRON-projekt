package tst;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;

/**
 * Klasa przechowuje logike sterujaca Driverem. Sluzy za keylistener do
 * sterowania kierunkiem jazdy. Przechowuje i dobiera obrazki reprezentujace
 * postac gracza w oknie
 */
public class DriverController implements KeyListener {
	private enum ImageSet {
		YELLOW(0), CYAN(7), RED(14);

		private final int index;

		private ImageSet(int value) {
			this.index = value;
		}
	};

	private int codeUP;
	private int codeDOWN;
	private int codeLEFT;
	private int codeRIGHT;
	private Driver driver;

	private static ImageIcon[] images = { new ImageIcon("PojazdZ.jpg"), new ImageIcon("liniaPoziomaZ.jpg"),
			new ImageIcon("liniaPionowaZ.jpg"), new ImageIcon("liniaDolLewoZ.jpg"), new ImageIcon("liniaDolPrawoZ.jpg"),
			new ImageIcon("liniaGoraLewoZ.jpg"), new ImageIcon("liniaGoraPrawoZ.jpg"), new ImageIcon("PojazdC.jpg"),
			new ImageIcon("liniaPoziomaC.jpg"), new ImageIcon("liniaPionowaC.jpg"), new ImageIcon("liniaDolLewoC.jpg"),
			new ImageIcon("liniaDolPrawoC.jpg"), new ImageIcon("liniaGoraLewoC.jpg"),
			new ImageIcon("liniaGoraPrawoC.jpg"), new ImageIcon("PojazdR.jpg"), new ImageIcon("liniaPoziomaR.jpg"),
			new ImageIcon("liniaPionowaR.jpg"), new ImageIcon("liniaDolLewoR.jpg"), new ImageIcon("liniaDolPrawoR.jpg"),
			new ImageIcon("liniaGoraLewoR.jpg"), new ImageIcon("liniaGoraPrawoR.jpg"), };

	/**
	 * Kody klawiszy zwiazanych z kierunkami gracza sa podawane w konstruktorze
	 */
	public DriverController(Driver d, int up, int down, int left, int right) {
		this.driver = d;
		codeUP = up;
		codeDOWN = down;
		codeLEFT = left;
		codeRIGHT = right;
	}

	/**
	 * Nacisniecie klawisza zmienia kierunek jazdy. Nie jest dopuszczalne zawrocenie
	 * w miejscu powodujace natychmiastowa przegrana
	 */
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if (codeUP(keyCode)) {
			driver.setDirection(driver.getReverseSteering().active ? Driver.Direction.DOWN : Driver.Direction.UP);
		}
		if (codeDOWN(keyCode)) {
			driver.setDirection(driver.getReverseSteering().active ? Driver.Direction.UP : Driver.Direction.DOWN);
		}
		if (codeLEFT(keyCode)) {
			driver.setDirection(driver.getReverseSteering().active ? Driver.Direction.RIGHT : Driver.Direction.LEFT);
		}
		if (codeRIGHT(keyCode)) {
			driver.setDirection(driver.getReverseSteering().active ? Driver.Direction.LEFT : Driver.Direction.RIGHT);
		}
	}

	/**
	 * metoda zmieniajaca polozenie gracza zgodnie z kierunkiem jazdy
	 */
	public void drive() {
		driver.setPreviousDirection(driver.getDirection());
		if (driver.getDirection() == Driver.Direction.DOWN) {
			driver.setLocation(new Point(driver.getLocation().x, ++driver.getLocation().y));
		} else if (driver.getDirection() == Driver.Direction.UP) {
			driver.setLocation(new Point(driver.getLocation().x, --driver.getLocation().y));
		} else if (driver.getDirection() == Driver.Direction.LEFT) {
			driver.setLocation(new Point(--driver.getLocation().x, driver.getLocation().y));
		} else if (driver.getDirection() == Driver.Direction.RIGHT) {
			driver.setLocation(new Point(++driver.getLocation().x, driver.getLocation().y));
		}
	}

	/**
	 * 6 metod zaczynajacych sie od adjustTrace jest odpowiedzialnych za dobranie
	 * odpowiedniego obrazka sladu gracza
	 * 
	 */
	public ImageIcon adjustTraceColor() {
		if (driver.getDriverColor() == Driver.DColor.YELLOW) {
			return adjustTrace(ImageSet.YELLOW.index);
		} else if (driver.getDriverColor() == Driver.DColor.CYAN) {
			return adjustTrace(ImageSet.CYAN.index);
		} else if (driver.getDriverColor() == Driver.DColor.RED) {
			return adjustTrace(ImageSet.RED.index);
		}
		throw new IllegalArgumentException();
	}

	private ImageIcon adjustTrace(int i) {
		if (driver.getDirection() == Driver.Direction.DOWN) {
			return adjustTraceDOWN(i);
		} else if (driver.getDirection() == Driver.Direction.UP) {
			return adjustTraceUP(i);
		} else if (driver.getDirection() == Driver.Direction.LEFT) {
			return adjustTraceLEFT(i);
		} else {
			return adjustTraceRIGHT(i);
		}
	}

	private ImageIcon adjustTraceRIGHT(int i) {
		if (driver.getPreviousDirection() == Driver.Direction.DOWN) {
			driver.setPreviousDirection(Driver.Direction.RIGHT);
			return DriverController.images[i + 6];
		} else if (driver.getPreviousDirection() == Driver.Direction.UP) {
			driver.setPreviousDirection(Driver.Direction.RIGHT);
			return DriverController.images[i + 4];
		} else {
			return DriverController.images[i + 1];
		}
	}

	private ImageIcon adjustTraceLEFT(int i) {
		if (driver.getPreviousDirection() == Driver.Direction.DOWN) {
			driver.setPreviousDirection(Driver.Direction.LEFT);
			return DriverController.images[i + 5];
		} else if (driver.getPreviousDirection() == Driver.Direction.UP) {
			driver.setPreviousDirection(Driver.Direction.LEFT);
			return DriverController.images[i + 3];
		} else {
			return DriverController.images[i + 1];
		}
	}

	private ImageIcon adjustTraceUP(int i) {
		if (driver.getPreviousDirection() == Driver.Direction.LEFT) {
			driver.setPreviousDirection(Driver.Direction.UP);
			return DriverController.images[i + 6];
		} else if (driver.getPreviousDirection() == Driver.Direction.RIGHT) {
			driver.setPreviousDirection(Driver.Direction.UP);
			return DriverController.images[i + 5];
		} else {
			return DriverController.images[i + 2];
		}
	}

	private ImageIcon adjustTraceDOWN(int i) {
		if (driver.getPreviousDirection() == Driver.Direction.LEFT) {
			driver.setPreviousDirection(Driver.Direction.DOWN);
			return DriverController.images[i + 4];
		} else if (driver.getPreviousDirection() == Driver.Direction.RIGHT) {
			driver.setPreviousDirection(Driver.Direction.DOWN);
			return DriverController.images[i + 3];
		} else {
			return DriverController.images[i + 2];
		}
	}
	/**
	 * metoda dobierajaca obrazek postaci gracza
	 */
	public ImageIcon adjustDriver() {
		if (driver.getDriverColor() == Driver.DColor.YELLOW) {
			return DriverController.images[ImageSet.YELLOW.index];
		} else if (driver.getDriverColor() == Driver.DColor.CYAN) {
			return DriverController.images[ImageSet.CYAN.index];
		} else if (driver.getDriverColor() == Driver.DColor.RED) {
			return DriverController.images[ImageSet.RED.index];
		}
		throw new IllegalArgumentException();
	}

	public Driver getDriver() {
		return driver;
	}

	private boolean codeRIGHT(int keyCode) {
		return keyCode == codeRIGHT && driver.getPreviousDirection() != Driver.Direction.LEFT
				&& driver.getPreviousDirection() != Driver.Direction.RIGHT;
	}

	private boolean codeLEFT(int keyCode) {
		return keyCode == codeLEFT && driver.getPreviousDirection() != Driver.Direction.RIGHT
				&& driver.getPreviousDirection() != Driver.Direction.LEFT;
	}

	private boolean codeDOWN(int keyCode) {
		return keyCode == codeDOWN && driver.getPreviousDirection() != Driver.Direction.UP
				&& driver.getPreviousDirection() != Driver.Direction.DOWN;
	}

	private boolean codeUP(int keyCode) {
		return keyCode == codeUP && driver.getPreviousDirection() != Driver.Direction.DOWN
				&& driver.getPreviousDirection() != Driver.Direction.UP;
	}

	public void keyTyped(KeyEvent e) {
	}

	public void keyReleased(KeyEvent e) {
	}

}