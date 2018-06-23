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
import javax.swing.ToolTipManager;

public class Drawing extends Canvas implements MouseMotionListener, MouseListener, ActionListener{
	protected Point first, second;
	protected String tool = "Line";
	protected Color c;
	protected int stroke = 2;
	protected int currentIdx;
	private Color eraseC = Color.WHITE;
	private ArrayList<Object> saver;
	private ArrayList<PenTrace> trace;
	
	Drawing() {
		this.saver = new ArrayList<>();
		this.trace = new ArrayList<>();
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
		int j;
		for(int i = 0; i < saver.size(); i++) {
			History drawhistory = (History)saver.get(i);
			
			if(drawhistory.getTool().equals("Pen")) {
				for(j = 0; j < trace.size(); j++) {
					if(trace.get(j).getObjIdx() == i)
						break;
				}
				for(int k = j; k != i; k++) {
					g2.setStroke(new BasicStroke(drawhistory.getStroke()));
					g2.setColor(drawhistory.getColor());
					g2.draw(new Line2D.Double(trace.get(k).getStart().x, trace.get(k).getStart().y, trace.get(k).getEnd().x, trace.get(k).getEnd().y));
				}
			}
			
			else {
				g2.setStroke(new BasicStroke(drawhistory.getStroke()));
				g2.setColor(drawhistory.getColor());
				g2.draw(drawhistory.getShape());
			}
		}
		
		g2.setStroke(new BasicStroke(stroke));
		if((first != null) && (second != null)) {
			if(tool.equals("Pen") || tool.equals("Line")) {
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
		Graphics2D g2 = (Graphics2D) getGraphics();
		second = e.getPoint();
		if(tool.equals("Pen")) {
			PenTrace pt = new PenTrace(currentIdx, first, second);
			trace.add(pt);
			g2.draw(new Line2D.Double(first.x, first.y, second.x, second.y));
			first = second;
		}
		repaint();
	}


	@Override
	public void mousePressed(MouseEvent e) {
		first = e.getPoint();
		if(tool.equals("Pen"))
			currentIdx = saver.size();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		Graphics2D g2 = (Graphics2D) getGraphics();
		second = e.getPoint();
		
		if(tool.equals("Pen")) {
			Line2D p = new Line2D.Double(first.x, first.y, second.x, second.y);
			History pen = new History(tool, p, c, stroke);
			saver.add(pen);
		}
		
		else if (tool.equals("Line")) {
			Line2D li = new Line2D.Double(first.x, first.y, second.x, second.y);
			History line = new History(tool, li, c, stroke);
	        saver.add(line);
	        g2.draw(li);
		}
		
		else if(tool.equals("Rectangle")) {
			Rectangle2D rect = new Rectangle2D.Double(Math.min(first.x, second.x), Math.min(first.y, second.y), Math.max(first.x, second.x) - Math.min(first.x, second.x), 
					Math.max(first.y, second.y) - Math.min(first.y, second.y));
			History rectangle = new History(tool, rect, c, stroke);
			saver.add(rectangle);
			g2.draw(rect);
		}
		 
		else if(tool.equals("Circle")) {
			Ellipse2D el = new Ellipse2D.Double(Math.min(first.x, second.x), Math.min(first.y, second.y), Math.max(first.x, second.x) - Math.min(first.x, second.x), 
					Math.max(first.y, second.y) - Math.min(first.y, second.y));
			History ellipse = new History(tool, el, c, stroke);
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
