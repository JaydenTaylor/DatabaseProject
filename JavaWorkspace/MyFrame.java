import java.awt.GridLayout;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

public class MyFrame {
	private JFrame frame;
	private JLabel text;
	private JScrollPane scrollPane;
	private JPanel panel;
	
	private ArrayList<String> tableNames = new ArrayList<String>();
	private ArrayList<ArrayList<String>> tables = new ArrayList<ArrayList<String>>();
	
	public MyFrame() {
		frame = new JFrame();
		menuBuilder();
		populateData();
	}
	
	//builds the main menu
	public void menuBuilder() {
		frameInit("Database Menu");
		
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
				viewBuilder();
			}
		});
		
		tablesAndEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tableBuilder();
			}
		});
			
		frame.setVisible(true);
	}
	
	public void tableBuilder() {
		frameInit("Table View");
		addMenuReturnButton();
		
		text = new JLabel();
		JPanel tableSelect = new JPanel();
		JButton btn = new JButton("OK");
		
		String c[] = new String[tableNames.size()];
		tableNames.toArray(c);
		JComboBox<String> cb = new JComboBox<String>(c);
		
		text.setBounds(175, 40, 200, 20);
		btn.setBounds(235, 10, 50, 20);
		tableSelect.setBounds(60, 2, 150, 35);
		tableSelect.add(cb);
		
		frame.add(text);
		frame.add(btn);
		frame.add(tableSelect);
		frame.setVisible(true);
		
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayTable(cb.getSelectedIndex());
			}
		});
	}
	public void displayTable(int table) {
		tableBuilder();
		panel = new JPanel(new GridLayout(0,1));
		for (int i = 0; i < 10; i++) {
			int item = i;
			JButton b = new JButton(tableNames.get(table) + " Test: " + i);
			b.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					itemDisplay(table, item);
				}
			});
			panel.add(b);
		}
		scrollPane = new JScrollPane(panel);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(10, 40, 150, 80);
		frame.add(scrollPane);
		frame.setVisible(true);
	}
	
	public void itemDisplay(int table, int item) {
		text.setText(tables.get(table).get(item));
	}
	
	public void viewBuilder() {
		frameInit("Views");
		addMenuReturnButton();
		frame.setVisible(true);
	}
	
	public void frameInit(String name) {
		frame.setVisible(false);
		frame = null;
		frame = new JFrame();
		frame.setTitle(name);
		frame.setSize(300,150);
		frame.setLocation(425,250);
		frame.setLayout(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void addMenuReturnButton() {
		JButton mainMenu = new JButton("<--");
		mainMenu.setBounds(10, 10, 50, 20);
		mainMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menuBuilder();
			}
		});
		frame.add(mainMenu);
	}
	//this will integrate our data base
	public void populateData() {
		String choices[] = {"Table 1", "Table 2", "Table 3"};
		for(String s: choices)
			tableNames.add(s);
		ArrayList<String> table1Rows = new ArrayList<String>();
		ArrayList<String> table2Rows = new ArrayList<String>();
		ArrayList<String> table3Rows = new ArrayList<String>();
		tables.add(table1Rows);
		tables.add(table2Rows);
		tables.add(table3Rows);
		for(int i = 0; i < 3; i++)
			for(int j = 0; j < 10; j++)
				tables.get(i).add("Table: " + (i + 1) + "\tItem: " + j);

	}
}
