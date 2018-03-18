package blackjack.fachada_para_interface.jogo_observado;

import java.util.List;

import blackjack.engine.CartaBlackjack;

public interface ParticipanteObservado {
	public List<CartaBlackjack> getMao();
	public int getPontos();
	public int getCredito();
}
