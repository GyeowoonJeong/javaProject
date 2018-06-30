package ImageProcessing;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

import javax.swing.JButton;

public class GrayScale extends JButton implements ActionListener{
	JButton maintanance;
	ImagePanel imp;
	BufferedImage bimage;
	Color color, newColor;
	boolean toggle = false;
	int red, green, blue;
	
	GrayScale() {
		super("Change To Gray");
		maintanance = new JButton("MainTain");
		this.addActionListener(this);
	}
	
	public void changeToGray() {
		for(int i = 0; i < MainFrame.imgPanel1.getImage().getHeight(); i++) {
			for(int j = 0; j < MainFrame.imgPanel1.getImage().getWidth(); j++) {
				color = new Color(MainFrame.imgPanel1.getImage().getRGB(j, i));
				red = (int)(color.getRed() * 0.299);
	            green = (int)(color.getGreen() * 0.587);
	            blue = (int)(color.getBlue() *0.114);
	            
	            newColor = new Color(red+green+blue, red+green+blue, red+green+blue);
	            imp.getImage().setRGB(j,i,newColor.getRGB());
			}
		}
	}
	
	static BufferedImage deepCopy(BufferedImage bi) {
		 ColorModel cm = bi.getColorModel();
		 boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		 WritableRaster raster = bi.copyData(null);
		 return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("MainTain")) {
			Brightness.isGray = true;
			this.setForeground(Color.black);
			toggle = false;
		}
		
		else if(e.getActionCommand().equals("Change To Gray")) {
			if(!toggle) {
				this.setForeground(new Color(195, 224, 244));
				setChangedImgPanel();
				changeToGray();
				bimage = imp.getImage();
				imp.drawImage(imp.resizeImage(bimage));
				toggle = true;
				return;
			}
			else {
				this.setForeground(Color.black);
				imp.drawImage(imp.resizeImage(MainFrame.imgPanel1.getImage()));
				toggle = false;
			}
		}
		
	}
	
	public void setChangedImgPanel() {
		BufferedImage temp = deepCopy(MainFrame.imgPanel1.getImage());
		MainFrame.imgPanel2.setImage(temp);
		imp = MainFrame.imgPanel2;
	}
}
