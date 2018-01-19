package control;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Turnier {

	private DataProvider dataProvider;
	
	public Turnier(String filepath) throws IOException {
		this.dataProvider = new DataProvider(filepath);
		
		try {
			PreparedStatement stmt = dataProvider.getConnection().prepareStatement("CREATE TABLE ");
			stmt.execute();
			ResultSet result = stmt.getResultSet();
			while(result.next()) {
				System.out.println(result.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
