import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import java.sql.*;
import java.util.Calendar;

public class Main extends JFrame implements ActionListener{
	private String mysqlDriver = "com.mysql.cj.jdbc.Driver";
	private String url = "jdbc:mysql://localhost:3306/login?serverTimezone=UTC&&useSSL=false&&characterEncoding=utf-8";
	private String db_id = "root";
	private String db_pw = "rudnsgkfka0306";
	
	Connection conn;
    Statement state;
    ResultSet rs;
    int r = 0;
    
    Login lgn = new Login();
    Signin snn = new Signin();
    SuccessLogin slgn = new SuccessLogin();
    UpdateInfo ui = new UpdateInfo();
    AdminPage ad;
    IDSearch is = new IDSearch();
    PWSearch ps = new PWSearch();
    
    String id, password, checkPW;
    int student_id;
	String depart;
    
    Main() {
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

    	lgn.login.addActionListener(this);
    	lgn.signin.addActionListener(this);
    	lgn.idSearch.addActionListener(this);
    	lgn.pwSearch.addActionListener(this);
    	snn.checkID.addActionListener(this);
    	snn.exit.addActionListener(this);
    	snn.save.addActionListener(this);
    	slgn.signout.addActionListener(this);
    	slgn.edit.addActionListener(this);
    	slgn.logout2.addActionListener(this);
    	ui.save.addActionListener(this);
    	ui.exit.addActionListener(this);
    	is.ok2.addActionListener(this);
    	is.cancel2.addActionListener(this);
    	ps.ok1.addActionListener(this);
    	ps.cancel1.addActionListener(this);
    }
    
    public void checkLogin() throws SQLException {
    	id = lgn.id.getText().trim();
    	password = String.valueOf(lgn.password.getPassword());
    	
    	rs = state.executeQuery("SELECT * FROM user WHERE user_id='"+ id + "'");
    	
    	if(!rs.next())
    		JOptionPane.showMessageDialog(null, "잘못된 ID 입니다.");
    	
    	else {
    		if(password.equals(rs.getString("user_pw"))) {
    			password = rs.getString("user_pw");
    			if(id.equals("admin")) {
    				ad = new AdminPage();
    				ad.logout.addActionListener(this);
    				lgn.setVisible(false);
    				ad.setVisible(true);
    			}
    			else {
	    			lgn.setVisible(false);
	    			slgn.setVisible(true);
    			}
    		}
    		else
    			JOptionPane.showMessageDialog(null, "잘못된 Password 입니다.");
    	}
    }
    		
 
    
    public void isAvailableID() throws SQLException {
    	id = snn.id.getText().trim();
    	rs = state.executeQuery("SELECT user_id FROM user WHERE user_id='" + id + "'");
    	
    	if(!(id.matches("[a-zA-Z0-9]+"))) 
    			JOptionPane.showMessageDialog(null, "ID는 영문자 혹은 숫자의 조합이여야 합니다!");
    	
    	else if (id.length() < 5)
    			JOptionPane.showMessageDialog(null, "ID 길이가 너무 짧습니다!");
    
    	else {
	    	if(rs.next()) {
	    		JOptionPane.showMessageDialog(null, "이미 사용 중인 ID 입니다.");
	    		snn.id.setText("");
	    	}
	    	else {
	    		JOptionPane.showMessageDialog(null, "사용가능한 ID 입니다.");
	    	}
    	}
    }
    
