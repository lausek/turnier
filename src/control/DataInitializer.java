package control;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
