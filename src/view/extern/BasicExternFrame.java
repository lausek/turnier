package view.extern;

import java.awt.BorderLayout;
import java.util.concurrent.PriorityBlockingQueue;

import javax.swing.JPanel;

import view.BasicFrame;
import view.main.components.GameTimer;

@SuppressWarnings("serial")
public abstract class BasicExternFrame extends BasicFrame implements Runnable {

	public enum TurnierEvent {
		START, END
	};

	protected PriorityBlockingQueue<TurnierEvent> queue;
	protected GameTimer gameTimer;

	public BasicExternFrame() {
		this.queue = new PriorityBlockingQueue<>();

		setAlwaysOnTop(true);

		gameTimer = new GameTimer(this);

		JPanel contentPane = new JPanel();
		getContentPane().add(contentPane, BorderLayout.CENTER);

		new Thread(this).start();
	}

	public void addEvent(TurnierEvent evt) {
		this.queue.add(evt);
	}

	public GameTimer getTimer() {
		return gameTimer;
	}

	@Override
	public void run() {
		TurnierEvent next;

		for (;;) {
			if ((next = queue.poll()) == null) {
				continue;
			}

			handle(next);
		}

	}

	protected abstract void handle(TurnierEvent evt);

}
