package ImageProcessing;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
//import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

import javax.swing.JButton;
import javax.swing.JPanel;

public class MagnifyingGlass implements MouseMotionListener, ActionListener {
	JPanel mPanel = MainFrame.imgPanel2;
	JButton button;
	static int x, y;
	BufferedImage img;
	Rectangle viewer, imgBound;
	float scale;
	boolean clicked = false;
	
	MagnifyingGlass() {
		button = new JButton("Magnify");
		MainFrame.imgPanel2.addMouseMotionListener(this);
		button.addActionListener(this);
	}

	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		MainFrame.imgPanel2.paint(g);
		if(clicked)
			g2.drawImage(img, x-20, y-20, x+20, y+20, (x-20)/2, (y-20)/2, (x+20)/2, (y+20)/2, null);
	}
	
	static BufferedImage deepCopy(BufferedImage bi) {
		 ColorModel cm = bi.getColorModel();
		 boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		 WritableRaster raster = bi.copyData(null);
		 return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {}

	@Override
	public void mouseMoved(MouseEvent e) {
		MainFrame.imgPanel2.repaint();
		x = e.getX();
		y = e.getY();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		img = deepCopy(MainFrame.imgPanel1.getImage());
		MainFrame.imgPanel2.setImage(img);
		MainFrame.imgPanel2.drawImage(MainFrame.imgPanel2.resizeImage(img));
		if(clicked) {
			clicked = false;
			return;
		}
		else
			clicked = true;
	}

	
}
