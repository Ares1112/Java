package gameState;

import graphics.Background;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

/**
 * Klasa reprezentuj�ca menu "do��cz"
 * @author Arkadiusz B�asiak
 *
 */
public class JoinState extends GameState{
	
	/**
	 * T�o
	 */
	private Background bg;
	/**
	 * Ip serwera
	 */
	private String ip = "";
	/**
	 * port serwera
	 */
	private String port = ""; 
	/**
	 * Zmienna pomocnicza, podkre�lnik przy aktualnie wpisywanym
	 */
	private String slicz1 = "_";
	/**
	 * Zmienna pomocnicza, podkre�lnik przy aktualnie wpisywanym
	 */
	private String slicz2 = "";
	/**
	 * Warto�� okre�laj�ca czy komunikat o 
	 * niepoprawnym adresie ma zosta� wy�wietlony
	 */
	public static boolean wrong = false;
	
	/**
	 * Konstruktor, ustawiaj�cy GameStateManager oraz t�o
	 */
	public JoinState(GameStateManager gsm){
		
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
	 * Metoda rysuj�ca na ekran dane 
	 * wprowadzane przez gracza (ip i port serwera)
	 */
	public void draw(Graphics2D g) {
		
		bg.draw(g);
		g.setColor(new Color(0,255,0));
		g.setFont(new Font("Times New Roman",Font.PLAIN,30));
		g.drawString("Do��cz", 330, 70);
		g.drawString("Ip:", 20, 120);
		g.drawString(ip+slicz1, 60, 120);
		g.drawString("Port:", 20, 160);
		g.drawString(port+slicz2, 80, 160);
		if(wrong){
			g.setColor(Color.RED);
			g.drawString("NIE MA TAKIEGO HOSTA", 300, 200);
		}
		
	}

	/**
	 * Metoda pobieraj�ca wci�ni�ty klawisz, 
	 * prze��czaj�ca pomi�dzy wpisywaniem ip-port,
	 * kasuj�ca poprzedni znak,
	 * zmieniaj�ca stan gry na "w grze",
	 * powracaj�ca do menu g��wnego
	 */
	public void keyPressed(int k) {
		
		if(k == KeyEvent.VK_DOWN){
			slicz1 = "";
			slicz2 = "_"; 
		}
		
		if(k == KeyEvent.VK_BACK_SPACE){
			if(slicz1 == "_" && ip.length() > 0){
				ip = ip.substring(0, ip.length()-1);
			} else if(port.length() > 0){
				port = port.substring(0,port.length()-1);
			}
		}
		
		if(k == KeyEvent.VK_UP){
			slicz1 = "_";
			slicz2 = "";
		}
		
		if(k == KeyEvent.VK_ENTER){
			if(ip.length() != 0 && port.length() != 0){
				try{
					Integer.parseInt(port);
					InPlayState.ip = ip;
					InPlayState.port = Integer.parseInt(port);
					gsm.setState(GameStateManager.INPLAYSTATE);
				} catch (NumberFormatException e){
					wrong = true;
				}
			}
		}
		
		if(k == KeyEvent.VK_ESCAPE){
			gsm.setState(GameStateManager.MENUSTATE);
		}
		
	}

	public void keyReleased(int k) {
		
	}
	
	/**
	 * Metoda pobieraj�ca wprowadzane znaki
	 */
	public void keyTyped(char k) {
		if(slicz1 == "_"){
			ip = (ip + k).trim();
		} else {
			port = (port + k).trim();
		}
		
	}
	
}
