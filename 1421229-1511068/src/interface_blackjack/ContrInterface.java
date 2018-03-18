package interface_blackjack;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import blackjack.fachada_para_interface.FacadeParaInterface;
import blackjack.fachada_para_interface.interface_observadora.ObservadorControlador;
import blackjack.fachada_para_interface.jogo_observado.ControladorObservado;
import blackjack.fachada_para_interface.jogo_observado.ControladorObservado.Evento;
import interface_blackjack.acao_listener.ApostarListener;
import interface_blackjack.acao_listener.ComprarCreditoListener;
import interface_blackjack.acao_listener.EncerrarPartidaListener;
import interface_blackjack.acao_listener.FecharMenuListener;
import interface_blackjack.acao_listener.FichaListener;
import interface_blackjack.acao_listener.LoadListener;
import interface_blackjack.acao_listener.SaiuListener;
import interface_blackjack.acao_listener.SalvarListener;
import interface_blackjack.paineis.Painel;
import interface_blackjack.paineis.PainelBanca;
import interface_blackjack.paineis.PainelJogador;

public class ContrInterface implements ObservadorControlador {
   
    private static ContrInterface contrInterface = new ContrInterface();
   
    private PainelBanca bancaPainel = null;
    private JFrame menuFrame = null;
    private JFrame bancaFrame = null;
    private PainelJogador[] paineisJogs = new PainelJogador[]{null, null, null, null};
    private int scrWidth;
    private int scrHeight;
    private float proporcaoJog = 2.f;
   
    private ContrInterface() {
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension d = tk.getScreenSize();
        scrWidth = d.width;
        scrHeight = d.height;
    }
   
    public static ContrInterface getContrInterface(){
        return contrInterface;
    }
    
    public void criarInterface() {
    	FacadeParaInterface.getFacade().registrarObservadorParaControlador(this);
    }
    
    public JFrame getMenuFrame() {
    	return menuFrame;
    }
    
    public JFrame getBancaFrame() {
    	return bancaFrame;
    }
    
    public PainelJogador[] getPaineisJogs() {
    	return paineisJogs;
    }
    
    @Override
    public void notificarMenuCriado(ControladorObservado cO) {
        Imagem i = new Imagem("Imagens\\blackjackBKG.png");
        menuFrame = new JFrame("Menu Inicial");
        JPanel p = new Painel(i);
        JButton b;
        
        p.setBackground(Color.WHITE);
        menuFrame.getContentPane().add(p);
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        int bckWidth = i.getImagem().getWidth(null);
        int bckHeight = i.getImagem().getHeight(null);
        int posX = scrWidth/2 - bckWidth/2;
        int posY = scrHeight/2 - bckHeight/2;
        menuFrame.setBounds(posX, posY, bckWidth, bckHeight);
        menuFrame.setResizable(false);
        
        // Só para escrever menu
        JLabel blackjack = new JLabel("<html>Blackjack</html>");
        blackjack.setBounds(260, 100, 600, 100);
        Font fonte = new Font("fonte", Font.BOLD, 80);
        blackjack.setForeground(Color.WHITE);
        blackjack.setFont(fonte);
        p.add(blackjack);
        
        // Só para escrever menu
        JLabel tituloMenu = new JLabel("<html><center>Escolha a quantidade de jogadores:</center></html>");
        tituloMenu.setBounds(20, 240, 900, 300);
        Font fonte1 = new Font("fonte1", Font.PLAIN, 60);
        tituloMenu.setForeground(Color.WHITE);
        tituloMenu.setFont(fonte1);
        p.add(tituloMenu);
        
        // Melhores nomes
        JLabel nome = new JLabel("<html>by Marcelo Costalonga e Rafael Gato</html>");
        nome.setBounds(20, 20, 800, 40);
        Font fonte2 = new Font("fonte2", Font.ITALIC, 30);
        nome.setForeground(Color.WHITE);
        nome.setFont(fonte2);
        p.add(nome);
        
        for (int j = 0; j < 4; j++) {
                //Cria e adiciona 4 JButtons ao painel p
            b = new JButton(String.valueOf(j + 1));
            b.setActionCommand(String.valueOf(j + 1));
            b.addActionListener(new FecharMenuListener());
            	// Handler para fechar janela menu
            b.setBounds((bckWidth/2)+(j-2)*80, bckHeight-100, 50, 40);
            p.add(b);
        }
        
        menuFrame.repaint();
        menuFrame.setVisible(true);
        FacadeParaJogo.getFacade().registrarListenerParaNumJogs(null);
    }
    
