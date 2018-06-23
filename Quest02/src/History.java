import java.awt.Color;
import java.awt.Shape;

public class History  {
	private Shape shape;
	private Color color;
	private int stroke;
	
	History(Shape shape, Color color, int stroke) {
		this.shape = shape;
		this.color = color;
		this.stroke = stroke;
	}
	
	public void setShape(Shape shape) {
		this.shape = shape;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public void setStroke(int stroke) {
		this.stroke = stroke;
	}
	
	public Shape getShape() {
		return this.shape;
	}
	
	public Color getColor() {
		return this.color;
	}
	
	public int getStroke() {
		return this.stroke;
	}
}
