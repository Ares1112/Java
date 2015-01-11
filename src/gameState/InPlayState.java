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
 * Klasa reprezentuj�ca stan "w grze"
 * @author Arkadiusz B�asiak
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
	 * Pi�eczka
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
	 * pozycja X pi�eczki
	 */
	private double bx;
	/**
	 * pozycja Y pi�eczki
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
	 * po��czenie z serwerem
	 */
	private Socket s;
	/**
	 * Ip serwera, je�li gracz nie jest hostem
	 */
	public static String ip;
	/**
	 * zwyci�zca gry
	 */
	public static String winner;
	/**
	 * Dane wej�ciowe otrzymane z serwera
	 */
	private DataInputStream in = null;
	/**
	 * Dane wyj�ciowe przesy�ane do serwera
	 */
	private DataOutputStream out = null;
	/**
	 * pr�dko�� pi�eczki
	 */
	private double speed;

	/**
	 * Konstruktor ustawiaj�cy GameStateManager, 
	 * tworz�cy paletki 1 i 2 gracza oraz pi�eczk�
	 * @param gsm
	 */
	public InPlayState(GameStateManager gsm) {

		this.gsm = gsm;

		p1 = new Bumper();
		p2 = new Bumper();
		ball = new Ball();

	}
	
	/**
	 * Metoda tworz�ca po��czenie mi�dzy graczem a serwerem
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
	 * Inicjalizacja, tworzy po��czenie i strumie� danych wysy�anych do serwera
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
	 * Metoda aktualizuj�ca parametry pi�eczki, 
	 * punkt�w, odbijak�w - pobiera dane z serwera,
	 * je�li otrzyma tylko jedn� dan� - jest to zwyci�zca
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
	 * Metoda rysuj�ca na ekran pi�eczk�, punkty, odbijaki, warto�� pr�dko�ci
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
	 * Metoda pobieraj�ca wci�ni�te klawisze i przesy�aj�ca je na serwer
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
