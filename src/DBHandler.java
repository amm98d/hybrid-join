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
	
	public void destroy() throws SQLException {
		this.conn.close();
	}

}
