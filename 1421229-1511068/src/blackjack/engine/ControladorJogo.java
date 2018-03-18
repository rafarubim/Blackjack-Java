package blackjack.engine;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import blackjack.acao_listener.ApostaListener;
import blackjack.acao_listener.CompraListener;
import blackjack.acao_listener.DobrarListener;
import blackjack.acao_listener.LoadListener;
import blackjack.acao_listener.NumJogadoresListener;
import blackjack.acao_listener.PararListener;
import blackjack.acao_listener.PedirListener;
import blackjack.acao_listener.ReiniciarRodadaListener;
import blackjack.acao_listener.RenderListener;
import blackjack.acao_listener.SaiuListener;
import blackjack.acao_listener.SalvarListener;
import blackjack.acao_listener.SeguroListener;
import blackjack.fachada_para_interface.interface_observadora.ObservadorControlador;
import blackjack.fachada_para_interface.jogo_observado.ControladorObservado;
import interface_blackjack.FacadeParaJogo;

//TODO Testar se é public, private ou protected e se pode fazer imports

public class ControladorJogo implements ControladorObservado {
	public enum AcaoJogador {
		PEDIR_CARTA, PARAR, DOBRAR, SEGURO, RENDER;
	}
	
	private static ControladorJogo controladorJogo = new ControladorJogo();
	private List<ObservadorControlador> lstObservadores = new ArrayList<>();
		//Lista de observadores
	private Evento eventoEsperado = Evento.NENHUM;
	JogadoresPartida jogsPartida = null;
	private boolean partidaComecou = false;
	private boolean interfaceExiste = false;

	private ControladorJogo() {}

	public static ControladorJogo getControladorJogo() {
		return controladorJogo;
	}
	
	//Adiciona observadores na lista
	public void addObservadorControlador (ObservadorControlador oC) {
		lstObservadores.add(oC);
		if (eventoEsperado == Evento.ESCOLHA_NUM_JOG) {
			updateMenuCriado();
		}
		else if (partidaComecou) {
			updateBancaCriada();
			updateJogadoresCriados();
			updateJogAtual();
			updateEventoEsperado();
		}
	}
	
	public void rmObservadorControlador (ObservadorControlador oC) {
		lstObservadores.remove(oC);
	}
	
	private void updateMenuCriado() {
		for(ObservadorControlador oC : lstObservadores) {
			oC.notificarMenuCriado(this);
		}
		FacadeParaJogo.getFacade().registrarListenerParaNumJogs(new NumJogadoresListener());
	}
	
	private void updateBancaCriada() {
		for(ObservadorControlador oC : lstObservadores) {
			oC.notificarBancaCriada(this);
		}
		FacadeParaJogo.getFacade().registrarListenerParaCarregarJogo(new LoadListener());
		FacadeParaJogo.getFacade().registrarListenerParaSalvarJogo(new SalvarListener());
		FacadeParaJogo.getFacade().registrarListenerParaReiniciarRodada(new ReiniciarRodadaListener());
		FacadeParaJogo.getFacade().registrarListenerParaApostar(new ApostaListener());
		FacadeParaJogo.getFacade().registrarListenerParaComprar(new CompraListener());
	}
	
	private void updateJogadoresCriados() {
		for(ObservadorControlador oC : lstObservadores) {
			oC.notificarJogadoresCriados(this);
		}
		FacadeParaJogo.getFacade().registrarListenerParaPedir(new PedirListener());
		FacadeParaJogo.getFacade().registrarListenerParaParar(new PararListener());
		FacadeParaJogo.getFacade().registrarListenerParaDobrar(new DobrarListener());
		FacadeParaJogo.getFacade().registrarListenerParaSeguro(new SeguroListener());
		FacadeParaJogo.getFacade().registrarListenerParaRender(new RenderListener());
		FacadeParaJogo.getFacade().registrarListenerParaSair(new SaiuListener());
	}
	
	private void updateJogadoresRemovidos() {
		for(ObservadorControlador oC : lstObservadores) {
			oC.notificarJogadoresRemovidos(this);
		}
	}
	
	private void updateJogAtual() {
		for (ObservadorControlador oC : lstObservadores) {
			oC.notificarJogAtual(this);
		}
	}
	
	private void updateEventoEsperado() {
		for (ObservadorControlador oC : lstObservadores) {
			oC.notificarEventoEsperado(this);
		}
	}
	
