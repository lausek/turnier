package view.main.components.game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JButton;

import view.FrameComponent;
import view.extern.BasicExternFrame;
import view.extern.BasicExternFrame.TurnierEvent;
import view.extern.FullscreenFrame;

import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import control.CountdownListener;
import control.Turnier;
import model.ScheduleItem;

@SuppressWarnings("serial")
public class Control extends FrameComponent implements ActionListener, ChangeListener {

	protected BasicExternFrame externFrame;
	protected FrameComponent selectedComponent;
	protected Timer gameTimer;
	protected JPanel inGameShortcuts, postGameShortcuts;
	protected JButton cmdTimer, cmdNext, cmdShootout, cmdOvertime;

	protected Turnier turnier;
	protected Overview overview;

	public Control(Turnier turnier) {
		this.turnier = turnier;
		this.externFrame = new FullscreenFrame();
		this.gameTimer = externFrame.getTimer();

		this.gameTimer.addCountdownListener(new CountdownListener(60) {
			@Override
			public void reached() {
				// TODO: interesting for mixer
				System.out.println("Letzte Minute!");
			}
		});

		this.gameTimer.addCountdownListener(new CountdownListener(0) {
			@Override
			public void reached() {
				cmdOvertime.setEnabled(turnier.getCurrentScheduleItem().getEventType().hasOvertime());
				cmdShootout.setEnabled(turnier.getCurrentScheduleItem().getEventType().hasShootout());
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
		cmdOvertime = new JButton("Overtime");
		cmdOvertime.addActionListener(this);
		cmdShootout = new JButton("Shootout");
		cmdShootout.addActionListener(this);
		postGameShortcuts.add(cmdNext);

		inGameShortcuts = new JPanel();
		cmdTimer = new JButton("Start");
		cmdTimer.addActionListener(this);
		inGameShortcuts.add(cmdTimer);

		add(inGameShortcuts, BorderLayout.SOUTH);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		overview = new Overview(this);
		tabbedPane.addTab("Overview", overview);
		tabbedPane.addTab("Schedule", new Schedule(this));
		tabbedPane.addTab("Players", new Players(this));
		tabbedPane.addTab("Preview", new Preview(this));
		tabbedPane.addTab("Mixer", new Mixer(this));

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
		} else if (evt.getSource() == cmdOvertime) {
			gameTimer.setTime(turnier.getCurrentScheduleItem().getEventType().getOvertimeInSeconds());
			switchShortcuts(postGameShortcuts, inGameShortcuts);
		} else if (evt.getSource() == cmdShootout) {

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
		ScheduleItem event = turnier.nextEvent();
		if (event != null) {

			if (event.getEventType().isGame()) {
				overview.addEvent(event);

				externFrame.addEvent(TurnierEvent.NEW_EVENT, event);
			}

			gameTimer.setTime(event.getEventType().getLengthInSeconds());
			cmdTimer.setText(!gameTimer.isRunning() ? "Start" : "Pause");
			switchShortcuts(postGameShortcuts, inGameShortcuts);
		}
	}

}
