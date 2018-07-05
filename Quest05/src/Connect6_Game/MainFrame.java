package Connect6_Game;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MainFrame extends JFrame implements ActionListener{
	public static GamePanel gp;
	public static GameSubPanel gsp;
	public static StartPanel sp;
	public static SetPlayerPanel spp;
	public static WinnerPanel wp;
	public static HowToPlay htp;
	public static GameLogin gl;
	GameClient client;
	
	MainFrame() {
		this.setSize(1200, 850);
		getContentPane().setBackground(new Color(51, 51, 58));
		this.setLayout(null);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		
		sp = new StartPanel();
		sp.start.addActionListener(this);
		sp.howtoplay.addActionListener(this);
		this.add(sp);
		this.setVisible(true);

	}
	
	public void goToLogin() {
		getContentPane().removeAll();
		gl = new GameLogin();
		gl.login.addActionListener(this);
		setContentPane(gl);
		revalidate();
		repaint();
	}
	
	public void enterGame() {
		getContentPane().removeAll();
		gp = new GamePanel();
		gsp = new GameSubPanel();
		gp.setLocation(30, 30);
		gsp.setLocation(810, 30);
		this.add(gp);
		this.add(gsp);
		revalidate();
		repaint();
	}
	
	public void setPlayer() {
		getContentPane().removeAll();
		spp = new SetPlayerPanel();
		setContentPane(spp);
		spp.start.addActionListener(this);
		spp.cancel.addActionListener(this);
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
	
	public void showWinner() {
		getContentPane().removeAll();
		wp = new WinnerPanel();
		wp.exit.addActionListener(this);
		wp.replay.addActionListener(this);
		setContentPane(wp);
		revalidate();
		repaint();
	}
	
	public void showHowToPlay() {
		htp = new HowToPlay();
		htp.understand.addActionListener(this);
		htp.setLocation(350, 225);
		this.add(htp);
		this.htp.setVisible(true);
	}
	
	public void replay() {
		getContentPane().removeAll();
		gp.setLocation(30, 30);
		gsp.setLocation(810, 30);
		this.add(gp);
		this.add(gsp);
		revalidate();
		repaint();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Start")) {
			goToLogin();
			client = new GameClient();
		}
	
		else if(e.getActionCommand().equals("How To Play")) {
			JOptionPane.showMessageDialog(null, "두 사람이 바둑판에 흑돌과 백돌을 교대로 놓아 여섯개가 일렬로 놓이게 만드는 게임" + "\n\n" + 
					"흑은 처음에 한수를 두고, 그 다음에는 두수씩 둠" + "\n\n", "HowToPlay", JOptionPane.PLAIN_MESSAGE);
		}
		
		else if(e.getActionCommand().equals("LogIn")) {
			if(gl.id.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "플레이어 이름이 설정되지 않았습니다.", "경고", JOptionPane.WARNING_MESSAGE);
				return;
			}
			setPlayer();

			if(client.receiver.threadNum % 2 == 1) {
				spp.player1.namespace.setText(gl.id.getText());
				//client.sender.cmd = "[NAME]," + client.receiver.threadNum + "," + spp.player1.namespace.getText();
				client.sender.sendFunct("[NAME]," + client.receiver.threadNum + "," + spp.player1.namespace.getText());
			}
			else if(client.receiver.threadNum % 2 == 0) {
				spp.player2.namespace.setText(gl.id.getText());
				client.sender.sendFunct("[NAME]," + client.receiver.threadNum + "," + spp.player2.namespace.getText());
			}
		}
		
		else if(e.getActionCommand().equals("Let's Play")) {
			enterGame();
		}
		
		else if(e.getActionCommand().equals("Go Back")) {
			goToMain();
		}
		
		else if(e.getActionCommand().equals("EXIT")) {
			System.exit(0);
		}
		
		else if(e.getActionCommand().equals("REPLAY")) {
			replay();
		}
		
	}

}
