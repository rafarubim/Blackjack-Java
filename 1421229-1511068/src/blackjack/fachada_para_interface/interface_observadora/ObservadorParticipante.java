package blackjack.fachada_para_interface.interface_observadora;

import blackjack.fachada_para_interface.jogo_observado.ParticipanteObservado;

// Um observador Participante pode observar tanto uma banca quanto um jogador
public interface ObservadorParticipante {
	public void notificarMao(ParticipanteObservado pO);
	public void notificarCredito(ParticipanteObservado pO);
}
