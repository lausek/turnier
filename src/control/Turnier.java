package control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.Ranking;
import model.RankingItem;
import model.Schedule;
import model.ScheduleItem;

public class Turnier {

	private DataProvider dataProvider;
	private Ranking ranking;
	private String filepath;
	private ScheduleItem currentScheduleItem;
	private int currentScheduleIndex;

	public Turnier(String filepath) throws IOException, SQLException {
		this.filepath = filepath;
		this.dataProvider = DataProvider.newInstance(this);
		this.ranking = dataProvider.getInitialRanking();
		this.currentScheduleIndex = -1;
	}
	
	public String getFilepath() {
		return this.filepath;
	}
	
	public int getCurrentScheduleIndex() {
		return this.currentScheduleIndex;
	}
	
	public ScheduleItem getCurrentScheduleItem() {
		return this.currentScheduleItem;
	}
	
	private void saveGame() {
		if (currentScheduleItem != null && currentScheduleItem.getEventType().isGame()) {
			// TODO: change score here
			dataProvider.setGame(currentScheduleItem, 0, 0);
		}
	}
	
	public ScheduleItem nextEvent() {
		// TODO: save game here
		saveGame();
		
		if (currentScheduleIndex < dataProvider.getSchedule().getItems().size() - 1) {
			this.currentScheduleItem = dataProvider.getSchedule().getItems().get(++currentScheduleIndex);
			return currentScheduleItem;
		}
		return null;
	}

	public RankingItem executeTeamSelect(String query) {
		Pattern pattern = Pattern.compile("(team|loser|winner|group)\\((\\S+?)\\)((pos)\\((\\S+)\\))?");
		Matcher matcher = pattern.matcher(query);
		if (!matcher.find()) {
			return null;
		}

		System.out.println(query);

		String fun1 = matcher.group(1);
		String arg1 = matcher.group(2);
		String fun2 = "";
		String arg2 = "";

		try {
			fun2 = matcher.group(4);
			arg2 = matcher.group(5);
		} catch (IndexOutOfBoundsException e) {
		}

		switch (fun1) {
		case "team":
			return ranking.getTeam(Integer.parseInt(arg1));
		// return this.dataProvider.getTeams().get();

		case "winner":
			break;

		case "loser":
			break;

		case "group":
			return this.ranking.getGroups().get(arg1).get(Integer.parseInt(arg2));

		}

		return null;
	}

	public Schedule getSchedule() {
		return this.dataProvider.getSchedule();
	}

}
