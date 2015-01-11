package gameState;

import graphics.Background;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

/**
 * Klasa reprezentuj¹ca menu "menu g³ówne"
 * @author Arkadiusz B³asiak
 *
 */
public class MenuState extends GameState{
	
	/**
	 * T³o menu
	 */
	private Background bg;
	/**
	 * Obecny wybór w menu, który zostanie podœwietlony
	 */
	private int currentChoice = 0;
	/**
	 * Opcje menu
	 */
	private String[] options = {
			"Do³¹cz",
			"Host",
			"Instrukcja",
			"WyjdŸ"
	};
	
	/**
	 * Konstruktor, ustawiaj¹cy GameStateManager oraz t³o
	 */
	public MenuState(GameStateManager gsm) {
		
		this.gsm = gsm;
		
		try{
			bg = new Background("/bg.jpg");
		} catch(Exception e){
			e.printStackTrace();
		}
		
	}

	public void init() {
		
	}

	public void update() {
		
	}
	
	/**
	 * Metoda rysuj¹ca na ekran kolejne pozycje menu,
	 * podœwietlaj¹ca aktualnie wybran¹
	 */
	public void draw(Graphics2D g) {
		
		bg.draw(g);
		g.setColor(new Color(255,0,0));
		g.setFont(new Font("Times New Roman",Font.PLAIN,60));
		g.drawString("PONG", 330, 70);
		g.setFont(new Font("Arial",Font.PLAIN,20));
		for(int i = 0; i < options.length;i++){
			if(i == currentChoice){
				g.setColor(new Color(0,0,255));
				
			}
			else {
				g.setColor(new Color(0,255,0));
			}
			g.drawString(options[i], 145, 140+i*30);
		}
		
	}

	/**
	 * Metoda zmieniaj¹ca stan gry, 
	 * wywo³ana przez naciœniêcie ENTER
	 */
	private void select() {
		if(currentChoice == 0){
			gsm.setState(GameStateManager.JOINSTATE);
		} else if(currentChoice == 1){
			gsm.setState(GameStateManager.HOSTSTATE);
		} else if(currentChoice == 2){
			gsm.setState(GameStateManager.INSTRUCTIONSTATE);
		} else if(currentChoice == 3){
			System.exit(0);
		}
	}
	
	/**
	 * Metoda pobieraj¹ca wciœniêty klawisz, zmiana opcji, wybór
	 */
	public void keyPressed(int k) {
		
		if(k == KeyEvent.VK_ENTER){
			select();
		}else if(k == KeyEvent.VK_UP){
			currentChoice--;
			if(currentChoice == -1){
				currentChoice = options.length - 1;
			}
		}else if(k == KeyEvent.VK_DOWN){
			currentChoice++;
			if(currentChoice == options.length){
				currentChoice = 0;
			}
		}
		
	}

	public void keyReleased(int k) {
		
	}

	public void keyTyped(char k) {}

}
