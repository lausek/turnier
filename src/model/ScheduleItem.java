package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;

import control.DataProvider;

public class ScheduleItem {

	private EventType type;
	private LocalTime start, end;
	private String note, homeQuery, guestQuery;
	private Team home, guest;

	public ScheduleItem(EventType type, ResultSet result) throws SQLException {
		this.type = type;
		this.start = LocalTime.parse(result.getString("start"));
		this.end = LocalTime.parse(result.getString("end"));
		this.note = result.getString("note");
		this.homeQuery = result.getString("home");
		this.guestQuery = result.getString("guest");

		update();
	}

	public Object[] getCells() {
		Object[] arr = new Object[7];

		arr[0] = type;
		
		// TODO: don't hardcode this
		if (arr[0].equals("Gruppenspiel")) {
			arr[1] = home.getGroup();
		}

		arr[2] = start.toString();
		arr[3] = end.toString();
		arr[4] = note;
		arr[5] = home.toString();
		arr[6] = guest.toString();
		return arr;
	}
	
	public EventType getEventType() {
		return type;
	}

	public boolean update() {
		this.home = DataProvider.get().getTurnier().executeTeamSelect(homeQuery);
		this.guest = DataProvider.get().getTurnier().executeTeamSelect(guestQuery);
		return false;
	}

}