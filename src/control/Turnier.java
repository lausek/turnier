package control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.Schedule;
import model.ScheduleItem;
import model.Team;

public class Turnier {

	private DataProvider dataProvider;
	private String filepath;
	private int currentScheduleItem;
	
	public Turnier(String filepath) throws IOException, SQLException {
		this.filepath = filepath;
		this.dataProvider = DataProvider.newInstance(this);
		this.currentScheduleItem = -1;
	}
	
	public String getFilepath() {
		return this.filepath;
	}
	
	public int getCurrentScheduleItem() {
		return this.currentScheduleItem;
	}
	
	public ScheduleItem nextEvent() {
		return dataProvider.getSchedule().getItems().get(++currentScheduleItem);
	}
	
	public Team executeTeamSelect(String query) {
		Pattern pattern = Pattern.compile("(team|loser|winner|group)\\((\\S+)\\)((pos)\\((\\S+)\\))?");
		Matcher matcher = pattern.matcher(query);
		if (!matcher.find()) {
			return null;
		}

		String fun1 = matcher.group(1);
		String arg1 = matcher.group(2);
		String fun2 = "";
		String arg2 = "";
		
		try {
			fun2 = matcher.group(4);
			arg2 = matcher.group(5);			
		} catch(IndexOutOfBoundsException e) {
		}
		
		switch (fun1) {
		case "team":
			return this.dataProvider.getTeams().get(Integer.parseInt(arg1)-1);

		case "winner":
			break;

		case "loser":
			break;

		case "group":
			return this.dataProvider.getGroups().get(arg1).get(Integer.parseInt(arg2));

		}

		return null;
	}

	public Schedule getSchedule() {
		return this.dataProvider.getSchedule();
	}

}
