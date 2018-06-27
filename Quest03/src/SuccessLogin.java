import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class SuccessLogin extends JFrame{
	JButton edit, signout, logout2;
	
	SuccessLogin() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setBackground(Color.getHSBColor(150, 194, 255));
		this.setSize(400, 600);
		this.setTitle("My Page");
		setLocationRelativeTo(null);
		
		edit = new JButton("정보 수정하기");
		signout = new JButton("회원 탈퇴");
		logout2 = new JButton("LogOut");
		
		this.setSize(300, 500);
		this.setLayout(new GridLayout(2, 1));

		this.add(edit);
		this.add(signout);
		//this.add(logout2);
	}
}