	public JogadoresPartida getJogsPartida() {
		return jogsPartida;
	}
	
	public void interfaceCriada() {
		interfaceExiste = true;
	}
	
	public void iniciarJogo() {
		setEventoEsperado(Evento.ESCOLHA_NUM_JOG);
		if (interfaceExiste) {
			updateMenuCriado();
		}
	}
	
	private void setEventoEsperado(Evento evento) {
		eventoEsperado = evento;
		updateEventoEsperado();
	}
	
	private void distribuirCartas() {
		Banca banca = Banca.getBanca();
		Baralho deck = banca.getBaralho();
		// Distribuir 2 cartas para cada jogador/banca
		for (int i = 0; i < 2; i++) {
			
			for (Jogador jog: jogsPartida.getJogsRodada()) {
				if (deck.isEmpty()) {
					throw new RuntimeException("Baralho esvaziou em momento inesperado");
				}
				jog.addCartaMao(deck.comprarCarta());
			}
			banca.addCartaMao(deck.comprarCarta());
		}
	}
	
	public void eventoNumJogadores(int numJogs) {
		if (eventoEsperado != Evento.ESCOLHA_NUM_JOG) {
			return;
		}
		if (numJogs < 1 || numJogs > 4) {
			throw new RuntimeException("Número de jogsPartida inválido");
		}
		
		updateBancaCriada();
		
		// Criar jogsPartida
		jogsPartida = new JogadoresPartida(numJogs);
		
		updateJogadoresCriados();
		
		jogsPartida.iniciarRodada();
		updateJogAtual();
		setEventoEsperado(Evento.APOSTA_JOGADOR);
		partidaComecou = true;
	}
	
	public void eventoJogadorSaiu(int numJogador) {
		jogsPartida.rmJogPartida(numJogador);
		updateJogAtual();
		if (jogsPartida.getJogsRodada().isEmpty()) {
			setEventoEsperado(Evento.NENHUM);
			return;
		}
		else if (testarFimRodada()) {
			return;
		}
	}
	
	private void testarFimApostas() {
		Jogador jogAtual = jogsPartida.getJogAtual();
		Jogador.Status status = jogAtual.getStatus();
		if (status == Jogador.Status.PASSOU || status == Jogador.Status.APOSTOU) {
			distribuirCartas();
			
			for (Jogador jog: jogsPartida.getJogsRodada()) {
				jog.setStatus(Jogador.Status.INDETERMINADO);
			}
			
			setEventoEsperado(Evento.ACAO_JOGADOR);
		}
	}
	
	public void eventoCompraJogador(int credito) {
		if (eventoEsperado != Evento.APOSTA_JOGADOR) {
			return;
		}
		// Verifica se jogador já fez mais de duas compras de credito
		Jogador jogAtual = jogsPartida.getJogAtual();
		if (jogAtual.getQtdCompras() >= 2) {
			return;
		}
		// Verifica se nao esta na rodada de compras
		if (jogAtual.getStatus() != Jogador.Status.INDETERMINADO) {
			return;
		}
		// Credito inválido não compra
		if (credito <= 0) {
			return;
		}
		// Limite para compra de credito de até 250$
		if (credito <= 250) {
			jogAtual.addCredito(credito);
			jogAtual.incQtdCompras();
			jogAtual.setStatus(Jogador.Status.COMPROU);
		}
	}
	
	public void eventoApostaJogador(int aposta) {
		if (eventoEsperado != Evento.APOSTA_JOGADOR) {
			return;
		}
		if (aposta < 0) {
			return;
		}
		if (aposta == 0) {
			eventoJogadorPassou(jogsPartida.getJogAtual().getNumJogador());
			return;
		}
		
		Jogador jogAtual = jogsPartida.getJogAtual();
		
		if (jogAtual.getCredito() >= aposta) {
			jogAtual.rmCredito(aposta);
			jogAtual.setAposta(aposta);
			jogAtual.setStatus(Jogador.Status.APOSTOU);
			
			jogsPartida.vezProxJogador();
			updateJogAtual();
			testarFimApostas();
		}
	}
	
