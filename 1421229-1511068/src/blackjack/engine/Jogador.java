package blackjack.engine;

import java.util.ArrayList;
import java.util.List;

import blackjack.fachada_para_interface.interface_observadora.ObservadorJogador;
import blackjack.fachada_para_interface.jogo_observado.JogadorObservado;

public class Jogador extends Participante implements JogadorObservado {
   
	public enum Status {
        INDETERMINADO, COMPROU, APOSTOU, PASSOU, RENDEU, FINALIZOU, ESTOROU, VENCEU, PERDEU, EMPATOU;

        public String getSymbol() {
            return name();
        }

        public static Status valueOfSymbol(String simbolo) {
            for (Status s: Status.values()) {
                if (s.getSymbol().equals(simbolo)) {
                    return s;
                }
            }
            throw new RuntimeException("Status desconhecido");
        }
    }
    
    //TODO Imprimir o valor do seguro, se der tempo
   
    private Status status = Status.INDETERMINADO;
    private List<ObservadorJogador> lstObservadoresJ = new ArrayList<ObservadorJogador>();
    private int id = 0;
    private int aposta = 0;
    private int seguro = 0;
    private int qtdCompras = 0;
   
    public Jogador(int id) {
        this.id = id;
        credito = 500;
    }
    
    //XXX Mudar para private
    // Construtor private, para usar em valueOfFirstSymbol
    private Jogador(int credito, List<CartaBlackjack> listaMao, Status status, int id, int aposta, int seguro, int qtdCompras) {
    	this.credito = credito;
    	this.listaMao = listaMao;
    	this.status = status;
    	this.id = id;
    	this.aposta = aposta;
    	this.seguro = seguro;
    	this.qtdCompras = qtdCompras;
    }
   
    public void addObservadorJogador(ObservadorJogador oJ) {
        lstObservadoresJ.add(oJ);
        updateStatus();
    }
    
    public void rmObservadorJogador(ObservadorJogador oJ) {
        lstObservadoresJ.remove(oJ);
    }
   
    private void updateStatus() {
        for (ObservadorJogador oJ: lstObservadoresJ) {
            oJ.notificarStatus(this);
        }
    }
    
    public int getSeguro() {
    	return seguro;
    }
    
    public void setSeguro(int valor) {
    	seguro = valor;
    }
    
    public void incQtdCompras() {
    	qtdCompras++;
    }
    
    public int getQtdCompras() {
    	return qtdCompras;
    }
    
    @Override
    public void updateMao() {
    	super.updateMao();
    	for (ObservadorJogador oJ: lstObservadoresJ) {
    		oJ.notificarMao(this);
    	}
    }
   
    public int getNumJogador() {
        return id;
    }
   
    public void setStatus(Status status) {
        this.status = status;
        updateStatus();
    }
   
    public Status getStatus() {
        return status;
    }
    
    public int getAposta() {
    	return aposta;
    }
    
    public void setAposta(int qtdCreditos) {
    	aposta = qtdCreditos;
    	updateCredito();
    }
   
    @Override
    public void addCartaMao(Carta c) {
        listaMao.add(new CartaBlackjack(c, true));
        updateMao();
    }
    
    // Obtém string-símbolo que representa um Jogador
 	@Override
 	public String getSymbol() {
 		String str1 = super.getSymbol();
 		return str1 + " " + status.getSymbol() + " " + Integer.toString(id) + " " + Integer.toString(aposta) + " " + Integer.toString(seguro) + " " + Integer.toString(qtdCompras);
 	}

 	/*
 	 * Recebe uma string no primeiro parâmetro e retornará, através do segundo
 	 * parâmetro (que pode ser null caso não se deseje obter o retorno) a substring
 	 * prefixo da string original que representa a string-símbolo de uma banca. Essa
 	 * substring também será retirada da string original. O retorno normal desse
 	 * método é uma banca correspondente a essa string-símbolo.
 	 */
 	public static Jogador valueOfFirstSymbol(StringBuilder string, StringBuilder firstSymbol) {
 		
 		//XXX
 		//String str = new String(string);
 		
 		StringBuilder builder = new StringBuilder(string);
 		
 		StringBuilder strPrefixo = new StringBuilder("");
 		List<CartaBlackjack> listaMao = new ArrayList<CartaBlackjack>();
 		int creditos = Participante.valueOfFirstSymbol(builder, strPrefixo, listaMao);
 		if (builder.charAt(0) != ' ') {
 			throw new RuntimeException("Jogador desconhecido");
 		}
 		strPrefixo.append(builder.substring(0, 1));
 		builder.replace(0, 1, "");
 		
 		//XXX
 		String str = new String(builder);
 		String[] tokens = str.split("[ \n]", 6);
 		if (tokens.length != 5 && tokens.length != 6) {
 			throw new RuntimeException("Jogador desconhecido");
 		}
 		strPrefixo.append(tokens[0] + " " + tokens[1] + " " + tokens[2] + " " + tokens[3] + " " + tokens[4]);
 		
 		string.replace(0, strPrefixo.length(), "");
 		
 		if (firstSymbol != null) {
 			firstSymbol.replace(0, firstSymbol.length(), new String(strPrefixo));
 		}
 		
 		Status St = Status.INDETERMINADO;
 		int Id = 0;
 		int Ap = 0;
 		int Se = 0;
 		int Qt = 0;
 		try {
	 		St = Status.valueOfSymbol(tokens[0]);
	 		Id = Integer.valueOf(tokens[1]);
	 		Ap = Integer.valueOf(tokens[2]);
	 		Se = Integer.valueOf(tokens[3]);
	 		Qt = Integer.valueOf(tokens[4]);
 		}
 		catch(NumberFormatException e) {
 			e.printStackTrace();
 			System.exit(1);
 		}
 		
 		return new Jogador(creditos, listaMao, St,Id,Ap,Se,Qt);
 	}
}