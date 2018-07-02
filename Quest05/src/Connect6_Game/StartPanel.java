package Connect6_Game;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class StartPanel extends JPanel{
	JLabel title;
	JButton start, howtoplay;
	StartPanel(){
		setBackground(new Color(51, 51, 58));
		title = new JLabel("Connect6");
		title.setFont(new Font("60sSTRIPE", Font.BOLD, 150));
		title.setForeground(Color.WHITE);
		this.setSize(1200, 850);
		this.setLayout(null);
		start = new JButton("Start");
		howtoplay = new JButton("How To Play");
		
		start.setFont(new Font("60sSTRIPE", Font.BOLD, 40));
		howtoplay.setFont(new Font("60sSTRIPE", Font.BOLD, 40));
		start.setBackground(new Color(247, 234, 255));
		howtoplay.setBackground(new Color(247, 234, 255));
		start.setOpaque(true);
		howtoplay.setOpaque(true);
		start.setBorderPainted(false);
		howtoplay.setBorderPainted(false);
		
		title.setBounds(200, 200, 600, 150);
		start.setBounds(200, 550, 300, 100);
		howtoplay.setBounds(700, 550, 300, 100);
		
		this.add(title);
		this.add(start);
		this.add(howtoplay);
		//this.setVisible(true);
	}

}