	public void eventoJogadorPassou(int jogadorNum) {
		if (eventoEsperado != Evento.APOSTA_JOGADOR) {
			return;
		}
		if (jogsPartida.getJogAtual().getNumJogador() != jogadorNum) {
			return;
		}
		Jogador jogAtual = jogsPartida.getJogAtual();
		jogAtual.setStatus(Jogador.Status.PASSOU);
		jogsPartida.rmJogRodada(jogAtual.getNumJogador());
		updateJogAtual();
		if (jogsPartida.getJogsRodada().isEmpty()) {
			novaRodada();
			return;
		}
		testarFimApostas();
	}
	
	public void eventoAcaoJogador(int jogadorNum, AcaoJogador acao) {
		if (eventoEsperado != Evento.ACAO_JOGADOR) {
			return;
		}
		if (jogsPartida.getJogAtual().getNumJogador() != jogadorNum) {
			return;
		}
		Jogador jogador = jogsPartida.getJogador(jogadorNum);
		boolean jogadorAcabou = false;
		
		switch(acao) {
		case PEDIR_CARTA:
			Banca banca = Banca.getBanca();
			Baralho deck = banca.getBaralho();
			if (deck.isEmpty()) {
				throw new RuntimeException("Baralho esvaziou em momento inesperado");
			}
			jogador.addCartaMao(deck.comprarCarta());
			int pontos = jogador.getPontos();
			if (pontos > 21) {
				jogador.setStatus(Jogador.Status.ESTOROU);
				jogadorAcabou = true;
			}
			break;
		case PARAR:
			jogador.setStatus(Jogador.Status.FINALIZOU);
			jogadorAcabou = true;
			break;
		case DOBRAR:
            if (jogador.getMao().size() != 2) {
                return;
            }
            int aposta = jogador.getAposta();
            if (jogador.getCredito() < aposta) {
                return;
            }           
            jogador.setAposta(2*aposta);
            jogador.rmCredito(aposta);
            eventoAcaoJogador(jogadorNum, AcaoJogador.PEDIR_CARTA);
            eventoAcaoJogador(jogadorNum, AcaoJogador.PARAR);
            return;
		case SEGURO:
            if (jogador.getMao().size() != 2) {
                return;
            }
            if (Banca.getBanca().getPontos() != 11) {
            	return;
            }
            if (jogador.getSeguro() != 0) {
            	return;
            }
            aposta = jogador.getAposta();
            int seguro = (aposta + 1)/2;
            if (jogador.getCredito() < seguro) {
                return;
            }
            jogador.rmCredito(seguro);
            jogador.setSeguro(seguro);            
            return;
		case RENDER:
            if (jogador.getMao().size() != 2) {
                return;
            }
            if (Banca.getBanca().getPontos() == 11) {
                return;
            }
            aposta = jogador.getAposta();
            jogador.addCredito((aposta + 1)/2);
            jogador.setAposta(0);
            jogador.setStatus(Jogador.Status.RENDEU);
            jogsPartida.rmJogRodada(jogadorNum);
            updateJogAtual();
            if (jogsPartida.getJogsRodada().isEmpty()) {
    			novaRodada();
    			return;
    		}
            else if (testarFimRodada()) {
    			return;
    		}
            return;
		default:
			throw new RuntimeException("Ação de jogador desconhecida");	
		}
		
		if (testarFimRodada()){
			return;
		}
		else if (jogadorAcabou) {
			// Achar próximo jogador
		Jogador novoJogador;
		Jogador.Status status;
		int cont = 0;
		do {
			jogsPartida.vezProxJogador();
			novoJogador = jogsPartida.getJogAtual();
			cont++;
			if (cont > jogsPartida.getJogsRodada().size()) {
				throw new RuntimeException("Inconsistencia: todos os jogsPartida da rodada já acabaram");
			}
			status = novoJogador.getStatus();
		} while(status == Jogador.Status.FINALIZOU || status == Jogador.Status.ESTOROU);
			updateJogAtual();
		}
	}
	
	// Retorna true se a rodada acabou
	private boolean testarFimRodada() {
		if (eventoEsperado != Evento.ACAO_JOGADOR) {
			return false;
		}
		
		// Testar se todos os jogsPartida da rodada acabaram
		boolean todosAcabaram = true;
		for (Jogador jog: jogsPartida.getJogsRodada()) {
			Jogador.Status status = jog.getStatus();
			if (status != Jogador.Status.FINALIZOU && status != Jogador.Status.ESTOROU) {
				todosAcabaram = false;
				break;
			}
		}
		
		if (todosAcabaram) {
				// Terminar rodada
			setEventoEsperado(Evento.NENHUM);
			jogsPartida.terminarRodada();
			updateJogAtual();
			finalizarRodada();
			return true;
		}
		return false;
	}
	
