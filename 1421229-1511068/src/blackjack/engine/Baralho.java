package blackjack.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Baralho {
	private List<Carta> lstCartas = new ArrayList<Carta>();
		/* A primeira carta da lista é a primeira com face para cima (fundo do baralho).
		 * a última carta da lista é a primeira com a face para baixo (topo do baralho).
		 * Ao comprar uma carta, se compra a primeira com face para baixo (do topo). */
	
	private List<Carta> cartasCompradas = new ArrayList<Carta>();
	
	public Baralho() {
		for (Carta.Naipe naipe: Carta.Naipe.values()) {
			for (Carta.Valor valor: Carta.Valor.values()) {
				Carta c;
				c = new Carta(valor, naipe);
				lstCartas.add(c);
			}
		}
	}
	
	// Construtor private, para usar em valueOfFirstSymbol
	private Baralho(List<Carta> lstCartas, List<Carta> cartasCompradas) {
		this.lstCartas = lstCartas;
		this.cartasCompradas = cartasCompradas;
	}
	
	public void embaralhar() {
		Random gerador = new Random();
		List<Carta> aux = new ArrayList<Carta>();
		while(! lstCartas.isEmpty()) {
			int inx = gerador.nextInt(lstCartas.size());
			aux.add(lstCartas.remove(inx));
		}
		lstCartas = aux;
	}
	
	public Carta comprarCarta() {
		if (lstCartas.isEmpty()) {
			throw new RuntimeException("O baralho está vazio");
		}
		Carta c = lstCartas.remove(lstCartas.size() - 1);
		cartasCompradas.add(c);
		return c;
	}
	
	public void reembaralharCartas() {
		while(! cartasCompradas.isEmpty()) {
			lstCartas.add(cartasCompradas.remove(0));
		}
		embaralhar();
	}
	
	public boolean isEmpty() {
		return lstCartas.isEmpty();
	}
	
	// Obtém string-símbolo que representa um baralho
	public String getSymbol() {
		// lstCartas
		String strLstCartas = String.valueOf(lstCartas.size());
		for (Carta c: lstCartas) {
			strLstCartas += " " + c.getSymbol();
		}
		// cartasCompradas
		String strCartasCompradas = String.valueOf(cartasCompradas.size());
		for (Carta c: cartasCompradas) {
			strCartasCompradas += " " + c.getSymbol();
		}
		return strLstCartas + "\n" + strCartasCompradas;
		
	}
	
	/* 
	 * Recebe uma string no primeiro parâmetro e retornará, através do segundo parâmetro (que pode ser
	 *  null caso não se deseje obter o retorno) a substring prefixo da string original que representa
	 *  a string-símbolo de uma carta. Essa substring também será retirada da string original.
	 *  O retorno normal desse método é a carta correspondente a essa string-símbolo.
	 */
	public static Baralho valueOfFirstSymbol(StringBuilder string, StringBuilder firstSymbol) {		
		List<Carta> lstCartas = new ArrayList<Carta>();
		List<Carta> cartasCompradas = new ArrayList<Carta>();
		StringBuilder strRestante;
		StringBuilder strPrefixo = new StringBuilder("");
		
		String str = new String(string);
		
		// reconstruir lstCartas
		String[] tokenSizeLstCartas = str.split("[ \n]", 2);
		if (tokenSizeLstCartas.length != 2) {
			throw new RuntimeException("Baralho desconhecido");
		}
		strRestante = new StringBuilder(tokenSizeLstCartas[1]);
		int sizeLstCartas = 0;
		try {
			sizeLstCartas = Integer.valueOf(tokenSizeLstCartas[0]);
		}
		catch(NumberFormatException e) {
			e.printStackTrace();
			System.exit(1);
		}
		strPrefixo.append(tokenSizeLstCartas[0]);
		// Botar de volta o espaço, se foi retirado pelo split
		if (sizeLstCartas != 0) {
			strRestante.insert(0, " ");
		}
		for (int i = 0; i < sizeLstCartas; i++) {
			// tirar o espaço antes de cada carta
			strPrefixo.append(strRestante.substring(0, 1));
			strRestante.replace(0, 1, "");
			
			StringBuilder strCarta = new StringBuilder();
			Carta c = Carta.valueOfFirstSymbol(strRestante, strCarta);
			lstCartas.add(c);
			strPrefixo.append(strCarta);
		}
		
		// Tirar o \n, se ainda não foi retirado pelo split
		if (strRestante.charAt(0) == '\n') {
			strRestante.replace(0, 1, "");
		}
		strPrefixo.append("\n");
		
		String restante = new String(strRestante);
		
		// reconstruir cartasCompradas
		String[] tokenSizeCartasCompradas = restante.split("[ \n]", 2);
		if (tokenSizeCartasCompradas.length != 1 && tokenSizeCartasCompradas.length != 2) {
			throw new RuntimeException("Baralho desconhecido");
		}
		if (tokenSizeCartasCompradas.length > 1) {
			strRestante = new StringBuilder(tokenSizeCartasCompradas[1]);
		}
		int sizeCartasCompradas = 0;
		try {
			sizeCartasCompradas = Integer.valueOf(tokenSizeCartasCompradas[0]);
		}
		catch(NumberFormatException e) {
			e.printStackTrace();
			System.exit(1);
		}
		strPrefixo.append(tokenSizeCartasCompradas[0]);
		// Botar de volta o espaço, se foi retirado pelo split
		if (sizeCartasCompradas != 0) {
			strRestante.insert(0, " ");
		}
		for (int i = 0; i < sizeCartasCompradas; i++) {
			// tirar o espaço antes de cada carta
			strPrefixo.append(strRestante.substring(0, 1));
			strRestante.replace(0, 1, "");
			
			StringBuilder strCarta = new StringBuilder();
			Carta c = Carta.valueOfFirstSymbol(strRestante, strCarta);
			cartasCompradas.add(c);
			strPrefixo.append(strCarta);
		}
		string.replace(0, strPrefixo.length(), "");
		if (firstSymbol != null) {
			firstSymbol.replace(0, firstSymbol.length(), new String(strPrefixo));
		}
		
		return new Baralho(lstCartas, cartasCompradas);
	}
}