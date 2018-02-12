package model;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import control.DataProvider;

public class Schedule {

	private List<EventType> eventTypes;
	private List<ScheduleItem> items;

	public Object[][] getCells() {
		List<ScheduleItem> items = getItems();
		Object[][] buffer = new Object[items.size()][];
		for (int i = 0; i < buffer.length; i++) {
			buffer[i] = items.get(i).getCells();
		}
		return buffer;
	}

	public List<ScheduleItem> getItems() {
		if (items == null) {
			items = new java.util.ArrayList<>();
			try {
				Statement stmt = DataProvider.get().getConnection().createStatement();
				ResultSet result = stmt.executeQuery("SELECT * FROM `Schedule`");
				while (result.next()) {
					EventType type = getEventTypes().get(result.getInt("eventtype_id")-1);
					items.add(new ScheduleItem(type, result));
				}
			} catch (SQLException | IOException e) {
				e.printStackTrace();
			}
		}
		return items;
	}

	private List<EventType> getEventTypes() {
		if (eventTypes == null) {
			eventTypes = new java.util.ArrayList<>();
			try {
				Statement stmt = DataProvider.get().getConnection().createStatement();
				ResultSet result = stmt.executeQuery("SELECT * FROM `EventType`");
				while (result.next()) {
					eventTypes.add(new EventType(result));
				}
			} catch (SQLException | IOException e) {
				e.printStackTrace();
			}
		}
		return eventTypes;
	}

}
