package gameState;

import graphics.Background;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import server.ServerInstance;

/**
 * Klasa reprezentuj�ca menu "Host"
 * @author Arkadiusz B�asiak 
 * 
 */
public class HostState extends GameState {
	
	/**
	 * T�o menu "Host"
	 */
	private Background bg;
	/**
	 * Port na kt�rym serwer b�dzie hostowany
	 */
	public static String port = "";
	/**
	 * Zmienna u�ywana do sprawdzania czy gracz jest hostem
	 */
	public static boolean host;
	/**
	 * Zmienna u�ywana przy sprawdzaniu czy wpisany port jest prawid�owy
	 */
	private boolean wrong = false;

	/**
	 * Konstruktor, ustawia t�o i GameStateManager
	 * @param gsm - GameStateManager
	 */
	public HostState(GameStateManager gsm) {

		this.gsm = gsm;

		try {
			bg = new Background("/bg.jpg");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Metoda tworz�ca serwer
	 * @param p - port na kt�rym serwer ma zosta� uruchomiony
	 * @throws NumberFormatException - rzucony gdy port nie jest integerem
	 */
	public void hostGame(String p) throws NumberFormatException {
		new ServerInstance(Integer.parseInt(p));
	}

	public void init() {
	}

	public void update() {
	}

	/**
	 * Metoda rysuj�ca na ekran dane wprowadzone przez gracza
	 */
	public void draw(Graphics2D g) {

		bg.draw(g);
		g.setColor(new Color(0, 255, 0));
		g.setFont(new Font("Times New Roman", Font.PLAIN, 30));
		g.drawString("Nowy serwer", 330, 70);
		g.drawString("Port:", 20, 160);
		g.drawString(port + "_", 80, 160);
		if (wrong) {
			g.setColor(Color.RED);
			g.drawString("Z�Y NUMER PORTU", 300, 200);
		}

	}

	/**
	 * Metoda pobieraj�ca wci�ni�ty klawisz,
	 * w zale�no�ci od niego: usuwa znak, wychodzi z menu,
	 * tworzy serwer i uruchamia gr�
	 */
	public void keyPressed(int k) {

		if (k == KeyEvent.VK_BACK_SPACE) {
			port = port.substring(0, port.length() - 1);
		}

		if (k == KeyEvent.VK_ENTER) {
			if (port.length() != 0) {
				try {
					Integer.parseInt(port);
					hostGame(port);
					host = true;
					InPlayState.port = Integer.parseInt(port);
					wrong = false;
					gsm.setState(GameStateManager.INPLAYSTATE);
				} catch (IllegalArgumentException e1) {
					wrong = true;
				}
			}
		}
		if (k == KeyEvent.VK_ESCAPE) {
			gsm.setState(GameStateManager.MENUSTATE);
		}
	}

	public void keyReleased(int k) {

	}
	
	/**
	 * Metoda pobieraj�ca wprowadzone znaki
	 */
	public void keyTyped(char k) {
		port = (port + k).trim();
	}

}
