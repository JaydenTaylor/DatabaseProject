import java.util.ArrayList;

public class Table {
	private ArrayList<Row> rows;
	private String tableName;
	
	public Table(String name) {
		tableName = name;
		rows = new ArrayList<Row>();
	}
	
	public String getName() { return tableName; }
	
	public Row getRow(int row) { return rows.get(row); }
	
	public int size() { return rows.size(); }
	
	public void add(Row row) { 
		rows.add(row); 
	}
	
	public void remove(int row) {
		rows.remove(row);
	}
	
}