	private void finalizarRodada() {
		Banca banca = Banca.getBanca();
		banca.revelarCarta();
		comprarBanca();
		
		int pontosBanca = banca.getPontos();
		boolean bancaBlackjack = banca.temMaoBlackjack();
		for (Jogador jogador: jogsPartida.getJogsRodada()) {
			int pontosJog = jogador.getPontos();
			if ((pontosJog > pontosBanca && pontosJog <= 21 && pontosBanca <= 21) || (pontosJog <= 21 && pontosBanca > 21)) {
				jogador.setStatus(Jogador.Status.VENCEU);
			} else if (pontosJog == pontosBanca && bancaBlackjack == jogador.temMaoBlackjack() && pontosBanca <= 21) {
				jogador.setStatus(Jogador.Status.EMPATOU);
			} else if (pontosJog == pontosBanca && jogador.temMaoBlackjack() && !bancaBlackjack) {
				jogador.setStatus(Jogador.Status.VENCEU);
			} else {
				jogador.setStatus(Jogador.Status.PERDEU);
			}
			setEventoEsperado(Evento.REINICIO);
		}
	}
	
	public void reiniciarRodada() {
        if (eventoEsperado != Evento.REINICIO) {
            return;
        }
       
        List<Integer> jogsASair = new ArrayList<Integer>();
        for (Jogador jogador: jogsPartida.getJogsRodada()) {
            int aposta = jogador.getAposta();
            int credito = jogador.getCredito();
            int seguro = jogador.getSeguro();
            switch(jogador.getStatus()) {
            case VENCEU:
                int retorno = 0;
                if (jogador.temMaoBlackjack()) {
                    retorno = (5*aposta + 1)/2;
                }
                else {
                    retorno = 2*aposta;
                }
                jogador.setCredito(credito+retorno);
                break;
            case PERDEU:
                if (Banca.getBanca().temMaoBlackjack() && seguro != 0 && jogador.getPontos() <= 21) {
                    jogador.setCredito(credito+2*seguro);
                }
                break;
            case EMPATOU:
                jogador.setCredito(credito+aposta);
                break;
            default:
                throw new RuntimeException("Inconsistência no programa");
            }
            
            if (jogador.getCredito() <= 0 && jogador.getQtdCompras() >= 2) {
            	jogsASair.add(jogador.getNumJogador());
            }
        }
        for (Integer numJog: jogsASair) {
        	eventoJogadorSaiu(numJog);
        }
        updateJogadoresRemovidos();
        
        if (!jogsPartida.getJogadores().isEmpty()) {
        	novaRodada();
        }
    }
	
	private void novaRodada() {
		Banca banca = Banca.getBanca();
		for (Jogador jogador: jogsPartida.getJogadores()) {
            jogador.setAposta(0);
            jogador.setSeguro(0);
            jogador.setStatus(Jogador.Status.INDETERMINADO);
            // Sempre antes de comer, após usar o banheiro e antes de novas rodadas
            jogador.limparMao();
        }
        // Também após sujar-se trapaceando nos cassinos e após cumprimentar o pedro
        banca.limparMao();
        banca.getBaralho().reembaralharCartas();
        jogsPartida.iniciarRodada();
        updateJogAtual();
        setEventoEsperado(Evento.APOSTA_JOGADOR);
	}
	
