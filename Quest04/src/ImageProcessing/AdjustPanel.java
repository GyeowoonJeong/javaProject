package ImageProcessing;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AdjustPanel extends JPanel{
	AdjustPanel() {
		setSize(1200, 180);
		setBackground(Color.getHSBColor((float)(13.0/255.0), (float)(13.0/255.0), (float)(43.0/255.0)));
		setLayout(null);
		
		JLabel bright = new JLabel("Brightness");
		JLabel contrast = new JLabel("Constrast");
		JLabel red  = new JLabel("Red");
		JLabel green  = new JLabel("Green");
		JLabel blue  = new JLabel("Blue");
		
		
		GrayScale gs = new GrayScale();
		Brightness bns = new Brightness();
		Contrast cts = new Contrast();
		EdgeDetection edt = new EdgeDetection();
		JButton magnify = new JButton("Magnify");
		magnify.addActionListener(MainFrame.imgPanel2);
		RedScale rs = new RedScale();
		GreenScale grs = new GreenScale();
		BlueScale bs = new BlueScale();
		
		gs.setBounds(75, 40, 120, 20);
		gs.maintanance.setBounds(75, 110, 120, 20);
		bright.setBounds(350, 30, 100, 15);
		bns.setBounds(350, 50, 200, 15);
		contrast.setBounds(350, 100, 100, 15);
		cts.setBounds(350, 120, 200, 15);
		edt.setBounds(675, 40, 120, 20);
		magnify.setBounds(675, 110, 120, 20);
		red.setBounds(950, 30, 100, 15);
		rs.setBounds(950, 50, 200, 15);
		green.setBounds(950, 70, 100, 15);
		grs.setBounds(950, 90, 200, 15);
		blue.setBounds(950, 110, 100, 15);
		bs.setBounds(950, 130, 200, 15);
		
		bright.setFont(new Font("HanziPen TC", Font.BOLD, 15));
		bright.setForeground(new Color(233, 239, 255));
		contrast.setFont(new Font("HanziPen TC", Font.BOLD, 15));
		contrast.setForeground(new Color(233, 239, 255));
		red.setFont(new Font("HanziPen TC", Font.BOLD, 15));
		red.setForeground(new Color(233, 239, 255));
		green.setFont(new Font("HanziPen TC", Font.BOLD, 15));
		green.setForeground(new Color(233, 239, 255));
		blue.setFont(new Font("HanziPen TC", Font.BOLD, 15));
		blue.setForeground(new Color(233, 239, 255));
		gs.setFont(new Font("HanziPen TC", Font.BOLD, 12));
		gs.maintanance.setFont(new Font("HanziPen TC", Font.BOLD, 12));
		edt.setFont(new Font("HanziPen TC", Font.BOLD, 12));
		magnify.setFont(new Font("HanziPen TC", Font.BOLD, 12));
		
		this.add(gs);
		this.add(gs.maintanance);
		this.add(bns);
		this.add(cts);
		this.add(edt);
		this.add(magnify);
		this.add(bright);
		this.add(contrast);
		this.add(rs);
		this.add(red);
		this.add(grs);
		this.add(green);
		this.add(bs);
		this.add(blue);
	}
}
