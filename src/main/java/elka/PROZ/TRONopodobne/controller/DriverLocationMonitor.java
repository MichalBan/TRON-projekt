package elka.PROZ.TRONopodobne.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import elka.PROZ.TRONopodobne.model.BonusTimer;
import elka.PROZ.TRONopodobne.model.Direction;
import elka.PROZ.TRONopodobne.model.Driver;
import elka.PROZ.TRONopodobne.model.PointType;

/**
 * Checks what point type the driver is on and reacts accordingly
 */
public class DriverLocationMonitor {
	private GameTicker gt;

	public DriverLocationMonitor(GameTicker gt) {
		this.gt = gt;
	}

	/**
	 * Checks what is the driver on
	 * 
	 * @param dc Controller of the driver
	 */
	public void checkBoardPoint(DriverController dc) {
		if (isCollison(dc.getDriver())) {
			gt.stop();
			gt.getTickerBonus().stop();
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
	 * If driver is on bonus the Driver gets the effect and the bonus is removed
	 * 
	 * @param bt   bonus timer of the driver
	 * @param time how long will the bonus last
	 * @param p    location of bonus, needed for removal
	 */
	private void activateBonus(BonusTimer bt, int time, Point p) {
		bt.setActive(true);
		bt.setTime(time);
		BonusSpawner.removeBonus(p, gt);
	}

	/**
	 * If driver dies endgame screen is created. Drvier controllers stop listening
	 * to clicks.
	 * 
	 */
	private void createEndgameScreen() {
		gt.getPanel().removeKeyListener(gt.getControllers()[0]);
		gt.getPanel().removeKeyListener(gt.getControllers()[1]);
		JLabel continuation = new JLabel("Naciśnij spację by kontynuować", SwingConstants.CENTER);
		continuation.setForeground(Color.WHITE);
		continuation.setFont(new Font("Arial", Font.PLAIN, 50));
		continuation.setBounds(0, 0, gt.getBoardSize() * 10, gt.getBoardSize() * 10);
		gt.getPanel().add(continuation, Integer.valueOf(4));
		gt.getPanel().addKeyListener(createSpaceListener(continuation));
	}

	/**
	 * clicking space on endgame screen brings game back to main menu
	 * 
	 * @param continuation label with text to be removed
	 * @return returns the action listener
	 */
	private KeyListener createSpaceListener(final JLabel continuation) {
		return new KeyListener() {
			public void keyTyped(KeyEvent e) {
			}

			public void keyPressed(KeyEvent e) {
				int keyCode = e.getKeyCode();
				if (keyCode == KeyEvent.VK_SPACE) {
					gt.getPanel().remove(continuation);
					gt.getPanel().removeKeyListener(this);
					returnToMenu();
				}
			}

			public void keyReleased(KeyEvent e) {

			}
		};
	}

	/**
	 * Called when space is clicked in endgame screen. Returns to menu.
	 */
	private void returnToMenu() {
		gt.getPanel().createBackground();
		clearBoard();
		deactivateBonuses();
		setDriversOnStartingPositions();
		for (JButton b : gt.getButtons()) {
			b.setVisible(true);
		}
	}
	/**
	 * drivers are placed back on starting positions in preparation for next game
	 */
	private void setDriversOnStartingPositions() {
		gt.getControllers()[0].getDriver().setLocation(new Point(gt.getBoardSize() / 2, 10));
		gt.getControllers()[0].getDriver().setDirection(Direction.DOWN);
		gt.getControllers()[0].getDriver().setPreviousDirection(Direction.DOWN);
		gt.getControllers()[1].getDriver().setLocation(new Point(gt.getBoardSize() / 2, gt.getBoardSize() - 10));
		gt.getControllers()[1].getDriver().setDirection(Direction.UP);
		gt.getControllers()[1].getDriver().setPreviousDirection(Direction.UP);
	}
	/**
	 * all bonuses are disabled for next game
	 */
	private void deactivateBonuses() {
		for (DriverController s : gt.getControllers()) {
			s.getDriver().getReverseSteering().setActive(false);
			s.getDriver().getUntouchable().setActive(false);
			s.getDriver().getSpeed().setActive(false);
			s.getDriver().getReverseSteering().setTime(0);
			s.getDriver().getUntouchable().setTime(0);
			s.getDriver().getSpeed().setTime(0);
		}
	}
	/**
	 * board is cleared of lines and bonuses
	 */
	private void clearBoard() {
		for (int i = 0; i < gt.getBoardSize(); ++i) {
			for (int j = 0; j < gt.getBoardSize(); ++j) {
				gt.getBoard()[i][j] = PointType.NOTHING;
			}
		}
	}

	private boolean isOnSpeedBonus(DriverController dc) {
		return gt.getBoard()[dc.getDriver().getLocation().x][dc.getDriver().getLocation().y] == PointType.SPEED || gt
				.getBoard()[dc.getDriver().getLocation().x][dc.getDriver().getLocation().y] == PointType.SPEED_ON_LINE;
	}

	private boolean isOnUntouchableBonus(DriverController dc) {
		return gt.getBoard()[dc.getDriver().getLocation().x][dc.getDriver().getLocation().y] == PointType.UNTOUCHABLE
				|| gt.getBoard()[dc.getDriver().getLocation().x][dc.getDriver()
						.getLocation().y] == PointType.UNTOUCHABLE_ON_LINE;
	}

	private boolean isOnReverseSteeringBonus(DriverController dc) {
		return gt.getBoard()[dc.getDriver().getLocation().x][dc.getDriver()
				.getLocation().y] == PointType.REVERSE_STEERING
				|| gt.getBoard()[dc.getDriver().getLocation().x][dc.getDriver()
						.getLocation().y] == PointType.REVERSE_STEERING_ON_LINE;
	}

	private boolean isCollison(Driver p) {
		return p.getLocation().x == gt.getBoardSize() || p.getLocation().x == -1
				|| p.getLocation().y == gt.getBoardSize() || p.getLocation().y == -1
				|| (gt.getBoard()[p.getLocation().x][p.getLocation().y] == PointType.UNTOUCHABLE_ON_LINE
						&& !(p.getUntouchable().isActive()))
				|| (gt.getBoard()[p.getLocation().x][p.getLocation().y] == PointType.REVERSE_STEERING_ON_LINE
						&& !(p.getUntouchable().isActive()))
				|| (gt.getBoard()[p.getLocation().x][p.getLocation().y] == PointType.SPEED_ON_LINE
						&& !(p.getUntouchable().isActive()))
				|| (gt.getBoard()[p.getLocation().x][p.getLocation().y] == PointType.LINE
						&& !(p.getUntouchable().isActive()));
	}
}
