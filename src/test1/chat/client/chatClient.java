package test1.chat.client;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class chatClient {
	String chatID;
	Socket s;
	DataOutputStream dout;
	DataInputStream din;
	TextArea ta;
	
	public void createUi() {
		Frame f = new Frame();
		ta = new TextArea();
		TextField tf = new TextField(25);
		TextField tf2 = new TextField(10);
		Button b = new Button("연결");
		Panel p = new Panel();
		
		f.add(ta);
		f.add(p,BorderLayout.SOUTH);
		p.add(tf);
		p.add(tf2);
		p.add(b);
		
		tf.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//채팅 메세지 전송
				try {
					dout.writeUTF(chatID+tf.getText());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				tf.setText("");
			}
		});
		
		b.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//채팅 연결
				chatID = tf2.getText();
				f.setTitle(chatID);
				
				try {
					 s = new Socket("localhost",9999);
					 dout = new DataOutputStream(s.getOutputStream());
					 din = new DataInputStream(s.getInputStream());
					ClientThread t = new ClientThread();
					t.start();
					tf2.setBackground(Color.RED);
					tf2.setEditable(false);
					b.setEnabled(false);
					chatID = "["+chatID+"] ";
					dout.writeUTF(chatID);
					ta.append(chatID+"님이 입장하셨습니다.\n");
				} catch (UnknownHostException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		f.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		f.setBounds(500,150,400,500);
		f.setVisible(true);
	}
	public static void main(String[] args) {
		new chatClient().createUi();
	}
	
	class ClientThread extends Thread{
		@Override
		public void run() {
			while(true) {
				try {
					ta.append(din.readUTF()+'\n');
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
