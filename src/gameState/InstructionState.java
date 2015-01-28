package gameState;

import graphics.Background;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

/**
 * Klasa reprezentuj�ca menu "Instrukcja"
 * 
 * @author Arkadiusz B�asiak
 * 
 */
public class InstructionState extends GameState {

	/**
	 * T�o
	 */
	private Background bg;

	/**
	 * Konstruktor, ustawiaj�cy GameStateManager oraz t�o
	 */
	public InstructionState(GameStateManager gsm) {
		this.gsm = gsm;

		try {
			bg = new Background("/bg.jpg");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void init() {
	}

	public void update() {
	}

	/**
	 * Metoda rysuj�ca na ekran instrukcj� gry
	 */
	public void draw(Graphics2D g) {

		bg.draw(g);
		g.setColor(new Color(0, 255, 0));
		g.setFont(new Font("Times New Roman", Font.PLAIN, 30));
		g.drawString("INSTRUKCJA", 330, 70);
		g.drawString("Gra polega na odbijaniu pi�eczki w stron� przeciwnika.",
				20, 110);
		g.drawString(
				"Je�li pi�eczka wyjdzie poza obszar po stronie przeciwnika,",
				20, 150);
		g.drawString("zdobywasz punkt. Gra toczy si� do momentu a� kt�ry� z",
				20, 190);
		g.drawString("graczy zdob�dzie 10 punkt�w.", 20, 230);
		g.drawString("Sterowanie: W - g�ra, S - d�", 20, 270);
		g.setColor(new Color(255, 0, 0));
		g.drawString("NACI�NIJ ESC ABY WYJ��", 220, 400);

	}

	/**
	 * Metoda pobieraj�ca klawisz, je�li spacja to wraca do menu g��wnego
	 */
	public void keyPressed(int k) {

		if (k == KeyEvent.VK_ESCAPE) {
			gsm.setState(GameStateManager.MENUSTATE);
		}

	}

	public void keyReleased(int k) {
	}

	public void keyTyped(char k) {
	}

}
