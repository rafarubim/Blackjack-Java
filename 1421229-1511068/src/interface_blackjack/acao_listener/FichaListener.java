package interface_blackjack.acao_listener;

import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FichaListener extends MouseAdapter {
    private Rectangle ficha;
    private ApostarListener aL = null;
    
    public FichaListener (ApostarListener aL, int width, int height, int posX, int posY) {
    	super();
    	this.ficha = new Rectangle();
    	this.ficha.setBounds(posX, posY, width, height);
    	this.aL = aL;
    }
	
    @Override
	public void mousePressed(MouseEvent me) {
		int x = me.getX();
		int y = me.getY();
		int valor = 0;

		if (ficha.contains(x, y)) {
			valor = 1;
		}
		
		else if (ficha.contains(x-75, y)) {
			valor = 5;
		}
		
		else if (ficha.contains(x-150, y)) {
			valor = 10;
		}
		
		else if (ficha.contains(x-225, y)) {
			valor = 20;
		}
		
		else if (ficha.contains(x-300, y)) {
			valor = 50;
		}
		
		else if (ficha.contains(x-375, y)) {
			valor = 100;
		}
		if (valor != 0) {
			aL.addValAposta(valor);
		}
	}
}