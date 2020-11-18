import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DBHandler {
	private String url;
	private String username;
	private String password;
	private Connection conn;
	private Integer transactionsRead;

	public DBHandler() throws SQLException {
		this.url = "jdbc:mysql://localhost:3306/metro_db";
		this.username = "root";
		this.password = "1234";
		this.conn = DriverManager.getConnection(url, username, password);
		this.transactionsRead = 1;
	}

	public ArrayList<Transaction> getTransactions(int rows) throws SQLException {
		Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		ResultSet result = statement.executeQuery("SELECT * FROM metro_db.transactions;");
		ArrayList<Transaction> transactions = new ArrayList<>(rows);
		for (int i=this.transactionsRead; i<this.transactionsRead+rows; i++) {
			result.absolute(i);
			transactions.add(new Transaction(
					result.getString("TRANSACTION_ID"),
					result.getString("PRODUCT_ID"),
					result.getString("CUSTOMER_ID"),
					result.getString("CUSTOMER_NAME"),
					result.getString("STORE_ID"),
					result.getString("STORE_NAME"),
					result.getDate("T_DATE"),
					result.getInt("QUANTITY")
					));
		}
		this.transactionsRead += rows;
		return transactions;
	}

	public void destroy() throws SQLException {
		this.conn.close();
	}

}
