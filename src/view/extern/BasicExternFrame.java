package view.extern;

import java.awt.BorderLayout;
import java.util.concurrent.PriorityBlockingQueue;

import javax.swing.JPanel;

import view.BasicFrame;
import view.extern.BasicExternFrame.TurnierEvent;
import view.main.components.game.Timer;

@SuppressWarnings("serial")
public abstract class BasicExternFrame extends BasicFrame implements Runnable {

	public enum TurnierEvent {
		START, NEW_EVENT, GOAL, END
	};

	protected PriorityBlockingQueue<EventParams> queue;
	protected Timer gameTimer;

	public BasicExternFrame() {
		this.queue = new PriorityBlockingQueue<>();

		setAlwaysOnTop(true);

		gameTimer = new Timer(this);

		JPanel contentPane = new JPanel();
		getContentPane().add(contentPane, BorderLayout.CENTER);

		new Thread(this).start();
	}

	public void addEvent(TurnierEvent evt) {
		addEvent(evt, null);
	}
	
	public void addEvent(TurnierEvent evt, Object params) {
		this.queue.add(new EventParams(evt, params));
	}

	public Timer getTimer() {
		return gameTimer;
	}

	@Override
	public void run() {
		EventParams next;

		for (;;) {
			if ((next = queue.poll()) == null) {
				continue;
			}

			handle(next);
		}

	}

	protected abstract void handle(EventParams evt);

}

class EventParams implements Comparable<EventParams> {
	
	public TurnierEvent type;
	public Object params;
	
	public EventParams(TurnierEvent type, Object params) {
		this.type = type;
		this.params = params;
	}

	@Override
	public int compareTo(EventParams other) {
		// TODO: Add priority
		return 0;
	}
	
}
