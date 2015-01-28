package graphics;

import java.awt.*;
import java.awt.image.*;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Klasa reprezentuj¹ca t³o
 * @author Arkadiusz B³asiak
 *
 */
public class Background {

	/**
	 * Obraz t³a
	 */
	private BufferedImage image;

	/**
	 * pozycja X t³a
	 */
	private double x;
	/**
	 * pozycja Y t³a
	 */
	private double y;

	/**
	 * Konstruktor - odczytuje obraz z pliku
	 * @param s - œcie¿ka do obrazu
	 * @throws IOException 
	 */
	public Background(String s) throws IOException {

		image = ImageIO.read(getClass().getResourceAsStream(s));

	}
	
	/**
	 * Metoda rysuj¹ca t³o
	 * @param g - kontroler graficzny
	 */
	public void draw(Graphics2D g) {
		
		g.drawImage(image, (int) x, (int) y, 1024, 768, null);
		
	}

}
