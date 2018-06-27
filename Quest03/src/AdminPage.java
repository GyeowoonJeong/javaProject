import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

public class AdminPage extends JFrame implements MouseListener, ActionListener{
	private String mysqlDriver = "com.mysql.cj.jdbc.Driver";
	private String url = "jdbc:mysql://localhost:3306/login?serverTimezone=UTC&&useSSL=false&&characterEncoding=utf-8";
	private String db_id = "root";
	private String db_pw = "rudnsgkfka0306";
	Connection conn;
    Statement state;
    ResultSet rs;
    int r = 0;
	int selectedRow;
    
    DefaultTableModel defaultTableModel = new DefaultTableModel(new String[] { "ID", "Name", "NickName", "BirthDay", "StudentID", "Major"}, 0)
	{
    	@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	};
    
	JLabel title;
	JTable jTable;
	JScrollPane jScrollPane;
	JTextField jTextField;
	JButton jButton, logout;
	JComboBox<String> jComboBox;
	JPanel jPanel, jPanel2;
	JPopupMenu popupMenu;
	JMenuItem edit;
	JMenuItem delete;
	UpdateInfoByAdmin ud = new UpdateInfoByAdmin();
	UserInfo userInfo = new UserInfo();
	
    AdminPage() {
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	try {
    		Class.forName(mysqlDriver);
    		conn = DriverManager.getConnection(url, db_id, db_pw);
    		state = (Statement) conn.createStatement();
    		if(conn==null)
                System.out.println("연결실패");
    		else
                System.out.println("연결성공");
    	} 
    	
    	catch (Exception e) {
    	System.out.println("db연결실패");
    	}
    	adminFormat();
    	try {
			addTable();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    public void adminFormat() {
    	title = new JLabel("User List");
	
		//JTable에 DefaultTableModel을 담기
		jTable = new JTable(defaultTableModel);
		jScrollPane = new JScrollPane(jTable);
		jTextField = new JTextField(20);
		jButton = new JButton("검색");
		logout = new JButton("LogOut");
		jComboBox = new JComboBox<String>(new String[] {"ID", "Name", "Major", "전체검색"}); 
		jPanel = new JPanel(new FlowLayout());
		jPanel2 = new JPanel(new FlowLayout());
		jPanel2.add(title);
		jPanel2.add(logout);
		add(jScrollPane);
		jPanel.add(jComboBox);
		jPanel.add(jTextField);
		jPanel.add(jButton);
		add(jPanel, "South");
		add(jPanel2, "North");

		setSize(700, 800);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		title.setFont(new Font("HanziPen TC", Font.BOLD, 30));
		logout.setFont(new Font("HanziPen TC", Font.BOLD, 15));
		jPanel.setFont(new Font("HanziPen TC", Font.BOLD, 15));
		jComboBox.setFont(new Font("HanziPen TC", Font.BOLD, 15));
		jPanel.setBackground(Color.getHSBColor(150, 194, 255));
		jPanel2.setBackground(Color.getHSBColor(50, 27, 88));
		
		jTable.getTableHeader().setFont(new Font("HanziPen TC", Font.BOLD, 15));
		jTable.getTableHeader().setBackground(Color.getHSBColor(150, 194, 255));
		jTable.setRowHeight(20);
		jTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jTable.setColumnSelectionAllowed(false);
		jTable.getTableHeader().setReorderingAllowed(false);

		
		jTable.addMouseListener(this);
		ud.save.addActionListener(this);
		ud.exit.addActionListener(this);
		jButton.addActionListener(this);
    }
    
    private void addTable() throws SQLException {
    	rs = state.executeQuery("SELECT * FROM user");
    	while(rs.next()) {
    		defaultTableModel.addRow(new Object[] {rs.getString("user_id"), rs.getString("user_name"), rs.getString("nickname"), 
    				rs.getString("birthdate"), rs.getString("student_id"), rs.getString("student_dpt")});
    	}
    }
    
    private void deleteTable() {
    	DefaultTableModel dm = (DefaultTableModel)jTable.getModel();
    	while(dm.getRowCount() > 0)
    	{
    	    dm.removeRow(0);
    	}
    }
    public void selectAction()  {
    	popupMenu = new JPopupMenu();
    	edit = new JMenuItem("Edit");
    	delete = new JMenuItem("Delete");
    	
    	edit.addActionListener(this);
    	delete.addActionListener(this);
    	popupMenu.add(edit);
    	popupMenu.add(delete);
    }
    
    public void getInfo(int row) {
    	row = jTable.convertRowIndexToModel(row);
    	String val1 = (String)jTable.getModel().getValueAt(row, 0);
    	String val2 = (String)jTable.getModel().getValueAt(row, 1);
    	String val3 = (String)jTable.getModel().getValueAt(row, 2);
    	String val4 = (String)jTable.getModel().getValueAt(row, 3);
    	String val5 = (String)jTable.getModel().getValueAt(row, 4);
    	String val6 = (String)jTable.getModel().getValueAt(row, 5);
    	
    	ud.id.setText(val1);
    	ud.name.setText(val2);
    	ud.nickname.setText(val3);
    	ud.datePicker.getJFormattedTextField().setText(val4);
    	ud.student_id.setText(val5);
    	ud.depart.select(val6);
    	
    	userInfo.id = ud.id.getText().trim();
    }
    
    public void updateInfo() throws SQLException {
    	userInfo.nickname = ud.nickname.getText().trim();
    	userInfo.birthdate = ud.datePicker.getJFormattedTextField().getText();
    	userInfo.student_id = Integer.parseInt(ud.student_id.getText().trim());
    	userInfo.depart = ud.depart.getSelectedItem();
    	
    	r = state.executeUpdate("UPDATE user SET nickname='" + userInfo.nickname + "', birthdate='" + userInfo.birthdate + "', student_id=" +  userInfo.student_id 
    			+", student_dpt='" + userInfo.depart +"' WHERE user_id='" + ud.id.getText() + "'");
    	if(r==1) {
    		JOptionPane.showMessageDialog(null, "수정된 정보를 저장했습니다.");
    		ud.setVisible(false);
    	}
    }
    
    public void deleteInfo() throws SQLException {
    	int d = JOptionPane.showConfirmDialog(null, "정말로 삭제하시겠습니까?", "경고", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
    	state = (Statement) conn.createStatement();
    	if(d == 0) {
            r = state.executeUpdate("DELETE FROM user WHERE user_id='" + userInfo.id +"'");
            if(r == 1) {
            	deleteTable();
            	addTable();
            	JOptionPane.showMessageDialog(null, "성공적으로 삭제되었습니다.");
            }
            else {
            	JOptionPane.showMessageDialog(null,"탈퇴에 실패했습니다. 우린 운명인가 봅니다.");
            }
    	}
    }
    
    public void searchInfo() throws SQLException {
    	String searchText = jTextField.getText().trim();
    	String index = jComboBox.getSelectedItem().toString();
    	
    	if(searchText.equals(null))
    		addTable();
    	else {
	    	switch(index) {
	    	case "ID":
	    		rs = state.executeQuery("SELECT * FROM user WHERE user_id LIKE '%" + searchText +"%'");
	    		deleteTable();
	    		while(rs.next()) {
	        		defaultTableModel.addRow(new Object[] {rs.getString("user_id"), rs.getString("user_name"), rs.getString("nickname"), 
	        				rs.getString("birthdate"), rs.getString("student_id"), rs.getString("student_dpt")});
	        	}
	    		break;
	    	case "Name":
	    		rs = state.executeQuery("SELECT * FROM user WHERE user_name LIKE '%" + searchText +"%'");
	    		deleteTable();
	    		while(rs.next()) {
	        		defaultTableModel.addRow(new Object[] {rs.getString("user_id"), rs.getString("user_name"), rs.getString("nickname"), 
	        				rs.getString("birthdate"), rs.getString("student_id"), rs.getString("student_dpt")});
	        	}
	    		break;
	    	case "Major":
	    		rs = state.executeQuery("SELECT * FROM user WHERE student_dpt LIKE '%" + searchText +"%'");
	    		deleteTable();
	    		while(rs.next()) {
	        		defaultTableModel.addRow(new Object[] {rs.getString("user_id"), rs.getString("user_name"), rs.getString("nickname"), 
	        				rs.getString("birthdate"), rs.getString("student_id"), rs.getString("student_dpt")});
	        	}
	    		break;
	    	case "전체검색" :
	    		rs = state.executeQuery("SELECT DISTINCT * FROM user WHERE user_id LIKE '%" + searchText +"%' OR user_name LIKE '%" + searchText
	    				+ "%' OR student-id LIKE '%" + searchText +"%'");
	    		deleteTable();
	    		while(rs.next()) {
	        		defaultTableModel.addRow(new Object[] {rs.getString("user_id"), rs.getString("user_name"), rs.getString("nickname"), 
	        				rs.getString("birthdate"), rs.getString("student_id"), rs.getString("student_dpt")});
	    		}
	    		break;
	    	}
    	}
    }
    
	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if(cmd.equals("Edit")) {
			ud.setVisible(true);
		}
		else if(cmd.equals("Delete")) {
			try {
				deleteInfo();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		else if(cmd.equals("저장하기")) {
			try {
				updateInfo();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		else if(cmd.equals("돌아가기")) {
			ud.setVisible(false);
		}
		else if(cmd.equals("검색")) {
			try {
				searchInfo();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getButton() == 3) {
			selectedRow = jTable.getSelectedRow();
			if(selectedRow > 0) {
				selectAction();
				getInfo(selectedRow);
				popupMenu.show(e.getComponent(), e.getX(), e.getY());
			}
			 
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
	
}
