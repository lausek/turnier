package view.main;

import view.FrameComponent;
import view.main.components.game.Control;

@SuppressWarnings("serial")
public class GameFrameComponent extends FrameComponent {
	
	protected Control gameControl;
	
	public GameFrameComponent(Control gameControl) {
		this.gameControl = gameControl;
	}
	
}
