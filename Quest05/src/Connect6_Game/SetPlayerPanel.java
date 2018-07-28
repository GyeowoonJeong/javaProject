package Connect6_Game;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JPanel;

public class SetPlayerPanel extends JPanel{
	Player player1;
	Player player2;
	JButton start, cancel;
	
	public SetPlayerPanel() {
		setSize(1300, 850);
		setBackground(new Color(51, 51, 58));
		setLayout(null);
		player1 = new Player("img/boy.png");
		player2 = new Player("img/girl.png");
		start = new JButton("Let's Play");
		cancel = new JButton("Go Back");
		
		player1.setLocation(150, 30);
		player2.setLocation(150, 375);
		start.setBounds(550, 720, 200, 70);
		cancel.setBounds(750, 720, 200, 70);
		
		start.setBackground(new Color(247, 234, 255));
		start.setOpaque(true);
		start.setBorderPainted(false);
		start.setFont(new Font("I AM A PLAYER", Font.BOLD, 20));
		/*
		cancel.setBackground(new Color(247, 234, 255));
		cancel.setOpaque(true);
		cancel.setBorderPainted(false);
		cancel.setFont(new Font("I AM A PLAYER", Font.BOLD, 20));
		*/
		this.add(player1);
		this.add(player2);
		this.add(start);
		//this.add(cancel);
	}

}
