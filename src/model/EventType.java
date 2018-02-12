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
	
	@Override
	public String toString() {
		return getName();
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getLengthInSeconds() {
		return this.length;
	}
	
	public boolean isGame() {
		return this.isGame;
	}
	
	public boolean hasShootout() {
		return this.hasShootout;
	}
	
	public boolean hasOvertime() {
		return this.hasOvertime;
	}
	
	public int getOvertimeInSeconds() {
		return this.overtime;
	}
	
}
