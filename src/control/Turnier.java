package control;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class Turnier {
	
	public static final String SQL_FILE = "/db.sqlite";
	public static final String PROPERTIES_FILE = "/config.properties";
	
	protected FileSystem fs;
	protected Properties properties;
	
	public Turnier(String filepath) throws IOException {
		Path path = Paths.get(filepath);
		fs = FileSystems.newFileSystem(path, null);
		
		if(!Files.exists(fs.getPath(SQL_FILE))
		|| !Files.exists(fs.getPath(PROPERTIES_FILE))) {
			// TODO: file is corrupt
		}
		
//		properties.load(Files.newInputStream(fs.getPath(PROPERTIES_FILE)));
		
	}
	
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		fs.close();
	}
	
}
