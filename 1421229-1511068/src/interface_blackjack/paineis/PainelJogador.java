package interface_blackjack.paineis;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.List;

import blackjack.fachada_para_interface.interface_observadora.ObservadorJogador;
import blackjack.fachada_para_interface.jogo_observado.JogadorObservado;
import blackjack.fachada_para_interface.jogo_observado.ParticipanteObservado;
import interface_blackjack.Imagem;

@SuppressWarnings("serial")
public class PainelJogador extends PainelParticipante implements ObservadorJogador {
	public PainelJogador() {}
	public PainelJogador(List<Imagem> lstImgs) {
		super(lstImgs);
	}
	public PainelJogador(Imagem i) {
		super(i);
	}
	
	String status = null;
	Color corStatus = Color.WHITE;
	int aposta = 0;
	
	@Override
	public void paintComponent(Graphics g) {		
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D) g;
		
		int qtdCartas = lstImgsCartas.size();
		for (int i = 0; i < qtdCartas; i++) {
			Image im = lstImgsCartas.get(i);
			// Da posição 20 à 190
			int posX = 170/(qtdCartas+1);
			g.drawImage(im,20+posX+posX*i,180,null);
		}
		
		if (!lstImgsCartas.isEmpty()) {
			if (pontos > 21) {
				g2d.setColor(new Color(255, 100, 100));
			} else if (pontos == 21) {
				g2d.setColor(new Color(100, 255, 100));
			} else  {
				g2d.setColor(Color.WHITE);
			}
			Font fonte = new Font("fonte1", Font.BOLD, 50);
			g2d.setFont(fonte);
			g2d.drawString(String.valueOf(pontos), 10, 160);
		}
		
		if (status != null) {
			g2d.setColor(corStatus);
			Font fonte = new Font("fonte1", Font.BOLD, 50);
			g2d.setFont(fonte);
			g2d.drawString(status, 130, 160);
		}
		
		{
			g2d.setColor(Color.WHITE);
			Font fonte = new Font("fonte1", Font.BOLD, 20);
			g2d.setFont(fonte);
			g2d.drawString("Créditos: $"+credito, 280, 270);
		}
		
		if (aposta != 0) {
			g2d.setColor(Color.WHITE);
			Font fonte = new Font("fonte1", Font.BOLD, 20);
			g2d.setFont(fonte);
			g2d.drawString("Aposta: $"+aposta, 280, 290);
		}
	}
	
	@Override
	public void notificarStatus(JogadorObservado jO) {
		switch(jO.getStatus()) {
		case INDETERMINADO:
			status = null;
			break;
		case EMPATOU:
			status = "Empate!";
			corStatus = Color.WHITE;
			break;
		case PERDEU:
			status = "Perdeu!";
			corStatus = new Color(255, 100, 100);
			break;
		case VENCEU:
			status = "Vitória!";
			corStatus = new Color(100, 255, 100);
			break;
		case FINALIZOU:
			status = "Parou";
			corStatus = Color.WHITE;
			break;
		case ESTOROU:
			status = "Estorou";
			corStatus = new Color(255, 100, 100);
			break;
		case APOSTOU:
			status = "Apostou";
			corStatus = Color.WHITE;
			break;
		case COMPROU:
			break;
		case RENDEU:
			status = "Arregou";
			corStatus = Color.MAGENTA;
			break;
		case PASSOU:
			status = "Passou";
			corStatus = Color.WHITE;
			break;
		default:
			throw new RuntimeException("Inconsistência");
		}
		repaint();
	}
	
	@Override
	public void notificarCredito(ParticipanteObservado pO) {
		super.notificarCredito(pO);
		JogadorObservado jO = (JogadorObservado) pO;
		aposta = jO.getAposta();
		repaint();
	}
}
