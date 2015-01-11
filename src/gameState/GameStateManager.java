package gameState;

import java.util.ArrayList;

/**
 * Klasa reprezentuj¹ca zarz¹dzanie stanami gry
 * @author Arkadiusz B³asiak
 *
 */
public class GameStateManager {

	/**
	 * Lista ze stanami gry
	 */
	private ArrayList<GameState> gameStates;
	/**
	 * aktualny stan gry
	 */
	private int currentState;
	
	/**
	 * Stan menu g³ównego
	 */
	public static final int MENUSTATE = 0;
	/**
	 * Stan menu "host"
	 */
	public static final int HOSTSTATE = 1;
	/**
	 * Stan menu "instrukcja"
	 */
	public static final int INSTRUCTIONSTATE = 2;
	/**
	 * Stan menu "w grze" 
	 */
	public static final int INPLAYSTATE = 3;
	/**
	 * Stan menu "do³¹cz"
	 */
	public static final int JOINSTATE = 4;
	/**
	 * Stan menu "wygrana"
	 */
	public static final int WINSTATE = 5;
	
	/**
	 * Konstruktor, dodaj¹cy do listy stanów kolejne stany gry
	 */
	public GameStateManager() {
		
		gameStates = new ArrayList<GameState>();
		
		currentState = MENUSTATE;
		gameStates.add(new MenuState(this));
		gameStates.add(new HostState(this));
		gameStates.add(new InstructionState(this));
		gameStates.add(new InPlayState(this));
		gameStates.add(new JoinState(this));
		gameStates.add(new WinState(this));
		
	}
	
	/**
	 * Metoda ustawiaj¹ca stan gry
	 * @param state - stan gry
	 */
	public void setState(int state) {
		
		currentState = state;
		gameStates.get(currentState).init();
		
	}
	
	/**
	 * Metoda wywo³uj¹ca update() w aktualnym stanie gry
	 */
	public void update() {
		
		gameStates.get(currentState).update();
		
	}
	
	/**
	 * Metoda wywo³uj¹ca draw() w aktualnym stanie gry
	 * @param g - kontroler graficzny
	 */
	public void draw(java.awt.Graphics2D g){
		
		gameStates.get(currentState).draw(g);
	
	}

	/**
	 * Metoda wywo³uj¹ca keyTyped() w aktualnym stanie gry
	 * @param k - znak klawisza
	 */
	public void keyTyped(char k){
		
		gameStates.get(currentState).keyTyped(k);
		
	}
	
	/**
	 * Metoda wywo³uj¹ca keyPressed() w aktualnym stanie gry
	 * @param k - numer klawisza
	 */
	public void keyPressed(int k) {
		
		gameStates.get(currentState).keyPressed(k);
		
	}
	
	/**
	 * Metoda wywo³uj¹ca keyReleased() w aktualnym stanie gry 
	 * @param k - numer klawisza
	 */
	public void keyReleased(int k) {
		
		gameStates.get(currentState).keyReleased(k);
		
	}
	
}
