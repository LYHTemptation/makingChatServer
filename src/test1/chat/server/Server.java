package test1.chat.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
	ArrayList<DataOutputStream> list = new ArrayList();

	public void broadcast(String msg) {
		synchronized (list) { // 동시성 문제 해결
			for(DataOutputStream out:list) { //for each
				try {
						out.writeUTF(msg);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}			
		}
	}
	
	public void serverReady() {
		try {
			ServerSocket ss = new ServerSocket(9999);
			System.out.println("server ready...");
			while(true){
				Socket s = ss.accept();
				DataInputStream din = new DataInputStream(s.getInputStream());
				DataOutputStream dout = new DataOutputStream(s.getOutputStream());
				ServerThread st = new ServerThread(s,din,dout);
				list.add(dout);
				st.start();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	class ServerThread extends Thread{
		Socket s;
		DataInputStream din;
		DataOutputStream dout;
		String chatId;
		
		public ServerThread(Socket s, DataInputStream din, DataOutputStream dout) {
			// TODO Auto-generated constructor stub
			this.s =s;
			this.din = din;
			this.dout = dout;
		}

		@Override
		public void run() {
			try {
				while(true) {
					String receiveMsg = din.readUTF();
					if(chatId == null) {
						chatId = receiveMsg;						
					}else {
						broadcast(receiveMsg);						
					}
				}
			}catch(Exception e) {
				System.out.println("client 퇴실");
				if(chatId !=null) {
					broadcast(chatId+"님이 퇴장하였습니다.");					
				}
				synchronized (dout) {
					list.remove(dout);					
				}
				/* e.printStackTrace(); */
			}
		}
	}
	
	public static void main(String[] args) {
		Server server = new Server();
		server.serverReady();
	}
}
