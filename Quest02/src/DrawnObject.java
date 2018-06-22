import java.awt.Color;
import java.awt.Point;

public class DrawnObject {
	private Point p1, p2;
	private String shape; 
	private Color color;
	private int stroke;
	
	DrawnObject(Point p1, Point p2, String shape, Color color, int stroke) {
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

