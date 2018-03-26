package DB;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
/**
 * Singleton database class
 * Manages database
 * @author jaydentaylor
 *
 */
public class Database {
	private static Database database;
	private static Connection connection;
	public static ArrayList<Table> tables;
	public static Map<Integer, ArrayList<String>> dependencyMap;
	public static final String[] tableCodes = 
		{"Course_ID", "Department_ID", null, "Prof_ID", "St_ID", "Book_ID"};
	/*
	 * currentTable is a refcode for a table
	 * Refcodes are stored in Constants.java
	 * 				refcodes
	 * COURSE		0
	 * DEPARTMENT	1
	 * ENROLLMENT	2
	 * PROF			3
	 * STUDENT		4
	 * BOOK			5
	 *  
	 */
	
	private Database() throws SQLException {

		tables = new ArrayList<Table>();
		
		dependencyMap = new HashMap<Integer, ArrayList<String>>();
		
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
		for(int i = 0; i < tables.size(); i++) {
			dependencyMap.put(i, new ArrayList<String>());
			connection = DriverManager.getConnection(url, username, password);
			stmt = connection.createStatement();
			rs = stmt.executeQuery("select" + 
					"    referenced_table_name as 'references',  " + 
					"    table_name as 'foreign key' " + 
					"from" + 
					"    information_schema.key_column_usage " + 
					"where" + 
					"    referenced_table_name is not null" + 
					"    and table_schema = 'StudentRecord' " +
					"	 and table_name = '" + tables.get(i).getName() + "';");
			while (rs.next()) {
				dependencyMap.get(i).add(rs.getString(1));
			}
		}
		System.out.println(dependencyMap);
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
		
		Statement stmt = connection.createStatement();
		String query = "INSERT INTO "
				+ tables.get(currentTable).getName()
				+ " VALUES ('";
		for(int i = 0; i < size; i++)  {
			query += tables.get(currentTable).getRow(tables.get(currentTable).size()-1).getAttribute(i).getValue();			
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
	//TODO EDIT LOCALLY
	public static boolean edit(int currentTable,int currentButton, int size, Row input) throws SQLException {
		Statement statment;
		
		String query = "UPDATE " + tables.get(currentTable).getName() + " SET ";
		for(int i = 1; i < size; i++) {
			query += tables.get(currentTable).getRow(currentButton).getAttribute(i).getType()
					+ " = '"
					+ input.getAttribute(i).getValue();
			if(i < size - 1)
				query += "', ";
			else
				query += "'";
		}
		
		query += " WHERE " 
				+ tableCodes[currentTable]
				+ "="
				+ tables.get(currentTable).getRow(currentButton)
					.getAttribute(0).getValue();
		System.out.println(query);
		try {
			statment = connection.createStatement();
			statment.execute(query);
			//tables.get(currentTable).remove(currentButton);
		} catch (SQLException e) {
			for(int i = 0; i < dependencyMap.size(); i++) {
				if(dependencyMap.get(i).contains(tables.get(currentTable).getName())) 
					System.out.println(tables.get(currentTable).getName() +
						" is depended on by " + tables.get(i).getName());
				
			}
			return false;
		}
		return true;
	}

	public static boolean remove(int currentTable, int currentButton)  {
		Statement stmt;
		String query = "DELETE FROM " 
				+ tables.get(currentTable).getName()
				+ " WHERE " 
				+ tableCodes[currentTable]
				+ "="
				+ tables.get(currentTable).getRow(currentButton)
					.getAttribute(0).getValue();
		System.out.println(query);
		try {
			stmt = connection.createStatement();
			stmt.execute(query);
			tables.get(currentTable).remove(currentButton);
		} catch (SQLException e) {
			for(int i = 0; i < dependencyMap.size(); i++) {
				if(dependencyMap.get(i).contains(tables.get(currentTable).getName())) 
					System.out.println(tables.get(currentTable).getName() +
						" is depended on by " + tables.get(i).getName());
				
			}
			return false;
		}
		return true;
		
	}
	

}