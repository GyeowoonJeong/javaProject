package Connect6_Game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class GameSubPanel extends JPanel{
	BufferedImage p1, p2;
	JLabel label1, label2;
	JButton undo;
	boolean turn = true;
	LineBorder border;
	
	
	GameSubPanel() {
		this.setSize(360, 325);
		this.setLayout(null);
		this.setBackground(new Color(51, 51, 58));
		border = new LineBorder(new Color(247, 234, 255), 4, true);
		undo = new JButton("UNDO");
		label1 = new JLabel(MainFrame.spp.player1.namespace.getText());
		label2 = new JLabel(MainFrame.spp.player2.namespace.getText());
		label1.setBackground(new Color(252, 238, 169));
		label1.setOpaque(true);
		label1.setHorizontalAlignment(SwingConstants.CENTER);
		label1.setFont(new Font("I AM A PLAYER", Font.BOLD, 15));
		label2.setBackground(new Color(252, 238, 169));
		label2.setOpaque(true);
		label2.setHorizontalAlignment(SwingConstants.CENTER);
		label2.setFont(new Font("I AM A PLAYER", Font.BOLD, 15));
		
		undo.setBorder(border);
		undo.setFont(new Font("I AM A PLAYER", Font.BOLD, 25));
		undo.setForeground(Color.WHITE);
		undo.addActionListener(MainFrame.gp);
		
		label1.setBounds(5, 148, 90, 30);
		label2.setBounds(225, 148, 90, 30);
		undo.setBounds(130, 220, 100, 50);
		
		this.add(label1);
		this.add(label2);
		this.add(undo);
		try {	
			p1 = ImageIO.read(new File("/Users/jeong-gyeoun/Downloads/boy (2).png"));	
			p2 = ImageIO.read(new File("/Users/jeong-gyeoun/Downloads/girl (2).png"));	
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
	}
	
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		super.paintComponent(g);
		g2.drawImage(p1, 5, 5, 128, 128, null);
		g2.drawImage(p2, 225, 5, 128, 128, null);
		g.setColor(Color.BLACK);
		g.fillOval(101, 148, 30, 30);
		g.setColor(Color.WHITE);
		g.fillOval(323, 148, 30, 30);
		if(turn) {
			g.setColor(new Color(247, 234, 255));
			g2.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
			g.drawRect(220, 1, 138, 185);
		}
		else {
			g.setColor(new Color(247, 234, 255));
			g2.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
			g.drawRect(1, 1, 138, 185);
		}
	}
	
}
