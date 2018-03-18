package interface_blackjack.paineis;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import interface_blackjack.Imagem;

@SuppressWarnings("serial")
public class Painel extends JPanel {

    private List<Imagem> listaImagens;

    public Painel() {
    	super(null);
    	listaImagens =  new ArrayList<Imagem>();
    }

    public Painel(List<Imagem> li) {
        super(null);
        this.listaImagens = li;
    }

    public Painel(Imagem i) {
    	this();
        listaImagens.add(i);
    }

    public void paintComponent (Graphics g) {
        super.paintComponent(g);
        for(int j = 0; j < listaImagens.size(); j++) {
            Imagem i = listaImagens.get(j); 
            g.drawImage(i.getImagem(), i.getPosX(), i.getPosY(), null);
                //carrega imagem recebida pelo vetor
        }
    }

    public List<Imagem> getListaImagens() {
        return listaImagens;
    }
}