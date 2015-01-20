package gameState;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Klasa odczytuj�ca odpowiedzi z serwera i wysy�aj�ca je do InPlayState
 * @author Arkadiusz B�asiak
 *
 */
public class PlayListener implements Runnable {

	/**
	 * Port serwera
	 */
	Socket s;
	
	/**
	 * Dane od serwera
	 */
	DataInputStream in;
	
	/**
	 * Zmienna okre�laj�ca czy w�tek Listenera dzia�a
	 */
	boolean running = false;

	/**
	 * Konstruktor - ustawia socket i startuje w�tek
	 * @param s - socket serwera
	 */
	PlayListener(Socket s) {
		this.s = s;
		running = true;
		new Thread(this).start();
	}

	/**
	 * Metoda aktualizuj�ca parametry pi�eczki, punkt�w, odbijak�w - pobiera
	 * dane z serwera, je�li otrzyma tylko jedn� dan� - jest to zwyci�zca
	 */
	@Override
	public void run() {
		while (running) {
			try {
				in = new DataInputStream(s.getInputStream());
				String spl1;
				if ((spl1 = in.readUTF()) != null) {
					String[] spl = spl1.split(",");
					if (spl.length == 7) {
						InPlayState.p1y = Integer.parseInt(spl[0]);
						InPlayState.p2y = Integer.parseInt(spl[1]);
						InPlayState.bx = Double.parseDouble(spl[2]);
						InPlayState.by = Double.parseDouble(spl[3]);
						InPlayState.speed = Double.parseDouble(spl[4]);
						InPlayState.points1 = Integer.parseInt(spl[5]);
						InPlayState.points2 = Integer.parseInt(spl[6]);
					} else if (spl.length == 1) {
						InPlayState.winner = spl[0];
					}
				}

			} catch (IOException e) {
				 running = false;
			}
		}
	}
}
