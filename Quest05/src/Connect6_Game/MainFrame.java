package Connect6_Game;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MainFrame extends JFrame implements ActionListener{
	GamePanel gp;
	StartPanel sp;
	SetPlayerPanel spp;
	
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
	
	public void setPlayer() {
		getContentPane().removeAll();
		spp = new SetPlayerPanel();
		setContentPane(spp);
		spp.start.addActionListener(this);
		spp.player1.color = Color.BLACK;
		spp.player2.color = Color.WHITE;
		revalidate();
		repaint();
	}
	
	public void goToMain() {
		getContentPane().removeAll();
		setContentPane(sp);
		revalidate();
		repaint();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Start")) {
			setPlayer();
		}
		
		else if(e.getActionCommand().equals("Let's Play")) {
			if(spp.player1.namespace.getText().equals("") || spp.player2.namespace.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "플레이어 이름이 설정되지 않았습니다.", "경고", JOptionPane.WARNING_MESSAGE);
				return;
			}
			else if(spp.player1.namespace.getText().equals(spp.player2.namespace.getText())) {
				JOptionPane.showMessageDialog(null, "두 플레이어의 이름이 같습니다.", "경고", JOptionPane.WARNING_MESSAGE);
				spp.player2.namespace.setText("");
				spp.player2.namespace.setEditable(true);
			}
			enterGame();
		}
		
		else if(e.getActionCommand().equals("Go Back")) {
			goToMain();
		}
		
	}
	
	public static void main(String args[]) {
		new MainFrame();
	}

}
