package control;

import java.io.Closeable;
import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class DataActor implements Closeable {

	public static final String DATABASE_FILE_PATH = "/db.sqlite";
	public static final String CONFIG_FILE_PATH = "/config.properties";

	protected FileSystem fs;
	protected Connection connection;
	protected Path workingFolder, tempFolder, imageFolder;
	protected Path databaseFile, configFile;
	protected boolean configChanged = false;

	protected void initializeSystem(String filePath) throws IOException {
		Map<String, String> attributes = new HashMap<>();
		attributes.put("create", "true");

		// shpx jvaqbjf
		workingFolder = Paths.get(filePath.replace('\\', '/'));
		tempFolder = Paths.get(System.getProperty("java.io.tmpdir") + "/turnier/");

		fs = FileSystems.newFileSystem(URI.create("jar:file:///" + workingFolder.toString().replace('\\', '/')),
				attributes);

		if (!Files.isDirectory(tempFolder)) {
			Files.createDirectory(tempFolder);
		}
		
		imageFolder = fs.getPath("/images/");
		if (!Files.isDirectory(imageFolder)) {
			Files.createDirectory(imageFolder);
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
			try {
				databaseFile = loadResource(DATABASE_FILE_PATH);
			} catch (NoSuchFileException e) {
				Files.createFile(fs.getPath(DATABASE_FILE_PATH));
				databaseFile = loadResource(DATABASE_FILE_PATH);
			}
		}
		return databaseFile;
	}

	public Path getConfig() throws IOException {
		if (configFile == null) {
			try {
				configFile = loadResource(CONFIG_FILE_PATH);
			} catch (NoSuchFileException e) {
				Files.createFile(fs.getPath(CONFIG_FILE_PATH));
				configFile = loadResource(CONFIG_FILE_PATH);
			}
		}
		return configFile;
	}

	public Connection getConnection() throws SQLException, IOException {
		if (connection == null) {
			connection = DriverManager.getConnection("jdbc:sqlite:file:///" + getDatabase());
		}
		return connection;
	}

	protected void save() throws IOException {
		if (configChanged) {
			Files.copy(configFile, fs.getPath(CONFIG_FILE_PATH), StandardCopyOption.REPLACE_EXISTING);
		}

		if (databaseFile != null) {
			Files.copy(databaseFile, fs.getPath(DATABASE_FILE_PATH), StandardCopyOption.REPLACE_EXISTING);
		}
	}

	@Override
	public void close() throws IOException {
		save();

		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		fs.close();
	}

}
