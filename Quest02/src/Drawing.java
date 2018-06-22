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

public class Drawing extends Canvas implements MouseMotionListener, MouseListener, ActionListener{
	protected Point first, second;
	protected String tool = "Pen";
	protected Color c;
	protected int stroke = 2;
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
			DrawnObject drawhistory = (DrawnObject)saver.get(i); 

			if(drawhistory.getShape().equals("Pen")) {
				g2.setStroke(new BasicStroke(drawhistory.getStroke()));
				g.setColor(drawhistory.getColor());
				g.drawLine(drawhistory.getP1().x, drawhistory.getP1().y, drawhistory.getP2().x, drawhistory.getP2().y);
				
			}
		}
		
		for(int i = 0; i < saver.size(); i++) {
			DrawnObject drawhistory = (DrawnObject)saver.get(i);
	
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
			DrawnObject dobj = new DrawnObject(first, second, tool, c, stroke);
			saver.add(dobj);
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
		DrawnObject dobj = new DrawnObject(first, second, tool, c, stroke);
		saver.add(dobj);
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
