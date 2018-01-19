package view.main.components;

import view.main.GameFrameComponent;
import java.awt.BorderLayout;
import javax.swing.JTable;

@SuppressWarnings("serial")
public class GameSchedule extends GameFrameComponent {

	private JTable scheduleTable;

	public GameSchedule(GameControl gameControl) {
		super(gameControl);
		setLayout(new BorderLayout(0, 0));
		
//		Object[] columnNames = { "Events" };
//		Object[][] schedule = gameControl.turnier.getSchedule();
		
//		scheduleTable = new JTable(schedule, columnNames);
		add(scheduleTable, BorderLayout.CENTER);
	}

}
