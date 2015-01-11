package server;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Klasa reprezentuj�ca nas�uchiwanie klawiszy dla serwera
 * @author Arkadiusz B�asiak
 *
 */
public class Listener implements Runnable {

	/**
	 * w�tek w kt�rym nas�uchiwanie b�dzie dzia�a�o
	 */
	private Thread thread;
	/**
	 * ods�uchane dane
	 */
	private DataInputStream in;
	/**
	 * warto�� okre�laj�ca czy w�tek dzia�a
	 */
	private boolean running;
	/**
	 * po��czenie z graczem
	 */
	private Socket s;
	/**
	 * czy UP zosta�o wci�ni�te
	 */
	private boolean up;
	/**
	 * czy DOWN zosta�o wci�ni�te
	 */
	private boolean dw;
	/**
	 * czy ESC zosta�o wci�ni�te
	 */
	private boolean esc;
	/**
	 * czy SPACE zosta�o wci�ni�te
	 */
	private boolean space;

	/**
	 * Konstruktor, ustawiaj�cy po��czenie i tworz�cy w�tek
	 * @param s
	 */
	public Listener(Socket s) {

		this.s = s;
		thread = new Thread(this);
		thread.start();
	}
	
	/**
	 * Metoda zwracaj�ca stan UP
	 * @return true, je�li UP by�o wci�ni�te, inaczej false
	 */
	public boolean getUp() {
		boolean temp = up;
		up = false;
		return temp;
	}

	/**
	 * Metoda zwracaj�ca stan DOWN
	 * @return true, je�li DOWN by�o wci�ni�te, inaczej false
	 */
	public boolean getDw() {
		boolean temp = dw;
		dw = false;
		return temp;
	}

	/**
	 * Metoda zwracaj�ca stan ESC
	 * @return true, je�li ESC by�o wci�ni�te, inaczej false
	 */
	public boolean getEsc() {
		boolean temp = esc;
		esc = false;
		return temp;
	}

	/**
	 * Metoda zwracaj�ca stan SPACE
	 * @return true, je�li SPACE by�o wci�ni�te, inaczej false
	 */
	public boolean getSpace() {
		boolean temp = space;
		space = false;
		return temp;
	}

	/**
	 * Metoda zatzymuj�ca nas�uchiwanie, ko�cz�ca w�tek
	 */
	public void stop() {
		running = false;
	}
	
	/**
	 * Dzia�anie w�tka, nas�uchiwanie klawiszy
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
