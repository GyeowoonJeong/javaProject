package Connect6_Game;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class ChattingPanel extends JPanel implements ActionListener  {	
	JTextField sendMsg;
	JTextArea msgBox;
	JScrollPane scrollPane;
	TitledBorder border;
	
	ChattingPanel() {
		setSize(360, 440);
		setLayout(null);
		setBackground(new Color(51, 51, 58));
		
		TitledBorder border = new TitledBorder(new LineBorder(new Color(247, 234, 255), 2, true), "Chatting", TitledBorder.CENTER, 
				TitledBorder.CENTER, new Font("I AM A PLAYER", Font.BOLD, 15),new Color(247, 234, 255));
		sendMsg = new JTextField();
		msgBox = new JTextArea();
		scrollPane = new JScrollPane(msgBox);
		scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());

		setBorder(border);
		msgBox.setEnabled(false);
		
		sendMsg.setFont(new Font("I AM A PLAYER", Font.BOLD, 17));
		msgBox.setFont(new Font("I AM A PLAYER", Font.BOLD, 17));
		sendMsg.setForeground(new Color(51, 51, 58));
		msgBox.setForeground(new Color(51, 51, 58));
		
		scrollPane.setBounds(10, 15, 340, 370);
		sendMsg.addActionListener(this);
		sendMsg.setBounds(7, 390, 345, 40);
		this.add(scrollPane);
		this.add(sendMsg);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == sendMsg) {
			String msg=sendMsg.getText();
			
		      if(msg.length()==0)return;
		      
		      try{  
		    	  Main.frame.client.sender.sendFunct("[MESSAGE],"+ Main.frame.client.receiver.threadNum + "," + 
		        Main.frame.client.sender.name + "," + msg);
		    	  msgBox.append("[" + Main.frame.gl.id.getText() + "] : " + msg + "\n");
		    	  sendMsg.setText("");

		      }catch(Exception ie){}
		    }
	}
		
}
