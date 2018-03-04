package DB;

public class Attribute {
	private String type;
	private String value;
	
	public Attribute(String t, String v) {
		type = t;
		value = v;
	}
	
	public String toString() {
		return type + ": " + value;
	}
	
	public String getValue() {
		return value;
	}
}
