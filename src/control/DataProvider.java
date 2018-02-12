package control;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import model.Ranking;
import model.Schedule;
import model.Team;

public class DataProvider extends DataActor {

	protected static DataProvider instance;

	private Turnier turnier;
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

		{
			ResultSet result = getConnection().createStatement().executeQuery("SELECT * FROM `Team`");
			while (result.next()) {
				teams.add(new Team(result.getInt(1), result.getString(2), result.getString(3)));
			}
		}

	}

	public Turnier getTurnier() {
		return turnier;
	}

	public Ranking getInitialRanking() {
		Ranking ranking = new Ranking() {};
		ResultSet result = null;

		try {
			result = getConnection().createStatement().executeQuery("SELECT * FROM `Group`");

			if (result != null && result.next()) {
				do {
					String groupID = result.getString(1);
					Team current = teams.get(result.getInt(2));

					ranking.add(groupID, current);
				} while (result.next());
			} else {
				ranking.add("0", teams);
			}

		} catch (SQLException | IOException e) {
			e.printStackTrace();
			ranking.add("0", teams);
		}

		return ranking;
	}

	public List<Team> getTeams() {
		return teams;
	}

	public Schedule getSchedule() {
		return schedule;
	}

}
