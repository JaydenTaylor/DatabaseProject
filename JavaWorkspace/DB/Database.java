package DB;
import java.sql.*;
import java.util.ArrayList;

public class Database {
	private static Database database;
	public static Connection connection;
	private static ArrayList<Table> tables;
	
	public static final String[] tableCodes = 
		{"Course_ID", "Department_ID", null, "Prof_ID", "St_ID", "Book_ID"};
	/*
	 * currentTable is a refcode for a table
	 * 
	 * 				refcodes
	 * COURSE		0
	 * DEPARTMENT	1
	 * ENROLLMENT	2
	 * PROF			3
	 * STUDENT		4
	 * BOOK			5
	 *  
	public static final String STUDENT = "St_ID";
	public static final String PROF = "Prof_ID";
	public static final String DEPARTMENT = "Department_ID";
	public static final String COURSE = "Course_ID";
	//NO ENROLLMENT ID AS OF NOW
	//public static final String ENROLLMENT = "";
	public static final String TEXTBOOK = "Book_ID";
	*/
	private Database() throws SQLException {

		tables = new ArrayList<Table>();
		
		String url = "jdbc:mysql://localhost:3306/StudentRecord";
		String username = "Group";
		String password = "password";

		connection = DriverManager.getConnection(url, username, password);
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
	
	public static ArrayList<String> getColumns(int currentTable) throws SQLException {
		ArrayList<String> columns = new ArrayList<String>();
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM " + tables.get(currentTable).getName());
		ResultSetMetaData rsmd = rs.getMetaData();
		for(int i = 1; i <= rsmd.getColumnCount(); i++) {
			columns.add(rsmd.getColumnName(i));
		}
		return columns;
	}
	//NOT NEEDED but may be useful
	public static ArrayList<String> getTypes(int currentTable) throws SQLException {
		Statement stmt = connection.createStatement();
		String query = "SHOW FIELDS FROM " + tables.get(currentTable).getName();
		ResultSet rs = stmt.executeQuery(query);
		ArrayList<String> types = new ArrayList<String>();
		while(rs.next()) {
			String type = rs.getString(2);
			if(type.matches("int*"))
				types.add("int");
			else
				types.add("String");
		}
		return types;
	}
	
	public static void add(int currentTable,int currentButton, int size, Row input) throws SQLException {
		//tables.get(currentTable).add(input);
		Statement stmt = connection.createStatement();
		String query = "INSERT INTO "
				+ tables.get(currentTable).getName()
				+ " VALUES ('";
		for(int i = 0; i < size; i++)  {
			query += tables.get(currentTable).getRow(currentButton).getAttribute(i).getValue();			
			if(i < size - 1)
				query += "', '";
			else
				query += "'";
		}
		query += ")";
		System.out.println(query);
		if(stmt.execute(query))
			System.out.println("ADD FAIL");
	}

	public static void remove(int currentTable, int currentButton) throws SQLException {
		Statement stmt = connection.createStatement();
		String query = "DELETE FROM " 
				+ tables.get(currentTable).getName()
				+ " WHERE " 
				+ tableCodes[currentTable]
				+ "="
				+ tables.get(currentTable).getRow(currentButton)
					.getAttribute(0).getValue();
		System.out.println(query);
		if(stmt.execute(query))
			System.out.println("DELETE FAIL");
	}
}