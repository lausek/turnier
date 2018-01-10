package control;

import view.ControlFrame;
import view.components.ControlStart;
import view.components.GameControl;

public class Control {
	
	private ControlFrame cframe;
	
	public static void main(String[] args) {
		new Control();
	}

	public Control() {
		cframe = new ControlFrame(this);
//		cframe.switchTo(new ControlStart());
		cframe.switchTo(new GameControl());
	}
	
}
