import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {

	public static void main(String[] args) {
		String url = "jdbc:mysql://localhost:3306/metro_db";
		String username = "root";
		String password = "1234";
		try {
			Connection connection = DriverManager.getConnection(url, username, password);
			System.out.println("Connected!");
		} catch (SQLException e) {
			System.out.println("Oops, error!");
			e.printStackTrace();
		}
	}

}
