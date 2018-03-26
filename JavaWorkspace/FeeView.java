import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.Comparator;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;

public class FeeView {
	
	private static JFrame frame;
	private JPanel contentPane;
	static Statement stmt;
	static Connection cn;
	
	static DefaultTableModel TModel = new DefaultTableModel() {
		@Override
        public Class getColumnClass(int column) {
            switch (column) {
                case 0:
                    return String.class;
                case 1:
                    return String.class;
                case 2:
                    return String.class;
                case 3:
                	return Integer.class;
                default:
                    return String.class;
            }
        }
	};
	static JTable table = new JTable(TModel);
	static String student_type = "";
		
	private static JPanel tableSelect;
	/**
	 * Updates Current frame to TableView
	 * @param f Current Frame reference
	 * @throws SQLException
	 */
	public static void updateFrame(JFrame f) throws SQLException {
		frame = f;
		table.setAutoCreateRowSorter(true);
		
		initDb();
		instantiate();
		addReturnButton(frame);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(47, 70, 370, 165);
		scrollPane.setViewportView(table);
		
		JCheckBox chckbx1 = new JCheckBox("Full time");
		chckbx1.setBounds(180, 10, 100, 20);
		chckbx1.setSelected(true);
		JCheckBox chckbx2 = new JCheckBox("Part time");
		chckbx2.setBounds(300, 10, 100, 20);
		chckbx2.setSelected(true);
		
		JLabel rangeFrom = new JLabel("Range:        From");
		rangeFrom.setBounds(50, 45, 100, 20);
		JLabel rangeTo = new JLabel("To");
		rangeTo.setBounds(230, 45, 70, 20);
		
		JTextField textField1 = new JTextField();
		textField1.setColumns(10);
		textField1.setText("0");  
		textField1.setBounds(150, 45, 70, 20);
		JTextField textField2 = new JTextField();
		textField2.setColumns(10);
		textField2.setText("9000"); 
		textField2.setBounds(250, 45, 70, 20);

		JButton buttonx = new JButton("Show");
		buttonx.setBounds(80, 10, 70, 20);
		buttonx.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String textFieldValue1 = textField1.getText();
				String textFieldValue2 = textField2.getText();
				if (chckbx1.isSelected() && !chckbx2.isSelected()) {
					student_type = " AND `Student Type`='Full time'";
					updateTable(student_type, textFieldValue1, textFieldValue2);
				}
				else if (chckbx2.isSelected() && !chckbx1.isSelected()) {
					student_type = " AND `Student Type`='Part time'";
					updateTable(student_type, textFieldValue1, textFieldValue2);
				}
				else if (chckbx2.isSelected() && chckbx1.isSelected()) {
					student_type = "";
					updateTable(student_type, textFieldValue1, textFieldValue2);
				}
				else
					while (TModel.getRowCount()>0){
				    	  TModel.removeRow(0);
				      }		
				
			}
		});

		frame.add(scrollPane);	
		frame.add(buttonx);
		frame.add(chckbx1);
		frame.add(chckbx2);
		frame.add(textField1);
		frame.add(textField2);
		frame.add(rangeFrom);
		frame.add(rangeTo);
		addTable();
		
		frame.setVisible(true);
	}
	
	static void addTable() {
		TModel.setColumnIdentifiers(new Object[]{"Student Name", "Courses", "Type", "Fees"});		
	}

	
	static void updateTable(String student_type, String feeMin, String feeMax) {		
		try {  
		      while (TModel.getRowCount()>0){
		    	  TModel.removeRow(0);
		      }		      
		      ResultSet rs = stmt.executeQuery("SELECT * FROM Fees WHERE `Student Fees`>=" +
		    		  feeMin +
		    		  " AND `Student Fees`<=" +
		    		  feeMax +
		    		  " " +
		    		  student_type);
		      while(rs.next()){
		    	  int tempNum = rs.getInt("Student Fees");
		    	  TModel.addRow(new Object[]{
		          rs.getString("Student Name"),
		          rs.getString("Number of Courses"),
		          rs.getString("Student Type"),        
		          new Integer(tempNum)});
		      } 
		    }catch (Exception ex) {
		      System.err.println(ex);
		    }				
	}
	
	public static void instantiate() {
		frame.setVisible(false);
		frame = new JFrame();
		frame.setTitle("Student Fees");
		frame.setSize(490, 300);
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
