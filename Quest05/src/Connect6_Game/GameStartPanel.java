package Connect6_Game;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameStartPanel extends JPanel{
	JLabel start;
	
	GameStartPanel() {
		setSize(500, 200);
		setBackground(new Color(51, 51, 58));
		setLayout(null);
		
		start = new JLabel("Start");
		start.setFont(new Font("I AM A PLAYER", Font.BOLD, 60));
		start.setForeground(new Color(242, 109, 109));
		start.setBounds(150, 70, 200, 60);
		
		this.add(start);
	}

}
