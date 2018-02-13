//import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.*;
import javax.swing.*;

public class MyFrame {
	private JFrame frame = new JFrame();
	
	public MyFrame() {
		menuBuilder();
	}
	
	//builds the main menu
	public void menuBuilder() {
		frameInit("Database Menu");
		
		JLabel welcome = new JLabel("Welcome to the database!");
		JButton views = new JButton("Views/Stored Procedures");
		JButton tablesAndEdit = new JButton("Tables/Edit");
			
		welcome.setBounds(20, 10, 200, 20);
		views.setBounds(50, 40, 200, 20);
		tablesAndEdit.setBounds(50, 80, 200, 20);
		
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
		
		JPanel tableSelect = new JPanel();
		JButton btn = new JButton("OK");
		
		String choices[] = {"Table 1", "Table 2", "Secret Fancy Table"};
		JComboBox<String> cb = new JComboBox<String>(choices);
		
		btn.setBounds(235, 10, 50, 20);
		tableSelect.setBounds(60, 2, 175, 50);
		tableSelect.add(cb);
		
		
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
        JPanel panel = new JPanel(new GridLayout(0,1));
        for (int i = 0; i < 10; i++) {
            panel.add(new JButton("Test " + i));
        }
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(10, 40, 100, 80);
		frame.add(scrollPane);
        frame.setVisible(true);
		System.out.println(table);
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
}
