package gameState;

import graphics.Background;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

/**
 * Klasa reprezentuj¹ca menu "Instrukcja"
 * 
 * @author Arkadiusz B³asiak
 * 
 */
public class InstructionState extends GameState {

	/**
	 * T³o
	 */
	private Background bg;

	/**
	 * Konstruktor, ustawiaj¹cy GameStateManager oraz t³o
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
	 * Metoda rysuj¹ca na ekran instrukcjê gry
	 */
	public void draw(Graphics2D g) {

		bg.draw(g);
		g.setColor(new Color(0, 255, 0));
		g.setFont(new Font("Times New Roman", Font.PLAIN, 30));
		g.drawString("INSTRUKCJA", 330, 70);
		g.drawString("Gra polega na odbijaniu pi³eczki w stronê przeciwnika.",
				20, 110);
		g.drawString(
				"Jeœli pi³eczka wyjdzie poza obszar po stronie przeciwnika,",
				20, 150);
		g.drawString("zdobywasz punkt. Gra toczy siê do momentu a¿ któryœ z",
				20, 190);
		g.drawString("graczy zdobêdzie 10 punktów.", 20, 230);
		g.drawString("Sterowanie: W - góra, S - dó³", 20, 270);
		g.setColor(new Color(255, 0, 0));
		g.drawString("NACIŒNIJ ESC ABY WYJŒÆ", 220, 400);

	}

	/**
	 * Metoda pobieraj¹ca klawisz, jeœli spacja to wraca do menu g³ównego
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
