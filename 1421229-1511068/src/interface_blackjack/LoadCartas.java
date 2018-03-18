package interface_blackjack;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import blackjack.engine.Baralho;
import blackjack.engine.Carta;

// Esta classe não pode ser instanciada
public class LoadCartas {
	private static LoadCartas loadCartas = new LoadCartas();
	private Map<Carta, Image> imgCartas = new HashMap<Carta, Image>();
	
	private LoadCartas() {
		Baralho deck = new Baralho();
		while (! deck.isEmpty()) {
			loadCarta(deck.comprarCarta());
		}
		try {
			Image i = ImageIO.read(new File("Imagens\\deck2.gif"));
			imgCartas.put(null, i);
		}
		catch (IOException e) {
			throw new RuntimeException("Falha em abertura de imagem");
		}
	}
	
	public static LoadCartas getLoadCartas() {
		return loadCartas;
	}
	
	private void loadCarta(Carta c) {
		try {
			Image i = ImageIO.read(new File("Imagens\\"+c.getValor().getSymbol()+c.getNaipe().getSymbol()+".gif"));
			imgCartas.put(c, i);
		}
		catch (IOException e) {
			throw new RuntimeException("Falha em abertura de imagem");
		}
	}
	
	public Image getImagem(Carta c) {
		return imgCartas.get(c);
	}
}
