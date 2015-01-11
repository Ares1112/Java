package graphics;

import java.awt.Graphics2D;

/**
 * Klasa reprezentuj�ca odbijak
 * @author Arkadiusz B�asiak
 *
 */
public class Bumper {
	
	/**
	 * wysoko�� odbijaka
	 */
	public static final int BUMPERHEIGHT = 80;
	/**
	 * szeroko�� odbijaka
	 */
	public static final int BUMPERWIDTH = 15;

	/**
	 * Metoda rysuj�ca odbijak
	 * @param g - kontroler graficzny
	 * @param x - pozycja X 
	 * @param y - pozycja Y
	 */
	public void draw(Graphics2D g, int x, int y) {
		
		g.fillRect(x, y, BUMPERWIDTH, BUMPERHEIGHT);
		
	}

}
