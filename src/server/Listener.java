package server;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Klasa reprezentuj¹ca nas³uchiwanie klawiszy dla serwera
 * @author Arkadiusz B³asiak
 *
 */
public class Listener implements Runnable {

	/**
	 * w¹tek w którym nas³uchiwanie bêdzie dzia³a³o
	 */
	private Thread thread;
	/**
	 * ods³uchane dane
	 */
	private DataInputStream in;
	/**
	 * wartoœæ okreœlaj¹ca czy w¹tek dzia³a
	 */
	private boolean running;
	/**
	 * po³¹czenie z graczem
	 */
	private Socket s;
	/**
	 * czy UP zosta³o wciœniête
	 */
	private boolean up;
	/**
	 * czy DOWN zosta³o wciœniête
	 */
	private boolean dw;
	/**
	 * czy ESC zosta³o wciœniête
	 */
	private boolean esc;
	/**
	 * czy SPACE zosta³o wciœniête
	 */
	private boolean space;

	/**
	 * Konstruktor, ustawiaj¹cy po³¹czenie i tworz¹cy w¹tek
	 * @param s
	 */
	public Listener(Socket s) {

		this.s = s;
		thread = new Thread(this);
		thread.start();
	}
	
	/**
	 * Metoda zwracaj¹ca stan UP
	 * @return true, jeœli UP by³o wciœniête, inaczej false
	 */
	public boolean getUp() {
		boolean temp = up;
		up = false;
		return temp;
	}

	/**
	 * Metoda zwracaj¹ca stan DOWN
	 * @return true, jeœli DOWN by³o wciœniête, inaczej false
	 */
	public boolean getDw() {
		boolean temp = dw;
		dw = false;
		return temp;
	}

	/**
	 * Metoda zwracaj¹ca stan ESC
	 * @return true, jeœli ESC by³o wciœniête, inaczej false
	 */
	public boolean getEsc() {
		boolean temp = esc;
		esc = false;
		return temp;
	}

	/**
	 * Metoda zwracaj¹ca stan SPACE
	 * @return true, jeœli SPACE by³o wciœniête, inaczej false
	 */
	public boolean getSpace() {
		boolean temp = space;
		space = false;
		return temp;
	}

	/**
	 * Metoda zatzymuj¹ca nas³uchiwanie, koñcz¹ca w¹tek
	 */
	public void stop() {
		running = false;
	}
	
	/**
	 * Dzia³anie w¹tka, nas³uchiwanie klawiszy
	 */
	public void run() {

		running = true;

		while (running) {
			try {
				in = new DataInputStream(s.getInputStream());
			} catch (IOException e1) {
				running = false;
			}
			String a = null;
			try {
				if ((a = in.readUTF()) != null) {
					if (a.equals("UP")) {
						up = true;
					}
					if (a.equals("DW")) {
						dw = true;
					}
					if (a.equals("ESC")) {
						esc = true;
					}
					if (a.equals("SPACE")) {
						space = true;
					}
				}

			} catch (IOException e) {
				running = false;
			}
		}
	}
}
