package tst;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Test;

public class GameTests {
	
	@Test
	public void testAdjustTraceColor() {
		DriverController dc = new DriverController(new Driver(new Point(0, 0), Driver.Direction.LEFT), 10, 11, 12, 13);
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 4; ++j) {
				for (int k = 0; k < 4; ++k) {
					dc.getDriver().setDriverColor(Driver.DColor.values()[i]);
					dc.getDriver().setDirection(Driver.Direction.values()[j]);
					dc.getDriver().setPreviousDirection(Driver.Direction.values()[k]);
					assertNotNull(dc.adjustTraceColor());
				}
			}
		}
		try {
			dc.getDriver().setDriverColor(null);
			dc.adjustTraceColor();
		} catch (Exception e) {
			assertNotNull(e);
		}
	}

	@Test
	public void testDrive() {
		DriverController dc = new DriverController(new Driver(new Point(0, 0), Driver.Direction.LEFT), 10, 11, 12, 13);
		dc.drive();
		assertTrue(dc.getDriver().getLocation().x == -1);
	}

	@Test
	public void testAdjustDriver() {
		DriverController dc = new DriverController(new Driver(new Point(0, 0), Driver.Direction.LEFT), 10, 11, 12, 13);
		assertNotNull(dc.adjustDriver());
		dc.getDriver().setDriverColor(Driver.DColor.CYAN);
		assertNotNull(dc.adjustDriver());
		dc.getDriver().setDriverColor(Driver.DColor.RED);
		assertNotNull(dc.adjustDriver());
		try {
			dc.getDriver().setDriverColor(null);
			dc.adjustDriver();
		} catch (Exception e) {
			assertNotNull(e);
		}

	}

	@Test
	public void testChangeColor() {
		DriverController dc = new DriverController(new Driver(new Point(0, 0), Driver.Direction.LEFT), 10, 11, 12, 13);
		dc.getDriver().setDriverColor(Driver.DColor.CYAN);
		assertTrue(dc.getDriver().getDriverColor() == Driver.DColor.CYAN);
	}

	@Test
	public void testKeyListener() {
		DriverController dc = new DriverController(new Driver(new Point(0, 0), Driver.Direction.LEFT), 10, 11, 12, 13);
		KeyEvent e = new KeyEvent(new JFrame(), KeyEvent.KEY_PRESSED, 1, 0, 10, KeyEvent.CHAR_UNDEFINED);
		dc.keyPressed(e);
		dc.drive();
		assertTrue(dc.getDriver().getDirection() == Driver.Direction.UP);
		e = new KeyEvent(new JFrame(), KeyEvent.KEY_PRESSED, 1, 0, 12, KeyEvent.CHAR_UNDEFINED);
		dc.keyPressed(e);
		dc.drive();
		assertTrue(dc.getDriver().getDirection() == Driver.Direction.LEFT);
		e = new KeyEvent(new JFrame(), KeyEvent.KEY_PRESSED, 1, 0, 11, KeyEvent.CHAR_UNDEFINED);
		dc.keyPressed(e);
		dc.drive();
		assertTrue(dc.getDriver().getDirection() == Driver.Direction.DOWN);
		e = new KeyEvent(new JFrame(), KeyEvent.KEY_PRESSED, 1, 0, 13, KeyEvent.CHAR_UNDEFINED);
		dc.keyPressed(e);
		dc.drive();
		assertTrue(dc.getDriver().getDirection() == Driver.Direction.RIGHT);
	}

	@Test
	public void testBonus() {
		PointType[][] board = new PointType[5][5];
		board[3][3] = PointType.LINE;
		Point p = new Point(2, 2);
		new Bonus(p, board, PointType.REVERSE_STEERING);
	}

	@Test
	public void testRemoveBonus() {
		PointType[][] board = new PointType[5][5];
		board[3][3] = PointType.LINE;
		Point p = new Point(2, 2);
		new Bonus(p, board, PointType.REVERSE_STEERING);
		Bonus.removeBonus(p, board);
		for (PointType[] pa : board) {
			for (PointType poi : pa) {
				assertFalse(poi == PointType.REVERSE_STEERING || poi == PointType.REVERSE_STEERING_ON_LINE);
			}
		}
	}

	@Test
	public void testDrawBonus() {
		PointType[][] board = new PointType[5][5];
		board[3][3] = PointType.LINE;
		Point p = new Point(2, 2);
		new Bonus(p, board, PointType.REVERSE_STEERING);
		assertNotNull(new Bonus(p, board, PointType.REVERSE_STEERING).getIcon());
	}

	@Test
	public void testAdjustOnLinePoint() {
		PointType[][] board = new PointType[5][5];
		board[3][3] = PointType.LINE;
		Point p = new Point(2, 2);
		new Bonus(p, board, PointType.REVERSE_STEERING);
		assertTrue(new Bonus(p, board, PointType.REVERSE_STEERING)
				.getBoardOnLinePoint() == PointType.REVERSE_STEERING_ON_LINE);
		assertTrue(new Bonus(p, board, PointType.UNTOUCHABLE).getBoardOnLinePoint() == PointType.UNTOUCHABLE_ON_LINE);
		assertTrue(new Bonus(p, board, PointType.SPEED).getBoardOnLinePoint() == PointType.SPEED_ON_LINE);
		try {
			new Bonus(p, board, PointType.NOTHING);
		} catch (Exception e) {
			assertNotNull(e);
		}
	}

	@Test
	public void testPutOnBoard() {
		PointType[][] board = new PointType[5][5];
		board[3][3] = PointType.LINE;
		Point p = new Point(2, 2);
		new Bonus(p, board, PointType.REVERSE_STEERING);
		for (int i = -1; i < 2; ++i) {
			for (int j = -1; j < 2; ++j) {
				assertTrue(board[p.x + i][p.y + j] == PointType.REVERSE_STEERING
						|| board[p.x + i][p.y + j] == PointType.REVERSE_STEERING_ON_LINE);
			}
		}
	}

	@Test
	public void testBonusSpawner() {
		GameTicker gt = new GameTicker(30);
		BonusSpawner.createBonusForBoard(gt.board);
		for (PointType[] p : gt.board) {
			for (PointType pp : p) {
				assertNotNull(pp);
			}
		}
	}

	@Test
	public void testStartGame() {
		new MenuButton("", new JFrame(), (e) -> {
		});
		GameTicker g = new GameTicker(1000);
		MenuButton.startGame(g);
		for (MenuButton mb : GameTicker.buttons) {
			assertFalse(mb.isVisible());
		}
		assertNotNull(GameWindow.panel.getKeyListeners());
		assertTrue(g.isRunning());
	}

	@Test
	public void testSwitchBg() {
		MenuButton.switchBg();
		assertTrue(GameWindow.background == "bg3.jpg");
		MenuButton.switchBg();
		assertTrue(GameWindow.background == "bg.jpg");
		MenuButton.switchBg();
		assertTrue(GameWindow.background == "bg2.png");
	}

	@Test
	public void testSwitchBonus() {
		MenuButton mb = new MenuButton("", new JFrame(), (e) -> {
		});
		mb.switchBonus();
		assertFalse(GameTicker.bonusesActive);
		mb.switchBonus();
		assertTrue(GameTicker.bonusesActive);
	}

	@Test
	public void testMenuColor() {
		MenuColor m = new MenuColor(new DriverController[2], new JFrame(), new Point(0, 0));
		assertTrue(m.getColorButtons().size() == 6);
		assertNotNull(m.getDoneButton());
	}

	@Test
	public void testGameTick() {
		GameTicker gt = new GameTicker(1000);
		gt.start();
		try {
			Thread.sleep(40);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertFalse(gt.controllers[0].getDriver().getLocation().y == 10);
		assertFalse(gt.controllers[1].getDriver().getLocation().y == GameTicker.boardSize - 10);
		assertTrue(gt.board[GameTicker.boardSize / 2][10] == PointType.LINE);
	}

	@Test
	public void testActivateBonus() {
		GameTicker gt = new GameTicker(1000);
		gt.board[50][11] = PointType.REVERSE_STEERING;
		gt.board[50][12] = PointType.UNTOUCHABLE;
		gt.board[50][13] = PointType.SPEED;
		gt.start();
		waitNumOfFrames(3);
		assertTrue(gt.controllers[0].getDriver().getReverseSteering().active);
		assertTrue(gt.controllers[0].getDriver().getUntouchable().active);
		assertTrue(gt.controllers[0].getDriver().getSpeed().active);
	}

	@Test
	public void testTickBonus() {
		GameTicker gt = new GameTicker(1000);

		gt.board[50][11] = PointType.REVERSE_STEERING;
		gt.start();
		waitNumOfFrames(2);
		assertFalse(gt.controllers[0].getDriver().getReverseSteering().time == 75);
	}

	@Test
	public void testCollison() {
		GameTicker gt = new GameTicker(1000);
		gt.board[50][11] = PointType.REVERSE_STEERING_ON_LINE;
		gt.start();
		waitNumOfFrames(1);
		assertFalse(gt.isRunning());
		gt = new GameTicker(1000);
		gt.board[50][11] = PointType.UNTOUCHABLE_ON_LINE;
		gt.start();
		waitNumOfFrames(1);
		assertFalse(gt.isRunning());
		gt = new GameTicker(1000);
		gt.board[50][11] = PointType.SPEED_ON_LINE;
		gt.start();
		waitNumOfFrames(1);
		assertFalse(gt.isRunning());
		gt = new GameTicker(1000);
		gt.board[50][11] = PointType.LINE;
		gt.start();
		waitNumOfFrames(1);
		assertFalse(gt.isRunning());
	}

	@Test
	public void testUntouchableDetection() {
		GameTicker gt = new GameTicker(1000);
		gt.board[50][11] = PointType.UNTOUCHABLE;
		gt.board[50][12] = PointType.REVERSE_STEERING_ON_LINE;
		gt.start();
		waitNumOfFrames(2);
		assertTrue(gt.isRunning());
		gt.stop();
		gt = new GameTicker(1000);
		gt.board[50][11] = PointType.UNTOUCHABLE;
		gt.board[50][12] = PointType.UNTOUCHABLE_ON_LINE;
		gt.start();
		waitNumOfFrames(2);
		assertTrue(gt.isRunning());
		gt.stop();
		gt = new GameTicker(1000);
		gt.board[50][11] = PointType.UNTOUCHABLE;
		gt.board[50][12] = PointType.SPEED_ON_LINE;
		gt.start();
		waitNumOfFrames(2);
		assertTrue(gt.isRunning());
		gt.stop();
		gt = new GameTicker(1000);
		gt.board[50][11] = PointType.UNTOUCHABLE;
		gt.board[50][12] = PointType.LINE;
		gt.start();
		waitNumOfFrames(2);
		assertTrue(gt.isRunning());
		gt.stop();
	}
	
	@Test
	public void testCreateSpaceListener() {

		GameTicker gt = new GameTicker(1000);
		gt.board[50][11] = PointType.REVERSE_STEERING_ON_LINE;
		gt.start();
		waitNumOfFrames(1);
		assertTrue(GameWindow.panel.getKeyListeners().length == 1);
		KeyEvent e = new KeyEvent(GameWindow.panel, KeyEvent.KEY_PRESSED, 1, 0, KeyEvent.VK_SPACE, KeyEvent.CHAR_UNDEFINED);
		GameWindow.panel.getKeyListeners()[0].keyPressed(e);
		assertTrue(GameWindow.panel.getKeyListeners().length == 0);
	}
	
	@Test
	public void testReturnToMenu() {
		GameTicker gt = new GameTicker(1000);
		gt.board[50][11] = PointType.REVERSE_STEERING_ON_LINE;
		gt.start();
		waitNumOfFrames(1);
		KeyEvent e = new KeyEvent(GameWindow.panel, KeyEvent.KEY_PRESSED, 1, 0, KeyEvent.VK_SPACE, KeyEvent.CHAR_UNDEFINED);
		GameWindow.panel.getKeyListeners()[0].keyPressed(e);
		for(MenuButton b : GameTicker.buttons) {
			assertTrue(b.isVisible());
		}
	}
	
	@Test
	public void testSetDriversOnStartingPositions() {
		GameTicker gt = new GameTicker(1000);
		gt.board[50][11] = PointType.REVERSE_STEERING_ON_LINE;
		gt.start();
		waitNumOfFrames(1);
		KeyEvent e = new KeyEvent(GameWindow.panel, KeyEvent.KEY_PRESSED, 1, 0, KeyEvent.VK_SPACE, KeyEvent.CHAR_UNDEFINED);
		GameWindow.panel.getKeyListeners()[0].keyPressed(e);
		assertTrue(gt.controllers[0].getDriver().getLocation().x == GameTicker.boardSize / 2);
		assertTrue(gt.controllers[0].getDriver().getLocation().y == 10);
		assertTrue(gt.controllers[0].getDriver().getDirection() == Driver.Direction.DOWN);
		assertTrue(gt.controllers[0].getDriver().getPreviousDirection() == Driver.Direction.DOWN);
		assertTrue(gt.controllers[1].getDriver().getLocation().x == GameTicker.boardSize / 2);
		assertTrue(gt.controllers[1].getDriver().getLocation().y == GameTicker.boardSize - 10);
		assertTrue(gt.controllers[1].getDriver().getDirection() == Driver.Direction.UP);
		assertTrue(gt.controllers[1].getDriver().getPreviousDirection() == Driver.Direction.UP);
	}
	
	@Test
	public void testDeactivateBonuses() {
		GameTicker gt = new GameTicker(1000);
		gt.controllers[0].getDriver().setPreviousDirection(Driver.Direction.UP);
		gt.controllers[0].getDriver().setDirection(Driver.Direction.UP);
		gt.board[50][9] = PointType.REVERSE_STEERING;
		gt.board[50][8] = PointType.UNTOUCHABLE;
		gt.board[50][7] = PointType.SPEED;
		gt.start();
		waitNumOfFrames(10);
		KeyEvent e = new KeyEvent(GameWindow.panel, KeyEvent.KEY_PRESSED, 1, 0, KeyEvent.VK_SPACE, KeyEvent.CHAR_UNDEFINED);
		GameWindow.panel.getKeyListeners()[0].keyPressed(e);
		assertFalse(gt.controllers[0].getDriver().getReverseSteering().active);
		assertFalse(gt.controllers[0].getDriver().getUntouchable().active);
		assertFalse(gt.controllers[0].getDriver().getSpeed().active);
	}
	
	@AfterClass
	public static void testSwitchMenu() {
		new GameWindow();
		GameTicker.buttons.get(2).doClick();
		for(MenuButton mb : GameTicker.buttons) {
			assertFalse(mb.isVisible());
		}
		GameTicker.buttons.get(2).doClick();
		for(MenuButton mb : GameTicker.buttons) {
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

	@After
	public void tearDown() {
		GameTicker.buttons.clear();
		GameTicker.bonuses.clear();
		GameWindow.panel.removeAll();
		for(KeyListener kl : GameWindow.panel.getKeyListeners()) {
			GameWindow.panel.removeKeyListener(kl);
		}
	}
}
