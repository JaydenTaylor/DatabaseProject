import java.sql.*;
import java.util.ArrayList;

public class Database {
	private static Database database;
	private static ArrayList<Table> tables;

	private Database() throws SQLException {

		tables = new ArrayList<Table>();
		
		String url = "jdbc:mysql://localhost:3306/StudentRecord";
		String username = "Group";
		String password = "password";

		Connection connection = DriverManager.getConnection(url, username, password);
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery("SHOW TABLES");

		while (rs.next()) {
			tables.add(new Table(rs.getString(1)));
		}
		
		Table cur;
		for(int i = 0; i < tables.size(); i++) {
			cur = tables.get(i);
			rs = stmt.executeQuery("SELECT * FROM " + cur.getName());
			ResultSetMetaData rsmd = rs.getMetaData();
			while (rs.next()) {
				Row row = new Row();
				for(int j = 1; j <= rsmd.getColumnCount(); j++) {
					row.addAttribute(rsmd.getColumnName(j), rs.getString(j));
				}
				cur.add(row);
			}
		}
	}
	public static void instantiate() throws SQLException {
		database = new Database();
	}
	
	public static Database getInstance() throws SQLException {
		if (database == null) 
			database = new Database();
		return database;
	}
	
	public static ArrayList<Table> getTables() {
		return tables;
	}
}