package blackjack.fachada_para_interface;

import blackjack.engine.Banca;
import blackjack.engine.ControladorJogo;
import blackjack.engine.Jogador;
import blackjack.fachada_para_interface.interface_observadora.ObservadorControlador;
import blackjack.fachada_para_interface.interface_observadora.ObservadorJogador;
import blackjack.fachada_para_interface.interface_observadora.ObservadorParticipante;
import blackjack.fachada_para_interface.jogo_observado.ControladorObservado;
import blackjack.fachada_para_interface.jogo_observado.JogadorObservado;
import blackjack.fachada_para_interface.jogo_observado.ParticipanteObservado;

public class FacadeParaInterface {
	private static FacadeParaInterface facadeParaInterface = new FacadeParaInterface();
	
	private FacadeParaInterface() {}
	
	public static FacadeParaInterface getFacade() {
		return facadeParaInterface;
	}
	
	public ControladorObservado registrarObservadorParaControlador(ObservadorControlador oC) {
		ControladorJogo controlador = ControladorJogo.getControladorJogo();
		controlador.addObservadorControlador(oC);
		return controlador;
	}
	
	public void removerObservadorParaControlador(ObservadorControlador oC) {
		ControladorJogo controlador = ControladorJogo.getControladorJogo();
		controlador.rmObservadorControlador(oC);
	}
	
	public ParticipanteObservado registrarObservadorParaBanca(ObservadorParticipante oP) {
		Banca banca = Banca.getBanca();
		banca.addObservadorParticipante(oP);
		return banca;
	}
	
	public void removerObservadorParaBanca(ObservadorParticipante oP) {
		Banca banca = Banca.getBanca();
		banca.rmObservadorParticipante(oP);
	}
	
	public JogadorObservado registrarObservadorParaJogador(int numJogador, ObservadorJogador oJ) {
		Jogador jog = ControladorJogo.getControladorJogo().getJogsPartida().getJogador(numJogador);
		jog.addObservadorJogador(oJ);
		jog.addObservadorParticipante(oJ);
		return jog;
	}
	
	public void removerObservadorParaJogador(int numJogador, ObservadorJogador oJ) {
		Jogador jog = ControladorJogo.getControladorJogo().getJogsPartida().getJogador(numJogador);
		jog.rmObservadorJogador(oJ);
		jog.rmObservadorParticipante(oJ);
	}
}
