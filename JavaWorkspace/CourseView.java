import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;

public class CourseView {
	
	private static JFrame frame;
	private JPanel contentPane;
	static Statement stmt;
	static Connection cn;
	
	static DefaultTableModel TModel = new DefaultTableModel();
	static JTable table = new JTable(TModel);
	static String course_name;
		
	private static int currentTable = 0, currentButton = 0;
	private static JPanel tableSelect;
	/**
	 * Updates Current frame to TableView
	 * @param f Current Frame reference
	 * @throws SQLException
	 */
	public static void updateFrame(JFrame f) throws SQLException {
		frame = f;
		table.setAutoCreateRowSorter(true);
		table.setGridColor(Color.LIGHT_GRAY);
		initDb();
		instantiate();
		addReturnButton(frame);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(47, 70, 312, 165);
		scrollPane.setViewportView(table);	
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {}));
		comboBox.setBounds(149, 10, 169, 34);
		comboBox.addItem("...");
		try {  
		      while (TModel.getRowCount()>0){
		    	  TModel.removeRow(0);
		      }
		      ResultSet course_set = stmt.executeQuery("SELECT course_name FROM course");
		      while(course_set.next()){
		    	  comboBox.addItem(course_set.getString("course_name"));
		      } 
		    }catch (Exception ex) {
		      System.err.println(ex);
		    }
		
		
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				course_name = (String)comboBox.getSelectedItem();  
				updateTable(course_name);
			}
		});

		frame.add(scrollPane);	
		addTable();
		
		frame.add(comboBox);
		
		frame.setVisible(true);
	}
	
	static void addTable() {
		TModel.setColumnIdentifiers(new Object[]{"Student ID", "First Name", "Last Name"});		
	}
	
	static void updateTable(String course_name) {		
		try {  
		      while (TModel.getRowCount()>0){
		    	  TModel.removeRow(0);
		      }
		      ResultSet rs = stmt.executeQuery("CALL CourseView('" + course_name + "')");
		      while(rs.next()){
		    	  TModel.addRow(new Object[]{
		          rs.getString("St_ID"),
		          rs.getString("F_Name"),
		          rs.getString("L_Name")});
		      } 
		    }catch (Exception ex) {
		      System.err.println(ex);
		    }				
	}
	
	public static void instantiate() {
		frame.setVisible(false);
		frame = new JFrame();
		frame.setTitle("Courses");
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
	
	
	static void initDb(){
	    try {            
	      Class.forName("com.mysql.jdbc.Driver");
	      cn = DriverManager.getConnection ("jdbc:mysql://localhost:3306/StudentRecord", 
	                                        "Group", 
	                                        "password");        
	      stmt = cn.createStatement();      
	    } catch (Exception ex) {
	      System.err.println(ex);
	    }
	  }
	
	
}
