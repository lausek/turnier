package view.main;

import view.BasicFrame;
import control.Control;

@SuppressWarnings("serial")
public class ControlFrame extends BasicFrame {

	private Control control;

	public ControlFrame(Control control) {
		this.control = control;
	}
	
	public Control getControl() {
		return this.control;
	}

}