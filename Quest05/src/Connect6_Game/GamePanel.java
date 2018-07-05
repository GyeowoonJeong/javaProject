package Connect6_Game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements MouseMotionListener, MouseListener, ActionListener{
	int currentX, currentY;
	int setX, setY;
	int tempX, tempY;
	int centerX, centerY;
	final int currentCursor = 30;
	static boolean turn = true;
	boolean reset = false;
	int setCount1 = 0;
	int setCount2 = 0;
	Color color = Color.WHITE;
	Color[][] setted;
	ArrayList<SetStone> stones;
	
	public GamePanel() {
		this.setSize(750, 750);
		this.setBackground(new Color(252, 238, 169));
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		stones = new ArrayList<>();
		setted = new Color[19][19];
		setted[9][9] = Color.BLACK;
		stones.add(new SetStone(375, 375, Color.BLACK));
		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		for(int i = 15; i < 750; i += 40) {
			g.drawLine(15, i, 15 + 720, i);
			g.drawLine(i, 15, i, 15 + 720);
			if(i == 135 || i == 375 || i == 615) {
				g.fillOval(132, i - 3, 6, 6);
				g.fillOval(372, i - 3, 6, 6);
				g.fillOval(612, i - 3, 6, 6);
			}
		}
		
		g.fillOval(375 - currentCursor/2, 375 - currentCursor/2, currentCursor, currentCursor);
		for(int k = 0; k < stones.size(); k++) {
			g.setColor(stones.get(k).color);
			g.fillOval(stones.get(k).x  - currentCursor/2, stones.get(k).y  - currentCursor/2, currentCursor, currentCursor);
		}
		
		if(currentX % 40 > 5 && currentX % 40 < 25 && currentY % 40 > 5 && currentY % 40 < 25) {
			centerX = currentX + (40 * (currentX / 40) - (currentX - 15));
			centerY = currentY + (40 * (currentY / 40) - (currentY - 15));
			g.setColor(Color.RED);
			g.drawRect(centerX - currentCursor/2, centerY - currentCursor/2, currentCursor, currentCursor);
		}
	}
	
	public boolean checkSetted() {
		for(int i = 0; i < stones.size(); i++) {
			if(stones.get(i).x == centerX && stones.get(i).y == centerY)
				return false;
		}
		return true;
	}

	public boolean checkHorizontal(int x, int y, Color color) {
		int cx;
		int cy = y;
		int count = 0;
		for(cx = x; cx >= 0; cx--) {
			if(setted[cx][cy] != null && setted[cx][cy] == color)
				count++;
			else
				break;
		}
		for(cx = x; cx < 19; cx++) {
			if(setted[cx][cy] != null && setted[cx][cy] == color)
				count++;
			else
				break;
		}
		if((count - 1) == 6)
			return true;
		else
			return false;
	}
	
	public boolean checkVertical(int x, int y, Color color) {
		int cx = x;
		int cy;
		int count = 0;
		for(cy = y; cy >= 0; cy--) {
			if(setted[cx][cy] != null && setted[cx][cy] == color)
				count++;
			else
				break;
		}
		for(cy = y; cy < 19; cy++) {
			if(setted[cx][cy] != null && setted[cx][cy] == color)
				count++;
			else
				break;
		}
		if((count - 1) == 6)
			return true;
		else
			return false;
	}
	
	public boolean checkSlash(int x, int y, Color color) {
		int cx, cy;
		int count = 0;
		for(cx = x, cy = y; cx >= 0 && cy >= 0; cx-- , cy--) {
			if(setted[cx][cy] != null && setted[cx][cy] == color)
				count++;
			else
				break;
		}
		for(cx = x, cy = y; cx < 19 && cy < 19; cx++, cy++) {
			if(setted[cx][cy] != null && setted[cx][cy] == color)
				count++;
			else
				break;
		}
		if((count - 1) == 6)
			return true;
		else
			return false;
	}
	
	public boolean checkBackSlash(int x, int y, Color color) {
		int cx, cy;
		int count = 0;
		for(cx = x, cy = y; cx < 19 && cy >= 0; cx++ , cy--) {
			if(setted[cx][cy] != null && setted[cx][cy] == color)
				count++;
			else
				break;
		}
		for(cx = x, cy = y; cx >= 0 && cy < 19; cx--, cy++) {
			if(setted[cx][cy] != null && setted[cx][cy] == color)
				count++;
			else
				break;
		}
		if((count - 1) == 6)
			return true;
		else
			return false;
	}
	
	public void setTurn() {
		if(turn) {
			setCount1++;
			color = Color.WHITE;
			if(setCount1 == 2) {
				setCount1 = 0;
				turn = false;
				MainFrame.gsp.turn = false;
				Main.frame.client.sender.sendFunct("[TURN],"+ Main.frame.client.receiver.threadNum + "," + turn);
				return;
			}
		}
		else {
			setCount2++;
			color = Color.BLACK;
			if(setCount2 == 2) {
				setCount2 = 0;
				turn = true;
				MainFrame.gsp.turn = true;
				Main.frame.client.sender.sendFunct("[TURN],"+ Main.frame.client.receiver.threadNum + "," + turn);
			}
		}
	}
	
	public void checkMatch(int x, int y, Color color) {
		if(checkHorizontal(x, y, color) || checkVertical(x, y, color) || checkSlash(x, y, color) || checkBackSlash(x, y, color)) {
			if(color == Color.WHITE) {
				Main.frame.showWinner();
				removeArray();
				stones.removeAll(stones);
				turn = true;
				color = Color.WHITE;
				setCount1 = 0;
				setCount2 = 0;
				reset = true;
			}
			else {
				Main.frame.showWinner();
				removeArray();
				stones.removeAll(stones);
				turn = true;
				color = Color.WHITE;
				setCount1 = 0;
				setCount2 = 0;
				reset = true;
			}
		}
	}
	
	public void removeArray() {
		for(int i = 0; i < 19; i++) {
			for(int j = 0; j < 19; j++) {
				setted[i][j] = null;
			}
		}
	}
	public boolean checkBound() {
		if(setX % 40 > 5 && setX % 40 < 25 && setY % 40 > 5 && setY % 40 < 25)
			return true;
		else 
			return false;
	}
	
	public void addSound() {
		try {
		  AudioInputStream ais = AudioSystem.getAudioInputStream(new File("/Users/jeong-gyeoun/Downloads/step-sound9.wav"));
		  Clip clip = AudioSystem.getClip();
		  clip.stop();
		  clip.open(ais);
		  clip.start();
		 }
		 catch (Exception ex) {} 
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		if(Main.frame.client.receiver.threadNum % 2 == 0) {
			if(!turn)
				this.setEnabled(false);
			else {
				this.setEnabled(true);
				currentX = e.getX();
				currentY = e.getY();
				repaint();
			}
		}
		
		else if(Main.frame.client.receiver.threadNum % 2 == 1) {
			if(turn)
				this.setEnabled(false);
			else {
				this.setEnabled(true);
				currentX = e.getX();
				currentY = e.getY();
				repaint();
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(Main.frame.client.receiver.threadNum % 2 == 0) {
			if(!turn)
				this.setEnabled(false);
			else {
				this.setEnabled(true);
				setX = e.getX();
				setY = e.getY();
				centerX = setX + (40 * (setX / 40) - (setX - 15));
				centerY = setY + (40 * (setY / 40) - (setY - 15));
				if(checkSetted() && checkBound()) {
					setTurn();
					MainFrame.gsp.repaint();
					stones.add(new SetStone(centerX, centerY, color));
					setted[(centerX - 15) / 40][(centerY - 15) / 40] = color;
					checkMatch((centerX - 15) / 40, (centerY - 15) / 40, color);
					addSound();
					Main.frame.client.sender.sendFunct("[STONES]," + Main.frame.client.receiver.threadNum + "," +centerX + "," + centerY + "," + color);
				}
				else {
					JOptionPane.showMessageDialog(null, "이 곳에 돌을 놓을 수 없습니다.", "경고", JOptionPane.WARNING_MESSAGE);
				}
				repaint();
			}
		}
		
		else if(Main.frame.client.receiver.threadNum % 2 == 1) {
			if(turn)
				this.setEnabled(false);
			else {
				this.setEnabled(true);
				setX = e.getX();
				setY = e.getY();
				centerX = setX + (40 * (setX / 40) - (setX - 15));
				centerY = setY + (40 * (setY / 40) - (setY - 15));
				if(checkSetted() && checkBound()) {
					setTurn();
					MainFrame.gsp.repaint();
					stones.add(new SetStone(centerX, centerY, color));
					setted[(centerX - 15) / 40][(centerY - 15) / 40] = color;
					checkMatch((centerX - 15) / 40, (centerY - 15) / 40, color);
					addSound();
					Main.frame.client.sender.sendFunct("[STONES]," + Main.frame.client.receiver.threadNum + "," +centerX + "," + centerY + "," + color);
				}
				else {
					JOptionPane.showMessageDialog(null, "이 곳에 돌을 놓을 수 없습니다.", "경고", JOptionPane.WARNING_MESSAGE);
				}
				repaint();
			}
		}
		
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {}
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	@Override
	public void mouseDragged(MouseEvent e) {}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("UNDO")) {
			if(stones.size() == 1) {
				JOptionPane.showMessageDialog(null, "더이상 되돌릴 수 없습니다.", "경고", JOptionPane.WARNING_MESSAGE);
				return;
			}
			else {
				Main.frame.client.sender.sendFunct("[UNDO]," + Main.frame.client.receiver.threadNum);
				setted[(stones.get(stones.size()-1).x - 15) / 40][(stones.get(stones.size()-1).y - 15) / 40] = null;
				stones.remove(stones.size() - 1);
				repaint();	
				if(setCount1 == 0 && turn) {
					setCount2 = 1;
					turn = false;
					MainFrame.gsp.turn = false;
					MainFrame.gsp.repaint();
				}
				
				else if(setCount2 == 0 && !turn) {
					setCount1 = 1;
					turn = true;
					MainFrame.gsp.turn = true;
					MainFrame.gsp.repaint();
				}
				else if(setCount1 == 1) {
					setCount1--;
				}
				else if(setCount2 == 1) {
					setCount2--;
				}
			}
		}
		
	}

	
}
