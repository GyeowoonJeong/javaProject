import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JColorChooser;
import javax.swing.JOptionPane;


class DrawHistory {
	private Point p1, p2;
	private String shape; 
	private Color color;
	private int stroke;
	
	public DrawHistory(Point p1, Point p2, String shape, Color color, int stroke) {
		this.p1 = p1;
		this.p2 = p2;
		this.shape = shape;
		this.color = color;
		this.stroke = stroke;
	}
	
	public void setP1(Point p1) {
		this.p1 = p1;
	}
	
	public void setP2(Point p2) {
		this.p2 = p2;	
	}
	
	public void setShape(String shape) {
		this.shape = shape;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public void setStroke(int stroke) {
		this.stroke = stroke;
	}
	
	public Point getP1() {
		return this.p1;
	}
	
	public Point getP2() {
		return this.p2;
	}
	
	public String getShape() {
		return this.shape;
	}
	
	public Color getColor() {
		return this.color;
	}
	
	public int getStroke() {
		return this.stroke;
	}
}

public class Drawing extends Canvas implements MouseMotionListener, MouseListener, ActionListener{
	Point first, second;
	String tool = "Pen";
	Color c;
	int stroke = 2;
	JColorChooser colorChooser;
	
	private ArrayList saver;
	
	Drawing() {
		this.saver = new ArrayList();
		this.setBackground(Color.WHITE);
		this.addMouseListener(this); 
		this.addMouseMotionListener(this);
	}
	
	public void setSaver(ArrayList saver) {
		this.saver = saver;
	}
	
	public ArrayList getSaver() {
		return this.saver;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if(cmd.equals("Pen") || cmd.equals("Line") || cmd.equals("Rectangle") || cmd.equals("Circle")) {
			tool = e.getActionCommand();
		}
		else if(cmd.equals("COLOR")) {
			//colorChooser = new JColorChooser();
			c = JColorChooser.showDialog(null, "ColorPicker", Color.BLACK);
		}
		else if(cmd.equals("UP"))
			stroke++;
		
		else if(cmd.equals("DOWN")) {
			if(stroke > 1)
				stroke--;
			else 
				JOptionPane.showMessageDialog(null, "최소굵기 입니다.");
		}
	}
	
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		super.paint(g);
		
		for(int i = 0; i < saver.size()-1; i++) {
			DrawHistory drawhistory = (DrawHistory)saver.get(i); 

			if(drawhistory.getShape().equals("Pen")) {
				g2.setStroke(new BasicStroke(drawhistory.getStroke()));
				g.setColor(drawhistory.getColor());
				g.drawLine(drawhistory.getP1().x, drawhistory.getP1().y, drawhistory.getP2().x, drawhistory.getP2().y);
				
			}
		}
		
		for(int i = 0; i < saver.size(); i++) {
			DrawHistory drawhistory = (DrawHistory)saver.get(i);
	
			if(drawhistory.getShape().equals("Line")) {
				g2.setStroke(new BasicStroke(drawhistory.getStroke()));
				g.setColor(drawhistory.getColor());
				g.drawLine(drawhistory.getP1().x, drawhistory.getP1().y, drawhistory.getP2().x, drawhistory.getP2().y);
			}
			else if(drawhistory.getShape().equals("Rectangle")) {
				g2.setStroke(new BasicStroke(drawhistory.getStroke()));
				g.setColor(drawhistory.getColor());
				g.drawRect(drawhistory.getP1().x, drawhistory.getP1().y, drawhistory.getP2().x - drawhistory.getP1().x, drawhistory.getP2().y - drawhistory.getP1().y);
			}
			else if(drawhistory.getShape().equals("Circle")) {
				g2.setStroke(new BasicStroke(drawhistory.getStroke()));
				g.setColor(drawhistory.getColor());
				g.drawOval(drawhistory.getP1().x, drawhistory.getP1().y, drawhistory.getP2().x - drawhistory.getP1().x, drawhistory.getP2().y - drawhistory.getP1().y);
			}
			
		}
		
		g.setColor(c);
		g2.setStroke(new BasicStroke(stroke));
		if((first != null) && (second != null)) {
			if(tool.equals("Pen") || tool.equals("Line"))
				g.drawLine(first.x, first.y, second.x, second.y);
			else if(tool.equals("Rectangle"))
				g.drawRect(first.x, first.y, second.x - first.x, second.y - first.y);
			else if(tool.equals("Circle"))
				g.drawOval(first.x, first.y, second.x - first.x, second.y - first.y);
		}
	

	}
	
	public void update(Graphics g){
		   paint(g);
	}
	
	
	@Override
	public void mouseDragged(MouseEvent e) {
		second = e.getPoint();
		if(tool.equals("Pen")) {
			DrawHistory dh = new DrawHistory(first, second, tool, c, stroke);
			saver.add(dh);
			first = second;
		}
		repaint();
	}


	@Override
	public void mousePressed(MouseEvent e) {
		first = e.getPoint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		second = e.getPoint();
		DrawHistory dh = new DrawHistory(first, second, tool, c, stroke);
		saver.add(dh);
		repaint();	
	}

	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	@Override
	public void mouseMoved(MouseEvent e) {}
	@Override
	public void mouseClicked(MouseEvent e) {}

	

}
