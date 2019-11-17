package elka.PROZ.TRONopodobne.controller;

import java.io.File;

import javax.swing.ImageIcon;

import elka.PROZ.TRONopodobne.model.Direction;
import elka.PROZ.TRONopodobne.model.Driver;
import elka.PROZ.TRONopodobne.model.DriverColor;

/**
 * Adjusts images of driver and its trace for DriverController
 */
public class DriverImageAdjuster {
	/**
	 * connects position in image array with color
	 */
	private enum ImageSet {
		YELLOW(0), CYAN(7), RED(14);

		private final int index;

		private ImageSet(int value) {
			this.index = value;
		}
	};

	private static String fs = File.separator;
	/**
	 * array of all images of traces and drivers
	 */
	private static ImageIcon[] images = { new ImageIcon("Images" + fs + "PojazdZ.jpg"),
			new ImageIcon("Images" + fs + "liniaPoziomaZ.jpg"), new ImageIcon("Images" + fs + "liniaPionowaZ.jpg"),
			new ImageIcon("Images" + fs + "liniaDolLewoZ.jpg"), new ImageIcon("Images" + fs + "liniaDolPrawoZ.jpg"),
			new ImageIcon("Images" + fs + "liniaGoraLewoZ.jpg"), new ImageIcon("Images" + fs + "liniaGoraPrawoZ.jpg"),
			new ImageIcon("Images" + fs + "PojazdC.jpg"), new ImageIcon("Images" + fs + "liniaPoziomaC.jpg"),
			new ImageIcon("Images" + fs + "liniaPionowaC.jpg"), new ImageIcon("Images" + fs + "liniaDolLewoC.jpg"),
			new ImageIcon("Images" + fs + "liniaDolPrawoC.jpg"), new ImageIcon("Images" + fs + "liniaGoraLewoC.jpg"),
			new ImageIcon("Images" + fs + "liniaGoraPrawoC.jpg"), new ImageIcon("Images" + fs + "PojazdR.jpg"),
			new ImageIcon("Images" + fs + "liniaPoziomaR.jpg"), new ImageIcon("Images" + fs + "liniaPionowaR.jpg"),
			new ImageIcon("Images" + fs + "liniaDolLewoR.jpg"), new ImageIcon("Images" + fs + "liniaDolPrawoR.jpg"),
			new ImageIcon("Images" + fs + "liniaGoraLewoR.jpg"),
			new ImageIcon("Images" + fs + "liniaGoraPrawoR.jpg"), };
	private Driver driver;

	public DriverImageAdjuster(Driver d) {
		driver = d;
	}

	/**
	 * returns image of trace based on direction and color
	 */
	public ImageIcon adjustTraceColor() {
		if (driver.getDriverColor() == DriverColor.YELLOW) {
			return adjustTrace(ImageSet.YELLOW.index);
		} else if (driver.getDriverColor() == DriverColor.CYAN) {
			return adjustTrace(ImageSet.CYAN.index);
		} else if (driver.getDriverColor() == DriverColor.RED) {
			return adjustTrace(ImageSet.RED.index);
		}
		throw new IllegalArgumentException();
	}

	private ImageIcon adjustTrace(int i) {
		if (driver.getDirection() == Direction.DOWN) {
			return adjustTraceDOWN(i);
		} else if (driver.getDirection() == Direction.UP) {
			return adjustTraceUP(i);
		} else if (driver.getDirection() == Direction.LEFT) {
			return adjustTraceLEFT(i);
		} else {
			return adjustTraceRIGHT(i);
		}
	}

	private ImageIcon adjustTraceRIGHT(int i) {
		if (driver.getPreviousDirection() == Direction.DOWN) {
			driver.setPreviousDirection(Direction.RIGHT);
			return DriverImageAdjuster.images[i + 6];
		} else if (driver.getPreviousDirection() == Direction.UP) {
			driver.setPreviousDirection(Direction.RIGHT);
			return DriverImageAdjuster.images[i + 4];
		} else {
			return DriverImageAdjuster.images[i + 1];
		}
	}

	private ImageIcon adjustTraceLEFT(int i) {
		if (driver.getPreviousDirection() == Direction.DOWN) {
			driver.setPreviousDirection(Direction.LEFT);
			return DriverImageAdjuster.images[i + 5];
		} else if (driver.getPreviousDirection() == Direction.UP) {
			driver.setPreviousDirection(Direction.LEFT);
			return DriverImageAdjuster.images[i + 3];
		} else {
			return DriverImageAdjuster.images[i + 1];
		}
	}

	private ImageIcon adjustTraceUP(int i) {
		if (driver.getPreviousDirection() == Direction.LEFT) {
			driver.setPreviousDirection(Direction.UP);
			return DriverImageAdjuster.images[i + 6];
		} else if (driver.getPreviousDirection() == Direction.RIGHT) {
			driver.setPreviousDirection(Direction.UP);
			return DriverImageAdjuster.images[i + 5];
		} else {
			return DriverImageAdjuster.images[i + 2];
		}
	}

	private ImageIcon adjustTraceDOWN(int i) {
		if (driver.getPreviousDirection() == Direction.LEFT) {
			driver.setPreviousDirection(Direction.DOWN);
			return DriverImageAdjuster.images[i + 4];
		} else if (driver.getPreviousDirection() == Direction.RIGHT) {
			driver.setPreviousDirection(Direction.DOWN);
			return DriverImageAdjuster.images[i + 3];
		} else {
			return DriverImageAdjuster.images[i + 2];
		}
	}

	/**
	 * returns image of driver based on collor
	 */
	public ImageIcon adjustDriver() {
		if (driver.getDriverColor() == DriverColor.YELLOW) {
			return DriverImageAdjuster.images[ImageSet.YELLOW.index];
		} else if (driver.getDriverColor() == DriverColor.CYAN) {
			return DriverImageAdjuster.images[ImageSet.CYAN.index];
		} else if (driver.getDriverColor() == DriverColor.RED) {
			return DriverImageAdjuster.images[ImageSet.RED.index];
		}
		throw new IllegalArgumentException();
	}
}