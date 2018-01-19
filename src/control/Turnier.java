package control;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import model.EventType;
import model.Schedule;

public class Turnier {

	private DataProvider dataProvider;
	
	private Schedule schedule;
	
	public Turnier(String filepath) throws IOException {
		this.dataProvider = new DataProvider(filepath);
		this.schedule = new Schedule(dataProvider);
	}
	
	public Schedule getSchedule() {
		return this.schedule;
	}

}
