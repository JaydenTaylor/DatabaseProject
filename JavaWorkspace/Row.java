import java.util.*;

public class Row {
	private ArrayList<Attribute> row;
	
	public Row() {
		row = new ArrayList<Attribute>();
	}
	
	public int size() {
		return row.size();
	}
	
	public Attribute getAttribute(int i) {
		return row.get(i);
	}
	
	public void addAttribute(String type, String value) {
		Attribute a = new Attribute(type, value);
		row.add(a);
	}
	
	public String toString() {
		String ret = "";
		for (int i = 0; i < row.size(); i++)
			ret += row.get(i);
			ret += "\n";
		return ret;
	}
}
