import java.sql.SQLException;

public class Main {

	public static void main(String[] args) throws SQLException {
		DBHandler dbHandler = new DBHandler();
		HybridJoin.run(dbHandler);
		dbHandler.close();
	}

}
