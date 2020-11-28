import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
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
		this.loadDates();
	}

	private void loadDates() throws SQLException {
		PreparedStatement stmt = this.conn.prepareStatement("INSERT IGNORE INTO metro_dw.time VALUES (?, ?)");
		for (LocalDate date = LocalDate.parse("2016-01-01"); date
				.isBefore(LocalDate.parse("2016-12-31")); date = date.plusDays(1)) {
			stmt.setDate(1, Date.valueOf(date));
			stmt.setString(2, date.getDayOfWeek().toString());
			stmt.executeUpdate();
		}
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

	public void shipToDW(Transaction transaction) throws SQLException {
		// PRODUCT
		PreparedStatement stmt = this.conn.prepareStatement("INSERT IGNORE INTO metro_dw.product VALUES (?, ?)");
		stmt.setString(1, transaction.PRODUCT_ID);
		stmt.setString(2, transaction.PRODUCT_NAME);
		stmt.executeUpdate();
		// CUSTOMER
		stmt = this.conn.prepareStatement("INSERT IGNORE INTO metro_dw.customer VALUES (?, ?)");
		stmt.setString(1, transaction.CUSTOMER_ID);
		stmt.setString(2, transaction.CUSTOMER_NAME);
		stmt.executeUpdate();
		// STORE
		stmt = this.conn.prepareStatement("INSERT IGNORE INTO metro_dw.store VALUES (?, ?)");
		stmt.setString(1, transaction.STORE_ID);
		stmt.setString(2, transaction.STORE_NAME);
		stmt.executeUpdate();
		// SUPPLIER
		stmt = this.conn.prepareStatement("INSERT IGNORE INTO metro_dw.supplier VALUES (?, ?)");
		stmt.setString(1, transaction.SUPPLIER_ID);
		stmt.setString(2, transaction.SUPPLIER_NAME);
		stmt.executeUpdate();
		// SALES
		stmt = this.conn.prepareStatement("INSERT IGNORE INTO metro_dw.sales VALUES (?, ?, ?, ?, ?, ?, ?)");
		stmt.setString(1, transaction.CUSTOMER_ID);
		stmt.setString(2, transaction.STORE_ID);
		stmt.setString(3, transaction.PRODUCT_ID);
		stmt.setString(4, transaction.SUPPLIER_ID);
		stmt.setDate(5, transaction.T_DATE);
		stmt.setInt(6, transaction.QUANTITY);
		stmt.setFloat(7, transaction.TOTAL_SALE);
		stmt.executeUpdate();
	}

	public void close() throws SQLException {
		this.transactionsResultSet.close();
		this.masterDataResultSet.close();
		this.masterDataStatement.close();
		this.transactionsStatement.close();
		this.conn.close();
	}

}
