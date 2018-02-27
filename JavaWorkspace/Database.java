//STEP 1. Import required packages
import java.sql.*;

public class Database {
	public static void main(String args[]) {
		String url = "jdbc:mysql://localhost:3306/StudentRecord";
		String username = "Group";
		String password = "password";

		System.out.println("Connecting database...");

		try (Connection connection = DriverManager.getConnection(url, username, password)) {
		    System.out.println("Database connected!");
		} catch (SQLException e) {
		    throw new IllegalStateException("Cannot connect the database!", e);
		}
	}
}