package interface_blackjack.acao_listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import interface_blackjack.paineis.PainelBanca;

public class ApostarListener implements ActionListener {
    private int valorAposta = 0;
    private ActionListener apostaListener = null;
    private ActionListener compraListener = null;
    private PainelBanca painelBanca = null;
   
    public ApostarListener(PainelBanca painel) {
        painelBanca = painel;
    }
    
    public void setApostarListener(ActionListener aL) {
    	apostaListener = aL;
    }
    
    public void setComprarListener(ActionListener aL) {
    	compraListener = aL;
    }
   
    /*
     * ae.getActionCommand() = "a" -> apostar
     * ae.getActionCommand() = "z" -> zerar
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getActionCommand() == "a" && apostaListener != null) {
        	apostaListener.actionPerformed(new ActionEvent(this, 1, String.valueOf(valorAposta)));
        }
        else if (ae.getActionCommand() == "c" && compraListener != null) {
        	compraListener.actionPerformed(new ActionEvent(this, 1, String.valueOf(valorAposta)));
        }
        
        valorAposta = 0;
        painelBanca.setValAposta(valorAposta);
    }
    
    public void addValAposta(int val) {
    	valorAposta += val;
        painelBanca.setValAposta(valorAposta);
    }
}