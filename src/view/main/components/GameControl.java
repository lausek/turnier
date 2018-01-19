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

import control.Turnier;

@SuppressWarnings("serial")
public class GameControl extends FrameComponent implements ActionListener, ChangeListener {

	protected BasicExternFrame externFrame;
	protected FrameComponent selectedComponent;
	protected GameTimer gameTimer;
	protected JButton cmdTimer;

	protected Turnier turnier;

	public GameControl(Turnier turnier) {
		this.turnier = turnier;
		this.externFrame = new FullscreenFrame();
		this.gameTimer = externFrame.getTimer();
		
		build();
	}
	
	private void build() {
		setLayout(new BorderLayout(0, 0));
		JPanel panel = new JPanel();
		add(panel, BorderLayout.SOUTH);

		cmdTimer = new JButton("Start");
		cmdTimer.addActionListener(this);
		panel.add(cmdTimer);
			
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
		}
	}

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
	
	public BasicExternFrame getExternFrame() {
		return externFrame;
	}

}
