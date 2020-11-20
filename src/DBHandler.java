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
	private Statement transactionsStatement;
	private Statement masterDataStatement;
	private ResultSet transactionsResultSet;
	private ResultSet masterDataResultSet;

	public DBHandler() throws SQLException {
		this.url = "jdbc:mysql://localhost:3306/metro_db";
		this.username = "root";
		this.password = "1234";
		this.conn = DriverManager.getConnection(url, username, password);
		this.transactionsRead = 1;
		this.transactionsStatement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		this.masterDataStatement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		this.transactionsResultSet = transactionsStatement.executeQuery("SELECT * FROM metro_db.transactions;");
		this.masterDataResultSet = masterDataStatement.executeQuery("SELECT * FROM metro_db.masterdata;");
	}

	private int totalSize(ResultSet rs) throws SQLException {
		int prev = rs.getRow();
		int totalSize = 0;
		if (rs != null) {
			rs.last();
			totalSize = rs.getRow();
		}
		rs.absolute(prev);
		return totalSize;
	}

	private boolean isEnd(ResultSet rs) throws SQLException {
		return rs.getRow() == this.totalSize(rs);
	}

	public ArrayList<Transaction> getTransactions(int rows) throws SQLException {
		ArrayList<Transaction> transactionsToRet = new ArrayList<>(rows);
		for (int i = this.transactionsRead; (i < this.transactionsRead + rows)
				&& !this.isEnd(this.transactionsResultSet); i++) {
			this.transactionsResultSet.absolute(i);
			transactionsToRet.add(new Transaction(this.transactionsResultSet.getString("TRANSACTION_ID"),
					this.transactionsResultSet.getString("PRODUCT_ID"),
					this.transactionsResultSet.getString("CUSTOMER_ID"),
					this.transactionsResultSet.getString("CUSTOMER_NAME"),
					this.transactionsResultSet.getString("STORE_ID"),
					this.transactionsResultSet.getString("STORE_NAME"), this.transactionsResultSet.getDate("T_DATE"),
					this.transactionsResultSet.getInt("QUANTITY")));
		}
		this.transactionsRead += rows;
		return transactionsToRet;
	}

	public ArrayList<MasterData> getMasterData(String PRODUCT_ID) throws SQLException {
		ArrayList<MasterData> masterDataToRet = new ArrayList<>(10);
		for (int i = 1; i <= this.totalSize(this.masterDataResultSet); i++) {
			this.masterDataResultSet.absolute(i);
			if (this.masterDataResultSet.getString("PRODUCT_ID").equals(PRODUCT_ID)) {
				for (int j = i; j < i + 10 && j <= this.totalSize(this.masterDataResultSet); j++) {
					this.masterDataResultSet.absolute(j);
					masterDataToRet.add(new MasterData(this.masterDataResultSet.getString("PRODUCT_ID"),
							this.masterDataResultSet.getString("PRODUCT_NAME"),
							this.masterDataResultSet.getString("SUPPLIER_ID"),
							this.masterDataResultSet.getString("SUPPLIER_NAME"),
							this.masterDataResultSet.getFloat("PRICE")));
				}
				break;
			}
		}
		return masterDataToRet;
	}

	public boolean endOfTransactions() throws SQLException {
		return this.transactionsResultSet.getRow() == this.totalSize(this.transactionsResultSet);
	}

	public void close() throws SQLException {
		this.transactionsResultSet.close();
		this.masterDataResultSet.close();
		this.masterDataStatement.close();
		this.transactionsStatement.close();
		this.conn.close();
	}

}
