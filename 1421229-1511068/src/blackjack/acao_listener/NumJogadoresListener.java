package blackjack.acao_listener;

import java.awt.event.*;

import blackjack.engine.ControladorJogo;

public class NumJogadoresListener implements ActionListener {
	@Override
    public void actionPerformed(ActionEvent ae) {
        String ac = ae.getActionCommand();
        int numJogadores = Integer.valueOf(ac);
        ControladorJogo.getControladorJogo().eventoNumJogadores(numJogadores);
    }
}