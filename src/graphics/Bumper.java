package graphics;

import java.awt.Graphics2D;

/**
 * Klasa reprezentuj¹ca odbijak
 * @author Arkadiusz B³asiak
 *
 */
public class Bumper {
	
	/**
	 * wysokoœæ odbijaka
	 */
	public static final int BUMPERHEIGHT = 80;
	/**
	 * szerokoœæ odbijaka
	 */
	public static final int BUMPERWIDTH = 15;

	/**
	 * Metoda rysuj¹ca odbijak
	 * @param g - kontroler graficzny
	 * @param x - pozycja X 
	 * @param y - pozycja Y
	 */
	public void draw(Graphics2D g, int x, int y) {
		
		g.fillRect(x, y, BUMPERWIDTH, BUMPERHEIGHT);
		
	}

}
