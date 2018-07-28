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
	public static ChattingPanel cp;
	public static GameReadyPanel grp;
	public static GameStartPanel gstartp;
	GameClient client;
	
	int start = 0;
	
	MainFrame() {
		this.setSize(1300, 850);
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
		grp = new GameReadyPanel();
		gsp = new GameSubPanel();
		cp = new ChattingPanel();
		
		gp.setLocation(30, 30);
		grp.setLocation(140, 290);
		gsp.setLocation(890, 30);
		cp.setLocation(890, 340);
		
		this.add(grp);
		this.add(gp);
		this.add(gsp);
		this.add(cp);
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
	
	public void replay() {
		getContentPane().removeAll();
		gp.setLocation(30, 30);
		gsp.setLocation(890, 30);
		cp.setLocation(890, 340);
		gsp.turn = true;
		this.add(gp);
		this.add(gsp);
		this.add(cp);
		revalidate();
		repaint();
	}
	
	public void getReplayMessage() {
		int result = JOptionPane.showConfirmDialog(null, "상대방이 다시 게임하기를 요청했습니다. 받아들이시겠습니까? ", 
				"Replay", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
		if(result == 0) {
			client.sender.sendFunct("[REPLAY_ANS]," + client.receiver.threadNum + "," + result);
			replay();
		}
		else if(result == 1) {
			client.sender.sendFunct("[REPLAY_ANS]," + client.receiver.threadNum + "," + result);
		}
			
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Start")) {
			goToLogin();
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
			client = new GameClient();
			setPlayer();
			if(client.receiver.threadNum % 2 == 1) {
				spp.player1.namespace.setText(gl.id.getText());
				//client.sender.cmd = "[NAME]," + client.receiver.threadNum + "," + spp.player1.namespace.getText();
				client.sender.sendFunct("[NAME]," + client.receiver.threadNum + "," + spp.player1.namespace.getText());
				spp.start.setEnabled(false);
			}
			else if(client.receiver.threadNum % 2 == 0) {
				spp.player2.namespace.setText(gl.id.getText());
				client.sender.sendFunct("[NAME]," + client.receiver.threadNum + "," + spp.player2.namespace.getText());
				client.sender.sendFunct("[REQUEST]," + client.receiver.threadNum);
				client.sender.sendFunct("[START]," + client.receiver.threadNum);
			}
		}
		
		else if(e.getActionCommand().equals("Let's Play")) {
			enterGame();
			start++;
			client.sender.sendFunct("[WAIT]," + client.receiver.threadNum +",ready");
		}
		
		else if(e.getActionCommand().equals("Go Back")) {
			goToMain();
		}
		
		else if(e.getActionCommand().equals("EXIT")) {
			System.exit(0);
		}
		
		else if(e.getActionCommand().equals("REPLAY")) {
			client.sender.sendFunct("[REPLAY]," + client.receiver.threadNum);
		}
		
	}

}
