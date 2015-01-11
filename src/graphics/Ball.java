package graphics;

import java.awt.*;
import java.awt.geom.Ellipse2D;

/**
 * Klasa reprezentuj¹ca pi³eczkê
 * @author Arkadiusz B³asiak
 *
 */
public class Ball {

	/**
	 * szerokoœæ pi³eczki
	 */
	public static final int BALLWIDTH = 20;
	/**
	 * Wysokoœæ pi³eczki
	 */
	public static final int BALLHEIGHT = 20;

	/**
	 * Metoda rysuj¹ca pi³eczkê
	 * @param g - kontroler graficzny
	 * @param x - pozycja x
	 * @param y - pozycja y
	 */
	public void draw(Graphics2D g, double x, double y) {
		
		g.fill(new Ellipse2D.Double(x, y, BALLWIDTH, BALLHEIGHT));
		
	}

}
