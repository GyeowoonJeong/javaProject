package Connect6_Game;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class WinnerPanel extends JPanel{
	JButton replay, exit;
	BufferedImage image;
	JLabel title, winner;
	JPanel titlePanel, winnerName;
	WinnerPanel() {
		setSize(1300, 850);
		setBackground(new Color(51, 51, 58));
		setLayout(null);
		if(MainFrame.gp.stones.get(MainFrame.gp.stones.size() - 1).color == Color.WHITE) {
			winner = new JLabel(MainFrame.gsp.label2.getText());
			image = MainFrame.gsp.p2;
		}
		else {
			winner = new JLabel(MainFrame.gsp.label1.getText());
			image = MainFrame.gsp.p1;
		}
		
		titlePanel = new JPanel();
		titlePanel.setBounds(200, 100, 900, 150);
		titlePanel.setBackground(new Color(51, 51, 58));
		winnerName = new JPanel();
		winnerName.setBounds(600, 400, 300, 200);
		winnerName.setBackground(new Color(51, 51, 58));
		
		title = new JLabel("The Winner Is");
		replay = new JButton("REPLAY");
		exit = new JButton("EXIT");
		
		title.setFont(new Font("60sSTRIPE", Font.BOLD, 120));
		winner.setFont(new Font("I AM A PLAYER", Font.BOLD, 80));
		replay.setFont(new Font("I AM A PLAYER", Font.BOLD, 40));
		exit.setFont(new Font("I AM A PLAYER", Font.BOLD, 40));
		
		title.setForeground(Color.WHITE);
		winner.setForeground(Color.WHITE);
		
		replay.setBackground(new Color(247, 234, 255));
		exit.setBackground(new Color(247, 234, 255));
		replay.setOpaque(true);
		replay.setBorderPainted(false);
		exit.setOpaque(true);
		exit.setBorderPainted(false);
		
		replay.setBounds(250, 720, 200, 70);
		exit.setBounds(850, 720, 200, 70);
		
		titlePanel.add(title);
		winnerName.add(winner);
		this.add(titlePanel);
		this.add(winnerName);
		this.add(replay);
		this.add(exit);	
	}
	
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		super.paintComponent(g);
		g2.drawImage(image, 400, 400, 128, 128, null);
		g.setColor(MainFrame.gp.color);
	}
}
