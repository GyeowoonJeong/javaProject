package Connect6_Game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements MouseMotionListener, MouseListener{
	int currentX, currentY;
	int setX, setY;
	int tempX, tempY;
	int centerX, centerY;
	final int currentCursor = 30;
	boolean turn = true;
	int setCount1 = 0;
	int setCount2 = 0;
	int count = 0;
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
		
		if(checkBound()) {
			centerX = setX + (40 * (setX / 40) - (setX - 15));
			centerY = setY + (40 * (setY / 40) - (setY - 15));
			if(checkSetted()) {
				g.setColor(color);
				g.fillOval(centerX - currentCursor/2, centerY - currentCursor/2, currentCursor, currentCursor);
				stones.add(new SetStone(centerX, centerY, color));
				setted[(centerX - 15) / 40][(centerY - 15) / 40] = color;
			}
		}
	}
	
	public boolean checkSetted() {
		for(int i = 0; i < stones.size(); i++) {
			if(stones.get(i).x == tempX && stones.get(i).y == tempY)
				return false;
		}
		return true;
	}

	public boolean checkHorizontal(int x, int y, Color color) {
		int cx;
		int cy = y;
		/*
		for(int i = 0; i < stones.size(); i++) {
			for(int j = centerX; j >= 15; j -= 40) {
				if(j == stones.get(i).x && centerY == stones.get(i).y && color == stones.get(i).color) 
					count++;
				else
					break;
			}
			for (int k = centerX; k <= 735; k++) {
				if(k == stones.get(i).x && centerY == stones.get(i).y && color == stones.get(i).color)
					count++;
				else
					break;
			}
		}
		if((count - 1) == 8)
			return true;
		
		return false;
		*/
		for(cx = x; cx >= 0; cx--) {
			if(setted[cx][cy] != null && setted[cx][cy] == color)
				count++;
			else
				break;
		}
		for(cx = x; cx <= 19; cx++) {
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
		/*
		for(int i = 0; i < stones.size(); i++) {
			for(int j = centerY; j >= 15; j -= 40) {
				if(centerX == stones.get(i).x && j == stones.get(i).y && color == stones.get(i).color) 
					count++;
				else
					break;
			}
			for (int k = centerY; k <= 735; k++) {
				if(centerX == stones.get(i).x && k == stones.get(i).y && color == stones.get(i).color)
					count++;
				else
					break;
			}
		}
		if((count - 1) == 8)
			return true;
		
		return false;
		*/
		for(cy = y; cy >= 0; cy--) {
			if(setted[cx][cy] != null && setted[cx][cy] == color)
				count++;
			else
				break;
		}
		for(cy = y; cy <= 19; cy++) {
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
		for(cx = x, cy = y; cx >= 0 && cy >= 0; cx-- , cy--) {
			if(setted[cx][cy] != null && setted[cx][cy] == color)
				count++;
			else
				break;
		}
		for(cx = x, cy = y; cx <= 19 && cy <= 19; cx++, cy++) {
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
		for(cx = x, cy = y; cx <= 19 && cy >= 0; cx++ , cy--) {
			if(setted[cx][cy] != null && setted[cx][cy] == color)
				count++;
			else
				break;
		}
		for(cx = x, cy = y; cx >= 0 && cy <= 19; cx--, cy++) {
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
				return;
			}
		}
		else {
			setCount2++;
			color = Color.BLACK;
			if(setCount2 == 2) {
				setCount2 = 0;
				turn = true;
			}
		}
	}
	
	public void checkMatch(int x, int y, Color color) {
		if(checkHorizontal(x, y, color) || checkVertical(x, y, color) || checkSlash(x, y, color) || checkBackSlash(x, y, color)) {
			if(color == Color.WHITE)
				JOptionPane.showMessageDialog(null, "흰 돌이 이겼습니다.", "게임종료 ", JOptionPane.PLAIN_MESSAGE);
			else
				JOptionPane.showMessageDialog(null, "검은 돌이 이겼습니다.", "게임종료 ", JOptionPane.PLAIN_MESSAGE);
		}
	}
	
	public boolean checkBound() {
		if(setX % 40 > 5 && setX % 40 < 25 && setY % 40 > 5 && setY % 40 < 25)
			return true;
		else 
			return false;
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		currentX = e.getX();
		currentY = e.getY();
		repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
			setX = e.getX();
			setY = e.getY();
			tempX = setX + (40 * (setX / 40) - (setX - 15));
			tempY = setY + (40 * (setY / 40) - (setY - 15));
		if(checkSetted() && checkBound()) {
			checkMatch((tempX - 15) / 40, (tempY - 15) / 40, color);
			setTurn();
		}
		else {
			JOptionPane.showMessageDialog(null, "이미 돌이 놓여 있습니다.", "경고", JOptionPane.WARNING_MESSAGE);
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

	
}
