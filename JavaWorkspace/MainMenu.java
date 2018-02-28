import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class MainMenu {
	
	private static JFrame frame;
	
	public static void updateFrame(JFrame f) {
		frame = f;
		frame = new JFrame();
		frame.setVisible(false);
		frame = null;
		frame = new JFrame();
		frame.setTitle("Database Menu");
		frame.setSize(300, 300);
		frame.setLocation(425, 250);
		frame.setLayout(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300, 150);
		
		JLabel welcome = new JLabel("Welcome to the database!");
		JButton views = new JButton("Views(Bonus)");
		JButton tablesAndEdit = new JButton("Tables/Edit");
		
		welcome.setBounds(20, 10, 200, 20);
		views.setBounds(50, 80, 200, 20);
		tablesAndEdit.setBounds(50, 40, 200, 20);
		
		frame.add(views);
		frame.add(welcome);
		frame.add(tablesAndEdit);
		
		views.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//viewBuilder();
			}
		});
		
		tablesAndEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//tableBuilder();
				try {
					TableView.updateFrame(frame);
				} catch (SQLException e1) {
					
				}
			}
		});
			
		frame.setVisible(true);
	}
	
	
}