    public void fecharMenu() {
    	if (menuFrame != null) {
    		menuFrame.dispose();
    	}
    }
    
    @Override
    public void notificarBancaCriada(ControladorObservado cO) {
    	// Se já havia banca, trocá-la por uma nova
        if (bancaFrame != null) {
        	FacadeParaInterface.getFacade().removerObservadorParaBanca(bancaPainel);
        	bancaFrame.dispose();
        }
        
        List<Imagem> li = new ArrayList<Imagem>();
        Imagem i = new Imagem("Imagens\\blackjackBKG.png");

        li.add(i);
        int bckWidth = i.getImagem().getWidth(null);
        int bckHeight = i.getImagem().getHeight(null);
        int posX = scrWidth/2 - bckWidth/2;
        int posY = scrHeight/2 - bckHeight/2;

        int pos = 238;
        Imagem i2 = new Imagem("Imagens\\ficha 1$.png",pos,600);
        li.add(i2);
        Imagem i3 = new Imagem("Imagens\\ficha 5$.png",pos+75,600);
        li.add(i3);
        Imagem i4 = new Imagem("Imagens\\ficha 10$.png",pos+150,600);
        li.add(i4);
        Imagem i5 = new Imagem("Imagens\\ficha 20$.png",pos+225,600);
        li.add(i5);
        Imagem i6 = new Imagem("Imagens\\ficha 50$.png",pos+300,600);
        li.add(i6);
        Imagem i7 = new Imagem("Imagens\\ficha 100$.png",pos+375,600);
        li.add(i7);
        Imagem i8 = new Imagem("Imagens\\comprar.png",680,500);
        li.add(i8);
        String str1 = "Imag";
        bancaFrame = new JFrame("Banca");
        bancaPainel = new PainelBanca(li);
        JButton bEncerrar = new JButton("Encerrar Partida");
        bancaPainel.setBackground(Color.WHITE);
        bancaFrame.getContentPane().add(bancaPainel);
        bancaPainel.add(bEncerrar);
        bEncerrar.setBounds(20, 70, 160, 58);
        bEncerrar.addActionListener(new EncerrarPartidaListener());
        String str2 = "ens\\C";
        int bckWidthFicha = i2.getImagem().getWidth(null);
        int bckHeightFicha = i2.getImagem().getHeight(null);
        ApostarListener aL = new ApostarListener(bancaPainel);
        bancaPainel.addMouseListener(new FichaListener(aL, bckWidthFicha,bckHeightFicha,pos,600));
        int bckWidthCompra = i8.getImagem().getWidth(null);
        int bckHeightCompra = i8.getImagem().getHeight(null);
        bancaPainel.addMouseListener(new ComprarCreditoListener(aL,bckWidthCompra,bckHeightCompra,680,500));

        JButton bApostar = new JButton("Apostar");
        JButton bZerar = new JButton("Zerar");
        String str3 = "J.png";
        bApostar.setActionCommand("a");
        bZerar.setActionCommand("z");
        bApostar.addActionListener(aL);
        bZerar.addActionListener(aL);
        bApostar.setBounds(60, 600, 120, 58);
        bZerar.setBounds(710, 600, 120, 58);
        String strSpc = str1 + str2 + str3;
        bancaPainel.add(bApostar);
        bancaPainel.add(bZerar);

        JButton bSalvar = new JButton("Salvar Jogo");
        bSalvar.setBounds(680, 70, 160, 58);
        bancaPainel.add(bSalvar);
        bSalvar.addActionListener(new SalvarListener());

        JButton bReiniciar = new JButton("Reiniciar Rodada");
        bReiniciar.setBounds(20, 140, 160, 58);

        JButton bLoad = new JButton("Load Game");
        bLoad.setBounds(680, 140, 160, 58);
        bancaPainel.add(bLoad);
        bLoad.addActionListener(new LoadListener());

        Imagem cj = new Imagem(strSpc, 1500, 600);
        bancaPainel.add(bReiniciar);

        bancaFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        bancaFrame.setBounds(posX, posY, bckWidth, bckHeight);

        bancaFrame.setVisible(true);
        li.add(cj);
        FacadeParaInterface.getFacade().registrarObservadorParaBanca(bancaPainel);
    }
    
