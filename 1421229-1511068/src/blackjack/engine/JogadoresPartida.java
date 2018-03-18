package blackjack.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class JogadoresPartida {
	private List<Jogador> lstJogadores = new ArrayList<Jogador>();
	private List<Jogador> lstJogsRodada = new ArrayList<Jogador>();
	private ListIterator<Jogador> iteratorJogAtual = null;
		//previous é o jogador atual
	
	public JogadoresPartida(int numJogs) {
		for (int i = 1; i <= numJogs; i++) {
			lstJogadores.add(new Jogador(i));
		}
	}
	
	// Construtor private, para usar em valueOfFirstSymbol
	private JogadoresPartida(List<Jogador> lstJogadores) {
		this.lstJogadores = lstJogadores;
		lstJogsRodada = new ArrayList<Jogador>();
		iteratorJogAtual = null;
	}
	
	private Jogador buscarNumJogador(List<Jogador> lst, int numJogador) {
		for(Jogador jog: lst) {
			if (jog.getNumJogador() == numJogador) {
				return jog;
			}
		}
		return null;
	}
	
	// Bota todos os jogadores na rodada e inicia no primeiro
	public void iniciarRodada() {
		if (lstJogadores.isEmpty()) {
			throw new RuntimeException("Não há jogadores para começar rodada!");
		}
		lstJogsRodada.clear();
		lstJogsRodada.addAll(lstJogadores);
		iteratorJogAtual = lstJogsRodada.listIterator(1);
	}
	
	// Tira os jogadores da rodada
	public void terminarRodada() {
		iteratorJogAtual = null;
	}
	
	// Obtém jogador atual da rodada (ou null se não houver)
	public Jogador getJogAtual() {
		if (iteratorJogAtual == null || !iteratorJogAtual.hasPrevious()) {
			return null;
		}
		iteratorJogAtual.previous();
		return iteratorJogAtual.next();
	}
	
	// Determina jogador atual da rodada (0 para não haver atual)
	public void setJogAtual(int numJog) {
		if (numJog == 0) {
			iteratorJogAtual = null;
		}
		else {
			if (lstJogsRodada.isEmpty()) {
				throw new RuntimeException("Não há jogador na rodada!");
			}
			iteratorJogAtual = lstJogsRodada.listIterator();
			boolean achou = false;
			while(iteratorJogAtual.hasNext()) {
				Jogador jog = iteratorJogAtual.next();
				if (jog.getNumJogador() == numJog) {
					achou = true;
					break;
				}
			}
			if (!achou) {
				throw new RuntimeException("O jogador não está na rodada");
			}
		}
	}
	
	// Vez do próxima na rodada
	public void vezProxJogador() {
		if (lstJogsRodada.size() == 0) {
			throw new RuntimeException("Lista de jogadores na rodada está vazia");
		}
		if (iteratorJogAtual == null) {
			throw new RuntimeException("Não há jogador atual");
		}
		if (iteratorJogAtual.hasNext()) {
			iteratorJogAtual.next();
		} else {
			iteratorJogAtual = lstJogsRodada.listIterator();
			iteratorJogAtual.next();
		}
	}
	
	// Retorna lista de jogadores da rodada
	public List<Jogador> getJogsRodada() {
		return lstJogsRodada;
	}
	
	// Retorna lista de jogadores da partida
	public List<Jogador> getJogadores() {
		return lstJogadores;
	}
	
	// Retorna jogador de número específico
	public Jogador getJogador(int numJog) {
    	for(Jogador jog: lstJogadores) {
    		if (jog.getNumJogador() == numJog) {
    			return jog;
    		}
    	}
    	throw new RuntimeException("Jogador não está na partida!");
	}
	
	// Remove jogador da rodada. Se for o atual, o atual vira o próximo.
	public void rmJogRodada(int numJogador) {
		Jogador jogAtualAnterior = getJogAtual();
		// Encontra jogador a remover correspondente ao número passado
		Jogador jogARemover = buscarNumJogador(lstJogsRodada, numJogador);
		if (jogARemover == null) {
			throw new RuntimeException("Jogador não está na rodada");
		}
		// Obter jogador que será o novo atual (ou null se não houver)
		Jogador novoJogAtual = jogAtualAnterior;
		if (jogAtualAnterior == jogARemover) {
				// Se o jogador atual é o que foi removido, o novo atual será o próximo
			if (iteratorJogAtual.hasNext()) {
				novoJogAtual = iteratorJogAtual.next();
			} else {
				novoJogAtual = lstJogsRodada.get(0);
				if (novoJogAtual == jogAtualAnterior) {
						// Se o novo jog atual é igual ao anterior, então não haverá novo atual
					novoJogAtual = null;
				}
			}
		}
		// Remove jogador
		lstJogsRodada.remove(jogARemover);
		// Encontrar novo jogador atual
		if (lstJogsRodada.isEmpty()) {
				// Se a rodada não tem mais jogadores, não há atual
			iteratorJogAtual = null;
		} else {
				// Se a rodada ainda tem jogadores
			iteratorJogAtual = lstJogsRodada.listIterator();
			boolean achouNovoAtual = false;
			// Procurar novo jogador atual na nova lista
			while(iteratorJogAtual.hasNext()) {
				Jogador jog = iteratorJogAtual.next();
				if (jog == novoJogAtual) {
						// Se o novo jogador atual foi encontrado, parar o iterator aqui
					achouNovoAtual = true;
					break;
				}
			}
			if (!achouNovoAtual) {
					// Se o atual anterior não foi encontrado, ou é porque ele não existia ou porque foi removido
				iteratorJogAtual = null;
			}
		}
	}
	
	// Adiciona jogador à rodada, em posição inespecífica em relação ao atual.
	public void addJogRodada(int numJogador) {
		Jogador jogador = null;
		for (Jogador jog: lstJogadores) {
			if (jog.getNumJogador() == numJogador) {
				jogador = jog;
				break;
			}
		}
		if (jogador == null) {
			throw new RuntimeException("Jogador não está na partida!");
		}
		// Assertiva
		if (lstJogsRodada.contains(jogador)) {
			throw new RuntimeException("Jogador já está na rodada!");
		}
		Jogador jogAtual = getJogAtual();
		int numJogAtual = 0;
		if (jogAtual != null) {
			numJogAtual = jogAtual.getNumJogador();
		}
		lstJogsRodada.add(jogador);
		setJogAtual(numJogAtual);
	}
	
	// Remove jogador da partida e da rodada.
	public void rmJogPartida(int numJogador) {
		rmJogRodada(numJogador);
		Jogador jogARemover = buscarNumJogador(lstJogadores, numJogador);
		if (jogARemover == null) {
			throw new RuntimeException("Jogador não está na partida");
		}
		lstJogadores.remove(jogARemover);
	}
	
	// Obtém string-símbolo que representa um objeto JogadoresPartida
	public String getSymbol() {
 		// lstJogadores
 		String strLstJogadores = String.valueOf(lstJogadores.size());
 		for (Jogador jog: lstJogadores) {
 			strLstJogadores += " " + jog.getSymbol();
 		}
 		
 		// lstJogsRodada
 		String strLstJogsRodada = String.valueOf(lstJogsRodada.size());
 		for (Jogador jog: lstJogsRodada) {
 			strLstJogsRodada += " " + jog.getNumJogador();
 		}
 		
 		int numJogAtual = 0;
 		Jogador jogAtual = getJogAtual();
 		if (jogAtual != null) {
 			numJogAtual = jogAtual.getNumJogador();
 		}
 		
 		return strLstJogadores + "\n" + strLstJogsRodada + "\n" + numJogAtual;
 	}
 	
 	/*
	 * Recebe uma string no primeiro parâmetro e retornará, através do segundo
	 * parâmetro (que pode ser null caso não se deseje obter o retorno) a substring
	 * prefixo da string original que representa a string-símbolo de um objeto JogadoresPartida.
	 * Essa substring também será retirada da string original.
	 * O retorno normal desse método é um objeto JogadoresPartida correspondente a essa string-símbolo.
	 */
 	public static JogadoresPartida valueOfFirstSymbol(StringBuilder string, StringBuilder firstSymbol) {
 		String str = new String(string);
 		List<Jogador> lstJogadores = new ArrayList<Jogador>();
 		List<Integer> lstNumJogsRodada = new ArrayList<Integer>();
 		StringBuilder strPrefixo = new StringBuilder("");
 		StringBuilder strRestante;
 		
 		// Recuperar lstJogadores
 		String[] tokenSizeLstJogadores = str.split("[ \n]", 2);
 		if (tokenSizeLstJogadores.length != 2) {
 			throw new RuntimeException("Objeto JogadoresPartida desconhecido");
 		}
 		int sizeLstJogadores = 0;
 		try {
 			sizeLstJogadores = Integer.valueOf(tokenSizeLstJogadores[0]);
 		}
 		catch (NumberFormatException e) {
 			e.printStackTrace();
 			System.exit(1);
 		}
 		strPrefixo.append(tokenSizeLstJogadores[0]);
 		strRestante = new StringBuilder(tokenSizeLstJogadores[1]);
 		// Colocar o espaço antes do primeiro jogador, se o split o tirou
 		if (sizeLstJogadores != 0) {
 			strRestante.insert(0, " ");
 		}
 		for (int i = 0; i < sizeLstJogadores; i++) {
 			// retirar espaço antes de cada jogador
 			strPrefixo.append(strRestante.substring(0, 1));
 			strRestante.replace(0, 1, "");
 			
 			StringBuilder strJog = new StringBuilder();
 			Jogador jog = Jogador.valueOfFirstSymbol(strRestante, strJog);
 			lstJogadores.add(jog);
 			strPrefixo.append(strJog);
 		}
 		// Retirar \n, se o split já não o fez
 		if (strRestante.charAt(0) == '\n') {
 			strRestante.replace(0, 1, "");
 		}
 		strPrefixo.append("\n");
 		
 		String restante = new String(strRestante);
 		
 		// Recuperar lstJogsRodada
 		String[] tokenSizeLstJogsRodada = restante.split("[ \n]", 2);
 		if (tokenSizeLstJogsRodada.length != 2) {
 			throw new RuntimeException("Objeto JogadoresPartida desconhecido");
 		}
 		int sizeLstJogsRodada = 0;
 		try {
 			sizeLstJogsRodada = Integer.valueOf(tokenSizeLstJogsRodada[0]);
 		}
 		catch (NumberFormatException e) {
 			e.printStackTrace();
 			System.exit(1);
 		}
 		strPrefixo.append(tokenSizeLstJogsRodada[0]);
 		strRestante = new StringBuilder(tokenSizeLstJogsRodada[1]);
 		restante = new String(strRestante);
 		String[] tokenLstJogsRodada = restante.split("[ \n]", sizeLstJogsRodada+1);
 		if (tokenLstJogsRodada.length != sizeLstJogsRodada+1) {
 			throw new RuntimeException("Objeto JogadoresPartida desconhecido");
 		}
 		for (int i = 0; i < sizeLstJogsRodada; i++) {
 			int numJog = 0;
 			try {
 				numJog = Integer.valueOf(tokenLstJogsRodada[i]);
 			}
 			catch (NumberFormatException e) {
 	 			e.printStackTrace();
 	 			System.exit(1);
 	 		}
 			
 			lstNumJogsRodada.add(numJog);
 			strPrefixo.append(" "+tokenLstJogsRodada[i]);
 		}
 		strRestante = new StringBuilder(tokenLstJogsRodada[sizeLstJogsRodada]);
 		// Se o split não tirou o \n, retirar
 		if (strRestante.charAt(0) == '\n') {
 			strRestante.replace(0, 1, "");
 		}
 		strPrefixo.append("\n");
 		
 		restante = new String(strRestante);
 		String[] tokenJogAtual = restante.split("[ \n]", 2);
 		if (tokenJogAtual.length != 1 && tokenJogAtual.length != 2) {
 			throw new RuntimeException("Objeto JogadoresPartida desconhecido");
 		}
 		int jogAtual = 0;
 		try {
 			jogAtual = Integer.valueOf(tokenJogAtual[0]);
 		}
 		catch (NumberFormatException e) {
 			e.printStackTrace();
 			System.exit(1);
 		}
 		strPrefixo.append(tokenJogAtual[0]);
 		
 		string.replace(0, strPrefixo.length(), "");
 		if (firstSymbol != null) {
 			firstSymbol.replace(0, firstSymbol.length(), new String(strPrefixo));
 		}
 		
 		JogadoresPartida jogsPart = new JogadoresPartida(lstJogadores);
 		for (int numJog: lstNumJogsRodada) {
 			jogsPart.addJogRodada(numJog);
 		}
 		jogsPart.setJogAtual(jogAtual);
 		return jogsPart;
 	}
}
