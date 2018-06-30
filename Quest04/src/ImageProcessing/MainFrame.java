package ImageProcessing;
import java.awt.Color;
import java.awt.image.BufferedImage;

import javax.swing.*;

public class MainFrame extends JFrame{
	public static ImagePanel imgPanel1; 
	public static ImagePanel imgPanel2;
	AdjustPanel adjustPanel;
	
	MainFrame() {
		imgPanel1 = new ImagePanel();
		imgPanel2 = new ImagePanel();
		adjustPanel = new AdjustPanel();
		this.setTitle("Image Processing");
		this.setSize(1300, 850);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setLayout(null);
		imgPanel1.setLocation(50, 20);
		imgPanel2.setLocation(700, 20);
		adjustPanel.setLocation(50, 600);
		imgPanel1.setBackground(Color.getHSBColor((float)(13.0/255.0), (float)(13.0/255.0), (float)(43.0/255.0)));
		imgPanel2.setBackground(Color.getHSBColor((float)(13.0/255.0), (float)(13.0/255.0), (float)(43.0/255.0)));

		//adjustPanel.gray.addActionListener(new GrayScale(imgPanel2));
		this.add(imgPanel1);
		this.add(imgPanel2);
		this.add(adjustPanel);
		this.setMenuBar();
		getContentPane().setBackground(Color.DARK_GRAY);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		this.setVisible(true);
	}
	
	public ImagePanel getImagePanel1() {
		return imgPanel1;
	}
	
	public ImagePanel getImagePanel2() {
		return imgPanel2;
	}
	
	public void setMenuBar() {
		JMenuBar mb = new JMenuBar(); 
		JMenu fileMenu = new JMenu("File");
		JMenuItem open, save, exit;
		
		open = new JMenuItem("OpenFile");
		save = new JMenuItem("SaveFile");
		exit = new JMenuItem("Exit");
		
		open.addActionListener(imgPanel1);
		save.addActionListener(new FileSave());
		fileMenu.add(open);
		fileMenu.add(save);
		fileMenu.addSeparator();
		fileMenu.add(exit);
		mb.add(fileMenu);
		this.setJMenuBar(mb);
	}
}
