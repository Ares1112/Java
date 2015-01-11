package main;

import javax.swing.JFrame;

/**
 * Klasa tworz¹ca nowy JFrame, ustawiaj¹ca jego parametry
 * @author Arkadiusz B³asiak
 *
 */
public class Game {

	/**
	 * G³ówna metoda uruchamiaj¹ca program
	 * @param args - argumenty wejœciowe (nie u¿ywane)
	 */
	public static void main(String... args){
		
		JFrame window = new JFrame("Pong");
		window.setContentPane(new GamePanel());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.pack();
		window.setVisible(true);
		
		
	}
	
}
