package view.main.components;

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JLabel;

import view.BasicFrame;

@SuppressWarnings("serial")
public class GameTimer extends JLabel {
	
	private static final int DEFAULT_TIME = 60;
	
	private BasicFrame parent;
	private boolean running = false;
	private int seconds;

	public GameTimer(BasicFrame parent) {
		this.parent = parent;
		this.seconds = DEFAULT_TIME;
		
		new Timer(true).scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				if(running && 0 < seconds) {
					seconds--;				
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
	
	public void setRunning(boolean running) {
		this.running = running;
	}
	
	public boolean isRunning() {
		return this.running;
	}

	public void setTime(int secs) {
		this.seconds = secs;
	}

}
