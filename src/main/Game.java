package main;

import javax.swing.JFrame;

/**
 * Klasa tworz�ca nowy JFrame, ustawiaj�ca jego parametry
 * @author Arkadiusz B�asiak
 *
 */
public class Game {

	/**
	 * G��wna metoda uruchamiaj�ca program
	 * @param args - argumenty wej�ciowe (nie u�ywane)
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
