import java.sql.SQLException;

import javax.swing.JFrame;

public class Demo {
	public static void main(String args[]) throws SQLException {
		Database.instantiate();
		JFrame frame = new JFrame();
		MainMenu.updateFrame(frame);
	}
}
