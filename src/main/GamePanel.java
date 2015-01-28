package main;

import gameState.GameStateManager;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

/**
 * Klasa reprezentuj�ca interfejs, mechanik� gry
 * @author Arkadiusz B�asiak
 *
 */
@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable, KeyListener{
	
	/**
	 * Szeroko�� interfejsu
	 */
	public static final int WIDTH = 797;
	/**
	 * Wysoko�� interfejsu
	 */
	public static final int HEIGHT = 436;
	/**
	 * Skala interfejsu
	 */
	public static final int SCALE = 2;
	
	/**
	 * W�tek w kt�rym dzia�a� b�dzie GamePanel
	 */
	private Thread thread;
	/**
	 * Warto�� okre�laj�ca czy w�tek pracuje
	 */
	private boolean running;
	/**
	 * liczba FPS'�w
	 */
	private int FPS = 30;
	/**
	 * czas ograniczaj�cy (do liczby FPS)
	 */
	private long targetTime = 1000/FPS;
	
	/**
	 * Obszar na kt�rym b�d� rysowane grafiki
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
	 * Konstruktor klasy, ustawia rozdzielczo��
	 */
	public GamePanel(){
		super();
		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		setFocusable(true);
		requestFocus();
	}
	
	/**
	 * metoda uruchamiaj�ca nas�uchiwanie klawiszy
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
	 * Dzia�anie w�tka ograniczone przez FPS, 
	 * wywo�uje metody update() draw() i drawToScreen()
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
	 * Metoda wywo�uj�ca update() w GameStateManager
	 */
	private void update(){
		
		gsm.update();
		
	}
	
	/**
	 * Metoda wywo�uj�ca draw(Graphics2D g) w GameStateUpdate
	 */
	private void draw(){
		
		gsm.draw(g);
		
	}
	
	/**
	 * Metoda rysuj�ca obszar (menu, gry)
	 */
	private void drawToScreen(){
		
		Graphics g2 = getGraphics();
		g2.drawImage(image, 0, 0, WIDTH*SCALE, HEIGHT*SCALE, null);
		g2.dispose();
		
	}
	
	/**
	 * Metoda pobieraj�ca wprowadzony znak, 
	 * przekazuj�ca go dalej do GameStateManager
	 */
	public void keyTyped(KeyEvent key){
		
		gsm.keyTyped(key.getKeyChar());
		
	}
	
	/**
	 * Metoda pobieraj�ca naci�ni�ty klawisz, 
	 * przekazuj�ca go dalej do GameStateManager
	 */
	public void keyPressed(KeyEvent key){
		
		gsm.keyPressed(key.getKeyCode());
		
	}
	
	/**
	 * Metoda pobieraj�ca puszczony klawisz, 
	 * przekazuj�ca go dalej do GameStateManager
	 */
	public void keyReleased(KeyEvent key){
		
		gsm.keyReleased(key.getKeyCode());
	}
}
