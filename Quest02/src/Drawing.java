import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.JColorChooser;
import javax.swing.JOptionPane;

public class Drawing extends Canvas implements MouseMotionListener, MouseListener, ActionListener{
	protected Point first, second;
	protected String tool = "Line";
	protected Color c;
	protected int stroke = 2;
	private Color eraseC = Color.WHITE;
	private ArrayList saver;
	
	Drawing() {
		this.saver = new ArrayList<>();
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
		if(cmd.equals("Pen") || cmd.equals("Line") || cmd.equals("Rectangle") || cmd.equals("Circle") || cmd.equals("Eraser")) {
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
		super.paint(g2);
		
		for(int i = 0; i < saver.size(); i++) {
			History drawhistory = (History)saver.get(i);
			g2.setStroke(new BasicStroke(drawhistory.getStroke()));
			g2.setColor(drawhistory.getColor());
			g2.draw(drawhistory.getShape());
			
		}
		
		g2.setStroke(new BasicStroke(stroke));
		if((first != null) && (second != null)) {
			if(tool.equals("Line")) {
				g2.setColor(c);
				g2.drawLine(first.x, first.y, second.x, second.y);
			}
			
			else if(tool.equals("Rectangle")) {
				g2.setColor(c);
				g2.drawRect(Math.min(first.x, second.x), Math.min(first.y, second.y), Math.max(first.x, second.x) - Math.min(first.x, second.x), 
						Math.max(first.y, second.y) - Math.min(first.y, second.y));
				
			}
			else if(tool.equals("Circle")) {
				g2.setColor(c);
				g2.drawOval(Math.min(first.x, second.x), Math.min(first.y, second.y), Math.max(first.x, second.x) - Math.min(first.x, second.x), 
						Math.max(first.y, second.y) - Math.min(first.y, second.y));
			}
		}
	

	}
	
	public void update(Graphics g){
		   paint(g);
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		second = e.getPoint();
		
		repaint();
	}


	@Override
	public void mousePressed(MouseEvent e) {
		first = e.getPoint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		Graphics2D g2 = (Graphics2D) getGraphics();
		second = e.getPoint();
		
		if (tool.equals("Line")) {
			Line2D li = new Line2D.Double(first.x, first.y, second.x, second.y);
			History line = new History(li, c, stroke);
	        saver.add(line);
	        g2.draw(li);
		}
		else if(tool.equals("Rectangle")) {
			Rectangle2D rect = new Rectangle2D.Double(Math.min(first.x, second.x), Math.min(first.y, second.y), Math.max(first.x, second.x) - Math.min(first.x, second.x), 
					Math.max(first.y, second.y) - Math.min(first.y, second.y));
			History rectangle = new History(rect, c, stroke);
			saver.add(rectangle);
			g2.draw(rect);
		}
		else if(tool.equals("Circle")) {
			Ellipse2D el = new Ellipse2D.Double(Math.min(first.x, second.x), Math.min(first.y, second.y), Math.max(first.x, second.x) - Math.min(first.x, second.x), 
					Math.max(first.y, second.y) - Math.min(first.y, second.y));
			History ellipse = new History(el, c, stroke);
			saver.add(ellipse);
			g2.draw(el);
		}
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
