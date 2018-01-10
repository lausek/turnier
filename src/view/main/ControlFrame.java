package view.main;
import javax.swing.JFrame;

import view.BasicFrame;
import control.Control;

@SuppressWarnings("serial")
public class ControlFrame extends BasicFrame {

	private Control control;

	public ControlFrame(Control control) {

		this.control = control;

		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 600, 400);
	}

}
