package tst;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

class GameLoop extends JFrame {


	final static int boardSize = 100;
	final static int resolution = (boardSize) * 10;
	final int fps = 25;
	PointType[][] board = new PointType[boardSize][boardSize];
	DriverControl[] drivers = {
			new DriverControl(new Driver(new Point(boardSize / 2, 10), Driver.Direction.DOWN), KeyEvent.VK_UP, KeyEvent.VK_DOWN,
					KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT),
			new DriverControl(new Driver(new Point(boardSize / 2, boardSize - 10), Driver.Direction.UP), KeyEvent.VK_W,
					KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D) };
	static JLayeredPane panel = new JLayeredPane();
	Timer ticker = new Timer(1000 / fps, (e) -> refresh());
	int timeBetweenBonus = 1500;
	Timer tickerBonus = new Timer(timeBetweenBonus, (e) -> spawnBonus());
	static ArrayList<Bonus> bonuses = new ArrayList<Bonus>();
	static ArrayList<MenuButton> buttons = new ArrayList<MenuButton>();
	static String background = "bg2.png";
	static boolean bonusesActive = true;

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new GameLoop().setUpWindow());
	}

	private void setUpWindow() {
		this.setTitle("TRONopodobne");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		createMenu();
		GameLoop.createBackground();
		GameLoop.panel.setPreferredSize(new Dimension(resolution, resolution));
		this.add(GameLoop.panel);
		this.pack();
	}

	private void createMenu() {
		new MenuButton("Start", this, (e) -> MenuButton.startGame(ticker, tickerBonus, drivers));
		MenuButton p = new MenuButton("Wy��cz bonus", this, (e) -> {
		});
		p.addActionListener((e) -> p.switchBonus());
		new MenuButton("Kolory graczy", this, (e) -> MenuButton.switchMenuColor());
		new MenuButton("Zmie� t�o", this, (e) -> MenuButton.switchBg());
		new MenuButton("Wyj�cie", this, (e) -> System.exit(0));
		new MenuColor(drivers, this);
	}

	private void refresh() {
		for (DriverControl dc : drivers) {
			tickBonus(dc.d.reverseSteering);
			tickBonus(dc.d.untouchable);
			tickBonus(dc.d.speed);
			for (int i = dc.d.speed.active ? 2 : 1; i > 0; --i) {
				board[dc.d.location.x][dc.d.location.y] = PointType.LINE;
				draw(dc.d.location, dc.adjustTraceColor(), new Integer(3));
				dc.drive();
				draw(dc.d.location, dc.adjustDriver(), new Integer(2));
				checkBoardPoint(dc);
			}
		}
	}

	private void returnToMenu() {
		GameLoop.createBackground();
		clearBoard();
		deactivateBonuses();
		setDriversOnStartingPositions();
		for (JButton b : GameLoop.buttons) {
			b.setVisible(true);
		}
		GameLoop.panel.addKeyListener(drivers[0]);
		GameLoop.panel.addKeyListener(drivers[1]);
	}

	private void createEndgameScreen() {
		GameLoop.panel.removeKeyListener(drivers[0]);
		GameLoop.panel.removeKeyListener(drivers[1]);
		JLabel continuation = new JLabel("Naci�nij spacj� by kontynuowa�", SwingConstants.CENTER);
		continuation.setForeground(Color.WHITE);
		continuation.setFont(new Font("Arial", Font.PLAIN, 50));
		continuation.setBounds(0, 0, resolution, resolution);
		GameLoop.panel.add(continuation, new Integer(4));
		GameLoop.panel.addKeyListener(createSpaceListener(continuation));
	}

	private void spawnBonus() {
		int t = ThreadLocalRandom.current().nextInt(1, 4);
		int x = ThreadLocalRandom.current().nextInt(1, GameLoop.boardSize - 1);
		int y = ThreadLocalRandom.current().nextInt(1, GameLoop.boardSize - 1);
		switch (t) {
		case 1:
			new Bonus(new Point(x, y), board, PointType.REVERSE_STEERING);
			break;
		case 2:
			new Bonus(new Point(x, y), board, PointType.UNTOUCHABLE);
			break;
		case 3:
			new Bonus(new Point(x, y), board, PointType.SPEED);
			break;
		}
	}

	private void checkBoardPoint(DriverControl dc) {
		if (collison(dc.d)) {
			ticker.stop();
			tickerBonus.stop();
			createEndgameScreen();
		} else if (isOnReverseSteeringBonus(dc)) {
			activateBonus(dc.d.reverseSteering, 75, dc.d.location);
		} else if (isOnUntouchableBonus(dc)) {
			activateBonus(dc.d.untouchable, 75, dc.d.location);
		} else if (isOnSpeedBonus(dc)) {
			activateBonus(dc.d.speed, 15, dc.d.location);
		}
	}

	static void createBackground() {
		GameLoop.panel.removeAll();
		JLabel lBg = new JLabel(new ImageIcon(background));
		lBg.setBounds(0, 0, resolution, resolution);
		GameLoop.panel.add(lBg, new Integer(0));
	}

	private void activateBonus(Driver.BonusTimer bt, int time, Point p) {
		bt.active = true;
		bt.time = time;
		Bonus.removeBonus(p, board);
	}

	// Przenie�� do DriverControl lub Driver
	private void tickBonus(Driver.BonusTimer bt) {
		if (bt.time > 0) {
			bt.time -= 1;
			if (bt.time == 0) {
				bt.active = false;
			}
		}
	}

	// Sprawd� czy nie ma implementacji, w kt�rej tylko zoverridujesz
	private KeyListener createSpaceListener(JLabel continuation) {
		return new KeyListener() {
			public void keyTyped(KeyEvent e) {
			}

			public void keyPressed(KeyEvent e) {
				int keyCode = e.getKeyCode();
				if (keyCode == KeyEvent.VK_SPACE) {
					GameLoop.panel.remove(continuation);
					GameLoop.panel.removeKeyListener(this);
					returnToMenu();
				}
			}

			public void keyReleased(KeyEvent e) {

			}
		};
	}

	private void setDriversOnStartingPositions() {
		drivers[0].d.location.setLocation(boardSize / 2, 10);
		drivers[0].d.direction = Driver.Direction.DOWN;
		drivers[0].d.previousDirection = Driver.Direction.DOWN;
		drivers[1].d.location.setLocation(boardSize / 2, boardSize - 10);
		drivers[1].d.direction = Driver.Direction.UP;
		drivers[1].d.previousDirection = Driver.Direction.UP;
	}

	private void deactivateBonuses() {
		for (DriverControl s : drivers) {
			s.d.reverseSteering.active = false;
			s.d.untouchable.active = false;
			s.d.speed.active = false;
			s.d.reverseSteering.time = 0;
			s.d.untouchable.time = 0;
			s.d.speed.time = 0;
		}
	}

	private void clearBoard() {
		for (int i = 0; i < boardSize; ++i) {
			for (int j = 0; j < boardSize; ++j) {
				board[i][j] = PointType.NOTHING;
			}
		}
	}

	private void draw(Point p, ImageIcon ic, Integer i) {
		JLabel lImage = new JLabel(ic);
		lImage.setBounds(10 * p.x, 10 * p.y, 10, 10);
		GameLoop.panel.add(lImage, i);
	}

	private boolean isOnSpeedBonus(DriverControl dc) {
		return board[dc.d.location.x][dc.d.location.y] == PointType.SPEED
				|| board[dc.d.location.x][dc.d.location.y] == PointType.SPEED_ON_LINE;
	}

	private boolean isOnUntouchableBonus(DriverControl dc) {
		return board[dc.d.location.x][dc.d.location.y] == PointType.UNTOUCHABLE
				|| board[dc.d.location.x][dc.d.location.y] == PointType.UNTOUCHABLE_ON_LINE;
	}

	private boolean isOnReverseSteeringBonus(DriverControl dc) {
		return board[dc.d.location.x][dc.d.location.y] == PointType.REVERSE_STEERING
				|| board[dc.d.location.x][dc.d.location.y] == PointType.REVERSE_STEERING_ON_LINE;
	}

	private boolean collison(Driver p) {
		return p.location.x == boardSize || p.location.x == -1 || p.location.y == boardSize || p.location.y == -1
				|| (board[p.location.x][p.location.y] == PointType.LINE && !(p.untouchable.active));
	}
}