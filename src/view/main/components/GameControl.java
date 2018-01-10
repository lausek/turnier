package view.main.components;

import java.awt.BorderLayout;
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

@SuppressWarnings("serial")
public class GameControl extends FrameComponent implements ActionListener {

	protected BasicExternFrame externFrame;
	protected FrameComponent selectedComponent;
	protected GameTimer gameTimer;
	protected JButton cmdTimer;

	public GameControl() {
		setLayout(new BorderLayout(0, 0));

		externFrame = new FullscreenFrame();

		JPanel panel = new JPanel();
		add(panel, BorderLayout.SOUTH);

		gameTimer = externFrame.getTimer();

		cmdTimer = new JButton("Start");
		cmdTimer.addActionListener(this);
		panel.add(cmdTimer);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.addTab("Schedule", new GameSchedule());
		tabbedPane.addTab("Preview", new GamePreview(externFrame));
		tabbedPane.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent evt) {
				JTabbedPane pane = (JTabbedPane) evt.getSource();
				
				if(selectedComponent != null) {
					selectedComponent.leave();
				}
				
				selectedComponent = (FrameComponent) pane
						.getSelectedComponent();
				selectedComponent.setFrame(frame);
				
				selectedComponent.enter();
				frame.redraw();
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

	public BasicExternFrame getScreen() {
		return externFrame;
	}

}