    private PainelJogador criarJanelaJogador(String s, int numJog) {
        Imagem i = new Imagem("Imagens\\blackjack.png");
        JFrame f = new JFrame(s);
        PainelJogador p = new PainelJogador(i);
        JButton b  = new JButton("Pedir");
        JButton b2 = new JButton("Parar");
        JButton b3 = new JButton("Dobrar");
        JButton b4 = new JButton("Seguro");
        JButton b5 = new JButton("Arregar");
        int buttonLarg = 80;
        int buttonAlt = 40;
        p.setBackground(Color.WHITE);
        f.getContentPane().add(p);
        p.add(b);
        p.add(b2);
        p.add(b3);
        p.add(b4);
        p.add(b5);
        b.setActionCommand(String.valueOf(numJog));
        b.setBounds(10, 10, buttonLarg, buttonAlt);
        
        f.setResizable(false);
        
        b2.setActionCommand(String.valueOf(numJog));
        b2.setBounds(10, 60, buttonLarg, buttonAlt);
        
        b3.setActionCommand(String.valueOf(numJog));
        b3.setBounds(115, 10, buttonLarg, buttonAlt);
        
        b4.setActionCommand(String.valueOf(numJog));
        b4.setBounds(115, 60, buttonLarg, buttonAlt);
        
        b5.setActionCommand(String.valueOf(numJog));
        b5.setBounds(330, 10, buttonLarg, buttonAlt);
        
        int bckWidth = i.getImagem().getWidth(null);
        int bckHeight = i.getImagem().getHeight(null);
        int posXBanca = scrWidth/2 - bckWidth/2;
        int posYBanca = scrHeight/2 - bckHeight/2;
        int fWidth = (int) (bckWidth / proporcaoJog);
        int fHeight = (int) (bckHeight / proporcaoJog);
        int posX = 0, posY = 0;
       
        if (numJog == 1 || numJog == 2) {
            posY = posYBanca;
        } else if (numJog == 3 || numJog == 4){
            posY =  posYBanca + (int) ((proporcaoJog - 1.)*fHeight);
        }
        if (numJog == 1 || numJog == 3) {
            posX = posXBanca - fWidth;
        } else if (numJog == 2 || numJog == 4) {
            posX = posXBanca + (int) (proporcaoJog*fWidth);
        }
        f.setBounds(posX, posY, fWidth, fHeight);
        f.addWindowListener(new SaiuListener(numJog));
        f.setVisible(true);
        return p;
    }
    
    @Override
    public void notificarJogadoresCriados(ControladorObservado cO) {
    	List<Integer> lstNums = cO.getNumsJogadores();
    	for (int i: lstNums) {
    		// Se janela já existia, retirá-la
    		if (paineisJogs[i-1] != null) {
    			JFrame frameJog = (JFrame) paineisJogs[i-1].getTopLevelAncestor();
    			frameJog.dispose();
    		}
    		// Criar nova janela de jogador
    		PainelJogador p = criarJanelaJogador("Jogador "+i, i);
    		paineisJogs[i-1] = p;
    		FacadeParaInterface.getFacade().registrarObservadorParaJogador(i, p);    		
    	}
    }
    
    @Override
    public void notificarJogadoresRemovidos(ControladorObservado cO) {
    	List<Integer> lstNums = cO.getNumsJogadores();
    	for (int i = 1; i <= 4; i++) {
    		if (paineisJogs[i-1] != null) {
    				// Para cada janela de jogador existente
    			
    			if (!lstNums.contains(i)) {
    					// Se ele não está na lista de jogadores do jogo
    				// Fechar a janela
    				JFrame frameJog = (JFrame) paineisJogs[i-1].getTopLevelAncestor();
    				frameJog.dispose();
    				paineisJogs[i-1] = null;
    			}    			
    		}
    	}
    }
    
    @Override
	public void notificarJogAtual(ControladorObservado cO) {
    	if (bancaPainel != null) {
    		bancaPainel.setJogAtual(cO.getJogAtual());
    		bancaPainel.repaint();
    	}
	}

	@Override
	public void notificarEventoEsperado(ControladorObservado cO) {
		ControladorObservado.Evento evento = cO.getEventoEsperado();
		if (bancaPainel != null) {
			if (evento == Evento.APOSTA_JOGADOR) {
				bancaPainel.setStringEvento("Rodada de apostas");
			} else if (evento == Evento.ACAO_JOGADOR) {
				bancaPainel.setStringEvento("Escolha uma ação");
			} else if (evento == Evento.REINICIO) { 
				bancaPainel.setStringEvento("Término da rodada");
			} else {
				bancaPainel.setStringEvento("");
			}
		}
	}
}