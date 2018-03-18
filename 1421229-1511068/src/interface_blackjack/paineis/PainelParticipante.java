package interface_blackjack.paineis;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import blackjack.engine.Carta;
import blackjack.engine.CartaBlackjack;
import blackjack.fachada_para_interface.interface_observadora.ObservadorParticipante;
import blackjack.fachada_para_interface.jogo_observado.ParticipanteObservado;
import interface_blackjack.Imagem;
import interface_blackjack.LoadCartas;

@SuppressWarnings("serial")
public abstract class PainelParticipante extends Painel implements ObservadorParticipante {
	List<Image> lstImgsCartas = new ArrayList<Image>();
	int pontos = 0;
	int credito = 0;
	
	public PainelParticipante() {}
	public PainelParticipante(List<Imagem> lstImgs) {
		super(lstImgs);
	}
	public PainelParticipante(Imagem i) {
		super(i);
	}
	
	@Override
	public void notificarMao(ParticipanteObservado pO) {
		List<CartaBlackjack> lstCartasB = pO.getMao();
		lstImgsCartas.clear();
		for (int i = 0; i < lstCartasB.size(); i++) {
			CartaBlackjack cB = lstCartasB.get(i);
			Carta chave = null;
			if (cB.getVisibilidade() == true) {
				chave = cB.getCarta();
			}
			Image image = LoadCartas.getLoadCartas().getImagem(chave);
			lstImgsCartas.add(image);
		}
		pontos = pO.getPontos();
		repaint();
	}
	
	@Override
	public void notificarCredito(ParticipanteObservado pO) {
		credito = pO.getCredito();
		repaint();
	}
}
