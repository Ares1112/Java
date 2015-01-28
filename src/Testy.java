import static org.junit.Assert.*;
import gameState.GameStateManager;
import gameState.HostState;
import gameState.InPlayState;
import gameState.InstructionState;
import gameState.JoinState;
import gameState.MenuState;
import gameState.PlayListener;
import gameState.WinState;
import graphics.Background;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import main.Game;
import main.GamePanel;

import org.junit.Test;

import server.Listener;
import server.ServerInstance;

/**
 * Klasa u¿ywana do testu odpowiedzi z listenera, sprawdza czy klawisze zosta³y
 * odebrane przez Listener
 * 
 * @author Arkadiusz B³asiak
 * 
 */
class ToTestListener implements Runnable {

	ToTestListener() {
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

/**
 * Klasa u¿ywana przy testach PlayListener, tworzy PlayListener i sprawdza czy
 * zmienia dane
 * 
 * @author Arkadiusz B³asiak
 * 
 */
class ToTestPListener implements Runnable {
	ToTestPListener() {
		new Thread(this).start();
	}

	@Override
	public void run() {
		ServerSocket sv = null;
		Socket s = null;
		try {
			sv = new ServerSocket(12349);
			s = sv.accept();
		} catch (IOException e) {
			e.printStackTrace();
		}
		PlayListener lst = new PlayListener(s);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		assertEquals(InPlayState.winner, "Test");

		try {
			s.close();
			sv.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}

/**
 * Klasa u¿ywana przy testach PlayListener2, tworzy PlayListener i sprawdza czy
 * zmienia dane
 * 
 * @author Arkadiusz
 * 
 */
class ToTestPListener2 implements Runnable {
	ToTestPListener2() {
		new Thread(this).start();
	}

	@Override
	public void run() {
		ServerSocket sv = null;
		Socket s = null;
		try {
			sv = new ServerSocket(12350);
			s = sv.accept();
		} catch (IOException e) {
			e.printStackTrace();
		}
		PlayListener lst = new PlayListener(s);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		assertEquals(InPlayState.p1y, 11);
		assertEquals(InPlayState.p2y, 12);
		assertEquals(InPlayState.bx, 13, 0.1);
		assertEquals(InPlayState.by, 14, 0.1);
		assertEquals(InPlayState.speed, 15, 0.1);
		assertEquals(InPlayState.points1, 1);
		assertEquals(InPlayState.points2, 0);

		try {
			s.close();
			sv.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}

/**
 * Klasa u¿ywana przy testach PlayListener3, tworzy PlayListener i sprawdza czy
 * nie zmienia danych
 * 
 * @author Arkadiusz
 * 
 */
class ToTestPListener3 implements Runnable {
	ToTestPListener3() {
		new Thread(this).start();
	}

	@Override
	public void run() {
		ServerSocket sv = null;
		Socket s = null;
		try {
			sv = new ServerSocket(12351);
			s = sv.accept();
		} catch (IOException e) {
			e.printStackTrace();
		}
		PlayListener lst = new PlayListener(s);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		assertEquals(InPlayState.p1y, 11);
		assertEquals(InPlayState.p2y, 12);
		assertEquals(InPlayState.bx, 13, 0.1);
		assertEquals(InPlayState.by, 14, 0.1);
		assertEquals(InPlayState.speed, 15, 0.1);
		assertEquals(InPlayState.points1, 1);
		assertEquals(InPlayState.points2, 0);
		assertEquals(InPlayState.winner, "Test");

		try {
			s.close();
			sv.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}

/**
 * Testy jednostkowe
 * 
 * @author Arkadiusz B³asiak
 * 
 */
public class Testy {

	/**
	 * GameStateManager potrzebny do testów
	 */
	private GameStateManager gsm = new GameStateManager();

	/**
	 * Test klasy Listener - przesy³anie danych i odebranie ich przez listener,
	 * jeœli OK to PASS
	 * 
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	@Test
	public void testListener() throws UnknownHostException, IOException {
		new ToTestListener();
		Socket s = new Socket("localhost", 12346);
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

	/**
	 * Test HostState - sprawdza czy port jest dobrze wpisany
	 */
	@Test
	public void testHostState() {
		HostState hs = new HostState(gsm);
		try {
			hs.hostGame("test");
			fail("Integer przeszedl jako znaki");
		} catch (NumberFormatException e) {

		}
	}

	/**
	 * Test JoinState - sprawdza czy nie ³¹czy siê z nieistniej¹cym serwerem
	 */
	@Test
	public void testJoinState() {
		JoinState js = new JoinState(gsm);
		js.keyTyped('1');
		js.keyPressed(KeyEvent.VK_DOWN);
		js.keyTyped('1');
		js.keyPressed(KeyEvent.VK_ENTER);
		assertEquals(js.wrong, true);
	}

	/**
	 * Test serwera - sprawdza czy mo¿e siê po³¹czyæ 2 klientów
	 * 
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	@Test
	public void testServer() throws UnknownHostException, IOException {
		ServerInstance sv = new ServerInstance(12347);
		Socket s = null;
		Socket ss = null;
		try {
			s = new Socket("localhost", 12347);
			ss = new Socket("localhost", 12347);
		} catch (SocketException e) {
			fail("Mniej niz 2 polaczenia");
		}
		s.close();
		ss.close();
	}

	/**
	 * Test Background - sprawdza czy nie ³aduje nieistniej¹cego pliku, potem
	 * istniej¹cy
	 */
	@Test
	public void testLoadBg() {
		try {
			new Background("Abc");
			fail("Nie wyskoczy³ b³¹d podczas ³adowania");
		} catch (Exception e) {

		}
		try {
			new Background("/bg.jpg");
		} catch (Exception e) {
			fail("Nie za³adowano bg");
		}
	}

	/**
	 * Test PlayListenera - przes³anie jednej danej, sprawdzenie czy odbiór
	 * zmieni wartoœæ winner w InPlayState
	 */
	@Test
	public void testPlayListener1() {
		new ToTestPListener();
		Socket s = null;
		DataOutputStream out = null;
		try {
			s = new Socket("localhost", 12349);
			out = new DataOutputStream(s.getOutputStream());
			out.writeUTF("Test");
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		try {
			s.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Test PlayListenera - przes³anie 7 danych, sprawdzenie czy zosta³y
	 * zmienione wartoœci pi³eczki, paletek itp
	 */
	@Test
	public void testPlayListener2() {
		new ToTestPListener2();
		Socket s = null;
		DataOutputStream out = null;
		try {
			s = new Socket("localhost", 12350);
			out = new DataOutputStream(s.getOutputStream());
			out.writeUTF("11,12,13,14,15,1,0");
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		try {
			s.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Test PlayListenera - przes³anie 2 danych, sprawdzenie czy PlayListener je
	 * zignoruje
	 */
	@Test
	public void testPlayListener3() {
		new ToTestPListener3();
		Socket s = null;
		DataOutputStream out = null;
		try {
			s = new Socket("localhost", 12351);
			out = new DataOutputStream(s.getOutputStream());
			out.writeUTF("11,12");
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		try {
			s.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Test wyjœcia z menu, jeœli naciœniêto ESC
	 */
	@Test
	public void testESC() {
		gsm.setState(GameStateManager.INSTRUCTIONSTATE);
		gsm.keyPressed(KeyEvent.VK_ESCAPE);
		assertEquals(gsm.getState(), GameStateManager.MENUSTATE);
		gsm.setState(GameStateManager.WINSTATE);
		gsm.keyPressed(KeyEvent.VK_ESCAPE);
		assertEquals(gsm.getState(), GameStateManager.MENUSTATE);
		gsm.setState(GameStateManager.HOSTSTATE);
		gsm.keyPressed(KeyEvent.VK_ESCAPE);
		assertEquals(gsm.getState(), GameStateManager.MENUSTATE);
		gsm.setState(GameStateManager.JOINSTATE);
		gsm.keyPressed(KeyEvent.VK_ESCAPE);
		assertEquals(gsm.getState(), GameStateManager.MENUSTATE);
	}

	/**
	 * Testy GamePanel - utworzenie, nas³uchiwanie klawiszy, dzia³anie w¹tka
	 */
	@Test
	public void testGamePanel() {
		try {
			new Game();
		} catch (Exception e) {
			fail("Nie mo¿na utworzyæ okienka gry");
		}
		GamePanel gp = null;
		try {
			gp = new GamePanel();
		} catch (Exception e) {
			fail("Nie mo¿na utworzyæ GamePanel");
		}
	}

	/**
	 * Test czy wchodzi do podmenu instrukcja
	 */
	@Test
	public void testMenuState1() {
		try {
			gsm.setState(GameStateManager.MENUSTATE);
			gsm.keyPressed(KeyEvent.VK_DOWN);
			gsm.keyPressed(KeyEvent.VK_DOWN);
			gsm.keyPressed(KeyEvent.VK_ENTER);
		} catch (Exception e) {
			fail("Nie mo¿na zmieniaæ opcji w menu");
		}
		assertEquals(gsm.getState(), GameStateManager.INSTRUCTIONSTATE);
	}

	/**
	 * Test czy wchodzi do podmenu host
	 */
	@Test
	public void testMenuState2() {
		try {
			gsm.setState(GameStateManager.MENUSTATE);
			gsm.keyPressed(KeyEvent.VK_DOWN);
			gsm.keyPressed(KeyEvent.VK_ENTER);
		} catch (Exception e) {
			fail("Nie mo¿na zmieniaæ opcji w menu");
		}
		assertEquals(gsm.getState(), GameStateManager.HOSTSTATE);
	}

	/**
	 * Test czy wchodzi do podmenu join
	 */
	@Test
	public void testMenuState3() {
		try {
			gsm.setState(GameStateManager.MENUSTATE);
			gsm.keyPressed(KeyEvent.VK_ENTER);
		} catch (Exception e) {
			fail("Nie mo¿na zmieniaæ opcji w menu");
		}
		assertEquals(gsm.getState(), GameStateManager.JOINSTATE);
	}

	/**
	 * Test czy wszystkie GameState rysuj¹ siê (przy okazji Bumper, Ball i
	 * Background
	 */
	@Test
	public void testDraw() {
		BufferedImage image = new BufferedImage(100, 100,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D g = (Graphics2D) image.getGraphics();
		HostState hs = new HostState(gsm);
		try {
			hs.draw(g);
		} catch (Exception e) {
			fail("Nie mo¿na narysowaæ HostState");
		}
		InPlayState ips = new InPlayState(gsm);
		try {
			ips.draw(g);
		} catch (Exception e) {
			fail("Nie mo¿na narysowaæ InPlayState");
		}
		InstructionState is = new InstructionState(gsm);
		try {
			is.draw(g);
		} catch (Exception e) {
			fail("Nie mo¿na narysowaæ InstructionState");
		}
		JoinState js = new JoinState(gsm);
		try {
			js.draw(g);
		} catch (Exception e) {
			fail("Nie mo¿na narysowaæ JoinState");
		}
		MenuState ms = new MenuState(gsm);
		try {
			ms.draw(g);
		} catch (Exception e) {
			fail("Nie mo¿na narysowaæ MenuState");
		}
		WinState ws = new WinState(gsm);
		try {
			ws.draw(g);
		} catch (Exception e) {
			fail("Nie mo¿na narysowaæ WinState");
		}
	}
	
	/**
	 * Test koñcowy, uruchamia ca³¹ grê
	 */
	@Test
	public void testzRunGame(){
		try{
			Game g = new Game();
			g.main();
		} catch(Exception e){
			fail("Nie mo¿na uruchomiæ gry");
		}
	}
}