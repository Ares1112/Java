package main;

import gameState.GameStateManager;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

/**
 * Klasa reprezentuj¹ca interfejs, mechanikê gry
 * @author Arkadiusz B³asiak
 *
 */
@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable, KeyListener{
	
	/**
	 * Szerokoœæ interfejsu
	 */
	public static final int WIDTH = 797;
	/**
	 * Wysokoœæ interfejsu
	 */
	public static final int HEIGHT = 436;
	/**
	 * Skala interfejsu
	 */
	public static final int SCALE = 2;
	
	/**
	 * W¹tek w którym dzia³a³ bêdzie GamePanel
	 */
	private Thread thread;
	/**
	 * Wartoœæ okreœlaj¹ca czy w¹tek pracuje
	 */
	private boolean running;
	/**
	 * liczba FPS'ów
	 */
	private int FPS = 30;
	/**
	 * czas ograniczaj¹cy (do liczby FPS)
	 */
	private long targetTime = 1000/FPS;
	
	/**
	 * Obszar na którym bêd¹ rysowane grafiki
	 */
	private BufferedImage image;
	/**
	 * Kontroler grafiki
	 */
	private Graphics2D g;
	
	/**
	 * GameStateManager
	 */
	private GameStateManager gsm;
	
	/**
	 * Konstruktor klasy, ustawia rozdzielczoœæ
	 */
	public GamePanel(){
		super();
		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		setFocusable(true);
		requestFocus();
	}
	
	/**
	 * metoda uruchamiaj¹ca nas³uchiwanie klawiszy
	 */
	public void addNotify(){
		super.addNotify();
		if(thread == null){
			thread = new Thread(this);
			addKeyListener(this);
			thread.start();
		}
	}
	
	/**
	 * Inicjalizacja kontrolera graficznego, stworzenie GameStateManager
	 */
	private void init(){
		
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.getGraphics();
		running = true;
		gsm = new GameStateManager();
	
	}
	
	/**
	 * Dzia³anie w¹tka ograniczone przez FPS, 
	 * wywo³uje metody update() draw() i drawToScreen()
	 */
	public void run(){
	
		init();
		
		long start;
		long elapsed;
		long wait;
		
		while(running){
			start = System.nanoTime();
			
			update();
			draw();
			drawToScreen();
			
			elapsed = System.nanoTime() - start;
			
			wait = targetTime - elapsed / 1000000;
			
			if(wait<0) wait=1;
			
			try{
				Thread.sleep(wait);
			} catch (InterruptedException e){
				
			}
		}
		
	}
	
	/**
	 * Metoda wywo³uj¹ca update() w GameStateManager
	 */
	private void update(){
		
		gsm.update();
		
	}
	
	/**
	 * Metoda wywo³uj¹ca draw(Graphics2D g) w GameStateUpdate
	 */
	private void draw(){
		
		gsm.draw(g);
		
	}
	
	/**
	 * Metoda rysuj¹ca obszar (menu, gry)
	 */
	private void drawToScreen(){
		
		Graphics g2 = getGraphics();
		g2.drawImage(image, 0, 0, WIDTH*SCALE, HEIGHT*SCALE, null);
		g2.dispose();
		
	}
	
	/**
	 * Metoda pobieraj¹ca wprowadzony znak, 
	 * przekazuj¹ca go dalej do GameStateManager
	 */
	public void keyTyped(KeyEvent key){
		
		gsm.keyTyped(key.getKeyChar());
		
	}
	
	/**
	 * Metoda pobieraj¹ca naciœniêty klawisz, 
	 * przekazuj¹ca go dalej do GameStateManager
	 */
	public void keyPressed(KeyEvent key){
		
		gsm.keyPressed(key.getKeyCode());
		
	}
	
	/**
	 * Metoda pobieraj¹ca puszczony klawisz, 
	 * przekazuj¹ca go dalej do GameStateManager
	 */
	public void keyReleased(KeyEvent key){
		
		gsm.keyReleased(key.getKeyCode());
	}
}
