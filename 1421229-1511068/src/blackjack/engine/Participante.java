package blackjack.engine;

import java.util.ArrayList;
import java.util.List;

import blackjack.fachada_para_interface.interface_observadora.ObservadorParticipante;
import blackjack.fachada_para_interface.jogo_observado.ParticipanteObservado;

public abstract class Participante implements ParticipanteObservado {
    protected List<CartaBlackjack> listaMao = new ArrayList<CartaBlackjack>();
    private List<ObservadorParticipante> lstObservadoresP = new ArrayList<ObservadorParticipante>();
    protected int credito = 0;
   
    public int getPontos() {
        return CartaBlackjack.getPontos(listaMao);
    }
   
    public void addObservadorParticipante(ObservadorParticipante oP) {
        lstObservadoresP.add(oP);
        updateCredito();
        updateMao();
    }
    
    public void rmObservadorParticipante(ObservadorParticipante oP) {
        lstObservadoresP.remove(oP);
    }
   
    protected void updateMao() {
        for (ObservadorParticipante oP: lstObservadoresP) {
            oP.notificarMao(this);
        }
    }
    
    protected void updateCredito() {
    	for (ObservadorParticipante oP: lstObservadoresP) {
            oP.notificarCredito(this);
        }
    }
    
    public boolean temMaoBlackjack() {
    	if (listaMao.size() == 2 && getPontos() == 21) {
    		return true;
    	}
    	return false;
    }
    
    public void addCredito(int qtdCredito) {
    	if (qtdCredito < 0) {
    		throw new RuntimeException("Adi��o de cr�dito inv�lida");
    	}
    	credito += qtdCredito;
    	updateCredito();
    }
    
    public int getCredito() {
    	return credito;
    }
    
    public void setCredito(int qtdCredito) {
    	credito = qtdCredito;
    	updateCredito();
    }
    
    public void rmCredito(int qtdCredito) {
    	if (qtdCredito < 0 || credito - qtdCredito < 0) {
    		throw new RuntimeException("Remo��o de cr�dito inv�lida");
    	}
    	credito -= qtdCredito;
    	updateCredito();
    }
    
    @Override
    public List<CartaBlackjack> getMao() {
        return listaMao;
    }
    
    public void limparMao() {
    	listaMao.clear();
    	updateMao();
    }
   
    public abstract void addCartaMao(Carta c);
   
    public void esvaziarMao() {
        listaMao.clear();
    }
    
    // Obt�m string-s�mbolo que representa um participante
 	public String getSymbol() {
 		String strListaMao = String.valueOf(listaMao.size());
 		for (CartaBlackjack cB: listaMao) {
 			strListaMao += " " + cB.getSymbol();
 		}
 		return strListaMao + "\n" + credito;
 	}
 	
 	/*
	 * Recebe uma string no primeiro par�metro e retornar�, atrav�s do segundo
	 * par�metro (que pode ser null caso n�o se deseje obter o retorno) a substring
	 * prefixo da string original que representa a string-s�mbolo de um participante. Essa
	 * substring tamb�m ser� retirada da string original. O terceiro par�metro serve para o
	 * retorno (tamb�m opcional, com null) da lista de cartas blackjack que o participante representado
	 * pela string-s�mbolo possui. O retorno normal desse m�todo � o n�mero de cr�ditos correspondente a
	 * esse participante.
	 */
 	public static int valueOfFirstSymbol(StringBuilder string, StringBuilder firstSymbol, List<CartaBlackjack> listaMao) {
 		String str = new String(string);
 		List<CartaBlackjack> lstMao = new ArrayList<CartaBlackjack>();
 		StringBuilder strPrefixo = new StringBuilder("");
 		
 		// reconstruir listaMao
 		String[] tokenSizeListaMao = str.split("[ \n]", 2);
 		if (tokenSizeListaMao.length != 2) {
 			throw new RuntimeException("Participante desconhecido");
 		}
 		int sizeListaMao = 0;
 		try {
 			sizeListaMao = Integer.valueOf(tokenSizeListaMao[0]);
 		}
 		catch (NumberFormatException e) {
 			e.printStackTrace();
 			System.exit(1);
 		}
 		strPrefixo.append(tokenSizeListaMao[0]);
 		
 		StringBuilder strRestante = new StringBuilder(tokenSizeListaMao[1]);
 		
 		// Adicionar espa�o, se split havia retirado
 		if (sizeListaMao != 0) {
 			strRestante.insert(0, " ");
 		}
 		for (int i = 0; i < sizeListaMao; i++) {
 			// Tira o espa�o antes de cada carta
 			strPrefixo.append(strRestante.substring(0, 1));
 			strRestante.replace(0, 1, "");
 			
 			StringBuilder strCartaB = new StringBuilder();
 			CartaBlackjack cB = CartaBlackjack.valueOfFirstSymbol(strRestante, strCartaB);
 			lstMao.add(cB);
 			strPrefixo.append(strCartaB);
 		}
 		
 		// Se split n�o tirou \n, retir�-lo
 		if (strRestante.charAt(0) == '\n') {
 			strRestante.replace(0, 1, "");
 		}
 		strPrefixo.append("\n");
 		
 		String restante = new String(strRestante);
 		
 		// reconstruir credito
 		String[] tokenCredito = restante.split("[ \n]", 2);
 		if (tokenCredito.length != 1 && tokenCredito.length != 2) {
 			throw new RuntimeException("Participante desconhecido");
 		}
 		int credito = 0;
 		try {
 			credito = Integer.valueOf(tokenCredito[0]);
 		}
 		catch (NumberFormatException e) {
 			e.printStackTrace();
 			System.exit(1);
 		}
 		strPrefixo.append(tokenCredito[0]);
 		
 		string.replace(0, strPrefixo.length(), "");
 		if (firstSymbol != null) {
 			firstSymbol.replace(0, firstSymbol.length(), new String(strPrefixo));
 		}
 		
 		if (listaMao != null) {
	 		listaMao.clear();
	 		listaMao.addAll(lstMao);
 		}
 		return credito;
 	}
}
