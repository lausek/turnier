package view.extern;

import view.extern.components.GameInfo;

@SuppressWarnings("serial")
public class FullscreenFrame extends BasicExternFrame implements Runnable {

	public FullscreenFrame() {
		switchTo(new GameInfo());
	}
	
	@Override
	protected void handle(TurnierEvent evt) {
		switch (evt) {
		case START:
			break;

		case END:
			synchronized (this) {
				setVisible(false);
			}
			return;
			
		}
	}
	
}
