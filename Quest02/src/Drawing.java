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
	protected Point first = null, second = null;
	protected boolean isUndo = false;
	protected String tool = "Pen";
	protected Color c;
	protected int stroke = 2;
	protected int currentIdx = 0;
	private Color eraseC = Color.WHITE;
	private ArrayList<Object> saver;
	PenTrace pt, et;
	
	Drawing() {
		this.saver = new ArrayList<>();
		this.setBackground(Color.WHITE);
		this.addMouseListener(this); 
		this.addMouseMotionListener(this);
	}
	
	public void setSaver(ArrayList<Object> saver) {
		this.saver = saver;
	}
	
	public ArrayList<Object> getSaver() {
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
		
		else if(cmd.equals("Undo")) {
			isUndo = true;
			if(currentIdx <= 0)
				JOptionPane.showMessageDialog(null, "더 이상 되돌릴 것이 없습니다.");
			currentIdx--;
			repaint();
		}
	}
	

	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		super.paint(g);
		
		for(int i = 0; i < currentIdx; i++) {
			if(saver.get(i) instanceof PenTrace) {
				PenTrace pentrace = (PenTrace) saver.get(i);
				g2.setStroke(new BasicStroke(pentrace.getStroke()));
				g2.setColor(pentrace.getColor());
				//System.out.println("Color : " + pentrace.getColor());
				for(int j = 0; j < pentrace.getTrace().size(); j++) {
					g2.draw(pentrace.getTrace().get(j));
				}
			}
			
			else if(saver.get(i) instanceof History) {
				History drawhistory = (History)saver.get(i);
					g2.setStroke(new BasicStroke(drawhistory.getStroke()));
					g2.setColor(drawhistory.getColor());
					g2.draw(drawhistory.getShape());
			}
		}
		
		if(!isUndo) {
		g2.setStroke(new BasicStroke(stroke));
		if((first != null) && (second != null)) {
			if(tool.equals("Pen") || tool.equals("Line")) {
				g2.setColor(c);
				g2.draw(new Line2D.Double(first.x, first.y, second.x, second.y));
			}
			else if(tool.equals("Eraser")) {
				g2.setColor(eraseC);
				g2.draw(new Line2D.Double(first.x, first.y, second.x, second.y));
			}
			else if(tool.equals("Rectangle")) {
				g2.setColor(c);
				g2.draw(new Rectangle2D.Double(Math.min(first.x, second.x), Math.min(first.y, second.y), Math.max(first.x, second.x) - Math.min(first.x, second.x), 
						Math.max(first.y, second.y) - Math.min(first.y, second.y)));
			}
			else if(tool.equals("Circle")) {
				g2.setColor(c);
				g2.draw(new Ellipse2D.Double(Math.min(first.x, second.x), Math.min(first.y, second.y), Math.max(first.x, second.x) - Math.min(first.x, second.x), 
						Math.max(first.y, second.y) - Math.min(first.y, second.y)));
			}
		}
		}

}
	
	public void update(Graphics g){
		   paint(g);
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		Graphics2D g2 = (Graphics2D)getGraphics();
		second = e.getPoint();
		if(tool.equals("Pen")) {
			g2.setColor(c);
			g2.setStroke(new BasicStroke(stroke));
			Line2D p = new Line2D.Double(first.x, first.y, second.x, second.y);
			pt.getTrace().add(p);
			g2.draw(p);
			first = second;
		}
		
		else if(tool.equals("Eraser")) {
			g2.setColor(eraseC);
			g2.setStroke(new BasicStroke(stroke));
			Line2D p = new Line2D.Double(first.x, first.y, second.x, second.y);
			et.getTrace().add(p);
			g2.draw(p);
			first = second;
		}
		else
			repaint();
	}


	@Override
	public void mousePressed(MouseEvent e) {
		first = e.getPoint();
		if(tool.equals("Pen"))
			pt = new PenTrace(c, stroke);
		else if(tool.equals("Eraser"))
			et = new PenTrace(eraseC, stroke);
		
		if(currentIdx < saver.size() - 1) {
			for(int i = saver.size() -1; i >= currentIdx + 1; i--) {
				saver.remove(i);
			}
		}
	}
 
	@Override
	public void mouseReleased(MouseEvent e) {
		second = e.getPoint();
		currentIdx++;
		if(tool.equals("Pen")) {
			Line2D p = new Line2D.Double(first.x, first.y, second.x, second.y);
			pt.getTrace().add(p);
			saver.add(pt);
		}
		
		else if (tool.equals("Eraser")) {
			Line2D p = new Line2D.Double(first.x, first.y, second.x, second.y);
			et.getTrace().add(p);
			saver.add(et);
		}
		
		else if (tool.equals("Line")) {
			Line2D li = new Line2D.Double(first.x, first.y, second.x, second.y);
			History line = new History(li, c, stroke);
	        saver.add(line);
		}
		
		else if(tool.equals("Rectangle")) {
			Rectangle2D rect = new Rectangle2D.Double(Math.min(first.x, second.x), Math.min(first.y, second.y), Math.max(first.x, second.x) - Math.min(first.x, second.x), 
					Math.max(first.y, second.y) - Math.min(first.y, second.y));
			History rectangle = new History(rect, c, stroke);
			saver.add(rectangle);
		}
		 
		else if(tool.equals("Circle")) {
			Ellipse2D el = new Ellipse2D.Double(Math.min(first.x, second.x), Math.min(first.y, second.y), Math.max(first.x, second.x) - Math.min(first.x, second.x), 
					Math.max(first.y, second.y) - Math.min(first.y, second.y));
			History ellipse = new History(el, c, stroke);
			saver.add(ellipse);
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
