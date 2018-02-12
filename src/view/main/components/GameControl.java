package view.main.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JButton;

import view.FrameComponent;
import view.extern.BasicExternFrame;
import view.extern.FullscreenFrame;

import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import control.CountdownListener;
import control.Turnier;
import model.ScheduleItem;

@SuppressWarnings("serial")
public class GameControl extends FrameComponent implements ActionListener, ChangeListener {

	protected BasicExternFrame externFrame;
	protected FrameComponent selectedComponent;
	protected GameTimer gameTimer;
	protected JPanel inGameShortcuts, postGameShortcuts;
	protected JButton cmdTimer, cmdNext;

	protected Turnier turnier;
	protected ScheduleItem event;

	public GameControl(Turnier turnier) {
		this.turnier = turnier;
		this.externFrame = new FullscreenFrame();
		this.gameTimer = externFrame.getTimer();

		this.gameTimer.addCountdownListener(new CountdownListener(60) {
			@Override
			public void reached() {
				System.out.println("Letzte Minute!");
			}
		});

		this.gameTimer.addCountdownListener(new CountdownListener(0) {
			@Override
			public void reached() {
				switchShortcuts(inGameShortcuts, postGameShortcuts);
			}
		});

		build();
		nextEvent();
	}

	private void build() {
		setLayout(new BorderLayout(0, 0));

		postGameShortcuts = new JPanel();
		cmdNext = new JButton("Next");
		cmdNext.addActionListener(this);
		postGameShortcuts.add(cmdNext);

		inGameShortcuts = new JPanel();
		cmdTimer = new JButton("Start");
		cmdTimer.addActionListener(this);
		inGameShortcuts.add(cmdTimer);

		add(inGameShortcuts, BorderLayout.SOUTH);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.addTab("Schedule", new GameSchedule(this));
		tabbedPane.addTab("Players", new GamePlayers(this));
		tabbedPane.addTab("Preview", new GamePreview(this));
		tabbedPane.addTab("Mixer", new GameMixer(this));

		tabbedPane.addChangeListener(this);
		add(tabbedPane, BorderLayout.CENTER);
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == cmdTimer) {
			cmdTimer.setText(gameTimer.isRunning() ? "Start" : "Pause");
			gameTimer.setRunning(!gameTimer.isRunning());
			gameTimer.setForeground(gameTimer.isRunning() ? Color.BLACK : Color.RED);
		} else if (evt.getSource() == cmdNext) {
			nextEvent();
		}
	}

	@Override
	public void stateChanged(ChangeEvent evt) {
		JTabbedPane pane = (JTabbedPane) evt.getSource();

		if (selectedComponent != null) {
			selectedComponent.leave();
		}

		selectedComponent = (FrameComponent) pane.getSelectedComponent();
		selectedComponent.setFrame(frame);

		selectedComponent.enter();
		frame.redraw();
	}

	public BasicExternFrame getExternFrame() {
		return externFrame;
	}

	protected void switchShortcuts(JPanel old, JPanel next) {
		remove(old);
		add(next, BorderLayout.SOUTH);
		revalidate();
		repaint();
	}

	protected void nextEvent() {
		if (event != null && event.getEventType().isGame()) {
			// TODO: save game to database
		}

		event = turnier.nextEvent();
		gameTimer.setTime(event.getEventType().getLengthInSeconds());
		switchShortcuts(postGameShortcuts, inGameShortcuts);
	}

}
