package graphics;

import java.awt.*;
import java.awt.geom.Ellipse2D;

/**
 * Klasa reprezentuj�ca pi�eczk�
 * @author Arkadiusz B�asiak
 *
 */
public class Ball {

	/**
	 * szeroko�� pi�eczki
	 */
	public static final int BALLWIDTH = 20;
	/**
	 * Wysoko�� pi�eczki
	 */
	public static final int BALLHEIGHT = 20;

	/**
	 * Metoda rysuj�ca pi�eczk�
	 * @param g - kontroler graficzny
	 * @param x - pozycja x
	 * @param y - pozycja y
	 */
	public void draw(Graphics2D g, double x, double y) {
		
		g.fill(new Ellipse2D.Double(x, y, BALLWIDTH, BALLHEIGHT));
		
	}

}
