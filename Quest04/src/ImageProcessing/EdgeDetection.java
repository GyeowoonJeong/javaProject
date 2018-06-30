package ImageProcessing;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.util.ArrayList;

import javax.swing.JButton;

class Edges{
	int width;
	int height;
	
	Edges(int w, int h) {
		this.width = w;
		this.height = h;
	}
}

public class EdgeDetection extends JButton implements ActionListener{
	ImagePanel imp;
	BufferedImage bimage, image, initimage;
	Color color1 = new Color(0, 0, 0);
	Color color2 = new Color(255, 255, 255);
	Color newColor;
	int first, second;
	int cnt = 0;
	int height, width, rgb;
	ArrayList<Edges> edges;
	
	EdgeDetection() {
		super("Edge Detection");
		edges = new ArrayList<>();
		this.addActionListener(this);
	}
	
	public void detect() {
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < (width - 1); j++) {
				first = initimage.getRGB(j, i);
	            second = initimage.getRGB(j + 1, i);
	            
	            if(Math.abs(first - second) > 1000000){
	            	if(first > second) {
	            		edges.add(new Edges(j + 1, i));
	            	}
	            	else {
	            		edges.add(new Edges(j, i));
	            	}
	            }
			}
		}
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height - 1; j++) {
				first = initimage.getRGB(i, j);
	            second = initimage.getRGB(i, j+ 1);
	            
	            if(Math.abs(first - second) > 1000000){
	            	if(first > second) {
	            		edges.add(new Edges(i, j + 1));
	            	}
	            	else
	            		edges.add(new Edges(i, j));
	            }
			}
		}
	}
	
	public void setBound() {
		height = imp.getImage().getHeight();
		width = imp.getImage().getWidth();	
		initimage = MainFrame.imgPanel1.getImage();
	}
	
	public void setEdgeColor() {
		for(int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				imp.getImage().setRGB(j, i, color2.getRGB());
				
			}
		}
		for(int k = 0; k < edges.size(); k++) {
			imp.getImage().setRGB(edges.get(k).width, edges.get(k).height, color1.getRGB());
		}
	}

	
	static BufferedImage deepCopy(BufferedImage bi) {
		 ColorModel cm = bi.getColorModel();
		 boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		 WritableRaster raster = bi.copyData(null);
		 return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}
	
	public void setChangedImgPanel() {
		BufferedImage temp = deepCopy(MainFrame.imgPanel1.getImage());
		MainFrame.imgPanel2.setImage(temp);
		imp = MainFrame.imgPanel2;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		setChangedImgPanel();
		setBound();
		detect();
		setEdgeColor();
		imp.drawImage(imp.resizeImage(imp.getImage()));	
		edges.clear();
	}
}
