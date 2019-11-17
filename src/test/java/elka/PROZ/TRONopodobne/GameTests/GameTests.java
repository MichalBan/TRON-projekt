package elka.PROZ.TRONopodobne.GameTests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.awt.Point;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;

import org.junit.AfterClass;
import org.junit.Test;

import elka.PROZ.TRONopodobne.controller.BonusSpawner;
import elka.PROZ.TRONopodobne.controller.ColorMenuController;
import elka.PROZ.TRONopodobne.controller.DriverController;
import elka.PROZ.TRONopodobne.controller.GameTicker;
import elka.PROZ.TRONopodobne.controller.GameWindow;
import elka.PROZ.TRONopodobne.model.Bonus;
import elka.PROZ.TRONopodobne.model.Direction;
import elka.PROZ.TRONopodobne.model.Driver;
import elka.PROZ.TRONopodobne.model.DriverColor;
import elka.PROZ.TRONopodobne.model.PointType;
import elka.PROZ.TRONopodobne.view.MenuButton;
import elka.PROZ.TRONopodobne.view.MyPanel;

public class GameTests {

	@Test
	public void testAdjustTraceColor() {
		DriverController dc = new DriverController(new Driver(new Point(0, 0), Direction.LEFT), 10, 11, 12, 13);
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 4; ++j) {
				for (int k = 0; k < 4; ++k) {
					dc.getDriver().setDriverColor(DriverColor.values()[i]);
					dc.getDriver().setDirection(Direction.values()[j]);
					dc.getDriver().setPreviousDirection(Direction.values()[k]);
					assertNotNull(dc.getTraceImage());
				}
			}
		}
		try {
			dc.getDriver().setDriverColor(null);
			dc.getTraceImage();
		} catch (Exception e) {
			assertNotNull(e);
		}
	}

	@Test
	public void testDrive() {
		DriverController dc = new DriverController(new Driver(new Point(0, 0), Direction.LEFT), 10, 11, 12, 13);
		dc.drive();
		assertTrue(dc.getDriver().getLocation().x == -1);
	}

	@Test
	public void testAdjustDriver() {
		DriverController dc = new DriverController(new Driver(new Point(0, 0), Direction.LEFT), 10, 11, 12, 13);
		assertNotNull(dc.getDriverImage());
		dc.getDriver().setDriverColor(DriverColor.CYAN);
		assertNotNull(dc.getDriverImage());
		dc.getDriver().setDriverColor(DriverColor.RED);
		assertNotNull(dc.getDriverImage());
		try {
			dc.getDriver().setDriverColor(null);
			dc.getDriverImage();
		} catch (Exception e) {
			assertNotNull(e);
		}

	}

	@Test
	public void testChangeColor() {
		DriverController dc = new DriverController(new Driver(new Point(0, 0), Direction.LEFT), 10, 11, 12, 13);
		dc.getDriver().setDriverColor(DriverColor.CYAN);
		assertTrue(dc.getDriver().getDriverColor() == DriverColor.CYAN);
	}

	@Test
	public void testKeyListener() {
		DriverController dc = new DriverController(new Driver(new Point(0, 0), Direction.LEFT), 10, 11, 12, 13);
		KeyEvent e = new KeyEvent(new JFrame(), KeyEvent.KEY_PRESSED, 1, 0, 10, KeyEvent.CHAR_UNDEFINED);
		dc.keyPressed(e);
		dc.drive();
		assertTrue(dc.getDriver().getDirection() == Direction.UP);
		e = new KeyEvent(new JFrame(), KeyEvent.KEY_PRESSED, 1, 0, 12, KeyEvent.CHAR_UNDEFINED);
		dc.keyPressed(e);
		dc.drive();
		assertTrue(dc.getDriver().getDirection() == Direction.LEFT);
		e = new KeyEvent(new JFrame(), KeyEvent.KEY_PRESSED, 1, 0, 11, KeyEvent.CHAR_UNDEFINED);
		dc.keyPressed(e);
		dc.drive();
		assertTrue(dc.getDriver().getDirection() == Direction.DOWN);
		e = new KeyEvent(new JFrame(), KeyEvent.KEY_PRESSED, 1, 0, 13, KeyEvent.CHAR_UNDEFINED);
		dc.keyPressed(e);
		dc.drive();
		assertTrue(dc.getDriver().getDirection() == Direction.RIGHT);
	}

	@Test
	public void testRemoveBonus() {
		GameTicker gt = new GameTicker(new MyPanel());
		PointType[][] board = new PointType[5][5];
		board[3][3] = PointType.LINE;
		gt.setBoard(board);
		Point p = new Point(2, 2);
		new Bonus(p, gt, PointType.REVERSE_STEERING);
		BonusSpawner.removeBonus(p, gt);
		for (PointType[] pa : board) {
			for (PointType poi : pa) {
				assertFalse(poi == PointType.REVERSE_STEERING || poi == PointType.REVERSE_STEERING_ON_LINE);
			}
		}
	}

	@Test
	public void testDrawBonus() {
		GameTicker gt = new GameTicker(new MyPanel());
		PointType[][] board = new PointType[5][5];
		board[3][3] = PointType.LINE;
		gt.setBoard(board);
		Point p = new Point(2, 2);
		assertNotNull(new Bonus(p, gt, PointType.REVERSE_STEERING).getIcon());
	}

	@Test
	public void testAdjustOnLinePoint() {
		GameTicker gt = new GameTicker(new MyPanel());
		PointType[][] board = new PointType[5][5];
		board[3][3] = PointType.LINE;
		gt.setBoard(board);
		Point p = new Point(2, 2);
		assertTrue(new Bonus(p, gt, PointType.REVERSE_STEERING)
				.getBoardOnLinePoint() == PointType.REVERSE_STEERING_ON_LINE);
		assertTrue(new Bonus(p, gt, PointType.UNTOUCHABLE).getBoardOnLinePoint() == PointType.UNTOUCHABLE_ON_LINE);
		assertTrue(new Bonus(p, gt, PointType.SPEED).getBoardOnLinePoint() == PointType.SPEED_ON_LINE);
		try {
			new Bonus(p, gt, PointType.NOTHING);
		} catch (Exception e) {
			assertNotNull(e);
		}
	}

	@Test
	public void testPutOnBoard() {
		GameTicker gt = new GameTicker(new MyPanel());
		PointType[][] board = new PointType[5][5];
		board[3][3] = PointType.LINE;
		gt.setBoard(board);
		Point p = new Point(2, 2);
		new Bonus(p, gt, PointType.REVERSE_STEERING);
		for (int i = -1; i < 2; ++i) {
			for (int j = -1; j < 2; ++j) {
				assertTrue(board[p.x + i][p.y + j] == PointType.REVERSE_STEERING
						|| board[p.x + i][p.y + j] == PointType.REVERSE_STEERING_ON_LINE);
			}
		}
	}

	@Test
	public void testBonusSpawner() {
		GameTicker gt = new GameTicker(new MyPanel());
		BonusSpawner.createBonus(gt);
		for (PointType[] p : gt.getBoard()) {
			for (PointType pp : p) {
				assertNotNull(pp);
			}
		}
	}

	@Test
	public void testStartGame() {
		GameWindow gw = new GameWindow();
		gw.getTicker().getButtons().get(0).doClick();
		for (MenuButton mb : gw.getTicker().getButtons()) {
			assertFalse(mb.isVisible());
		}
		assertNotNull(gw.getPanel().getKeyListeners());
		assertTrue(gw.getTicker().isRunning());
	}

	@Test
	public void testSwitchBg() {
		GameWindow gw = new GameWindow();
		gw.getTicker().getButtons().get(3).doClick();
		assertTrue(gw.getPanel().getBgIndex() == 1);
		gw.getTicker().getButtons().get(3).doClick();
		assertTrue(gw.getPanel().getBgIndex() == 2);
		gw.getTicker().getButtons().get(3).doClick();
		assertTrue(gw.getPanel().getBgIndex() == 0);
	}

	@Test
	public void testSwitchBonus() {
		GameWindow gw = new GameWindow();
		gw.getTicker().getButtons().get(1).doClick();
		assertFalse(gw.getTicker().isBonusesActive());
		gw.getTicker().getButtons().get(1).doClick();
		assertTrue(gw.getTicker().isBonusesActive());
	}

	@Test
	public void testMenuColor() {
		GameWindow gw = new GameWindow();
		ColorMenuController m = new ColorMenuController(gw);
		assertTrue(m.getColorButtons().size() == 6);
		assertNotNull(m.getDoneButton());
	}

	@Test
	public void testGameTick() {
		GameTicker gt = new GameTicker(new MyPanel());
		gt.start();
		try {
			Thread.sleep(40);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertFalse(gt.getControllers()[0].getDriver().getLocation().y == 10);
		assertFalse(gt.getControllers()[1].getDriver().getLocation().y == gt.getBoardSize() - 10);
		assertTrue(gt.getBoard()[gt.getBoardSize() / 2][10] == PointType.LINE);
	}

	@Test
	public void testActivateBonus() {
		GameTicker gt = new GameTicker(new MyPanel());
		gt.getBoard()[50][11] = PointType.REVERSE_STEERING;
		gt.getBoard()[50][12] = PointType.UNTOUCHABLE;
		gt.getBoard()[50][13] = PointType.SPEED;
		gt.start();
		waitNumOfFrames(3);
		assertTrue(gt.getControllers()[0].getDriver().getReverseSteering().isActive());
		assertTrue(gt.getControllers()[0].getDriver().getUntouchable().isActive());
		assertTrue(gt.getControllers()[0].getDriver().getSpeed().isActive());
	}

	@Test
	public void testTickBonus() {
		GameTicker gt = new GameTicker(new MyPanel());
		gt.getBoard()[50][11] = PointType.REVERSE_STEERING;
		gt.start();
		waitNumOfFrames(2);
		assertFalse(gt.getControllers()[0].getDriver().getReverseSteering().getTime() == 75);
	}

	@Test
	public void testCollison() {
		GameTicker gt = new GameTicker(new MyPanel());
		gt.getBoard()[50][11] = PointType.REVERSE_STEERING_ON_LINE;
		gt.start();
		waitNumOfFrames(1);
		assertFalse(gt.isRunning());
		gt = new GameTicker(new MyPanel());
		gt.getBoard()[50][11] = PointType.UNTOUCHABLE_ON_LINE;
		gt.start();
		waitNumOfFrames(1);
		assertFalse(gt.isRunning());
		gt = new GameTicker(new MyPanel());
		gt.getBoard()[50][11] = PointType.SPEED_ON_LINE;
		gt.start();
		waitNumOfFrames(1);
		assertFalse(gt.isRunning());
		gt = new GameTicker(new MyPanel());
		gt.getBoard()[50][11] = PointType.LINE;
		gt.start();
		waitNumOfFrames(1);
		assertFalse(gt.isRunning());
	}

	@Test
	public void testUntouchableDetection() {
		GameTicker gt = new GameTicker(new MyPanel());
		gt.getBoard()[50][11] = PointType.UNTOUCHABLE;
		gt.getBoard()[50][12] = PointType.REVERSE_STEERING_ON_LINE;
		gt.start();
		waitNumOfFrames(2);
		assertTrue(gt.isRunning());
		gt.stop();
		gt = new GameTicker(new MyPanel());
		gt.getBoard()[50][11] = PointType.UNTOUCHABLE;
		gt.getBoard()[50][12] = PointType.UNTOUCHABLE_ON_LINE;
		gt.start();
		waitNumOfFrames(2);
		assertTrue(gt.isRunning());
		gt.stop();
		gt = new GameTicker(new MyPanel());
		gt.getBoard()[50][11] = PointType.UNTOUCHABLE;
		gt.getBoard()[50][12] = PointType.SPEED_ON_LINE;
		gt.start();
		waitNumOfFrames(2);
		assertTrue(gt.isRunning());
		gt.stop();
		gt = new GameTicker(new MyPanel());
		gt.getBoard()[50][11] = PointType.UNTOUCHABLE;
		gt.getBoard()[50][12] = PointType.LINE;
		gt.start();
		waitNumOfFrames(2);
		assertTrue(gt.isRunning());
		gt.stop();
	}

	@Test
	public void testCreateSpaceListener() {

		GameTicker gt = new GameTicker(new MyPanel());
		gt.getBoard()[50][11] = PointType.REVERSE_STEERING_ON_LINE;
		gt.start();
		waitNumOfFrames(1);
		assertTrue(gt.getPanel().getKeyListeners().length == 1);
		KeyEvent e = new KeyEvent(gt.getPanel(), KeyEvent.KEY_PRESSED, 1, 0, KeyEvent.VK_SPACE,
				KeyEvent.CHAR_UNDEFINED);
		gt.getPanel().getKeyListeners()[0].keyPressed(e);
		assertTrue(gt.getPanel().getKeyListeners().length == 0);
	}

	@Test
	public void testReturnToMenu() {
		GameTicker gt = new GameTicker(new MyPanel());
		gt.getBoard()[50][11] = PointType.REVERSE_STEERING_ON_LINE;
		gt.start();
		waitNumOfFrames(1);
		KeyEvent e = new KeyEvent(gt.getPanel(), KeyEvent.KEY_PRESSED, 1, 0, KeyEvent.VK_SPACE,
				KeyEvent.CHAR_UNDEFINED);
		gt.getPanel().getKeyListeners()[0].keyPressed(e);
		for (MenuButton b : gt.getButtons()) {
			assertTrue(b.isVisible());
		}
	}

	@Test
	public void testSetDriversOnStartingPositions() {
		GameTicker gt = new GameTicker(new MyPanel());
		gt.getBoard()[50][11] = PointType.REVERSE_STEERING_ON_LINE;
		gt.start();
		waitNumOfFrames(1);
		KeyEvent e = new KeyEvent(gt.getPanel(), KeyEvent.KEY_PRESSED, 1, 0, KeyEvent.VK_SPACE,
				KeyEvent.CHAR_UNDEFINED);
		gt.getPanel().getKeyListeners()[0].keyPressed(e);
		assertTrue(gt.getControllers()[0].getDriver().getLocation().x == gt.getBoardSize() / 2);
		assertTrue(gt.getControllers()[0].getDriver().getLocation().y == 10);
		assertTrue(gt.getControllers()[0].getDriver().getDirection() == Direction.DOWN);
		assertTrue(gt.getControllers()[0].getDriver().getPreviousDirection() == Direction.DOWN);
		assertTrue(gt.getControllers()[1].getDriver().getLocation().x == gt.getBoardSize() / 2);
		assertTrue(gt.getControllers()[1].getDriver().getLocation().y == gt.getBoardSize() - 10);
		assertTrue(gt.getControllers()[1].getDriver().getDirection() == Direction.UP);
		assertTrue(gt.getControllers()[1].getDriver().getPreviousDirection() == Direction.UP);
	}

	@Test
	public void testDeactivateBonuses() {
		GameTicker gt = new GameTicker(new MyPanel());
		gt.getControllers()[0].getDriver().setPreviousDirection(Direction.UP);
		gt.getControllers()[0].getDriver().setDirection(Direction.UP);
		gt.getBoard()[50][9] = PointType.REVERSE_STEERING;
		gt.getBoard()[50][8] = PointType.UNTOUCHABLE;
		gt.getBoard()[50][7] = PointType.SPEED;
		gt.start();
		waitNumOfFrames(10);
		KeyEvent e = new KeyEvent(gt.getPanel(), KeyEvent.KEY_PRESSED, 1, 0, KeyEvent.VK_SPACE,
				KeyEvent.CHAR_UNDEFINED);
		gt.getPanel().getKeyListeners()[0].keyPressed(e);
		assertFalse(gt.getControllers()[0].getDriver().getReverseSteering().isActive());
		assertFalse(gt.getControllers()[0].getDriver().getUntouchable().isActive());
		assertFalse(gt.getControllers()[0].getDriver().getSpeed().isActive());
	}

	@AfterClass
	public static void testSwitchMenu() {
		GameWindow gw = new GameWindow();
		gw.getTicker().getButtons().get(2).doClick();
		for (MenuButton mb : gw.getTicker().getButtons()) {
			assertFalse(mb.isVisible());
		}
		gw.getTicker().getButtons().get(2).doClick();
		for (MenuButton mb : gw.getTicker().getButtons()) {
			assertTrue(mb.isVisible());
		}
	}

	private void waitNumOfFrames(int n) {
		try {
			Thread.sleep(40 * n);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
