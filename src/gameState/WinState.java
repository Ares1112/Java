package gameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import main.GamePanel;

/**
 * Klasa reprezentuj�ca menu "wygranej"
 * @author Arkadiusz
 *
 */
public class WinState extends GameState{
	
	/**
	 * Konstruktor klasy, ustawiaj�cy GameStateManager
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
	 * Metoda rysuj�ca komunikat o zwyci�zcy
	 */
	public void draw(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		g.setColor(Color.GREEN);
		g.setFont(new Font("Times New Roman",Font.PLAIN,30));
		g.drawString(InPlayState.winner+" wygra�!", 300, 150);
		g.drawString("Naci�nij ESC aby wyj��", 200, 250);
		
	}
	
	/**
	 * metoda pobieraj�ca klawisz, 
	 * je�li spacja to powraca do menu g�ownego
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
