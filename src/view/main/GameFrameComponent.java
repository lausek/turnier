package view.main;

import view.FrameComponent;
import view.main.components.GameControl;

@SuppressWarnings("serial")
public class GameFrameComponent extends FrameComponent {
	
	protected GameControl gameControl;
	
	public GameFrameComponent(GameControl gameControl) {
		this.gameControl = gameControl;
	}
	
}
