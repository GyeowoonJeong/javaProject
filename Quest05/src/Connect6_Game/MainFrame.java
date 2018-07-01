package Connect6_Game;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainFrame extends JFrame implements ActionListener{
	GamePanel gp;
	StartPanel sp;
	
	MainFrame() {
		this.setSize(1200, 850);
		getContentPane().setBackground(new Color(51, 51, 58));
		this.setLayout(null);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		
		sp = new StartPanel();
		sp.start.addActionListener(this);
		this.add(sp);
		this.setVisible(true);

	}
	
	public void enterGame() {
		getContentPane().removeAll();
		gp = new GamePanel();
		gp.setLocation(30, 30);
		this.add(gp);
		revalidate();
		repaint();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Start")) {
			enterGame();
		}
		
	}
	
	public static void main(String args[]) {
		new MainFrame();
	}

}
