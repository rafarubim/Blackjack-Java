package blackjack.acao_listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import blackjack.engine.ControladorJogo;

public class SaiuListener implements ActionListener {	
	@Override
	public void actionPerformed(ActionEvent aE) {
		int numJog = Integer.valueOf(aE.getActionCommand());
		ControladorJogo.getControladorJogo().eventoJogadorSaiu(numJog);
	}
}
