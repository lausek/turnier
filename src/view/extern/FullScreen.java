package view.extern;

import java.awt.BorderLayout;

@SuppressWarnings("serial")
public class FullScreen extends BasicScreen {
	
	public FullScreen() {
		
		getContentPane().add(gameTimer, BorderLayout.NORTH);
		
	}
	
}
