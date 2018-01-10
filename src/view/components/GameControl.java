package view.components;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JButton;

import view.extern.BasicScreen;
import view.extern.FullScreen;

import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

@SuppressWarnings("serial")
public class GameControl extends ControlComponent implements ActionListener {

	protected BasicScreen fscreen;
	protected ControlComponent selectedComponent;
	protected GameTimer gameTimer;
	protected JButton cmdTimer;

	public GameControl() {
		setLayout(new BorderLayout(0, 0));

		fscreen = new FullScreen();

		JPanel panel = new JPanel();
		add(panel, BorderLayout.SOUTH);

		gameTimer = fscreen.getTimer();

		cmdTimer = new JButton("Start");
		cmdTimer.addActionListener(this);
		panel.add(cmdTimer);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.addTab("Schedule", new GameSchedule());
		tabbedPane.addTab("Preview", new GamePreview(fscreen));
		tabbedPane.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent evt) {
				JTabbedPane pane = (JTabbedPane) evt.getSource();
				
				if(selectedComponent != null) {
					selectedComponent.leave();
				}
				
				selectedComponent = (ControlComponent) pane
						.getSelectedComponent();
				selectedComponent.setFrame(frame);
				
				selectedComponent.enter();
			}
		});
		add(tabbedPane, BorderLayout.CENTER);
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == cmdTimer) {
			cmdTimer.setText(gameTimer.isRunning() ? "Start" : "Pause");
			gameTimer.setRunning(!gameTimer.isRunning());
		}
	}

	public BasicScreen getScreen() {
		return fscreen;
	}

}
