package gameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import main.GamePanel;

/**
 * Klasa reprezentuj¹ca menu "wygranej"
 * @author Arkadiusz
 *
 */
public class WinState extends GameState{
	
	/**
	 * Konstruktor klasy, ustawiaj¹cy GameStateManager
	 * @param gsm
	 */
	public WinState(GameStateManager gsm){
		this.gsm = gsm;
	}
	
	public void init() {
		
	}

	public void update() {
		
	}

	/**
	 * Metoda rysuj¹ca komunikat o zwyciêzcy
	 */
	public void draw(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		g.setColor(Color.GREEN);
		g.setFont(new Font("Times New Roman",Font.PLAIN,30));
		g.drawString(InPlayState.winner+" wygra³!", 300, 150);
		g.drawString("Naciœnij ESC aby wyjœæ", 200, 250);
		
	}
	
	/**
	 * metoda pobieraj¹ca klawisz, 
	 * jeœli spacja to powraca do menu g³ownego
	 */
	public void keyPressed(int k) {
		if(k == KeyEvent.VK_ESCAPE) {
			gsm.setState(GameStateManager.MENUSTATE);
		}
	}

	public void keyReleased(int k) {
		
	}

	public void keyTyped(char k) {
		
	}

}