    public void signup() throws SQLException {
    	UserInfo userInfo = new UserInfo();
    	checkPW = String.valueOf(snn.checkPassword.getPassword());
    	userInfo.id = snn.id.getText().trim();
    	userInfo.password = String.valueOf(snn.password.getPassword());
    	userInfo.name = snn.name.getText().trim();
    	userInfo.nickname = snn.nickname.getText().trim();
    	userInfo.birthdate = snn.datePicker.getJFormattedTextField().getText();
    	userInfo.student_id = Integer.parseInt(snn.student_id.getText().trim());
    	userInfo.depart = snn.depart.getSelectedItem();
    	
    	if((userInfo.password.matches("[a-zA-Z]+")) || (userInfo.password.matches("[0-9]+")))  
    		JOptionPane.showMessageDialog(null, "비밀번호에는 영문자와 숫자가 반드시 들어가야 합니다.");
    	
    	else if(userInfo.password.length() < 8) 
    		JOptionPane.showMessageDialog(null, "비밀번호는 8자 이상여야 합니다.");
    	
    	else if(!checkPW.equals(userInfo.password)) {
    		JOptionPane.showMessageDialog(null, "비밀번호가 맞지 않습니다.");
    		snn.password.setFocusable(true);
    	}
    	
    	else {
	    	state = (Statement) conn.createStatement();
	    	
	        r = state.executeUpdate("INSERT INTO user VALUES ('" + userInfo.id + "', '" + userInfo.password + "', '" + userInfo.name + "', '" 
	    	+ userInfo.nickname + "', '" + userInfo.birthdate + "', " + userInfo.student_id + ", '" + userInfo.depart + "')");
	        if( r == 1 ) {
	        	JOptionPane.showMessageDialog(null, "!!회원가입을 축하합니다!!");
	        	setEmpty();
	        	snn.setVisible(false);
	        	lgn.setVisible(true);
	        }
	        else {
	        	JOptionPane.showMessageDialog(null, "**잘못된 입력입니다**");
	        }
 
    	}
    }
    
