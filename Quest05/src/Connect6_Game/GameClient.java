package Connect6_Game;

import java.awt.Color;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.JOptionPane;



public class GameClient {
	public static ClientSender sender;
	public static ClientReceiver receiver;
	
	GameClient() {
		
		try {
			String serverIp = "127.0.0.1";
			Socket socket = new Socket(serverIp, 7777);
			System.out.println("서버에 연결되었습니다.");
			sender = new ClientSender(socket, Main.frame.gl.id.getText());
			receiver = new ClientReceiver(socket);
			Thread senderThread = new Thread(sender);
			Thread receiverThread = new Thread(receiver);
			
			senderThread.start();
			receiverThread.start();
			
		} catch(ConnectException ce) {
			ce.printStackTrace();
		} catch(Exception e) {}
	}
	
	static class ClientSender extends Thread {
		Socket socket;
		DataOutputStream out;
		String name;
		
		ClientSender(Socket socket, String name) {
			this.socket = socket;
			try {
				out = new DataOutputStream(socket.getOutputStream());
				this.name = name;
			} catch(Exception e) {}
		}
		//채팅창에서 입력하는 메세지를 보내는 것
		public void run() {
			try {
				if(out!=null) {
					out.writeUTF(name);
				}
				while(out!=null) {
					//out.writeUTF(cmd);
				}
			} catch(IOException e) {}
		}
		
		public void sendFunct(String cmd) {
			try {
				out.writeUTF(cmd);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	static class ClientReceiver extends Thread {
		Socket socket;
		DataInputStream in;
		String cmd;
		static int threadNum;
		String[] arr;
		
		ClientReceiver(Socket socket) {
			this.socket = socket;
			try {
				in = new DataInputStream(socket.getInputStream());
			} catch(IOException e) {}
		}
		
		public void run() {
			while(in!=null) {
				try {
					cmd = in.readUTF();
					if(isInteger(cmd)) {
						threadNum = Integer.parseInt(cmd);
					}
					
					else if(cmd.startsWith("[NAME]")) {
						arr = cmd.split(",");
						if(Integer.parseInt(arr[1]) % 2 == 1)
							Main.frame.spp.player1.namespace.setText(arr[2]);
						else
							Main.frame.spp.player2.namespace.setText(arr[2]);
					}
					
					else if(cmd.startsWith("[START]")) {
						Main.frame.spp.start.setEnabled(true);
					}
					else if(cmd.startsWith("[REQUEST]")) {
						Main.frame.client.sender.sendFunct("[NAME]," + Main.frame.client.receiver.threadNum + "," + 
								Main.frame.spp.player1.namespace.getText());
					}
					
					else if(cmd.startsWith("[WARNING]")) {
						arr = cmd.split(",");
						JOptionPane.showMessageDialog(null, arr[1], "경고", JOptionPane.WARNING_MESSAGE);
					}
					
					else if(cmd.startsWith("[STONES]")) {
						arr = cmd.split(",");
						int x = Integer.parseInt(arr[2]);
						int y = Integer.parseInt(arr[3]);
						System.out.println(arr[4]);
						Color color = stoc(arr[4]);
						Main.frame.gp.setted[(x - 15) / 40][(y - 15) / 40] = color;
						Main.frame.gp.stones.add(new SetStone(x, y, color));
						Main.frame.gp.checkMatch((x - 15) / 40, (y - 15) / 40, color);
						Main.frame.gp.repaint();
					}
					
					else if(cmd.startsWith("[TURN]")) {
						arr = cmd.split(",");
						Main.frame.gp.turn = stob(arr[2]);
						Main.frame.gsp.turn = stob(arr[2]);
						Main.frame.gsp.repaint();
					}
					
					else if(cmd.startsWith("[UNDO]")) {
						arr = cmd.split(",");
						Main.frame.gp.getUndoMessage();
					}
					
					else if(cmd.startsWith("[REPLAY]")) {
						Main.frame.getReplayMessage();
					}
					
					else if(cmd.startsWith("[UNDO_ANS]")) {
						arr = cmd.split(",");
						if(arr[2].equals("0")) {
							Main.frame.gp.removeStone();
						}
						else if(arr[2].equals("1")) {
							JOptionPane.showMessageDialog(null, "상대방이 요청을 거절했습니다.", "Undo", JOptionPane.PLAIN_MESSAGE);
						}
					}
					
					else if(cmd.startsWith("[REPLAY_ANS]")) {
						arr = cmd.split(",");
						if(arr[2].equals("0")) {
							Main.frame.replay();
						}
						else if(arr[2].equals("1")) {
							JOptionPane.showMessageDialog(null, "상대방이 요청을 거절했습니다.", "Replay", JOptionPane.PLAIN_MESSAGE);
						}
					}
					
					else if(cmd.startsWith("[MESSAGE]")) {
						arr = cmd.split(",");
						Main.frame.cp.msgBox.append("[" + arr[2] + "] : " + arr[3] + "\n");
					}
				} catch(IOException e) {}
			}
		}
		
		public static boolean isInteger(String input) {
			try {
				Integer.parseInt(input);
				return true;
			}
			catch (NumberFormatException e) {
				return false;
			}
		}
		
		public Color stoc (String color) {
			Color c = Color.BLACK;
			if(color.equals("java.awt.Color[r=0"))
				c = Color.BLACK;
			else if (color.equals("java.awt.Color[r=255"))
				c = Color.WHITE;
			return c;
		}
		
		public boolean stob (String turn) {
			boolean b = false;
			if(turn.equals("true"))
				b = true;
			else if (turn.equals("false"))
				b = false;
			return b;
		}

	}
}
