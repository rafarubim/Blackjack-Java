package interface_blackjack.acao_listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EncerrarPartidaListener implements ActionListener {
    public void actionPerformed(ActionEvent ae) {
        System.exit(0);
    }   
}