package view.main.components.game;

import java.util.List;
import java.util.TimerTask;

import javax.swing.JLabel;

import control.CountdownListener;
import view.BasicFrame;

@SuppressWarnings("serial")
public class Timer extends JLabel {

	private static final int DEFAULT_TIME = 3;

	private BasicFrame parent;
	private boolean running = false;
	private int seconds;

	private List<CountdownListener> listeners = new java.util.ArrayList<>(2);

	public Timer(BasicFrame parent) {
		this.parent = parent;
		this.seconds = DEFAULT_TIME;

		new java.util.Timer(true).scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				if (running && 0 < seconds) {
					seconds--;
					listeners.forEach(l -> {
						if (l.toReach == seconds) {
							l.reached();
						}
					});
				} else if(seconds == 0) {
					setRunning(false);
				}
				update();
			}
		}, 0, 1000);
	}

	public void update() {
		String out = "";
		int minutes = this.seconds / 60;
		if (0 < minutes) {
			out += minutes + ":";
		}

		out += String.format("%02d", this.seconds % 60);

		setText(out);
		parent.revalidate();
	}

	public void addCountdownListener(CountdownListener listener) {
		listeners.add(listener);
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public boolean isRunning() {
		return this.running;
	}

	public void setTime(int secs) {
//		TODO: comment in!!
//		this.seconds = secs;
		this.seconds = 3;
		setRunning(false);
	}

}
