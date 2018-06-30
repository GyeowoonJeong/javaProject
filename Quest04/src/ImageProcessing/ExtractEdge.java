package ImageProcessing;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

import javax.swing.JButton;

public class ExtractEdge extends JButton implements ActionListener{
	BufferedImage initimage, edgeimage;
	double[][] imgarr;
	double[][] filterBlur;
	double[][] filterEdge = { { 1, 1, 1 }, { 1, -8, 1 }, { 1, 1, 1 } }; 
	int width, height;
	
	ExtractEdge() {
		super("Edge Detection");
		this.addActionListener(this);
	}

	static BufferedImage deepCopy(BufferedImage bi) {
		 ColorModel cm = bi.getColorModel();
		 boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		 WritableRaster raster = bi.copyData(null);
		 return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}
	
	public double[][] imgToarr() {
		double[][] output = new double[initimage.getHeight()][initimage.getWidth()]; 
		for (int y = 0; y < initimage.getHeight(); y++) { 
			for (int x = 0; x < initimage.getWidth(); x++) { 
				Color c = new Color(initimage.getRGB(x, y)); 
				output[y][x] += c.getRed(); 
				output[y][x] += c.getGreen(); 
				output[y][x] += c.getBlue(); 
				output[y][x] /= 3.0; 
			} 
		} 
		return output; 
	} 	
	
	public static double[][] getFilter(int size) { 
		int x = size / 2; 
		double[][] output = new double[size][size]; 
		double sum = 0; 
		for (int i = 0; i < size; i++) { 
			for (int j = 0; j < size; j++) { 
				output[i][j] = 1.0 / (1 + Math.pow(Math.pow(i - x, 2) + Math.pow(j - x, 2), 0.5)); 
				sum += output[i][j]; 
			} 
		} 
		for (int i = 0; i < size; i++) { 
			for (int j = 0; j < size; j++) { 
				output[i][j] /= sum; 
			} 
		} 
		
		return output; 
	} 
	
	public double[][] convolution(double filter[][]) { 
		if (filter.length % 2 == 1) { 
			int w = filter.length / 2; 
			double[][] output = new double[imgarr.length][imgarr[0].length]; 
			for (int y = 0; y < imgarr.length; y++) { 
				for (int x = 0; x < imgarr[y].length; x++) { 
					for (int i = 0; i < filter.length; i++) { 
						for (int j = 0; j < filter[i].length; j++) { 
							try { 
								output[y][x] += imgarr[y - i + w][x - j + w] * filter[i][j]; 
							} catch (ArrayIndexOutOfBoundsException e) { 
					
							} 
						} 
					} 
				} 
			} 
			return output; 
		} else { 
			return null; 
		} 
	} 
	
	public BufferedImage arrToimg() { 
		BufferedImage output = new BufferedImage(imgarr[0].length, imgarr.length, BufferedImage.TYPE_INT_BGR); 
		for (int y = 0; y < imgarr.length; y++) { 
			for (int x = 0; x < imgarr[y].length; x++) { 
				output.setRGB(x, y, new Color((int) imgarr[y][x], (int) imgarr[y][x], (int) imgarr[y][x]).getRGB()); 
			} 
		} 
		return output; 
	} 
	
	public double[][] arrayInColorBound() { 
		for (int i = 0; i < imgarr.length; i++) { 
			for (int j = 0; j < imgarr[i].length; j++) { 
				imgarr[i][j] = Math.max(0, imgarr[i][j]); 
				imgarr[i][j] = Math.min(225, imgarr[i][j]); 
				} 
			} 
		return imgarr; 
		} 
	
	@Override
	public void actionPerformed(ActionEvent e) {
		initimage = deepCopy(MainFrame.imgPanel1.getImage());
		imgarr = imgToarr();
		filterBlur = getFilter(3);
		imgarr = convolution(filterBlur);
		imgarr = convolution(filterEdge);
		imgarr = arrayInColorBound();
		edgeimage = arrToimg();
		MainFrame.imgPanel2.setImage(edgeimage);
		MainFrame.imgPanel2.drawImage(MainFrame.imgPanel2.resizeImage(edgeimage));
	}

}
