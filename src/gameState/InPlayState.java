package gameState;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.Line2D;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.DecimalFormat;

import main.GamePanel;
import graphics.Bumper;
import graphics.Ball;

/**
 * Klasa reprezentuj¹ca stan "w grze"
 * @author Arkadiusz B³asiak
 *
 */
public class InPlayState extends GameState {
	
	/**
	 * Odbijak 1 gracza
	 */
	private Bumper p1;
	/**
	 * Odbijak 2 gracza
	 */
	private Bumper p2;
	/**
	 * Pi³eczka
	 */
	private Ball ball;
	/**
	 * pozycja Y odbijaka 1 gracza
	 */
	public static int p1y = 50;
	/**
	 * pozycja Y odbijaka 2 gracza
	 */
	public static int p2y = 50;
	/**
	 * pozycja X pi³eczki
	 */
	public static double bx = 390;
	/**
	 * pozycja Y pi³eczki
	 */
	public static double by = 210;
	/**
	 * punkty 1 gracza
	 */
	public static int points1 = 0;
	/**
	 * punkty 2 gracza
	 */
	public static int points2 = 0;
	/**
	 * port serwera
	 */
	public static int port;
	/**
	 * po³¹czenie z serwerem
	 */
	private Socket s;
	/**
	 * Ip serwera, jeœli gracz nie jest hostem
	 */
	public static String ip;
	/**
	 * zwyciêzca gry
	 */
	public static String winner = "";
	/**
	 * Dane wyjœciowe przesy³ane do serwera
	 */
	private DataOutputStream out = null;
	/**
	 * prêdkoœæ pi³eczki
	 */
	public static double speed = 3.0;
	
	/**
	 * Nas³uchiwanie odpowiedzi z serwera
	 */
	private PlayListener ps;
	
	/**
	 * Konstruktor ustawiaj¹cy GameStateManager, 
	 * tworz¹cy paletki 1 i 2 gracza oraz pi³eczkê
	 * @param gsm
	 */
	public InPlayState(GameStateManager gsm) {

		this.gsm = gsm;

		p1 = new Bumper();
		p2 = new Bumper();
		ball = new Ball();

	}
	
	/**
	 * Metoda tworz¹ca po³¹czenie miêdzy graczem a serwerem
	 * @param _ip - ip serwera
	 * @param _port - port serwera
	 */
	private void createSocket(String _ip, int _port) {
		if (HostState.host == true) {
			try {
				s = new Socket("localhost", _port);
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				s = new Socket(_ip, _port);
				JoinState.wrong = false;
			} catch (SocketException e) {
				JoinState.wrong = true;
				gsm.setState(GameStateManager.JOINSTATE);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	/**
	 * Inicjalizacja, tworzy po³¹czenie i strumieñ danych wysy³anych do serwera
	 */
	public void init() {
		createSocket(ip, port);
		try {
			out = new DataOutputStream(s.getOutputStream());
			winner = "";
			bx = 390;
			by = 210;
			speed = 3;
			points1 = 0;
			points2 = 0;
			ps = new PlayListener(s);
		} catch (Exception e) {
			JoinState.wrong = true;
		}

	}

	/**
	 * Metoda sprawdzaj¹ca czy partia zosta³a wygrana
	 */
	public void update() {
		if (s != null) {
			if(!winner.equals("")){
				gsm.setState(GameStateManager.WINSTATE);
				ps.running = false;
			}
			 try { 
				 out.writeUTF("Ping"); 
				 out.flush(); 
			 }catch (IOException e){
				 winner="p2";
				 gsm.setState(GameStateManager.WINSTATE); 
				 ps.running = false;
				 try {
					s.close();
				} catch (IOException e1) {
				}
			 }
		}

	}
	
	/**
	 * Metoda rysuj¹ca na ekran pi³eczkê, punkty, odbijaki, wartoœæ prêdkoœci
	 */
	public void draw(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		g.setColor(Color.WHITE);
		g.draw(new Line2D.Double(398, 0, 398, GamePanel.HEIGHT));
		g.setColor(Color.BLUE);
		p1.draw(g, 20, p1y);
		p2.draw(g, 755, p2y);
		ball.draw(g, bx, by);
		g.drawString(String.valueOf(points1), 100, 50);
		g.drawString(String.valueOf(points2), 600, 50);
		g.setColor(Color.RED);
		DecimalFormat df = new DecimalFormat("#.00");
		g.drawString("Speed: "+String.valueOf(df.format(speed)), (GamePanel.WIDTH/2)-50, GamePanel.HEIGHT/6);

	}
	
	/**
	 * Metoda pobieraj¹ca wciœniête klawisze i przesy³aj¹ca je na serwer
	 */
	public void keyPressed(int k) {
		try {
			if (k == KeyEvent.VK_W) {
				out.writeUTF("UP");
			}
			if (k == KeyEvent.VK_S) {
				out.writeUTF("DW");
			}
			if (k == KeyEvent.VK_SPACE) {
				out.writeUTF("SPACE");
			}
			if (k == KeyEvent.VK_ESCAPE) {
				out.writeUTF("ESC");
				gsm.setState(GameStateManager.MENUSTATE);
				ps.running = false;
				HostState.host = false;
				s.close();
			}
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void keyReleased(int k) {

	}
	
	public void keyTyped(char k) {

	}

}
