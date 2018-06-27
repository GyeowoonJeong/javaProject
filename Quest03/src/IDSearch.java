import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class IDSearch extends JFrame {
	JTextField userName, studentID;
	JButton ok2, cancel2;
	JLabel label1, label2;
	
	IDSearch() {
		userName = new JTextField();
		studentID = new JTextField();
		ok2 = new JButton("OK");
		cancel2 = new JButton("CANCEL");
		label1 = new JLabel("Name");
		label2 = new JLabel("Student ID");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(400, 300);
		this.setLayout(null);
		this.setTitle("아이디 찾기");
		setLocationRelativeTo(null);
		getContentPane().setBackground(Color.getHSBColor(53, 22, 93));
		
		label1.setBounds(100, 70, 70, 40);
		label2.setBounds(100, 130, 70, 40);
		userName.setBounds(170, 70, 130, 40);
		studentID.setBounds(170, 130, 130, 40);
		ok2.setBounds(110, 190, 70, 40);
		cancel2.setBounds(220, 190, 70, 40);
		
		this.add(label1);
		this.add(label2);
		this.add(userName);
		this.add(studentID);
		this.add(ok2);
		this.add(cancel2);
		
	}
}
