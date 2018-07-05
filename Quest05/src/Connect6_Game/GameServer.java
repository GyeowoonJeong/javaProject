package Connect6_Game;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

public class GameServer {
	HashMap clients;
	private int count = 0;
	
	GameServer() {
		clients = new HashMap();
		Collections.synchronizedMap(clients);
	}
	
	public void start() {
		ServerSocket serverSocket = null;
		Socket socket = null;
		
		try {
			serverSocket = new ServerSocket(7777);
			System.out.println("서버가 시작되었습니다.");
			System.out.println(count);
			while(true) {
				socket = serverSocket.accept();
				System.out.println("["+socket.getInetAddress()+":"+socket.getPort()+"]"+"에서 접속하였습니다.");
				ServerReceiver thread = new ServerReceiver(socket);
				count++;
				thread.start();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	void sendToAll(String msg) {
		Iterator it = clients.keySet().iterator();
		
		while(it.hasNext()) {
			try {
				DataOutputStream out = (DataOutputStream)clients.get(it.next());
				out.writeUTF(msg);
			} catch(IOException e) {}
		}
	}
	
	void sendToThread(String cnt) {
		int spec = Integer.parseInt(cnt);
		try {
			DataOutputStream out = (DataOutputStream)clients.get(spec);
			out.writeUTF(cnt);
		} catch(IOException e) {}
	}
	
	void sendToPartner(int key, String cmd) {
		try {
			if(key % 2 == 1) {
				DataOutputStream out = (DataOutputStream)clients.get(key + 1);
				if(out == null) {
					out = (DataOutputStream)clients.get(key);
					out.writeUTF("[WARNING],상대가 없습니다.");
				}
				else
					out.writeUTF(cmd);
			}
			else if(key % 2 == 0) {
				DataOutputStream out = (DataOutputStream)clients.get(key - 1);
				out.writeUTF(cmd);
			}
		} catch(IOException e) {}
	}

	public static void main(String args[]) {
		new GameServer().start();
	}
	
	class ServerReceiver extends Thread {
		Socket socket;
		DataInputStream in;
		DataOutputStream out;
		int threadNum;
		String msg;
		
		ServerReceiver(Socket socket) {
			this.socket = socket;
			try {
				in = new DataInputStream(socket.getInputStream());
				out = new DataOutputStream(socket.getOutputStream());
			} catch(IOException e) {}
		}
		
		@Override  
		public void run() {
			String name = "";
											
			try {
				name = in.readUTF();
				sendToAll("#"+name+"님이 들어오셨습니다.");
				clients.put(count, out);
				sendToThread(Integer.toString(count));
				System.out.println("현재 서버접속자 수는 " + clients.size() + "입니다.");
				
				while(in!=null) {
					String msg = in.readUTF();
					sendToPartner(split(msg), msg);
				}
				
			} catch(IOException e) {
				
			} finally {
				sendToAll("#" + name + "님이 나가셨습니다.");
				clients.remove(count);
				count--;
				System.out.println("["+socket.getInetAddress()+":"+socket.getPort()+"]"+"에서 접속을 종료하였습니다.");
				System.out.println("현재 서버접속자 수는 "+clients.size()+"입니다.");
			}
			
		}
			
		public int split(String cmd) {
			String[] arr;
			arr = cmd.split(",");
			return Integer.parseInt(arr[1]);
		}
	}
	
}
