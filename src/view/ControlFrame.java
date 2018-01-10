package view;

import java.awt.Container;

import javax.swing.JFrame;

import view.components.ControlComponent;
import control.Control;

@SuppressWarnings("serial")
public class ControlFrame extends JFrame {

	private Control control;
	private ControlComponent current;

	public ControlFrame(Control control) {

		this.control = control;

		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 600, 400);
	}
	
	@Override
	@Deprecated
	public void setContentPane(Container arg0) { }
	
	public void switchTo(ControlComponent c) {
		
		if (current != null) {
			current.leave();
		}

		current = c;
		current.setFrame(this);

		current.enter();
		
		super.setContentPane(current);

	}

}
