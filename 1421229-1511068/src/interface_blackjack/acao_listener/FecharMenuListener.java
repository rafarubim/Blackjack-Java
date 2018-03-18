package interface_blackjack.acao_listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import interface_blackjack.ContrInterface;

public class FecharMenuListener implements ActionListener {
    public void actionPerformed(ActionEvent ae) {
        ContrInterface.getContrInterface().fecharMenu();
    }
}