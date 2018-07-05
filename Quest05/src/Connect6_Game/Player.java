package Connect6_Game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class Player extends JPanel implements ActionListener{
	private BufferedImage image1;
	String name, path;
	Shape circle;
	Color color;
	JTextField namespace;
	JButton character, ok;
	LineBorder border;
	int result;
	
	Player(String path) {
		this.path = path;
		setSize(1000, 325);
		setBackground(new Color(51, 51, 58));
		setLayout(null);
		border = new LineBorder(new Color(247, 234, 255), 4, true);
		this.setBorder(border);
		
		namespace = new JTextField();
		namespace.setBorder(border);
		namespace.setBackground(new Color(51, 51, 58));
		namespace.setForeground(Color.WHITE);
		namespace.setFont(new Font("I AM A PLAYER", Font.BOLD, 30));
		namespace.setHorizontalAlignment(SwingConstants.CENTER);
		namespace.setEditable(false);
		
		ok = new JButton("OK");
		ok.setBackground(new Color(247, 234, 255));
		ok.setOpaque(true);
		ok.setBorderPainted(false);
		ok.setFont(new Font("I AM A PLAYER", Font.BOLD, 20));
	
		namespace.setBounds(400, 120, 350, 70);
		ok.setBounds(830, 120, 100, 70);
		
		ok.addActionListener(this);
		this.add(namespace);
		//this.add(ok);
		try {	
			image1 = ImageIO.read(new File(path));	
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		this.setVisible(true);
	}
	
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		super.paint(g);
		g2.drawImage(image1, 50, 43, 250, 250, null);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		/*if(e.getActionCommand().equals("OK")) {
			if(namespace.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "플레이어 이름이 설정되지 않았습니다.", "경고", JOptionPane.WARNING_MESSAGE);
				return;
			}
			
			result = JOptionPane.showConfirmDialog(null, "더 이상 닉네임을 변경할 수 없습니다. 이대로 하시겠습니까?", "경고", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
			if(result == 0) {
				namespace.setText(namespace.getText());
				namespace.setEditable(false);
			}
		}*/
		
	}
	
}
