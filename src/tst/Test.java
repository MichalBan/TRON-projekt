package tst;

import static org.junit.Assert.*;

import java.awt.Point;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;

public class Test {

	@org.junit.Test
	public void test() {
		testMenuColor();
		testColorButtonAutolocation();
		testBonus();
		testDriverControl();
	}

	private void testDriverControl() {
		testDrive();
		testKeyListener();
		testChangeColor();
		testAdjustDriver();
		testAdjustTraceColor();
	}

	private void testAdjustTraceColor() {
		DriverControl dc = new DriverControl(new Driver(new Point(0, 0), Driver.Direction.LEFT), 10, 11, 12, 13);
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j<4; ++j) {
				for(int k = 0; k<4; ++k) {
					dc.changeColor(Driver.DColor.values()[i]);
					dc.d.direction = Driver.Direction.values()[j];
					dc.d.previousDirection = Driver.Direction.values()[k];
					assertNotNull(dc.adjustTraceColor());
				}
			}
		}
		try {
			dc.changeColor(null);
			dc.adjustTraceColor();
		} catch (Exception e) {
			assertNotNull(e);
		}
	}

	private void testDrive() {
		DriverControl dc = new DriverControl(new Driver(new Point(0, 0), Driver.Direction.LEFT), 10, 11, 12, 13);
		dc.drive();
		assertTrue(dc.d.location.x == -1);
	}

	private void testAdjustDriver() {
		DriverControl dc = new DriverControl(new Driver(new Point(0, 0), Driver.Direction.LEFT), 10, 11, 12, 13);
		assert (dc.adjustDriver() == Driver.images[0]);
		dc.changeColor(Driver.DColor.CYAN);
		assert (dc.adjustDriver() == Driver.images[7]);
		dc.changeColor(Driver.DColor.RED);
		assert (dc.adjustDriver() == Driver.images[14]);
		try {
			dc.changeColor(null);
			dc.adjustDriver();
		}catch(Exception e) {
			assertNotNull(e);
		}

	}

	private void testChangeColor() {
		DriverControl dc = new DriverControl(new Driver(new Point(0, 0), Driver.Direction.LEFT), 10, 11, 12, 13);
		dc.changeColor(Driver.DColor.CYAN);
		assertTrue(dc.d.driverColor == Driver.DColor.CYAN);
	}

	private void testKeyListener() {
		DriverControl dc = new DriverControl(new Driver(new Point(0, 0), Driver.Direction.LEFT), 10, 11, 12, 13);
		KeyEvent e = new KeyEvent(new JFrame(), KeyEvent.KEY_PRESSED, 1, 0, 10, KeyEvent.CHAR_UNDEFINED);
		dc.keyPressed(e);
		dc.drive();
		assertTrue(dc.d.direction == Driver.Direction.UP);
		e = new KeyEvent(new JFrame(), KeyEvent.KEY_PRESSED, 1, 0, 12, KeyEvent.CHAR_UNDEFINED);
		dc.keyPressed(e);
		dc.drive();
		assertTrue(dc.d.direction == Driver.Direction.LEFT);
		e = new KeyEvent(new JFrame(), KeyEvent.KEY_PRESSED, 1, 0, 11, KeyEvent.CHAR_UNDEFINED);
		dc.keyPressed(e);
		dc.drive();
		assertTrue(dc.d.direction == Driver.Direction.DOWN);
		e = new KeyEvent(new JFrame(), KeyEvent.KEY_PRESSED, 1, 0, 13, KeyEvent.CHAR_UNDEFINED);
		dc.keyPressed(e);
		dc.drive();
		assertTrue(dc.d.direction == Driver.Direction.RIGHT);
	}

	private void testBonus() {
		PointType[][] board = new PointType[5][5];
		board[3][3] = PointType.LINE;
		Point p = new Point(2, 2);
		new Bonus(p, board, PointType.REVERSE_STEERING);
		assertNotNull(Bonus.images);
		testPutOnBoard(p, board);
		testRemoveBonus(board, new Point(1, 1));
		testDrawBonus(board, p);
		testAdjustOnLinePoint(p, board);
	}

	private void testRemoveBonus(PointType[][] board, Point p) {
		Bonus.removeBonus(p, board);
		for (PointType[] pa : board) {
			for (PointType poi : pa) {
				assertFalse(poi == PointType.REVERSE_STEERING || poi == PointType.REVERSE_STEERING_ON_LINE);
			}
		}
	}

	private void testDrawBonus(PointType[][] board, Point p) {
		assertNotNull(new Bonus(p, board, PointType.REVERSE_STEERING).icon);
	}

	private void testAdjustOnLinePoint(Point p, PointType[][] board) {
		assertTrue(new Bonus(p, board, PointType.REVERSE_STEERING).boardOnLinePoint == PointType.REVERSE_STEERING_ON_LINE);
		assertTrue(new Bonus(p, board, PointType.UNTOUCHABLE).boardOnLinePoint == PointType.UNTOUCHABLE_ON_LINE);
		assertTrue(new Bonus(p, board, PointType.SPEED).boardOnLinePoint == PointType.SPEED_ON_LINE);
		try {
			new Bonus(p, board, PointType.NOTHING);
		} catch (Exception e) {
			assertNotNull(e);
		}
	}

	private void testPutOnBoard(Point p, PointType[][] board) {
		for (int i = -1; i < 2; ++i) {
			for (int j = -1; j < 2; ++j) {
				assertTrue(board[p.x + i][p.y + j] == PointType.REVERSE_STEERING
						|| board[p.x + i][p.y + j] == PointType.REVERSE_STEERING_ON_LINE);
			}
		}
	}

	private void testColorButtonAutolocation() {
		ColorButton.location = new Point(GameLoop.resolution / 3 - 100, GameLoop.resolution / 2 - 150);
		new ColorButton(new JFrame(), "", (e) -> {
		});
		new ColorButton(new JFrame(), "", (e) -> {
		});
		assertTrue(ColorButton.location.y == GameLoop.resolution / 2 + 50);
		assertTrue(ColorButton.location.x == GameLoop.resolution / 3 - 100);
		new ColorButton(new JFrame(), "", (e) -> {
		});
		new ColorButton(new JFrame(), "", (e) -> {
		});
		new ColorButton(new JFrame(), "", (e) -> {
		});
		assertTrue(ColorButton.location.y == GameLoop.resolution / 2 + 50);
		assertTrue(ColorButton.location.x == GameLoop.resolution * 2 / 3 - 100);
	}

	private void testMenuColor() {
		MenuColor m = new MenuColor(new DriverControl[2], new JFrame());
		testConstructorMenuColor();
		testSwitchMenu(m);

	}

	private void testConstructorMenuColor() {
		assertSame(MenuColor.colorButtons.size(), 6);
		assertNotNull(MenuColor.doneButton);
	}

	private void testSwitchMenu(MenuColor m) {
		GameLoop.buttons.add(new MenuButton("", new JFrame(), (e) -> {
		}));
		MenuColor.doneButton.doClick();
		for (ColorButton c : MenuColor.colorButtons) {
			assertFalse(c.isVisible());
		}
		for (MenuButton c : GameLoop.buttons) {
			assertTrue(c.isVisible());
		}
	}
}
