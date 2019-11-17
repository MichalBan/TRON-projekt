package elka.PROZ.TRONopodobne.controller;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.Timer;

import elka.PROZ.TRONopodobne.model.Bonus;
import elka.PROZ.TRONopodobne.model.BonusTimer;
import elka.PROZ.TRONopodobne.model.Direction;
import elka.PROZ.TRONopodobne.model.Driver;
import elka.PROZ.TRONopodobne.model.PointType;
import elka.PROZ.TRONopodobne.view.MenuButton;
import elka.PROZ.TRONopodobne.view.MyPanel;

/**
 * Represents game loop. Calls game tick method periodicly during game.
 */
public class GameTicker extends Timer {
	private MyPanel panel;
	private final int fps;
	private ArrayList<Bonus> bonuses = new ArrayList<Bonus>();
	private ArrayList<MenuButton> buttons = new ArrayList<MenuButton>();
	private boolean bonusesActive;
	private int boardSize;
	private PointType[][] board;
	private final int timeBetweenBonus;
	/**
	 * separate imer for spawning bonuses. Can be disabled
	 */
	private Timer tickerBonus;
	private DriverController[] controllers;
	private DriverLocationMonitor DLMonitor;

	public GameTicker(MyPanel panel) {
		super(0, (e) -> {
		});
		fps = 25;
		timeBetweenBonus = 1500;
		bonusesActive = true;
		this.setDelay(1000 / fps);
		this.panel = panel;
		boardSize = panel.getResolution() / 10;
		createClearBoard();
		createControllers();
		tickerBonus = new Timer(timeBetweenBonus, (e) -> BonusSpawner.createBonus(this));
		DLMonitor = new DriverLocationMonitor(this);
	}

	private void createClearBoard() {
		this.addActionListener((e) -> gameTick());
		board = new PointType[boardSize][boardSize];
		for (int i = 0; i < boardSize; ++i) {
			for (int j = 0; j < boardSize; ++j) {
				board[i][j] = PointType.NOTHING;
			}
		}
	}

	private void createControllers() {
		controllers = new DriverController[2];
		Driver d1 = new Driver(new Point(boardSize / 2, 10), Direction.DOWN);
		Driver d2 = new Driver(new Point(boardSize / 2, boardSize - 10), Direction.UP);
		controllers[0] = new DriverController(d1, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT,
				KeyEvent.VK_RIGHT);
		controllers[1] = new DriverController(d2, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D);
	}

	/**
	 * A single game tick of game loop. Is called for both driver controllers.
	 * Driver on speed moves twice, which means it moves twice as fast in game
	 */
	private void gameTick() {
		for (DriverController dc : controllers) {
			tickBonus(dc.getDriver().getReverseSteering());
			tickBonus(dc.getDriver().getUntouchable());
			tickBonus(dc.getDriver().getSpeed());
			for (int i = dc.getDriver().getSpeed().isActive() ? 2 : 1; i > 0; --i) {
				board[dc.getDriver().getLocation().x][dc.getDriver().getLocation().y] = PointType.LINE;
				panel.draw(dc.getDriver().getLocation(), dc.getTraceImage(), Integer.valueOf(3));
				dc.drive();
				panel.draw(dc.getDriver().getLocation(), dc.getDriverImage(), Integer.valueOf(2));
				DLMonitor.checkBoardPoint(dc);
			}
		}
	}

	/**
	 * if bonus is active its time goes down. If bonus time is over it gets
	 * deactivated. If no bonus is active nothing hapens
	 * 
	 * @param bt bonus timer updated
	 */
	private void tickBonus(BonusTimer bt) {
		if (bt.getTime() > 0) {
			bt.setTime(bt.getTime() - 1);
			if (bt.getTime() == 0) {
				bt.setActive(false);
			}
		}
	}

	public ArrayList<MenuButton> getButtons() {
		return buttons;
	}

	public boolean isBonusesActive() {
		return bonusesActive;
	}

	public void setBonusesActive(boolean b) {
		bonusesActive = b;
	}

	public DriverController[] getControllers() {
		return controllers;
	}

	public Timer getTickerBonus() {
		return tickerBonus;
	}

	public int getBoardSize() {
		return boardSize;
	}

	public void setBoard(PointType[][] board) {
		this.board = board;
	}

	public PointType[][] getBoard() {
		return board;
	}

	public ArrayList<Bonus> getBonuses() {
		return bonuses;
	}

	public MyPanel getPanel() {
		return panel;
	}
}