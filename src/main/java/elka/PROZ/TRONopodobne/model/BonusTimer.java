package elka.PROZ.TRONopodobne.model;
/**
 *contains information about active bonus efect, used by Driver
 */
public class BonusTimer {

	private boolean active;
	private int time;

	public BonusTimer(boolean a, int t) {
		active = a;
		time = t;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}
}
