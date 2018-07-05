package Connect6_Game;


import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class GameLogin extends JPanel{
	JTextField id;
	//JPasswordField password;
	JLabel title, idLabel;
	LineBorder border;
	JButton login;
	
	GameLogin() {
		setBackground(new Color(51, 51, 58));
		setLayout(null);
		border = new LineBorder(new Color(247, 234, 255), 4, true);	
		id = new JTextField();
		idLabel = new JLabel("ID");
		login = new JButton("LogIn");
		//password = new JPasswordField();
		title = new JLabel("Connect6");
		title.setFont(new Font("60sSTRIPE", Font.BOLD, 130));
		title.setBounds(200, 150, 600, 200);
		title.setForeground(Color.WHITE);
		
		id.setFont(new Font("I AM A PLAYER", Font.BOLD, 40));
		id.setForeground(Color.WHITE);
		id.setBorder(border);
		id.setBackground(new Color(51, 51, 58));
		id.setBounds(402, 400, 500, 70);
		id.setHorizontalAlignment(SwingConstants.CENTER);
		
		idLabel.setFont(new Font("I AM A PLAYER", Font.BOLD, 50));
		idLabel.setForeground(Color.WHITE);
		idLabel.setBounds(287, 400, 125, 70);
		
		login.setFont(new Font("I AM A PLAYER", Font.BOLD, 30));
		login.setBackground(new Color(247, 234, 255));
		login.setOpaque(true);
		login.setBorderPainted(false);
		login.setBounds(500, 650, 200, 70);
		
		this.add(title);
		this.add(id);
		this.add(idLabel);
		this.add(login);
	}
	
	
}
