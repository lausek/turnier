package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;

import control.DataProvider;

public class ScheduleItem {
	
	private int id;
	private EventType type;
	private LocalTime start, end;
	private String note, homeQuery, guestQuery;
	private RankingItem home, guest;

	public ScheduleItem(EventType type, ResultSet result) throws SQLException {
		this.id = result.getInt("rowid");
		this.type = type;
		this.start = LocalTime.parse(result.getString("start"));
		this.end = LocalTime.parse(result.getString("end"));
		this.note = result.getString("note");
		this.homeQuery = result.getString("home");
		this.guestQuery = result.getString("guest");

		update();
	}
	
	public int getId() {
		return this.id;
	}
	
	public RankingItem getHomeTeam() {
		return DataProvider.get().getTurnier().executeTeamSelect(homeQuery);
	}

	public RankingItem getGuestTeam() {
		return DataProvider.get().getTurnier().executeTeamSelect(guestQuery);
	}

	public Object[] getCells() {
		Object[] arr = new Object[9];
		
		arr[0] = id;
		arr[1] = type;

		// TODO: don't hardcode this
		if (arr[1].equals("Gruppenspiel")) {
			arr[2] = home.getTeam().getGroup();
		}

		arr[3] = start.toString();
		arr[4] = end.toString();
		arr[5] = note;

		if (type.isGame()) {
			arr[6] = home.getTeam().toString();
			arr[7] = guest.getTeam().toString();
			arr[8] = "0:0";
		}
		
		return arr;
	}

	public EventType getEventType() {
		return type;
	}

	public boolean update() {
		if (type.isGame()) {
			this.home = getHomeTeam();
			this.guest = getGuestTeam();
		}
		return false;
	}

}