package blackjack.engine;

public class Carta {
	public enum Valor {
		AS("A", 1), DOIS("2", 2), TRES("3", 3), QUATRO("4", 4), CINCO("5", 5), SEIS("6", 6),
		SETE("7", 7), OITO("8", 8), NOVE("9", 9), DEZ("10", 10), VALETE("J", 11), DAMA("Q", 12), REI("K", 13);
		
		private final String simbolo;
		private final int id;
		
		private Valor(String simb, int id) {
			simbolo = simb;
			this.id = id;
		}
		
		public int getId() {
			return id;
		}
		
		public String getSymbol() {
			return simbolo;
		}
		
		public static Valor valueOfSymbol(String simbolo) {
			for (Valor v: Valor.values()) {
				if (v.simbolo.equals(simbolo)) {
					return v;
				}
			}
			throw new RuntimeException("Valor desconhecido");
		}
	}
	
	public enum Naipe {
		PAUS("P"), COPAS("C"), ESPADAS("E"), OUROS("O");
		
		private final String simbolo;
		
		private Naipe(String simbolo) {
			this.simbolo = simbolo;
		}
		
		public String getSymbol() {
			return simbolo;
		}
		
		public static Naipe valueOfSymbol(String symbol) {
			for (Naipe n: Naipe.values()) {
				if (n.simbolo.equals(symbol)) {
					return n;
				}
			}
			throw new RuntimeException("Naipe desconhecido");
		}
	}
	
	private final Valor valor;
	private final Naipe naipe;
	
	public Carta(Valor v, Naipe n) {
		valor = v;
		naipe = n;
	}
	
	@Override
	public int hashCode() {
		return getSymbol().hashCode();
	}
	
	@Override
	public boolean equals(Object a) {
		Carta b = (Carta) a;
		if (b.valor == valor && b.naipe == naipe) {
			return true;
		}
		return false;
	}
	
	public Valor getValor() {
		return valor;
	}
	
	public Naipe getNaipe() {
		return naipe;
	}
	
	// Obtém string-símbolo que representa uma carta
	public String getSymbol() {
		// valor, naipe
		return valor.getSymbol() + " " + naipe.getSymbol();
	}
	
	/* 
	 * Recebe uma string no primeiro parâmetro e retornará, através do segundo parâmetro (que pode ser
	 *  null caso não se deseje obter o retorno) a substring prefixo da string original que representa
	 *  a string-símbolo de uma carta. Essa substring também será retirada da string original.
	 *  O retorno normal desse método é a carta correspondente a essa string-símbolo.
	 */
	public static Carta valueOfFirstSymbol(StringBuilder string, StringBuilder firstSymbol) {
		String str = new String(string);
		StringBuilder strPrefixo = new StringBuilder("");
		String[] tokens = str.split("[ \n]", 3);
		if (tokens.length != 2 && tokens.length != 3) {
			throw new RuntimeException("Carta desconhecida");
		}
		strPrefixo.append(tokens[0]+" "+tokens[1]);
		string.replace(0, strPrefixo.length(), "");
		if (firstSymbol != null) {
			firstSymbol.replace(0, firstSymbol.length(), new String(strPrefixo));
		}
		return new Carta(Valor.valueOfSymbol(tokens[0]), Naipe.valueOfSymbol(tokens[1]));
	}
}