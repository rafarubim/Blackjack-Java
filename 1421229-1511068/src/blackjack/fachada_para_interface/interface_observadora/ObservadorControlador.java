package blackjack.fachada_para_interface.interface_observadora;

import blackjack.fachada_para_interface.jogo_observado.ControladorObservado;

public interface ObservadorControlador {
	public void notificarMenuCriado(ControladorObservado cO);
	public void notificarBancaCriada(ControladorObservado cO);
	public void notificarJogadoresCriados(ControladorObservado cO);
	public void notificarJogadoresRemovidos(ControladorObservado cO);
	public void notificarJogAtual(ControladorObservado cO);
	public void notificarEventoEsperado(ControladorObservado cO);
}
