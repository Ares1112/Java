import static org.junit.Assert.*;
import gameState.GameStateManager;
import gameState.HostState;
import gameState.InPlayState;
import gameState.JoinState;

import java.awt.event.KeyEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import org.junit.Test;

import server.Listener;
import server.ServerInstance;


class toTestListener implements Runnable {
	
	toTestListener(){
		new Thread(this).start();
	}
	
	@Override
	public void run() {
		ServerSocket sv;
		try {
			sv = new ServerSocket(12346);
			Socket s = sv.accept();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
}

public class Testy {

	private GameStateManager gsm = new GameStateManager();
	
	@Test
	public void testListener() throws UnknownHostException, IOException {
		new toTestListener();
		Socket s = new Socket("localhost",12346);
		DataOutputStream out = new DataOutputStream(s.getOutputStream());
		//Listener lst = new Listener(s);
		out.writeUTF("UP");
		out.flush();
		//assertEquals(lst.getUp(), true);
		out.writeUTF("DW");
		out.flush();
		//assertEquals(lst.getDw(), true);
		out.writeUTF("ESC");
		out.flush();
		//assertEquals(lst.getEsc(), true);
		out.writeUTF("SPACE");
		out.flush();
		//assertEquals(lst.getSpace(), true);
		//s.close();
		//out.close();
	}
	
	@Test
	public void testServer() throws UnknownHostException, IOException {
		ServerInstance sv = new ServerInstance(12345);
		Socket s = null;
		Socket ss = null;
		try{
			s = new Socket("localhost", 12345);
			ss = new Socket("localhost", 12345);
		} catch (SocketException e){
			fail("Mniej niz 2 polaczenia");
		}
		DataInputStream in = new DataInputStream(s.getInputStream());
		String a = in.readUTF();
		assertEquals(a.split(",").length, 7);
	}
	
	@Test
	public void testHostState() {
		HostState hs = new HostState(gsm);
		try {
			hs.hostGame("test");
			fail("Integer przeszedl jako znaki");
		} catch (NumberFormatException e){
			
		}
	}
	
	@Test
	public void testJoinState() {
		JoinState js = new JoinState(gsm);
		js.keyTyped('1');
		js.keyPressed(KeyEvent.VK_DOWN);
		js.keyTyped('1');
		js.keyPressed(KeyEvent.VK_ENTER);
		assertEquals(js.wrong, true);
	}
	
}
