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


class ToTestListener implements Runnable {
	
	ToTestListener(){
		new Thread(this).start();
	}
	
	@Override
	public void run() {
		ServerSocket sv;
		Socket s = null;
		try {
			sv = new ServerSocket(12346);
			s = sv.accept();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Listener lst = new Listener(s);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		assertEquals(lst.getUp(), true);
		assertEquals(lst.getDw(), true);
		assertEquals(lst.getEsc(), true);
		assertEquals(lst.getSpace(), true);
		lst.stop();
		try {
			s.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		
	}
	
}

public class Testy {

	private GameStateManager gsm = new GameStateManager();
	
	@Test
	public void testListener() throws UnknownHostException, IOException {
		new ToTestListener();
		Socket s = new Socket("localhost",12346);
		DataOutputStream out = null;
		out = new DataOutputStream(s.getOutputStream());
		out.writeUTF("UP");
		out.flush();
		out.writeUTF("DW");
		out.flush();
		out.writeUTF("ESC");
		out.flush();
		out.writeUTF("SPACE");
		out.flush();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		s.close();
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
	
	@Test
	public void testServer() throws UnknownHostException, IOException {
		ServerInstance sv = new ServerInstance(12347);
		Socket s = null;
		Socket ss = null;
		try{
			s = new Socket("localhost", 12347);
			ss = new Socket("localhost", 12347);
		} catch (SocketException e){
			fail("Mniej niz 2 polaczenia");
		}
		DataInputStream in = new DataInputStream(s.getInputStream());
		String a = in.readUTF();
		assertEquals(a.split(",").length, 7);
		s.close();
		ss.close();
	}
	
}
