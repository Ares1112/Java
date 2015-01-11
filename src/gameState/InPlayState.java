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
	private int p1y;
	/**
	 * pozycja Y odbijaka 2 gracza
	 */
	private int p2y;
	/**
	 * pozycja X pi³eczki
	 */
	private double bx;
	/**
	 * pozycja Y pi³eczki
	 */
	private double by;
	/**
	 * punkty 1 gracza
	 */
	private int points1;
	/**
	 * punkty 2 gracza
	 */
	private int points2;
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
	public static String winner;
	/**
	 * Dane wejœciowe otrzymane z serwera
	 */
	private DataInputStream in = null;
	/**
	 * Dane wyjœciowe przesy³ane do serwera
	 */
	private DataOutputStream out = null;
	/**
	 * prêdkoœæ pi³eczki
	 */
	private double speed;

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
		} catch (Exception e) {
			JoinState.wrong = true;
		}

	}

	/**
	 * Metoda aktualizuj¹ca parametry pi³eczki, 
	 * punktów, odbijaków - pobiera dane z serwera,
	 * jeœli otrzyma tylko jedn¹ dan¹ - jest to zwyciêzca
	 */
	public void update() {
		if (s != null) {
			try {
				in = new DataInputStream(s.getInputStream());
				String[] spl = in.readUTF().split(",");
				if (spl.length == 7) {
					p1y = Integer.parseInt(spl[0]);
					p2y = Integer.parseInt(spl[1]);
					bx = Double.parseDouble(spl[2]);
					by = Double.parseDouble(spl[3]);
					speed = Double.parseDouble(spl[4]);
					points1 = Integer.parseInt(spl[5]);
					points2 = Integer.parseInt(spl[6]);
				} else if(spl.length == 1){
					winner = spl[0];
					gsm.setState(GameStateManager.WINSTATE);
				}

			} catch (IOException e) {
				e.printStackTrace();
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
