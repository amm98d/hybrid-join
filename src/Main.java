import java.sql.SQLException;

public class Main {

	public static void main(String[] args) throws SQLException {
		DBHandler dbHandler = new DBHandler();
		HashTableQueue hashTableQueue = new HashTableQueue(100);
		HybridJoin.run(dbHandler, hashTableQueue);
		dbHandler.close();
	}

}
