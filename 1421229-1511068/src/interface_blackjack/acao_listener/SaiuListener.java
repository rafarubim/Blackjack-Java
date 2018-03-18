package interface_blackjack.acao_listener;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

import blackjack.fachada_para_interface.FacadeParaInterface;
import interface_blackjack.paineis.PainelJogador;

public class SaiuListener extends WindowAdapter {
	private int numJog;
	private ActionListener saiuListener = null;
	
	public SaiuListener(int numJog) {
		this.numJog = numJog;
	}
	
	public void setSairListener(ActionListener aL) {
		saiuListener = aL;
	}
	
	@Override
	public void windowClosing(WindowEvent wE) {
		JFrame frameJog = (JFrame) wE.getSource();
		JPanel panel = (JPanel) frameJog.getContentPane();
		Component[] comps = panel.getComponents();
		PainelJogador painelJog = null;
		for (Component comp: comps) {
			if (comp instanceof PainelJogador) {
				painelJog = (PainelJogador) comp;
			}
		}
		if (painelJog == null) {
			throw new RuntimeException("Painel Jogador não encontrado");
		}
		FacadeParaInterface.getFacade().removerObservadorParaJogador(numJog, painelJog);   
		if (saiuListener != null) {
			saiuListener.actionPerformed(new ActionEvent(this, 1, String.valueOf(numJog)));
		}
	}
}
