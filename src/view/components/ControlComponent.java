package view.components;

import javax.swing.JPanel;

import view.ControlFrame;

@SuppressWarnings("serial")
public class ControlComponent extends JPanel {
	
	protected ControlFrame frame;
	
	public void setFrame(ControlFrame frame) {
		this.frame = frame;
	}
	
	public void enter() { }
	
	public void leave() { }
	
}
