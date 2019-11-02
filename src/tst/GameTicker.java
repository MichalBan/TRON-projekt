package tst;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

/**
 * Klasa odpowiedzialna za przebiego petli gry. Dziedziczy ona po klasie Timer i
 * z kazdym tyknieciem powtarza sie petla.
 */
public class GameTicker extends Timer {
	final int fps = 25;
	static ArrayList<Bonus> bonuses = new ArrayList<Bonus>();
	static ArrayList<MenuButton> buttons = new ArrayList<MenuButton>();
	static boolean bonusesActive = true;
	static int boardSize;
	PointType[][] board;
	int timeBetweenBonus = 1500;
	Timer tickerBonus;
	DriverController[] controllers = new DriverController[2];

	public GameTicker(int resolution) {
		super(0, (e) -> {
		});
		this.setDelay(1000 / fps);
		boardSize = resolution / 10;
		this.addActionListener((e) -> gameTick());
		board = new PointType[boardSize][boardSize];
		Driver d1 = new Driver(new Point(boardSize / 2, 10), Driver.Direction.DOWN);
		Driver d2 = new Driver(new Point(boardSize / 2, boardSize - 10), Driver.Direction.UP);
		controllers[0] = new DriverController(d1, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT,
				KeyEvent.VK_RIGHT);
		controllers[1] = new DriverController(d2, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D);
		tickerBonus = new Timer(timeBetweenBonus, (e) -> BonusSpawner.createBonusForBoard(board));
	}

	/**
	 * Pojedyncze przejsce petli gry. Jest ono powtarzane z tykaniem zegara az jeden
	 * z graczy przegra
	 */
	private void gameTick() {
		for (DriverController dc : controllers) {
			tickBonus(dc.getDriver().getReverseSteering());
			tickBonus(dc.getDriver().getUntouchable());
			tickBonus(dc.getDriver().getSpeed());
			for (int i = dc.getDriver().getSpeed().active ? 2 : 1; i > 0; --i) {
				board[dc.getDriver().getLocation().x][dc.getDriver().getLocation().y] = PointType.LINE;
				GameWindow.draw(dc.getDriver().getLocation(), dc.adjustTraceColor(), Integer.valueOf(3));
				dc.drive();
				GameWindow.draw(dc.getDriver().getLocation(), dc.adjustDriver(), Integer.valueOf(2));
				checkBoardPoint(dc);
			}
		}
	}

	/**
	 * metoda sprawdzajaca czy gracz wjechal w bonus lub linie i reagujaca
	 * odpowiednio
	 * 
	 * @param dc obiekt sterujacy danym graczem
	 */
	private void checkBoardPoint(DriverController dc) {
		if (isCollison(dc.getDriver())) {
			this.stop();
			tickerBonus.stop();
			createEndgameScreen();
		} else if (isOnReverseSteeringBonus(dc)) {
			activateBonus(dc.getDriver().getReverseSteering(), 75, dc.getDriver().getLocation());
		} else if (isOnUntouchableBonus(dc)) {
			activateBonus(dc.getDriver().getUntouchable(), 75, dc.getDriver().getLocation());
		} else if (isOnSpeedBonus(dc)) {
			activateBonus(dc.getDriver().getSpeed(), 15, dc.getDriver().getLocation());
		}
	}

	/**
	 * metoda tworzy ekran konca gry po przegranej ktoregos gracza
	 */
	private void createEndgameScreen() {
		GameWindow.panel.removeKeyListener(controllers[0]);
		GameWindow.panel.removeKeyListener(controllers[1]);
		JLabel continuation = new JLabel("Naciœnij spacjê by kontynuowaæ", SwingConstants.CENTER);
		continuation.setForeground(Color.WHITE);
		continuation.setFont(new Font("Arial", Font.PLAIN, 50));
		continuation.setBounds(0, 0, boardSize * 10, boardSize * 10);
		GameWindow.panel.add(continuation, Integer.valueOf(4));
		GameWindow.panel.addKeyListener(createSpaceListener(continuation));
	}

