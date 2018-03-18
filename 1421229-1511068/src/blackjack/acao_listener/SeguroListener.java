package blackjack.acao_listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import blackjack.engine.ControladorJogo;

public class SeguroListener implements ActionListener {
	@Override
	public void actionPerformed (ActionEvent ae) {
		String s = ae.getActionCommand();
        int indiceJog = Integer.parseInt(s);
        ControladorJogo.getControladorJogo().eventoAcaoJogador(indiceJog, ControladorJogo.AcaoJogador.SEGURO);
	}

}
