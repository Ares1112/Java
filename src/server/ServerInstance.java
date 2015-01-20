package server;

import graphics.Ball;
import graphics.Bumper;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import main.GamePanel;

/**
 * Klasa reprezentuj¹ca serwer gry
 * @author Arkadiusz B³asiak
 *
 */

public class ServerInstance implements Runnable {
	
	/**
	 * Socket na którym bêdzie dzia³a³ serwer
	 */
	private ServerSocket sv;
	/**
	 * Zmienna okreœlaj¹ca czy w¹tek dzia³a
	 */
	private boolean running;
	/**
	 * pozycja Y odbijaka gracza 1
	 */
	public static int p1y = 50;
	/**
	 * pozycja Y odbijaka gracza 2
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
	 * Wektor wzd³ó¿ którego ma siê 
	 * poruszaæ pi³eczka w pozycji X
	 */
	private double dbx = 1;
	/**
	 * Wektor wzd³ó¿ którego ma siê 
	 * poruszaæ pi³eczka w pozycji Y
	 */
	private double dby = -1;
	/**
	 * prêdkoœæ pi³eczki
	 */
	private double speed = 3;
	/**
	 * punkty gracza 1
	 */
	private int p1 = 0;
	/**
	 * punkty gracza 2
	 */
	private int p2 = 0;
	/**
	 * liczba FPS'ów
	 */
	private int FPS = 30;
	/**
	 * czas ograniczaj¹cy (do liczby FPS)
	 */
	private long targetTime = 1000 / FPS;
	/**
	 * tablica po³¹czeñ z graczami
	 */
	private Socket[] sock = new Socket[2];
	/**
	 * Dane wysy³ane do graczy
	 */
	private DataOutputStream out = null;
	/**
	 * Nas³uchiwanie klawiszy gracza 1
	 */
	private Listener lst1;
	/**
	 * Nas³uchiwanie klawiszy gracza 2
	 */
	private Listener lst2;

	/**
	 * Konstruktor, tworz¹cy serwer, uruchamiaj¹cy w¹tek
	 * @param port - port serwera
	 */
	public ServerInstance(int port) {
		try {
			sv = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
		bx = 390;
		by = 210;
		p1=0;
		p2=0;
		speed = 3;
		Thread t = new Thread(this);
		t.start();

	}

	/**
	 * Metoda wysy³aj¹ca dane do graczy
	 */
	private void send() {
		try {
			for (Socket ss : sock) {
				out = new DataOutputStream(ss.getOutputStream());
				out.writeUTF(p1y + "," + p2y + "," + bx + "," + by + ","
						+ speed + "," + p1 + "," + p2);
				out.flush();
			}
		} catch (SocketException e) {
			p1=10;
			reset(lst1);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Metoda resetuj¹ca grê po zaliczeniu gola, oczekiwanie na naciœniêcie SPACE,
	 * jeœli wygrana wysy³a komunikat do graczy ze zwyciêzcc¹
	 * @param lst - Listener gracza który dosta³ gola
	 */
	private void reset(Listener lst) {
		if (p1 == 10 || p2 == 10) {
			lst1.stop();
			lst2.stop();
			for (Socket ss : sock) {
				try {
					out = new DataOutputStream(ss.getOutputStream());
					out.writeUTF(p1 == 10 ? "p1" : "p2");
					out.flush();
					ss.close();
				} catch (SocketException e) {
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			running = false;
		} else {
			bx = 390;
			by = 210;
			dbx *= -1;
			speed = 3;
			send();
			while (!lst.getSpace()) {
				try {
					if(sock[0] == null || sock[1] == null) break;
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * Metoda aktualizuj¹ca ruch paletek, pi³eczki, 
	 * sprawdzaj¹ca czy zosta³ strzelony gol, 
	 * obs³uguj¹ca odbijanie pi³eczki od œcian i paletek
	 */
	private void process() {

		if (lst1.getUp()) {
			if (p1y - 5 <= 0) {
				p1y = 0;
			} else {
				p1y -= 5;
			}
		} else if (lst1.getDw()) {
			if (p1y + 5 >= GamePanel.HEIGHT - Bumper.BUMPERHEIGHT) {
				p1y = GamePanel.HEIGHT - Bumper.BUMPERHEIGHT;
			} else {
				p1y += 5;
			}
		} else if (lst1.getEsc()) {
			p2 = 10;
			reset(lst1);
		}

		if (lst2.getUp()) {
			if (p2y - 5 <= 0) {
				p2y = 0;
			} else {
				p2y -= 5;
			}
		} else if (lst2.getDw()) {
			if (p2y + 5 >= GamePanel.HEIGHT - Bumper.BUMPERHEIGHT) {
				p2y = GamePanel.HEIGHT - Bumper.BUMPERHEIGHT;
			} else {
				p2y += 5;
			}
		} else if (lst2.getEsc()) {
			p1 = 10;
			reset(lst2);
		}

		lst1.getSpace();
		lst2.getSpace();

		bx = bx + dbx * speed;
		by = by + dby * speed;

		if (by < 0 || by > GamePanel.HEIGHT - Ball.BALLHEIGHT) {
			dby = dby *= -1;
		}
		if (bx <= 17 + Bumper.BUMPERWIDTH && bx >= 17
				&& by <= p1y + Bumper.BUMPERHEIGHT && by >= p1y) {
			dbx = p1y/by+0.6;
			if(dbx>1) dbx = 1;
			else if (dbx<0.3) dbx = 0.3;
			speed += 0.1;
		} else if (bx <= 737 + Bumper.BUMPERWIDTH && bx >= 737
				&& by <= p2y + Bumper.BUMPERHEIGHT && by >= p2y) {
			dbx = -(p2y/by);
			if(dbx<-1) dbx = -1;
			else if (dbx>-0.3) dbx = -0.3;
			speed += 0.1;
		}
		if (bx >= GamePanel.WIDTH + 10) {
			p1++;
			reset(lst2);
		} else if (bx <= -10) {
			p2++;
			reset(lst1);
		}

	}
	
	/**
	 * Dzia³anie w¹tka, czekanie na po³¹czenie graczy,
	 * wywo³ywanie metod send() i process() - ograniczone FPS'ami
	 */
	public void run() {
		int count = 0;
		while (count<2) {
			try {
				count++;
				sock[count - 1] = sv.accept();
			} catch (IOException e) {
				e.printStackTrace();

			}

		}

		long start;
		long elapsed;
		long wait;
		running = true;
		
		lst1 = new Listener(sock[0]);
		lst2 = new Listener(sock[1]);
		
		lst1.getDw();
		lst1.getEsc();
		lst1.getUp();
		
		while (running) {

			start = System.nanoTime();

			send();
			process();
			
			if(sock[0] == null || sock[1] == null){
				p1=10;
				reset(lst1);
			}
			
			elapsed = System.nanoTime() - start;

			wait = targetTime - elapsed / 1000000;

			if (wait < 0)
				wait = 1;

			try {
				Thread.sleep(wait);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			sv.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}