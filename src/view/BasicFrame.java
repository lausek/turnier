package view;

import javax.swing.JFrame;

import control.Control;

import java.awt.Container;

@SuppressWarnings("serial")
public class BasicFrame extends JFrame {
	
	protected Control control;
	protected FrameComponent current;

	public BasicFrame() {
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 600, 400);
	}
	
	public BasicFrame(Control control) {
		this();
		this.control = control;
	}
	
	@Override
	@Deprecated
	public void setContentPane(Container arg0) { }
	
	public void switchTo(FrameComponent c) {
		
		if (current != null) {
			current.leave();
		}

		current = c;
		current.setFrame(this);

		current.enter();
		
		super.setContentPane(current);
		redraw();

	}
	
	public void redraw() {
		revalidate();
		repaint();
	}

	public Control getControl() {
		return this.control;
	}
	
}
