package interface_blackjack.paineis;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.List;

import interface_blackjack.Imagem;

@SuppressWarnings("serial")
public class PainelBanca extends PainelParticipante {
	private int valorAposta = 0;
	public PainelBanca() {}
	public PainelBanca(List<Imagem> lstImgs) {
		super(lstImgs);
	}
	public PainelBanca(Imagem i) {
		super(i);
	}
	
	private int jogAtual = 0;
	String strEvento = "";
	
	public void setJogAtual(int numJogador) {
		jogAtual = numJogador;
	}
	
	public void setStringEvento(String str) {
		strEvento = str;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D) g;
		
		int qtdCartas = lstImgsCartas.size();
		for (int i = 0; i < qtdCartas; i++) {
			Image im = lstImgsCartas.get(i);
			// Da posição 160 à 625
			int posX = 465/(qtdCartas+1);
			g.drawImage(im,160+posX+posX*i,330,null);
		}
		
		if (!lstImgsCartas.isEmpty()) {
			if (pontos > 21) {
				g2d.setColor(new Color(255, 100, 100));
			} else if (pontos == 21) {
				g2d.setColor(new Color(100, 255, 100));
			} else {
				g2d.setColor(Color.WHITE);
			}
			Font fonte = new Font("fonte1", Font.BOLD, 50);
			g2d.setFont(fonte);
			g2d.drawString(String.valueOf(pontos), 400, 260);
		}
		
		if (jogAtual != 0) {
			g2d.setColor(Color.WHITE);
			Font fonte = new Font("fonte1", Font.BOLD, 50);
			g2d.setFont(fonte);
			g2d.drawString("Vez do jogador " + jogAtual, 250, 570);
		}
		
		{
			g2d.setColor(Color.WHITE);
			Font fonte = new Font("fonte1", Font.BOLD, 50);
			g2d.setFont(fonte);
			g2d.drawString(strEvento, 220, 50);
		}
		
		{
            g2d.setColor(Color.WHITE);
            Font fonte = new Font("fonte1", Font.BOLD, 50);
            g2d.setFont(fonte);
            g2d.drawString("$"+valorAposta, 40, 570);
        }
	}
	
	public void setValAposta(int valor) {
        valorAposta = valor;
        repaint();
    }
}