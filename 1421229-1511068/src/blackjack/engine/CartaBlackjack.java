package blackjack.engine;

/*
 * Representa uma carta que já foi comprada do baralho. Possui variáveis que permitem
 * uma carta ser jogada especificamente num jogo de blackjack.
 */
import java.util.List;

public class CartaBlackjack {
	private Carta carta;
	private boolean faceVisivel;

	public CartaBlackjack(Carta carta, boolean faceVisivel) {
		this.carta = carta;
		this.faceVisivel = faceVisivel;
	}

	public static int getPontos(List<CartaBlackjack> lstCartas) {
		int ases = 0;
		int pontos = 0;
		for (CartaBlackjack cB : lstCartas) {
			Carta c = cB.getCarta();
			Carta.Valor val = c.getValor();
			if (cB.getVisibilidade() == true) {
				if (val == Carta.Valor.AS) {
					ases++;
				} else {
					int id = val.getId();
					if (id > 10) {
						pontos += 10;
					} else {
						pontos += id;
					}
				}
			}
		}
		if (ases > 0) {
			pontos += ases - 1;
			if (pontos <= 10) {
				pontos += 11;
			} else {
				pontos += 1;
			}
		}
		return pontos;
	}
	
	public int getValor() {
		CartaBlackjack cB = this;
		int pontos = 0;
		Carta c = cB.getCarta();
		Carta.Valor val = c.getValor();
		if (cB.getVisibilidade() == true) {
			int id = val.getId();
			if (id > 10) {
				pontos += 10;
			} 
			else {
				pontos += id;
			}
		}
		return pontos;
	}

	public boolean contido (List<CartaBlackjack> lstCartas) {
		for(int i=0; i < lstCartas.size(); i++) {
			CartaBlackjack cartaB = lstCartas.get(i);
			Carta carta = cartaB.getCarta();
			if (carta.equals(this.carta)) {
				return true;
			}
		}
		return false;
	}

	public Carta getCarta() {
		return carta;
	}

	public void setVisibilidade(boolean faceVisivel) {
		this.faceVisivel = faceVisivel;
	}

	public boolean getVisibilidade() {
		return faceVisivel;
	}

	// Obtém string-símbolo que representa uma CartaBlackjack
	public String getSymbol() {
		// Coverte um boolean para um int
		int faceInt = (faceVisivel) ? 1 : 0;
		// Retorna string da carta + um inteiro (0 ou 1)
		return carta.getSymbol() + " " + Integer.toString(faceInt);
	}

	/*
	 * Recebe uma string no primeiro parâmetro e retornará, através do segundo
	 * parâmetro (que pode ser null caso não se deseje obter o retorno) a substring
	 * prefixo da string original que representa a string-símbolo de uma carta. Essa
	 * substring também será retirada da string original. O retorno normal desse
	 * método é a carta correspondente a essa string-símbolo.
	 */
	public static CartaBlackjack valueOfFirstSymbol(StringBuilder string, StringBuilder firstSymbol) {
		
		String str = new String(string);
		StringBuilder strPrefixo = new StringBuilder("");
		
		String[] tokens = str.split("[ \n]", 4);
		if (tokens.length != 4 && tokens.length != 3) {
			throw new RuntimeException("CartaBlackjack desconhecida");
		}
		strPrefixo.append(tokens[0] + " " + tokens[1] + " " + tokens[2]);
		string.replace(0, strPrefixo.length(), "");
		if (firstSymbol != null) {
			firstSymbol.replace(0, firstSymbol.length(), new String(strPrefixo));
		}		
		String str2 = tokens[0] + " " + tokens[1];
		return new CartaBlackjack(Carta.valueOfFirstSymbol(new StringBuilder(str2),null), tokens[2].equals("1"));
	}
}