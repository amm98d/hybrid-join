import java.sql.SQLException;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) throws SQLException {
		HashTable hashTable = new HashTable(100);
		DBHandler dbHandler = new DBHandler();
		ArrayList<Transaction> test = dbHandler.getTransactions(10);
	}

}
