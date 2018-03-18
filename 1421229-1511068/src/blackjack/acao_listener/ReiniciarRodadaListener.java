package blackjack.acao_listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import blackjack.engine.ControladorJogo;

public class ReiniciarRodadaListener implements ActionListener {
	@Override
    public void actionPerformed(ActionEvent ae) {
        ControladorJogo.getControladorJogo().reiniciarRodada();
    }
}
