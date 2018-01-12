package control;

import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class DataProvider {

	public static final String DATABASE_FILE = "/db.sqlite";
	public static final String CONFIG_FILE = "/config.properties";

	protected FileSystem fs;
	protected Connection connection;
	protected Path fileFolder, tempFolder, databaseFile, configFile;

	public DataProvider(String filePath) throws IOException {
		Map<String, String> attributes = new HashMap<>();
		attributes.put("create", "true");

		// shpx jvaqbjf
		fileFolder = Paths.get(filePath.replace('\\', '/'));
		tempFolder = Paths.get(System.getProperty("java.io.tmpdir") + "/turnier/");

		fs = FileSystems.newFileSystem(URI.create("jar:file:///" + fileFolder.toString().replace('\\', '/')), attributes);

		if (!Files.isDirectory(tempFolder)) {
			Files.createDirectory(tempFolder);
		}

		getConfig();
		getDatabase();

	}

	private Path loadResource(String path) throws IOException {
		Path target = Paths.get(tempFolder + path);
		Files.copy(fs.getPath(path), target, StandardCopyOption.REPLACE_EXISTING);
		return target;
	}

	public Path getDatabase() throws IOException {
		if (databaseFile == null) {
			databaseFile = loadResource(DATABASE_FILE);
		}
		return databaseFile;
	}

	public Path getConfig() throws IOException {
		if (configFile == null) {
			configFile = loadResource(CONFIG_FILE);
		}
		return configFile;
	}

	public Connection getConnection() throws SQLException, IOException {
		if(connection == null) {
			connection = DriverManager.getConnection("jdbc:sqlite:file:///" + getDatabase());
		}
		return connection;
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		// TODO: save changes
		fs.close();
	}

	public static void main(String[] args) throws IOException {
		new Turnier("C:\\Users\\wn00086506\\Downloads\\cup.zip");
	}

}
