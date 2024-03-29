package graphics;

import java.awt.Graphics2D;

/**
 * Klasa reprezentująca odbijak
 * @author Arkadiusz Błasiak
 *
 */
public class Bumper {
	
	/**
	 * wysokość odbijaka
	 */
	public static final int BUMPERHEIGHT = 80;
	/**
	 * szerokość odbijaka
	 */
	public static final int BUMPERWIDTH = 15;

	/**
	 * Metoda rysująca odbijak
	 * @param g - kontroler graficzny
	 * @param x - pozycja X 
	 * @param y - pozycja Y
	 */
	public void draw(Graphics2D g, int x, int y) {
		
		g.fillRect(x, y, BUMPERWIDTH, BUMPERHEIGHT);
		
	}

}
