package ImageProcessing;
import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ImagePanel extends JPanel implements ActionListener, MouseMotionListener{
	private BufferedImage image;
	private Image resize;
	private int w, h;
	static String path;
	boolean clicked = false;
	int xlocation, ylocation;
	int x, y;
	
	public ImagePanel() {
		this.setSize(550, 540);
		this.addMouseMotionListener(this);
	}
	
	public void setImage(BufferedImage image) {
		this.image = image;
	}
	
	public BufferedImage getImage() {
		return this.image;
	}
	
	public void drawImage(Image resize) {
		xlocation = this.getWidth()/2 - resize.getWidth(null)/2; 
		ylocation = this.getHeight()/2 - resize.getHeight(null)/2;
		Graphics g = getGraphics();
		Graphics2D g2 = (Graphics2D) g;
		super.paint(g);
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);		
		if(resize.getWidth(null) == this.getWidth())
			g2.drawImage(resize, 0, ylocation, null);
		else if(resize.getHeight(null) == this.getHeight())
			g2.drawImage(resize, xlocation, 0, null);
		//g2.dispose();
	}
/*
	public void paint(Graphics g) {
		if(resize == null)
			return;
		//Graphics2D g2 = (Graphics2D) g;
		super.paint(g);
		drawImage(resize);
		if(MagnifyingGlass.clicked)
			g.drawImage(resizeImage(image), MagnifyingGlass.x-50,  MagnifyingGlass.y-50,  MagnifyingGlass.x+50,  MagnifyingGlass.y+50, 
					(MagnifyingGlass.x-20)/2, (MagnifyingGlass.y-20)/2, (MagnifyingGlass.x+20)/2, (MagnifyingGlass.y+20)/2, null);
	}
	*/
	
	public void rep() {
		repaint();
	}
	
	public Image resizeImage(BufferedImage image) {
		int imageWidth = image.getWidth(null);
		int imageHeight = image.getHeight(null);
		double wRatio = (double)this.getWidth()/(double)imageWidth;
		double hRatio = (double)this.getHeight()/(double)imageHeight;
		
		if(imageWidth > imageHeight) {
			w = (int)(imageWidth * wRatio);
			h = this.getWidth() * imageHeight / imageWidth;
		}
		else {
			h = (int)(imageHeight * hRatio);
			w = this.getHeight() * imageWidth / imageHeight;
		}
		Image resizeImage = image.getScaledInstance(w, h, Image.SCALE_SMOOTH);
		resize = resizeImage;
		
		return resizeImage;
	}
	
	public void paint(Graphics g) {
		if(image == null) {
			rep();
			return;
		}
		super.paint(g);
		if(resize.getWidth(null) == this.getWidth())
			g.drawImage(resize, 0, ylocation, null);
		else if(resize.getHeight(null) == this.getHeight())
			g.drawImage(resize, xlocation, 0, null);	
		if(clicked)
			g.drawImage(resize, x - 70, y - 70, x + 75, y + 75, x - 50, y - 50, x + 50, y + 50, null);
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		rep();
		x = e.getX();
		y = e.getY();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("OpenFile")) {
			FileDialog fd = new FileDialog(Main.frame, "Choose a file", FileDialog.LOAD);
			fd.setVisible(true);
			path = fd.getDirectory() + fd.getFile();
			try {	
				image = ImageIO.read(new File(path));	
				drawImage(resizeImage(image));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		else if(e.getActionCommand().equals("Magnify")) {
			image = deepCopy(MainFrame.imgPanel1.getImage());
			drawImage(resizeImage(image));
			if(clicked) {
				clicked = false;
				return;
			}
			else
				clicked = true;
		}
		
	}
	@Override
	public void mouseDragged(MouseEvent e) {}
	
	static BufferedImage deepCopy(BufferedImage bi) {
		 ColorModel cm = bi.getColorModel();
		 boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		 WritableRaster raster = bi.copyData(null);
		 return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}

}
