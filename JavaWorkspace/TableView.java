import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Insets;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import DB.*;

public class TableView {
	
	private static JFrame frame;
	
	private static JScrollPane scrollPane;
	private static JPanel panel;
	
	private static ArrayList<JLabel> texts;
	private static JButton add;
	private static JButton delete;
	private static JButton edit;
	
	private static JPanel tableSelect;
	private static JButton btn;

	
	
	//TODO: set spinner to correct table
	//TODO: select item 0 on initial table display
	
	private static int currentTable = 0, currentButton = 0;
	/**
	 * Updates Current frame to TableView
	 * @param f Current Frame reference
	 * @throws SQLException
	 */
	public static void updateFrame(JFrame f) throws SQLException {
		frame = f;
		
		instantiate();
		addReturnButton(frame);
		addComponents();
		
		frame.setVisible(true);
	}
	
	public static void displayTable(int table) throws SQLException {
		currentTable = table;
		
		updateFrame(frame);
		
		panel = new JPanel(new GridLayout(0,1));
		
		for (int i = 0; i < Database.tables.get(table).size(); i++) {
			int item = i;
			JButton b = new JButton(Database.tables.get(table).getName() + ": " + i);
			b.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					itemDisplay(table, item);
				}
			});
			panel.add(b);
		}
		
		add.setVisible(true);
		delete.setVisible(true);
		edit.setVisible(true);
		if(currentTable == Constants.ENROLLMENT)
			edit.setEnabled(false);
		
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
		currentTable = table;
		currentButton = item;
		for(int i = 0; i < Database.tables.get(table).getRow(item).size(); i++) 
			texts.get(i).setText(Database.tables.get(table).getRow(item).getAttribute(i).toString());
		//text.setText(tables.get(table).getRow(item).toString());
	}
	
	public static void instantiate() {
		frame.setVisible(false);
		frame = new JFrame();
		frame.setTitle("Table View");
		frame.setSize(450, 300);
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
				f.setVisible(false);
				MainMenu.updateFrame(frame);
			}
		});
		f.add(mainMenu);
	}
	
	public static void addComponents() {
		texts = new ArrayList<JLabel>();
		add = new JButton("Add");
		add.setMargin(new Insets(0,0,0,0));
		delete = new JButton("Delete");
		delete.setMargin(new Insets(0,0,0,0));
		edit = new JButton("Edit");
		edit.setMargin(new Insets(0,0,0,0));
		tableSelect = new JPanel();
		btn = new JButton("OK");
		btn.setMargin(new Insets(0,0,0,0));
		
		String c[] = new String[Database.tables.size()];
		for(int i = 0; i < Database.tables.size(); i++)
			c[i] = Database.tables.get(i).getName();
		JComboBox<String> cb = new JComboBox<String>(c);
		for(int i = 0; i < 10; i++) {
			JLabel text = new JLabel();
			text.setBounds(175, 40, 800, 20 + (40 * i));
			texts.add(text);
			frame.add(text);
		}
		
		add.setBounds(165, 240, 60, 20);
		delete.setBounds(230, 240, 60, 20);
		edit.setBounds(295, 240, 60, 20);
		btn.setBounds(235, 10, 50, 20);
		tableSelect.setBounds(60, 2, 150, 35);
		
		tableSelect.add(cb);
		
		add.setVisible(false);
		delete.setVisible(false);
		edit.setVisible(false);
		
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
				try {
					addPanel(false);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		edit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					addPanel(true);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		
		delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!(Database.tables.get(currentTable).size() == 0)) {
					try {
						if(Database.remove(currentTable, currentButton)) {
							displayTable(currentTable);
						} else {
							String message = "";
							for(int i = 0; i < Database.dependencyMap.size(); i++) {
								if(Database.dependencyMap.get(i).contains(Database.tables.get(currentTable).getName())) 
									message += "This " + Database.tables.get(currentTable).getName() +
										" is depended on by entries in " + Database.tables.get(i).getName() + "\n";
								
							}
							message += "You may not delete a " + Database.tables.get(currentTable).getName()
									+ " that is referenced in other tables\n"
									+ Database.tables.get(currentTable).getName()
									+ " may only be deleted when no references exist";
							JOptionPane.showMessageDialog(frame, message);
						}
						
						
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		
		
		frame.add(add);
		frame.add(edit);
		frame.add(delete);
		frame.add(btn);
		frame.add(tableSelect);
	}
	
	public static void addPanel(boolean edit) throws SQLException {
		JFrame addEntry = new JFrame();
		
		ArrayList<JTextField> texts = new ArrayList<JTextField>();
		ArrayList<String> columns = Database.getColumns(currentTable);
		
		addEntry.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addEntry.setLayout(null);
		addEntry.setResizable(true);
		addEntry.setTitle("Add Entry");
		addEntry.setSize(300,35 * columns.size());
		addEntry.setLocation(425,250);
		
		addReturnButton(addEntry);
		
		
		addEntry.setSize(300, 60 + (columns.size() * 30));
		for(int i = 0; i < columns.size(); i++) {
			JTextField textBox = new JTextField();
			JLabel label = new JLabel(columns.get(i));
			int y = 40 + ((i) * 30); //spacing
			label.setBounds(10, y, 100, 25);
			textBox.setBounds(110, y, 100, 25);
			if(edit) {
				textBox.setText(Database.tables.get(currentTable).getRow(currentButton).getAttribute(i).getValue());
				if(i == 0)
					textBox.setEnabled(false);
			}
			addEntry.add(label);
			addEntry.add(textBox);
			texts.add(textBox);
		}
		
		int size = columns.size();
		
		JButton submit = new JButton("Submit");
		submit.setBounds(70, 10, 50, 20);
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addEntry.setVisible(false);
				Row input = new Row();
				for(int i = 0; i < size; i++)
					input.addAttribute(columns.get(i), texts.get(i).getText());
				if(!edit)
					Database.tables.get(currentTable).add(input);
				try {
					displayTable(currentTable);
					if(edit)
						Database.edit(currentTable, currentButton, size, input);
					else
						Database.add(currentTable, currentButton, size, input);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		addEntry.add(submit);
		addEntry.setVisible(true);
		frame.setVisible(false);
	}

}