    public void deleteInfo() throws SQLException {
    	int d = JOptionPane.showConfirmDialog(null, "정말로 탈퇴하시겠습니까?", "경고", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
    	state = (Statement) conn.createStatement();
    	if(d == 0) {
            r = state.executeUpdate("DELETE FROM user WHERE user_id='" + id +"'");
            if(r == 1) {
            	JOptionPane.showMessageDialog(null, "다음에 다시 만나요~");
            	lgn.id.setText(null);
            	lgn.password.setText(null);
            	slgn.setVisible(false);
            	lgn.setVisible(true);
            }
            else {
            	JOptionPane.showMessageDialog(null,"탈퇴에 실패했습니다. 우린 운명인가 봅니다.");
            }
    	}
    }
    
    public void checkPW() throws SQLException {
    	JPasswordField pwd = new JPasswordField(20);
    	int d = JOptionPane.showConfirmDialog(null, pwd, "비밀번호를 입력해 주세요.", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
    	if(d >= 0) {
    		if(String.valueOf(pwd.getPassword()).equals(password)) {
    			ui.setVisible(true);
    		}
    	}
    }
    
    public void getInfo() throws SQLException {
    	//UserInfo userInfo = new UserInfo();   
    	rs = state.executeQuery("SELECT * FROM user WHERE user_id='" + id + "'");
    	if(rs.next()) {
	    	ui.id.setText(rs.getString("user_id"));
	    	ui.name.setText(rs.getString("user_name"));
	    	ui.nickname.setText(rs.getString("nickname"));
	    	ui.datePicker.getJFormattedTextField().setText(rs.getString("birthdate"));
	    	ui.student_id.setText(rs.getString("student_id"));
	    	ui.depart.select(rs.getString("student_dpt"));	
    	}
    }
    
    public void updateInfo() throws SQLException {
    	UserInfo userInfo = new UserInfo();
    	checkPW = String.valueOf(ui.checkPassword.getPassword());
    	userInfo.password = String.valueOf(ui.password.getPassword());
    	userInfo.nickname = ui.nickname.getText().trim();
    	userInfo.birthdate = ui.datePicker.getJFormattedTextField().getText();
    	userInfo.student_id = Integer.parseInt(ui.student_id.getText().trim());
    	userInfo.depart = ui.depart.getSelectedItem();
    	System.out.println(userInfo.depart);
    	if(!checkPW.equals(userInfo.password)) {
    		JOptionPane.showMessageDialog(null, "비밀번호가 맞지 않습니다.");
    		snn.password.setFocusable(true);
    	}
    	
    	else {
    		r = state.executeUpdate("UPDATE user SET user_pw='" + userInfo.password + "', " + "nickname='" + 
    				userInfo.nickname + "', birthdate='" + userInfo.birthdate + "', student_id=" +  userInfo.student_id 
    				+", student_dpt='" + userInfo.depart +"' WHERE user_id='" + id + "'");
    		if(r==1) {
    			JOptionPane.showMessageDialog(null, "수정된 정보를 저장했습니다.");
    			ui.setVisible(false);
    			slgn.setVisible(true);
    		}
    	}
    }
    
    public void setEmpty() {
    	snn.id.setText(null);
    	snn.password.setText(null);
    	snn.checkPassword.setText(null);
    	snn.name.setText(null);
    	snn.nickname.setText(null);
    	snn.student_id.setText(null);
    }
    
    public void searchPW() throws SQLException {
    	String userid = ps.userid.getText().trim();
    	rs = state.executeQuery("SELECT user_pw FROM user WHERE user_id='" + userid + "'");
    	
    	ps.setVisible(false);
    	if(rs.next()) {
    		String pw = rs.getString("user_pw");
    		char[] temp = pw.toCharArray();
    		for(int i = temp.length - 1; i >temp.length - 5; i--) {
    			temp[i] = '*';
    		}
    		pw = String.valueOf(temp);
    		JOptionPane.showMessageDialog(null, "사용자의 비밀번호는 \n\n " + pw + "\n \n입니다.");
    	}
    	else
    		JOptionPane.showMessageDialog(null, "존재하지 않는 사용자 정보입니다.");
    	ps.userid.setText(null);
    	
    }
    
    public void searchID() throws SQLException {
    	String name = is.userName.getText().trim();
    	String stdid = is.studentID.getText().trim();
    	rs = state.executeQuery("SELECT user_id FROM user WHERE user_name='" + name + "' AND student_id='" + stdid +"'");
    	
    	is.setVisible(false);
    	if (rs.next()) {
    		String userID = rs.getString("user_id");
    		JOptionPane.showMessageDialog(null, "사용자의 아이디는 \n\n " + userID + "\n \n입니다.");
    	}
    	else
    		JOptionPane.showMessageDialog(null, "존재하지 않는 사용자 정보입니다.");
    	is.userName.setText(null);
    	is.studentID.setText(null);
    }
    
    
	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		switch(cmd) {
		case "Login":
			try {
				checkLogin();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			break;
			
		case "Sign_in":
			lgn.setVisible(false);
			snn.setVisible(true);
			break;
			
		case "ID Search":
			is.setVisible(true);
			break;
		case "OK":
			try {
				if(e.getSource() == is.ok2)
					searchID();
				else if(e.getSource() == ps.ok1)
					searchPW();
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
			break;
		case "CANCEL":
			if(e.getSource() == is.cancel2)
				is.setVisible(false);
			else if(e.getSource() == ps.cancel1)
				ps.setVisible(false);
			break;
		case "PW Search":
			ps.setVisible(true);
			break;
		case "가입하기":
			try {
				signup();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			break;
		case "그만두기":
			setEmpty();
			snn.setVisible(false);
			lgn.id.setText(null);
			lgn.password.setText(null);
			lgn.setVisible(true);
			break;
		case "ID Check": 
			try {
				isAvailableID();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			break;
		case "회원 탈퇴":
			try {
				deleteInfo();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			break;
		case "정보 수정하기":
			slgn.setVisible(false);
			try {
				checkPW();
				getInfo();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			break;
		case "저장하기" :
			try {
				updateInfo();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			break;
		case "돌아가기":
			ui.setVisible(false);
			slgn.setVisible(true);
			break;
		
		case "LogOut":
			if(e.getSource() == ad.logout) {
				lgn.id.setText(null);
				lgn.password.setText(null);
				ad.setVisible(false);
			}
			else if(e.getSource() == slgn.logout2) {
				lgn.id.setText(null);
				lgn.password.setText(null);
				slgn.setVisible(false);
			}
		}
			
	}
	
	public static void main(String[] args) {
		
		new Main();
	}

}
