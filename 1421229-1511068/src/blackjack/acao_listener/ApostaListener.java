package blackjack.acao_listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import blackjack.engine.ControladorJogo;

public class ApostaListener implements ActionListener {
	@Override
	public void actionPerformed(ActionEvent ae) {
		int valor = Integer.parseInt(ae.getActionCommand());
		ControladorJogo.getControladorJogo().eventoApostaJogador(valor);
	}
}