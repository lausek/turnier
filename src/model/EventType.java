package model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EventType {
	
	private int id, length, overtime;
	private String name;
	private boolean isGame, hasShootout, hasOvertime;
	
	public EventType(ResultSet base) throws SQLException {
		this.id = base.getInt("rowid");
		this.length = base.getInt("length");
		this.name = base.getString("name");
		this.isGame = base.getBoolean("is_game");
		this.hasShootout = base.getBoolean("has_shootout");
		this.hasOvertime = base.getBoolean("has_overtime");
		this.overtime = base.getInt("overtime");
	}
	
}
