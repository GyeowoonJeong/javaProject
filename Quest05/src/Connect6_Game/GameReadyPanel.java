package Connect6_Game;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameReadyPanel extends JPanel{
	JLabel ready;
	
	GameReadyPanel() {
		setSize(500, 200);
		setBackground(new Color(51, 51, 58));
		setLayout(null);
		
		ready = new JLabel("Ready");
		ready.setFont(new Font("I AM A PLAYER", Font.BOLD, 60));
		ready.setForeground(new Color(242, 109, 109));
		ready.setBounds(150, 70, 200, 60);
		
		this.add(ready);
	}

}
