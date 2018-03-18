package blackjack.acao_listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import blackjack.engine.ControladorJogo;

public class SalvarListener implements ActionListener {
	@Override
	public void actionPerformed(ActionEvent ae) {
		String arquivo = ae.getActionCommand();
		ControladorJogo.getControladorJogo().salvarJogo(arquivo);
	}
}
