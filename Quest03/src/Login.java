import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Login extends JFrame{
	JTextField id;
	JPasswordField password;
	JButton login, signin, idSearch, pwSearch;
	JLabel label1, label2;
	
	Login() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		id = new JTextField();
		password = new JPasswordField();
		label1 = new JLabel("ID");
		label2 = new JLabel("Password");
		login = new JButton("Login");
		signin = new JButton("Sign_in");
		idSearch = new JButton("ID Search");
		pwSearch = new JButton("PW Search");

		this.setSize(700, 800);
		this.setLayout(null);
		setLocationRelativeTo(null);
		
		label1.setBounds(100, 250, 100, 50);
		label2.setBounds(100, 350, 100, 50);
		id.setBounds(200, 250, 400, 50);
		password.setBounds(200, 350, 400, 50);
		login.setBounds(100, 500, 70, 50);
		signin.setBounds(240, 500, 70, 50);
		idSearch.setBounds(380, 500, 75, 50);
		pwSearch.setBounds(500, 500, 75, 50);
		
		label1.setFont(new Font("HanziPen TC", Font.BOLD, 20));
		label2.setFont(new Font("HanziPen TC", Font.BOLD, 20));
		id.setFont(new Font("HanziPen TC", Font.BOLD, 20));
		password.setFont(new Font("HanziPen TC", Font.BOLD, 17));
		login.setFont(new Font("HanziPen TC", Font.BOLD, 12));
		signin.setFont(new Font("HanziPen TC", Font.BOLD, 12));
		idSearch.setFont(new Font("HanziPen TC", Font.BOLD, 12));
		pwSearch.setFont(new Font("HanziPen TC", Font.BOLD, 12));
		
		getContentPane().setBackground(Color.getHSBColor(150, 194, 255));
		
		
		this.add(label1);
		this.add(id);
		this.add(label2);
		this.add(password);
		this.add(login);
		this.add(signin);
		this.add(idSearch);
		this.add(pwSearch);
		
		this.setVisible(true);
	}
	
	
}
