package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;

import control.DataProvider;

public class ScheduleItem {

	private EventType type;
	private LocalTime start, end;
	private String note, homeQuery, guestQuery;
	private RankingItem home, guest;

	public ScheduleItem(EventType type, ResultSet result) throws SQLException {
		this.type = type;
		this.start = LocalTime.parse(result.getString("start"));
		this.end = LocalTime.parse(result.getString("end"));
		this.note = result.getString("note");
		this.homeQuery = result.getString("home");
		this.guestQuery = result.getString("guest");

		update();
	}

	public RankingItem getHomeTeam() {
		return DataProvider.get().getTurnier().executeTeamSelect(homeQuery);
	}

	public RankingItem getGuestTeam() {
		return DataProvider.get().getTurnier().executeTeamSelect(guestQuery);
	}

	public Object[] getCells() {
		Object[] arr = new Object[7];

		arr[0] = type;

		// TODO: don't hardcode this
		if (arr[0].equals("Gruppenspiel")) {
			arr[1] = home.getTeam().getGroup();
		}

		arr[2] = start.toString();
		arr[3] = end.toString();
		arr[4] = note;

		if (type.isGame()) {
			arr[5] = home.getTeam().toString();
			arr[6] = guest.getTeam().toString();
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