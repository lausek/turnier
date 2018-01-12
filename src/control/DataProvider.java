package control;

import java.io.IOException;

public class DataProvider extends DataActor {

	public DataProvider(String filePath) throws IOException {
		initializeSystem(filePath);
	}


	public static void main(String[] args) throws IOException {
		new Turnier("C:\\Users\\wn00086506\\Downloads\\cup.zip");
	}

}
