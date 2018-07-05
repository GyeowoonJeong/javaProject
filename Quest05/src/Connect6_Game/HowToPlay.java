package Connect6_Game;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class HowToPlay extends JPanel{
	JButton understand;
	JLabel howto;
	LineBorder border;
	
	HowToPlay() {
		setSize(500, 300);
		setBackground(new Color(51, 51, 58));
		setLayout(null);
		border = new LineBorder(new Color(247, 234, 255), 4, true);
		this.setBorder(border);
		howto = new JLabel("두 사람이 바둑판에 흑돌과 백돌을 교대로 놓아 여섯개가 일렬로 놓이게 만드는 게임" + "\n\n" + 
				"흑은 처음에 한수를 두고, 그 다음에는 두수씩 둠" + "\n\n");
		understand = new JButton("Understand");
		howto.setForeground(Color.white);
		
		howto.setBounds(30, 50, 300, 200);
		understand.setBounds(180, 230, 140, 40);
		this.add(understand);
		this.add(howto);
	}
}
