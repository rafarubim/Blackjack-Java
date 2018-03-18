package blackjack.engine;

import java.util.ArrayList;
import java.util.List;

public class Banca extends Participante {
	private static Banca banca = new Banca();
	private Baralho deck = new Baralho();
	
	private Banca() {
		deck.embaralhar();
	}
	
	// Construtor private, para usar em valueOfFirstSymbol
	private Banca(List<CartaBlackjack> listaMao, int credito, Baralho deck) {
		this.listaMao = listaMao;
		this.credito = credito;
		this.deck = deck;
	}
	
	public static Banca getBanca() {
		return banca;
	}
	
	public static void setBanca(Banca b) {
		banca = b;
	}
	
	public Baralho getBaralho() {
		return deck;
	}
	
	@Override
	public void addCartaMao(Carta c) {
		boolean faceVisivel = true;
		if (listaMao.isEmpty()) {
			faceVisivel = false;
		}
		listaMao.add(new CartaBlackjack(c, faceVisivel));
		updateMao();
	}
	
	public void revelarCarta() {
		if (listaMao.isEmpty()) {
			throw new RuntimeException("Banca não possui carta a revelar");
		}
		listaMao.get(0).setVisibilidade(true);
		updateMao();
	}
	
	// Obtém string-símbolo que representa uma banca
 	@Override
	public String getSymbol() {
 		String str1 = super.getSymbol();
 		return str1 + " " + deck.getSymbol();
 	}
 	
 	/*
	 * Recebe uma string no primeiro parâmetro e retornará, através do segundo
	 * parâmetro (que pode ser null caso não se deseje obter o retorno) a substring
	 * prefixo da string original que representa a string-símbolo de uma banca. Essa
	 * substring também será retirada da string original.
	 * O retorno normal desse método é uma banca correspondente a essa string-símbolo.
	 */
 	public static Banca valueOfFirstSymbol(StringBuilder string, StringBuilder firstSymbol) {
 		StringBuilder strPrefixo = new StringBuilder("");
 		List<CartaBlackjack> listaMao = new ArrayList<CartaBlackjack>();
 		int creditos = Participante.valueOfFirstSymbol(string, strPrefixo, listaMao);
 		if (string.charAt(0) != ' ') {
 			throw new RuntimeException("Banca desconhecida");
 		}
 		strPrefixo.append(string.substring(0, 1));
 		string.replace(0, 1, "");
 		StringBuilder deckSymbol = new StringBuilder();
 		Baralho deck = Baralho.valueOfFirstSymbol(string, deckSymbol);
 		strPrefixo.append(deckSymbol);
 		
 		
 		if (firstSymbol != null) {
 			firstSymbol.replace(0, firstSymbol.length(), new String(strPrefixo));
 		}
 		
 		return new Banca(listaMao, creditos, deck);
 	}
}