	private void comprarBanca() {
		Banca banca = Banca.getBanca();
		if (banca.temMaoBlackjack() == true) {
			// Verifica se banca ja comecou com 21
			return;
		}

		List<CartaBlackjack> CartasCompradas = new ArrayList<CartaBlackjack>();
		int MaxPoints = 0;
		int MinPoints = 100;
		for (Jogador jog : jogsPartida.getJogsRodada()) {
			// Adiciona todas as cartas dos jogadores a lista
			CartasCompradas.addAll(jog.getMao());
			
			if (jog.getPontos() <= 21 && MaxPoints < jog.getPontos()) {
				// Determina qual foi a maior pontuação entre os jogadores
				MaxPoints = jog.getPontos();
			}
			
			if (jog.getPontos() < MinPoints && jog.getPontos() > 0 && jog.getPontos() <= 21) {
				MinPoints = jog.getPontos();
			}
			
		}
		if (banca.getPontos() > MaxPoints) {
			// Verifica se banca ja tem a maior pontuacao da mesa
			return;
		}
		
		CartasCompradas.addAll(new ArrayList<CartaBlackjack>(banca.getMao()));
		// Adiciona as cartas da banca a lista

		double prob;
		while(true) {
			int TotalCartas = 52 - CartasCompradas.size();

			Baralho genericDeck = new Baralho();
			List<CartaBlackjack> CartasRestantes = new ArrayList<CartaBlackjack>();
			for (int i = 0; i < 52; i++) {
				Carta card = genericDeck.comprarCarta();
				CartaBlackjack cardB = new CartaBlackjack(card, true);
				
				if (cardB.contido(CartasCompradas) == false) {
					// Adiciona as cartas que ainda nao foram compradas a lista
					CartasRestantes.add(cardB);
				}
			}

			int cont = 0;
			int Dif = 21 - banca.getPontos();
			for (CartaBlackjack card : CartasRestantes) {
				// Conta a quantidade de cartas que a banca poderia comprar sem estourar
				if (card.getValor() <= Dif) {
					cont++;
				}
			}
			prob = (double) cont / TotalCartas;
			// Calcula probabildade e compra se for maior ou igual que 50%
			// Ou compra caso a Banca tenha a menor pontuacao da mesa 
			// (caso ela perca para todos os jogadores se nao comprar)
			if (prob >= 0.5 || banca.getPontos() < MinPoints) {
				Baralho deck = banca.getBaralho();
				if (deck.isEmpty()) {
					throw new RuntimeException("Baralho esvaziou em momento inesperado");
				}
				Carta hitCard = deck.comprarCarta();
				// Adiciona carta a mao da banca e a lista de cartas compradas
				banca.addCartaMao(hitCard);
				CartasCompradas.add(new CartaBlackjack(hitCard, true));
			}
			else {
					// Se a banca não comprou, encerrar o loop
				break;
			}
		}

	}
	
	public void salvarJogo(String arquivo) {
		PrintWriter outStream = null;
		try {
			outStream = new PrintWriter(new FileWriter(arquivo));
			
			// Salvar evento esperado na primeira linha
			outStream.println(eventoEsperado.name());
			
			// Da segunda linha em diante:
			
			// Salvar banca
			outStream.println(Banca.getBanca().getSymbol());
			
			// Salvar jogsPartida
			outStream.print(jogsPartida.getSymbol());
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			if (outStream != null) {
				outStream.close();
			}
		}
	}
	
	public void carregarJogo(String arquivo) {
		BufferedReader inStream = null;
		try {
			inStream = new BufferedReader(new FileReader(arquivo));
			
			// Ler  e carregar evento esperado da primeira linha
			String evento = inStream.readLine();
			setEventoEsperado(Evento.valueOf(evento));
			
			// Ler o resto
			String linha;
			StringBuilder string = new StringBuilder("");
			while((linha = inStream.readLine()) != null) {
				string.append(linha + "\n");
			}
			
			// Carregar a banca
			Banca b = Banca.valueOfFirstSymbol(string, null);
			Banca.setBanca(b);
			updateBancaCriada();
			
			// Assertiva
			if (string.charAt(0) != '\n') {
				throw new RuntimeException("Inesperado!!!");
			}
			
			// Retirar o \n
			string.replace(0, 1, "");
			
			// Carregar jogsPartida
			jogsPartida = JogadoresPartida.valueOfFirstSymbol(string, null);
			// Updates pra interface
			updateJogadoresRemovidos();
			updateJogadoresCriados();
			updateJogAtual();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			if (inStream != null) {
				try {
					inStream.close();
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	@Override
	public int getJogAtual() {
		Jogador jogAtual = jogsPartida.getJogAtual();
		if (jogAtual == null) {
			return 0;
		}
		return jogAtual.getNumJogador();
	}
	
	@Override
	public List<Integer> getNumsJogadores() {
		List<Integer> lstNums = new ArrayList<Integer>();
		for (Jogador jog: jogsPartida.getJogadores()) {
			lstNums.add(jog.getNumJogador());
		}
		return lstNums;
	}
	
	@Override
	public Evento getEventoEsperado() {
		return eventoEsperado;
	}
}
