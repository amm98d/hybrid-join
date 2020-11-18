import java.sql.SQLException;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) throws SQLException {
//		HashTable hashTable = new HashTable(100);
		DBHandler dbHandler = new DBHandler();
		ArrayList<Transaction> test = dbHandler.getTransactions(10);
		System.out.println(test.get(0).TRANSACTION_ID);
		System.out.println(test.get(test.size()-1).TRANSACTION_ID);
		System.out.println();
		test = dbHandler.getTransactions(12);
		System.out.println(test.get(0).TRANSACTION_ID);
		System.out.println(test.get(test.size()-1).TRANSACTION_ID);
		System.out.println();
		test = dbHandler.getTransactions(10);
		System.out.println(test.get(0).TRANSACTION_ID);
		System.out.println(test.get(test.size()-1).TRANSACTION_ID);
		System.out.println();
	}

}
