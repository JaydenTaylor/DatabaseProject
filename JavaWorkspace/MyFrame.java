import java.awt.GridLayout;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

public class MyFrame {
	private JFrame frame;
	private JScrollPane scrollPane;
	private JPanel panel;
	
	private JLabel text;
	private JButton add;
	private JButton delete;
	
	private ArrayList<String> tableNames;
	private ArrayList<ArrayList<String>> tables;
	
	int currentTable = 0, currentButton = 0;
	
	public MyFrame() {
		frame = new JFrame();
		tableNames = new ArrayList<String>();
		tables = new ArrayList<ArrayList<String>>();
		menuBuilder();
		populateData();
	}
	
	//builds the main menu
	public void menuBuilder() {
		frameInit("Database Menu");
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
	//builds table view
	public void tableBuilder() {
		frameInit("Table View");
		addMenuReturnButton();
		
		text = new JLabel();
		add = new JButton("Add");
		delete = new JButton("Delete");
		JPanel tableSelect = new JPanel();
		JButton btn = new JButton("OK");
		
		String c[] = new String[tableNames.size()];
		tableNames.toArray(c);
		JComboBox<String> cb = new JComboBox<String>(c);
		
		text.setBounds(175, 40, 200, 20);
		add.setBounds(165, 240, 60, 20);
		delete.setBounds(230, 240, 60, 20);
		btn.setBounds(235, 10, 50, 20);
		tableSelect.setBounds(60, 2, 150, 35);
		tableSelect.add(cb);
		
		add.setVisible(false);
		delete.setVisible(false);
		
		frame.add(text);
		frame.add(add);
		frame.add(delete);
		frame.add(btn);
		frame.add(tableSelect);
		frame.setVisible(true);
		
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayTable(cb.getSelectedIndex());
			}
		});
		
		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Add data
				JFrame addEntry = new JFrame();
				addEntry.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				addEntry.setLayout(null);
				addEntry.setResizable(false);
				addEntry.setTitle("Add Entry");
				addEntry.setSize(300,150);
				addEntry.setLocation(425,250);
				
				JButton mainMenu = new JButton("MENU");
				mainMenu.setBounds(10, 10, 50, 20);
				mainMenu.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						menuBuilder();
					}
				});
				
				JLabel ex1 = new JLabel("Attribute 1");
				JLabel ex2 = new JLabel("Attribute 2");
				JLabel ex3 = new JLabel("Attribute 3");
				ex1.setBounds(10, 40, 100, 25);
				ex2.setBounds(10, 70, 100, 25);
				ex3.setBounds(10, 100, 100, 25);
				
				JTextField text1 = new JTextField();
				JTextField text2 = new JTextField();
				JTextField text3 = new JTextField();
				text1.setBounds(110, 40, 100, 25);
				text2.setBounds(110, 70, 100, 25);
				text3.setBounds(110, 100, 100, 25);
				
				JButton submit = new JButton("Submit");
				submit.setBounds(225, 100, 50, 20);
				submit.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String input = text1.getText();
						input += ", ";
						input += text2.getText();
						input += ", ";
						input += text3.getText();
						
						tables.get(currentTable).add(input);
						displayTable(currentTable);
					}
				});
				addEntry.add(submit);
				addEntry.add(text1);
				addEntry.add(text2);
				addEntry.add(text3);
				addEntry.add(ex1);
				addEntry.add(ex2);
				addEntry.add(ex3);
				addEntry.add(mainMenu);
				addEntry.setVisible(true);
				frame.setVisible(false);
			}
		});
		
		delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//remove data
				if(!tables.get(currentTable).isEmpty()) {
					tables.get(currentTable).remove(currentButton);
					displayTable(currentTable);
				}
			}
		});
		
	}
	//fills panel with buttons, each button displays corresponding data
	public void displayTable(int table) {
		currentTable = table;
		
		tableBuilder();
		
		panel = new JPanel(new GridLayout(0,1));
		
		for (int i = 0; i < tables.get(table).size(); i++) {
			int item = i;
			JButton b = new JButton("Entry: " + i);
			b.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					itemDisplay(table, item);
				}
			});
			panel.add(b);
		}
		
		add.setVisible(true);
		delete.setVisible(true);
		
		scrollPane = new JScrollPane(panel);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(10, 40, 150, 220);
		
		frame.add(scrollPane);
		frame.setVisible(true);
		frame.revalidate();
	}
	//displays specific item, called when item selected in panel
	public void itemDisplay(int table, int item) {
		currentButton = item;
		text.setText(tables.get(table).get(item));
	}
	//BONUS MARKS
	public void viewBuilder() {
		frameInit("Views");
		addMenuReturnButton();
		frame.setVisible(true);
	}
	//Initialize empty frame
	public void frameInit(String name) {
		frame.setVisible(false);
		frame = null;
		frame = new JFrame();
		frame.setTitle(name);
		frame.setSize(300, 300);
		frame.setLocation(425, 250);
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
		tables.add(new ArrayList<String>());
		tables.add(new ArrayList<String>());
		tables.add(new ArrayList<String>());
		for(int i = 0; i < 3; i++)
			for(int j = 0; j < 10; j++)
				tables.get(i).add("Table:" + (i + 1) + "\tItem:" + j);

	}
}
