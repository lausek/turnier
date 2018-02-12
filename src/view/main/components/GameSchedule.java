package view.main.components;

import view.main.GameFrameComponent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import control.DataProvider;
import model.EventType;
import model.Schedule;

@SuppressWarnings("serial")
public class GameSchedule extends GameFrameComponent {

	private JTable scheduleTable;

	public GameSchedule(GameControl gameControl) {
		super(gameControl);
		setLayout(new BorderLayout(0, 0));
		
		Object[] columnNames = { "Type", "Group", "Start", "End", "Note", "Home", "Guest" };
		Schedule schedule = gameControl.turnier.getSchedule();
		
		scheduleTable = new JTable(schedule.getCells(), columnNames);
		scheduleTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
					int row, int column) {
				Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				// TODO: EventType not always at column 0!
				EventType eventType = (EventType)table.getValueAt(row, 0);
				if (DataProvider.get().getTurnier().getCurrentScheduleItem() == row) {
					c.setBackground(Color.GREEN);
				} else if (eventType != null && !eventType.isGame()) {
					c.setBackground(Color.YELLOW);
				} else {
					c.setBackground(Color.WHITE);
				}
				return this;
			}
		});
		
		JScrollPane scrollPane = new JScrollPane(scheduleTable);
		scheduleTable.setFillsViewportHeight(true);
		
		add(scrollPane, BorderLayout.CENTER);
	}

}
