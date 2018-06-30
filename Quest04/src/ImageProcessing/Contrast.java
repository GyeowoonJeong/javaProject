package ImageProcessing;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Contrast extends JSlider implements ChangeListener{
	ImagePanel imp;
	BufferedImage bimage, image, init;
	Color color, newColor;
	float red, green, blue;
	int intensity; 
	int cnt = 0;
	int height, width, rgb;
	float delta, a, b;
	Color contrast, brightness;
	
	Contrast() {
		super(-100, 100, 0);
		this.addChangeListener(this);
	}
	
	public void changeBrightness() {
		if (intensity > 0) 
	         delta = (float)(100.0/(100 - intensity/2));
	      else
	         delta = (float)((100 + intensity)/100.0);
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				color = new Color(init.getRGB(j, i));
				red = 128 + delta * (color.getRed() - 128);
	            green = 128 + delta * (color.getGreen() - 128);
	            blue = 128 + delta * (color.getBlue() - 128);
	            red = red  < 0? 0 : Math.min(255, red);
	            green = green  < 0? 0 : Math.min(255, green);
	            blue = blue  < 0? 0 : Math.min(255, blue);
	            
	            newColor = new Color(red/255, green/255, blue/255);
	            MainFrame.imgPanel2.getImage().setRGB(j,i,newColor.getRGB());
			}
		}
	}
	
	public void setBound() {
		height = MainFrame.imgPanel1.getImage().getHeight();
		width = MainFrame.imgPanel1.getImage().getWidth();	
		init = MainFrame.imgPanel1.getImage();
	}
	
	static BufferedImage deepCopy(BufferedImage bi) {
		 ColorModel cm = bi.getColorModel();
		 boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		 WritableRaster raster = bi.copyData(null);
		 return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}
	
	@Override
	public void stateChanged(ChangeEvent e) {
		if(cnt == 0) {
			image = deepCopy(MainFrame.imgPanel1.getImage());
			MainFrame.imgPanel2.setImage(image);
			setBound();
			cnt++;
		}
		else {
			intensity = this.getValue();
			changeBrightness();
			bimage = MainFrame.imgPanel2.getImage();
			MainFrame.imgPanel2.drawImage(MainFrame.imgPanel2.resizeImage(bimage));
			cnt = 0;
		}
	}
}
