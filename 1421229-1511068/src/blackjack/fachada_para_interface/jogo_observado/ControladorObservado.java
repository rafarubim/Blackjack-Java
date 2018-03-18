package blackjack.fachada_para_interface.jogo_observado;

import java.util.List;

public interface ControladorObservado {
	public enum Evento {
		NENHUM, ESCOLHA_NUM_JOG, APOSTA_JOGADOR, ACAO_JOGADOR, REINICIO;
	}
	public int getJogAtual();
		// Número do jogador atual (ou 0 se não houver)
	public List<Integer> getNumsJogadores();
	public Evento getEventoEsperado();
}
