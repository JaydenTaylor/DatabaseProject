import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class TableView {
	
	private static JFrame frame;
	
	private static JScrollPane scrollPane;
	private static JPanel panel;
	
	private static ArrayList<JLabel> texts;
	private static JButton add;
	private static JButton delete;
	
	private static JPanel tableSelect;
	private static JButton btn;

	private static ArrayList<Table> tables;
	
	private static int currentTable = 0, currentButton = 0;
	
	public static void updateFrame(JFrame f) throws SQLException {
		frame = f;
		tables = Database.getTables();
		
		instantiate();
		addReturnButton(frame);
		addComponents();
		frame.setVisible(true);
	}
	
	public static void displayTable(int table) throws SQLException {
		currentTable = table;
		
		updateFrame(frame);
		
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
	public static void itemDisplay(int table, int item) {
		currentButton = item;
		for(int i = 0; i < tables.get(table).getRow(item).size(); i++) 
			texts.get(i).setText(tables.get(table).getRow(item).getAttribute(i).toString());
		//text.setText(tables.get(table).getRow(item).toString());
	}
	
	public static void instantiate() {
		frame.setVisible(false);
		frame = new JFrame();
		frame.setTitle("Table View");
		frame.setSize(300, 300);
		frame.setLocation(425, 250);
		frame.setLayout(null);
		frame.setResizable(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void addReturnButton(JFrame f) {
		JButton mainMenu = new JButton("<--");
		mainMenu.setBounds(10, 10, 50, 20);
		mainMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				MainMenu.updateFrame(frame);
			}
		});
		f.add(mainMenu);
	}
	
	public static void addComponents() {
		texts = new ArrayList<JLabel>();
		add = new JButton("Add");
		delete = new JButton("Delete");
		tableSelect = new JPanel();
		btn = new JButton("OK");
		
		String c[] = new String[tables.size()];
		for(int i = 0; i < tables.size(); i++)
			c[i] = tables.get(i).getName();
		JComboBox<String> cb = new JComboBox<String>(c);
		for(int i = 0; i < 10; i++) {
			JLabel text = new JLabel();
			text.setBounds(175, 40, 200, 20 + (40 * i));
			texts.add(text);
			frame.add(text);
		}
		
		add.setBounds(165, 240, 60, 20);
		delete.setBounds(230, 240, 60, 20);
		btn.setBounds(235, 10, 50, 20);
		tableSelect.setBounds(60, 2, 150, 35);
		
		tableSelect.add(cb);
		
		add.setVisible(false);
		delete.setVisible(false);
		
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					displayTable(cb.getSelectedIndex());
				} catch (SQLException e1) {
					
				}
			}
		});
		
		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addPanel();
			}
		});
		
		delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//remove data
				if(!(tables.get(currentTable).size() == 0)) {
					tables.get(currentTable).remove(currentButton);
					try {
						displayTable(currentTable);
					} catch (SQLException e1) {
					}
				}
			}
		});
		
		
		frame.add(add);
		frame.add(delete);
		frame.add(btn);
		frame.add(tableSelect);
	}
	
	public static void addPanel() {
		JFrame addEntry = new JFrame();
		addEntry.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addEntry.setLayout(null);
		addEntry.setResizable(true);
		addEntry.setTitle("Add Entry");
		addEntry.setSize(300,150);
		addEntry.setLocation(425,250);
		
		addReturnButton(addEntry);
		
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
				addEntry.setVisible(false);
				Row input = new Row();
				input.addAttribute("Test: ", text1.getText());
				input.addAttribute("Test: ", text2.getText());
				input.addAttribute("Test: ", text3.getText());
				tables.get(currentTable).add(input);
				try {
					displayTable(currentTable);
				} catch (SQLException e1) {
					
				}
			}
		});
		
		addEntry.add(submit);
		addEntry.add(text1);
		addEntry.add(text2);
		addEntry.add(text3);
		addEntry.add(ex1);
		addEntry.add(ex2);
		addEntry.add(ex3);
		addEntry.setVisible(true);
		frame.setVisible(false);
	}

}
