package blackjack.fachada_para_interface.jogo_observado;

import blackjack.engine.Jogador;

public interface JogadorObservado extends ParticipanteObservado {
	public int getNumJogador();
	public Jogador.Status getStatus();
	public int getAposta();
}
