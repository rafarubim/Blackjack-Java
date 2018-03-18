package blackjack.fachada_para_interface.interface_observadora;

import blackjack.fachada_para_interface.jogo_observado.JogadorObservado;

public interface ObservadorJogador extends ObservadorParticipante {
	public void notificarStatus(JogadorObservado cO);
}
