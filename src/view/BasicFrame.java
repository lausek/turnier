package view;

import javax.swing.JFrame;

import java.awt.Container;

@SuppressWarnings("serial")
public class BasicFrame extends JFrame {
	
	private FrameComponent current;

	public BasicFrame() {
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 600, 400);
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

}
