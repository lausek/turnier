package control;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.zip.ZipOutputStream;

public class DataInitializer extends DataActor {

	public DataInitializer(String filePath) throws IOException {
		Path zip = Paths.get(filePath);

		if (!Files.isRegularFile(zip)) {
			Files.createFile(zip);
		}

		// initialize zip file
		new ZipOutputStream(new FileOutputStream(zip.toString())).close();

		initializeSystem(filePath);

		try {
			initializeDatabase();
		} catch (SQLException | IOException e) {
			e.printStackTrace();
			throw new IOException("Tables couldn't be created");
		}
	}

	private void execute(String sql) throws SQLException, IOException {
		getConnection().createStatement().execute(sql);
	}

	private String toHexString(byte[] arr) {
		String result = "";
		for (int i = 0; i < arr.length; i++) {
			result += Integer.toHexString(arr[i]);
		}
		return result;
	}

	protected void addTeam(String name, String logoPath) throws SQLException, IOException, NoSuchAlgorithmException {
		Path logo = Paths.get(logoPath);
		MessageDigest imgHash = MessageDigest.getInstance("SHA");
		imgHash.update(Files.readAllBytes(logo));

		// TODO: don't hardcode extension
		String filename = toHexString(imgHash.digest()) + ".png";
		Path packedFile = fs.getPath("/images/" + filename);
		Files.copy(logo, packedFile);

		PreparedStatement stmt = getConnection().prepareStatement("INSERT INTO `Team` (`name`, `logo`) VALUES (?, ?)");
		stmt.setString(1, name);
		stmt.setString(2, filename);
		stmt.executeUpdate();
	}

	protected void addEvent(String name, int length, boolean isGame, boolean hasShootout, boolean hasOvertime, int overtime) throws SQLException, IOException {

		PreparedStatement stmt = getConnection()
				.prepareStatement("INSERT INTO `EventType` (`name`, `length`, `is_game`, `has_shootout`, `has_overtime`, `overtime`) VALUES (?,?,?,?,?,?)");
		
		stmt.setString(1, name);
		stmt.setInt(2, length);
		stmt.setBoolean(3, isGame);
		stmt.setBoolean(4, hasShootout);
		stmt.setBoolean(5, hasOvertime);
		stmt.setInt(6, overtime);

		stmt.executeUpdate();
		
	}
	
	protected void addScheduleItem(int eventType, String start, String end, String home, String guest, String note) throws SQLException, IOException {
		
		PreparedStatement stmt = getConnection().prepareStatement("INSERT INTO `Schedule` (`eventtype_id`, `start`, `end`, `home`, `guest`, `note`) VALUES (?,?,?,?,?,?)");
		
		stmt.setInt(1, eventType);
		stmt.setString(2, start);
		stmt.setString(3, end);
		stmt.setString(4, home);
		stmt.setString(5, guest);
		stmt.setString(6, note);
		
		stmt.executeUpdate();
		
	}

