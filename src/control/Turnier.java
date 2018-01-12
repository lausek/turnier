package control;

import java.io.IOException;
public class Turnier {

	private DataProvider dataProvider;
	
	public Turnier(String filepath) throws IOException {
		this.dataProvider = new DataProvider(filepath);
	}

}
