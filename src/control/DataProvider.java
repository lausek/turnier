package control;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import model.Schedule;
import model.Team;

public class DataProvider extends DataActor {

	protected static DataProvider instance;
	
	private Turnier turnier;
	private HashMap<String, List<Team>> groups;
	private List<Team> teams;
	private Schedule schedule;
	
	public static void main(String[] args) throws IOException, SQLException {
		Turnier turnier = new Turnier("C:\\Users\\wn00086506\\Downloads\\turnier\\newcup095156.zip");
	}
	
	public static DataProvider newInstance(Turnier turnier) throws IOException, SQLException {
		instance = new DataProvider(turnier);
		return DataProvider.get();
	}

	public static DataProvider get() {
		return instance;
	}
	
	private DataProvider(Turnier turnier) throws IOException, SQLException {
		this.turnier = turnier;
		initializeSystem(turnier.getFilepath());
		
		this.schedule = new Schedule();
		this.teams = new java.util.ArrayList<>();
		this.groups = new HashMap<>();

		{
			ResultSet result = getConnection().createStatement().executeQuery("SELECT `name`, `logo` FROM `Team`");
			while (result.next()) {
				teams.add(new Team(result.getString(1), result.getString(2)));
			}
		}

		{
			ResultSet result = getConnection().createStatement().executeQuery("SELECT * FROM `Group`");
			if (result.next()) {
				do {
					String groupID = result.getString(1);
					if (!groups.containsKey(groupID)) {
						groups.put(groupID, new java.util.ArrayList<>());
					}

					Team current = teams.get(result.getInt(2));
					current.setGroup(groupID);
					groups.get(groupID).add(current);
				} while (result.next());
			} else {
				groups.put("0", teams);
			}
		}
		
	}

	public Turnier getTurnier() {
		return turnier;
	}
	
	public HashMap<String, List<Team>> getGroups() {
		return groups;
	}

	public List<Team> getTeams() {
		return teams;
	}

	public Schedule getSchedule() {
		return schedule;
	}

}