	protected void initializeDatabase() throws SQLException, IOException {

		execute("CREATE TABLE IF NOT EXISTS `Team` (`rowid` INTEGER NOT NULL, `name` VARCHAR(45) NULL, `logo` VARCHAR(45) NULL, PRIMARY KEY(`rowid`))");

		execute("CREATE TABLE IF NOT EXISTS `Player` (`rowid` INTEGER NOT NULL, `team_id` INT NOT NULL, `name` VARCHAR(45) NULL,"
				+ "  `surname` VARCHAR(45) NULL, `number` VARCHAR(2) NULL, PRIMARY KEY(`rowid`),"
				+ "  CONSTRAINT `fk_Player_Team` FOREIGN KEY (`team_id`)"
				+ "    REFERENCES `Team` (`rowid`) ON DELETE NO ACTION ON UPDATE NO ACTION)");

		execute("CREATE TABLE IF NOT EXISTS `EventType` (`rowid` INTEGER NOT NULL, `name` VARCHAR(45) NULL, `length` INT NULL,"
				+ "  `is_game` TINYINT(1) NULL, `has_shootout` TINYINT(1) NULL, `has_overtime` TINYINT(1) NULL,"
				+ "  `overtime` INT NULL, PRIMARY KEY(`rowid`))");

		execute("CREATE TABLE IF NOT EXISTS `PlayerStatistic` (`player_id` INT NOT NULL,"
				+ "  `goals` INT NULL, `assits` INT NULL,"
				+ "  CONSTRAINT `fk_PlayerStatistic_Player1` FOREIGN KEY (`player_id`)"
				+ "    REFERENCES `Player` (`rowid`) ON DELETE NO ACTION" + "    ON UPDATE NO ACTION)");

		execute("CREATE TABLE IF NOT EXISTS `Schedule` (`rowid` INTEGER NOT NULL, `eventtype_id` INT NOT NULL, "
				+ " `start` VARCHAR(6) NULL, `end` VARCHAR(6) NULL, home VARCHAR(16), guest VARCHAR(16), note VARCHAR(45) NULL, PRIMARY KEY(`rowid`),"
				+ " CONSTRAINT `fk_Schedule_Game1`"
				+ "    FOREIGN KEY (`eventtype_id`) REFERENCES `EventType` (`rowid`) ON DELETE NO ACTION"
				+ "    ON UPDATE NO ACTION)");

		execute("CREATE TABLE IF NOT EXISTS `Group` (`group` CHAR NOT NULL,"
				+ "  `team_id` INT NOT NULL, PRIMARY KEY (`group`, `team_id`),"
				+ "  CONSTRAINT `fk_Group_Team1` FOREIGN KEY (`team_id`)"
				+ "    REFERENCES `Team` (`rowid`) ON DELETE NO ACTION ON UPDATE NO ACTION)");

		execute("CREATE TABLE IF NOT EXISTS `Score` (`schedule_id` INT NOT NULL,"
				+ "  `team_id` INT NOT NULL, `goals` INT NULL,"
				+ "  PRIMARY KEY (`schedule_id`, `team_id`), CONSTRAINT `fk_Score_Games1`"
				+ "    FOREIGN KEY (`schedule_id`) REFERENCES `Schedule` (`rowid`) ON DELETE NO ACTION"
				+ "    ON UPDATE NO ACTION, CONSTRAINT `fk_Score_Team1`"
				+ "    FOREIGN KEY (`team_id`) REFERENCES `Team` (`rowid`) ON DELETE NO ACTION"
				+ "    ON UPDATE NO ACTION)");

	}

	public static void main(String[] args) throws NoSuchAlgorithmException, SQLException {
		java.util.Calendar cal = java.util.Calendar.getInstance();
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("HHmmss");
		try {
			DataInitializer d = new DataInitializer(
					"C:\\Users\\wn00086506\\Downloads\\turnier\\newcup" + sdf.format(cal.getTime()) + ".zip");
			d.getDatabase();
			d.getConfig();
			
			d.addEvent("Gruppenspiel", 600, true, false, false, 0);
			d.addEvent("Verlosung", 900, false, false, false, 0);
			d.addEvent("Halbfinale", 720, true, true, true, 3);
			d.addEvent("Finale", 720, true, true, true, 6);
			
			d.addScheduleItem(1, "10:00:00", "10:12:00", "team(1)", "team(2)", "");
			d.addScheduleItem(1, "10:14:00", "10:26:00", "team(3)", "team(4)", "");
			d.addScheduleItem(2, "10:28:00", "10:43:00", "", "", "");
			d.addScheduleItem(1, "10:45:00", "10:57:00", "team(1)", "team(5)", "");
			d.addScheduleItem(1, "10:59:00", "11:11:00", "team(2)", "team(3)", "");
			d.addScheduleItem(2, "11:13:00", "11:28:00", "", "", "");
			d.addScheduleItem(4, "11:30:00", "11:50:00", "group(0)pos(1)", "group(0)pos(2)", "");
			
			
			for (int i = 1; i <= 5; i++) {
				d.addTeam("Jena " + i, "C:\\Users\\wn00086506\\Downloads\\turnier\\logos\\" + i + ".png");
			}

			d.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
