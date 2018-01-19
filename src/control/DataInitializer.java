package control;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.sql.Statement;
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
	
	protected void initializeDatabase() throws SQLException, IOException {
		
		execute("CREATE TABLE IF NOT EXISTS `Team` (\r\n" + 
				"  `id` INT NOT NULL,\r\n" + 
				"  `name` VARCHAR(45) NULL,\r\n" + 
				"  `logo` VARCHAR(45) NULL,\r\n" + 
				"  PRIMARY KEY (`id`))\r\n");
		
		execute("CREATE TABLE IF NOT EXISTS `Player` (\r\n" + 
				"  `id` INT NOT NULL,\r\n" + 
				"  `team_id` INT NOT NULL,\r\n" + 
				"  `name` VARCHAR(45) NULL,\r\n" + 
				"  `surname` VARCHAR(45) NULL,\r\n" + 
				"  `number` VARCHAR(2) NULL,\r\n" + 
				"  PRIMARY KEY (`id`),\r\n" +
				"  CONSTRAINT `fk_Player_Team`\r\n" + 
				"    FOREIGN KEY (`team_id`)\r\n" + 
				"    REFERENCES `Team` (`id`)\r\n" + 
				"    ON DELETE NO ACTION\r\n" + 
				"    ON UPDATE NO ACTION)\r\n");
		
		execute("CREATE TABLE IF NOT EXISTS `GameType` (\r\n" + 
				"  `id` INT NOT NULL,\r\n" + 
				"  `name` VARCHAR(45) NULL,\r\n" + 
				"  `length` INT NULL,\r\n" + 
				"  `has_shootout` TINYINT(1) NULL,\r\n" + 
				"  `has_overtime` TINYINT(1) NULL,\r\n" + 
				"  `overtime` INT NULL,\r\n" + 
				"  PRIMARY KEY (`id`))\r\n");
		
		execute("CREATE TABLE IF NOT EXISTS `Game` (\r\n" + 
				"  `id` INT NOT NULL,\r\n" + 
				"  `gametype` INT NOT NULL,\r\n" + 
				"  `home` VARCHAR(16) NULL,\r\n" + 
				"  `guest` VARCHAR(16) NULL,\r\n" + 
				"  PRIMARY KEY (`id`),\r\n" + 
				"  CONSTRAINT `fk_Games_GameType1`\r\n" + 
				"    FOREIGN KEY (`gametype`)\r\n" + 
				"    REFERENCES `GameType` (`id`)\r\n" + 
				"    ON DELETE NO ACTION\r\n" + 
				"    ON UPDATE NO ACTION)\r\n");
		
		execute("CREATE TABLE IF NOT EXISTS `PlayerStatistic` (\r\n" + 
				"  `player_id` INT NOT NULL,\r\n" + 
				"  `goals` INT NULL,\r\n" + 
				"  `assits` INT NULL,\r\n" + 
				"  CONSTRAINT `fk_PlayerStatistic_Player1`\r\n" + 
				"    FOREIGN KEY (`player_id`)\r\n" + 
				"    REFERENCES `Player` (`id`)\r\n" + 
				"    ON DELETE NO ACTION\r\n" + 
				"    ON UPDATE NO ACTION)\r\n");
		
		execute("CREATE TABLE IF NOT EXISTS `Schedule` (\r\n" + 
				"  `step` INT NOT NULL,\r\n" + 
				"  `game_id` INT NOT NULL,\r\n" + 
				"  `start` TIME NULL,\r\n" + 
				"  `end` TIME NULL,\r\n" + 
				"  PRIMARY KEY (`step`),\r\n" + 
				"  CONSTRAINT `fk_Schedule_Game1`\r\n" + 
				"    FOREIGN KEY (`game_id`)\r\n" + 
				"    REFERENCES `Game` (`id`)\r\n" + 
				"    ON DELETE NO ACTION\r\n" + 
				"    ON UPDATE NO ACTION)\r\n");
		
		execute("CREATE TABLE IF NOT EXISTS `Group` (\r\n" + 
				"  `group` CHAR NOT NULL,\r\n" + 
				"  `team_id` INT NOT NULL,\r\n" + 
				"  PRIMARY KEY (`group`, `team_id`),\r\n" + 
				"  CONSTRAINT `fk_Group_Team1`\r\n" + 
				"    FOREIGN KEY (`team_id`)\r\n" + 
				"    REFERENCES `Team` (`id`)\r\n" + 
				"    ON DELETE NO ACTION\r\n" + 
				"    ON UPDATE NO ACTION)\r\n");
		
		execute("CREATE TABLE IF NOT EXISTS `Score` (\r\n" + 
				"  `game_id` INT NOT NULL,\r\n" + 
				"  `team_id` INT NOT NULL,\r\n" + 
				"  `goals` INT NULL,\r\n" + 
				"  PRIMARY KEY (`game_id`, `team_id`),\r\n" + 
				"  CONSTRAINT `fk_Score_Games1`\r\n" + 
				"    FOREIGN KEY (`game_id`)\r\n" + 
				"    REFERENCES `Game` (`id`)\r\n" + 
				"    ON DELETE NO ACTION\r\n" + 
				"    ON UPDATE NO ACTION,\r\n" + 
				"  CONSTRAINT `fk_Score_Team1`\r\n" + 
				"    FOREIGN KEY (`team_id`)\r\n" + 
				"    REFERENCES `Team` (`id`)\r\n" + 
				"    ON DELETE NO ACTION\r\n" + 
				"    ON UPDATE NO ACTION)\r\n");
		
	}
	
	public static void main(String[] args) {
		java.util.Calendar cal = java.util.Calendar.getInstance();
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("HHmmss");
		try {
			DataInitializer d = new DataInitializer("C:\\Users\\wn00086506\\Downloads\\turnier\\newcup"+sdf.format(cal.getTime())+".zip");
			d.getDatabase();
			d.getConfig();
			d.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
