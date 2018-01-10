package view.extern;

import java.util.concurrent.PriorityBlockingQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import view.components.GameTimer;
import java.awt.BorderLayout;

@SuppressWarnings("serial")
public class BasicScreen extends JFrame implements Runnable {

	public enum TurnierEvent {
		START, END
	};

	protected PriorityBlockingQueue<TurnierEvent> queue;
	protected JPanel panel;
	protected GameTimer gameTimer;
	
	public BasicScreen() {
		this.queue = new PriorityBlockingQueue<>();

		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setAlwaysOnTop(true);
		setBounds(0, 0, 800, 600);
		
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

			switch (next) {
			case START:
				break;

			case END:
				synchronized (this) {
					setVisible(false);
				}
				return;

			default:
				break;
			}
		}

	}

}