	/**
	 * metoda przechadzaca z ekranu gry do ekranu menu
	 */
	private void returnToMenu() {
		GameWindow.createBackground();
		clearBoard();
		deactivateBonuses();
		setDriversOnStartingPositions();
		for (JButton b : buttons) {
			b.setVisible(true);
		}
	}

	private void setDriversOnStartingPositions() {
		controllers[0].getDriver().setLocation(new Point(boardSize / 2, 10));
		controllers[0].getDriver().setDirection(Driver.Direction.DOWN);
		controllers[0].getDriver().setPreviousDirection(Driver.Direction.DOWN);
		controllers[1].getDriver().setLocation(new Point(boardSize / 2, boardSize - 10));
		controllers[1].getDriver().setDirection(Driver.Direction.UP);
		controllers[1].getDriver().setPreviousDirection(Driver.Direction.UP);
	}

	private void deactivateBonuses() {
		for (DriverController s : controllers) {
			s.getDriver().getReverseSteering().active = false;
			s.getDriver().getUntouchable().active = false;
			s.getDriver().getSpeed().active = false;
			s.getDriver().getReverseSteering().time = 0;
			s.getDriver().getUntouchable().time = 0;
			s.getDriver().getSpeed().time = 0;
		}
	}

	private void tickBonus(Driver.BonusTimer bt) {
		if (bt.time > 0) {
			bt.time -= 1;
			if (bt.time == 0) {
				bt.active = false;
			}
		}
	}

	private void clearBoard() {
		for (int i = 0; i < boardSize; ++i) {
			for (int j = 0; j < boardSize; ++j) {
				board[i][j] = PointType.NOTHING;
			}
		}
	}

	private KeyListener createSpaceListener(JLabel continuation) {
		return new KeyListener() {
			public void keyTyped(KeyEvent e) {
			}

			public void keyPressed(KeyEvent e) {
				int keyCode = e.getKeyCode();
				if (keyCode == KeyEvent.VK_SPACE) {
					GameWindow.panel.remove(continuation);
					GameWindow.panel.removeKeyListener(this);
					returnToMenu();
				}
			}

			public void keyReleased(KeyEvent e) {

			}
		};
	}

	private void activateBonus(Driver.BonusTimer bt, int time, Point p) {
		bt.active = true;
		bt.time = time;
		Bonus.removeBonus(p, board);
	}

	private boolean isOnSpeedBonus(DriverController dc) {
		return board[dc.getDriver().getLocation().x][dc.getDriver().getLocation().y] == PointType.SPEED
				|| board[dc.getDriver().getLocation().x][dc.getDriver().getLocation().y] == PointType.SPEED_ON_LINE;
	}

	private boolean isOnUntouchableBonus(DriverController dc) {
		return board[dc.getDriver().getLocation().x][dc.getDriver().getLocation().y] == PointType.UNTOUCHABLE
				|| board[dc.getDriver().getLocation().x][dc.getDriver()
						.getLocation().y] == PointType.UNTOUCHABLE_ON_LINE;
	}

	private boolean isOnReverseSteeringBonus(DriverController dc) {
		return board[dc.getDriver().getLocation().x][dc.getDriver().getLocation().y] == PointType.REVERSE_STEERING
				|| board[dc.getDriver().getLocation().x][dc.getDriver()
						.getLocation().y] == PointType.REVERSE_STEERING_ON_LINE;
	}

	private boolean isCollison(Driver p) {
		return p.getLocation().x == boardSize || p.getLocation().x == -1 || p.getLocation().y == boardSize
				|| p.getLocation().y == -1
				|| (board[p.getLocation().x][p.getLocation().y] == PointType.UNTOUCHABLE_ON_LINE
						&& !(p.getUntouchable().active))
				|| (board[p.getLocation().x][p.getLocation().y] == PointType.REVERSE_STEERING_ON_LINE
						&& !(p.getUntouchable().active))
				|| (board[p.getLocation().x][p.getLocation().y] == PointType.SPEED_ON_LINE
						&& !(p.getUntouchable().active))
				|| (board[p.getLocation().x][p.getLocation().y] == PointType.LINE && !(p.getUntouchable().active));
	}
}
