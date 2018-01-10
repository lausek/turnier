package control;

import view.main.ControlFrame;
import view.main.components.ControlStart;
import view.main.components.GameControl;

public class Control {
	
	private ControlFrame cframe;
	private Turnier turnier;
	
	public static void main(String[] args) {
		new Control();
	}

	public Control() {
		cframe = new ControlFrame(this);
		cframe.switchTo(new ControlStart());
//		cframe.switchTo(new GameControl());
	}
	
	public void setTurnier(Turnier turnier) {
		this.turnier = turnier;
	}
	
}
