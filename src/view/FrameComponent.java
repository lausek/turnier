package view;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class FrameComponent extends JPanel {
	
	protected BasicFrame frame;
	
	public void setFrame(BasicFrame frame) {
		this.frame = frame;
	}
	
	public void enter() { }
	
	public void leave() { }
	
}
