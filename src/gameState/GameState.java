package gameState;

/**
 * Klasa abstrakcyjna reprezentuj�ca poziom menu
 */
public abstract class GameState {

	/**
	 * GameStateManager
	 */
	protected GameStateManager gsm;
	
	/**
	 * Inicjalizacja, wywo�ywana po konstruktorze
	 */
	public abstract void init();
	/**
	 * Aktualizacja danych w klasie
	 */
	public abstract void update();
	/**
	 * Rysowanie na ekran
	 */
	public abstract void draw(java.awt.Graphics2D g);
	/**
	 * Metoda pobieraj�ca wci�ni�ty klawisz
	 */
	public abstract void keyPressed(int k);
	/**
	 * Metoda pobieraj�ca puszczony klawisz
	 */
	public abstract void keyReleased(int k);
	/**
	 * Metoda pobieraj�ca wprowadzony znak
	 */
	public abstract void keyTyped(char k);
	
}
