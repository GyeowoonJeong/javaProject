package ImageProcessing;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.util.ArrayList;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class GreenScale extends JSlider implements ChangeListener{
	ImagePanel imp;
	BufferedImage bimage, image, initimage;
	Color color, newColor;
	int red, green, blue;
	int intensity; 
	int cnt = 0;
	int height, width, rgb;
	static boolean isGray = false;
	Pixels p;
	ArrayList<Pixels> pixel;
	
	GreenScale() {
		super(-100, 100, 0);
		p = new Pixels();
		pixel = new ArrayList<>();
		this.addChangeListener(this);
	}

	public void changeBrightness() {

		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				color = new Color(initimage.getRGB(j, i));
				red = (int)(color.getRed());
				green = (int)(color.getGreen() + intensity);
				blue = (int)(color.getBlue());
	            green= adjustBoundary(green);
	            
	            newColor = new Color(red, green, blue);
	            MainFrame.imgPanel2.getImage().setRGB(j,i,newColor.getRGB());
			}
		}
	}
	
	public void setBound() {
		height = imp.getImage().getHeight();
		width = imp.getImage().getWidth();	
		initimage = MainFrame.imgPanel1.getImage();
	}
	
	public int adjustBoundary(int c) {
		if(c > 255)
			c = 255;
		else if(c < 0)
			c = 0;
		return c;
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
	public void stateChanged(ChangeEvent e) {
		if(cnt == 0) {
			setChangedImgPanel();
			setBound();
			cnt++;
		}
		else {
			intensity = (int)this.getValue();
			changeBrightness();
			bimage = imp.getImage();
			imp.drawImage(imp.resizeImage(bimage));
			cnt = 0;
		}
	}
}
