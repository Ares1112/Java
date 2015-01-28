package graphics;

import java.awt.*;
import java.awt.image.*;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Klasa reprezentuj�ca t�o
 * @author Arkadiusz B�asiak
 *
 */
public class Background {

	/**
	 * Obraz t�a
	 */
	private BufferedImage image;

	/**
	 * pozycja X t�a
	 */
	private double x;
	/**
	 * pozycja Y t�a
	 */
	private double y;

	/**
	 * Konstruktor - odczytuje obraz z pliku
	 * @param s - �cie�ka do obrazu
	 * @throws IOException 
	 */
	public Background(String s) throws IOException {

		image = ImageIO.read(getClass().getResourceAsStream(s));

	}
	
	/**
	 * Metoda rysuj�ca t�o
	 * @param g - kontroler graficzny
	 */
	public void draw(Graphics2D g) {
		
		g.drawImage(image, (int) x, (int) y, 1024, 768, null);
		
	}

}
