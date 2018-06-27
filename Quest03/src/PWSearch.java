import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class PWSearch extends JFrame {
	JTextField userid;
	JButton ok1, cancel1;
	JLabel label1;
	
	PWSearch() {
		userid = new JTextField();
		ok1 = new JButton("OK");
		cancel1 = new JButton("CANCEL");
		label1 = new JLabel("ID");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(400, 300);
		this.setLayout(null);
		this.setTitle("비밀번호 찾기");
		setLocationRelativeTo(null);
		getContentPane().setBackground(Color.getHSBColor(53, 22, 93));
		
		label1.setBounds(100, 70, 70, 40);
		userid.setBounds(170, 70, 130, 40);
		ok1.setBounds(110, 160, 70, 40);
		cancel1.setBounds(220, 160, 70, 40);
		
		this.add(label1);
		this.add(userid);
		this.add(ok1);
		this.add(cancel1);
		
	}
}

