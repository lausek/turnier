package model;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalTime;
import java.util.List;

import control.DataProvider;

public class Schedule {

	private DataProvider dataProvider;
	private List<EventType> eventTypes;
	private List<ScheduleItem> items;

	public Schedule(DataProvider dataProvider) {
		this.dataProvider = dataProvider;
	}

	private List<ScheduleItem> getItems() {
		if (items == null) {
			items = new java.util.ArrayList<>();
			try {
				Statement stmt = dataProvider.getConnection().createStatement();
				ResultSet result = stmt.executeQuery("SELECT * FROM `Schedule`");
				while (result.next()) {
					EventType type = getEventTypes().get(result.getInt("eventtype_id"));
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
				Statement stmt = dataProvider.getConnection().createStatement();
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

class ScheduleItem {

	private EventType type;
	private LocalTime start, end;
	private String note, home, guest;

	public ScheduleItem(EventType type, ResultSet result) throws SQLException {
		this.type = type;
		this.start = LocalTime.parse(result.getString("start"));
		this.end = LocalTime.parse(result.getString("end"));
		this.note = result.getString("note");
		this.home = result.getString("home");
		this.guest = result.getString("guest");
	}

}
