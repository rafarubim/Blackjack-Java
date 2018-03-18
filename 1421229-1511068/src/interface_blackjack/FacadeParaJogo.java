package interface_blackjack;

import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import interface_blackjack.acao_listener.ApostarListener;
import interface_blackjack.acao_listener.LoadListener;
import interface_blackjack.acao_listener.SaiuListener;
import interface_blackjack.acao_listener.SalvarListener;
import interface_blackjack.paineis.PainelJogador;

public class FacadeParaJogo {
	private static FacadeParaJogo facadeParaJogo = new FacadeParaJogo();
	
	private FacadeParaJogo() {}
	
	public static FacadeParaJogo getFacade() {
		return facadeParaJogo;
	}
	
	private JButton getButton(JPanel painel, String buttonName, boolean recursive) {
		Component[] comps = painel.getComponents();
		for (Component comp: comps) {
			if (comp instanceof JButton) {
				JButton botao = (JButton) comp;
				if (botao.getText().equals(buttonName)) {
					return botao;
				}
			}
			else if (comp instanceof JPanel) {
				JPanel painelComp = (JPanel) comp;
				JButton b = getButton(painelComp, buttonName, true);
				if (b != null) {
					return b;
				}
			}
		}
		return null;
	}
	
	private JButton getButton(JPanel painel, String buttonName) {
		JButton botao = getButton(painel, buttonName, true);
		if (botao == null) {
			throw new RuntimeException("Botão desconhecido");
		}
		return botao;
	}
	
	private JButton getButton(JFrame frame, String buttonName) {
		return getButton((JPanel) frame.getContentPane(), buttonName);
	}
	
	private void registrarListenerParaBotaoJogador(ActionListener aL, String botao) {
		PainelJogador[] paineisJogs = ContrInterface.getContrInterface().getPaineisJogs();
		for (int i = 1; i <= 4; i++) {
			if (paineisJogs[i-1] != null) {
				JButton bot = getButton((JPanel)paineisJogs[i-1], botao);
				bot.addActionListener(aL);
			}
		}
	}
	
	private void removerListenerParaBotaoJogador(ActionListener aL, String botao) {
		PainelJogador[] paineisJogs = ContrInterface.getContrInterface().getPaineisJogs();
		for (int i = 1; i <= 4; i++) {
			if (paineisJogs[i-1] != null) {
				JButton bot = getButton((JPanel)paineisJogs[i-1], botao);
				bot.removeActionListener(aL);
			}
		}
	}
	
	private void registrarListenerParaBotaoBanca(ActionListener aL, String botao) {
		JFrame bancaFrame = ContrInterface.getContrInterface().getBancaFrame();
		JButton bot = getButton(bancaFrame, botao);
		bot.addActionListener(aL);
	}
	
	private void removerListenerParaBotaoBanca(ActionListener aL, String botao) {
		JFrame bancaFrame = ContrInterface.getContrInterface().getBancaFrame();
		JButton bot = getButton(bancaFrame, botao);
		bot.removeActionListener(aL);
	}
	
	public void registrarListenerParaNumJogs(ActionListener aL) {
		JFrame menuFrame = ContrInterface.getContrInterface().getMenuFrame();
		JButton botao1 = getButton(menuFrame, "1");
		JButton botao2 = getButton(menuFrame, "2");
		JButton botao3 = getButton(menuFrame, "3");
		JButton botao4 = getButton(menuFrame, "4");
		botao1.addActionListener(aL);
		botao2.addActionListener(aL);
		botao3.addActionListener(aL);
		botao4.addActionListener(aL);
	}
	
	public void removerListenerParaNumJogs(ActionListener aL) {
		JFrame menuFrame = ContrInterface.getContrInterface().getMenuFrame();
		JButton botao1 = getButton(menuFrame, "1");
		JButton botao2 = getButton(menuFrame, "2");
		JButton botao3 = getButton(menuFrame, "3");
		JButton botao4 = getButton(menuFrame, "4");
		botao1.removeActionListener(aL);
		botao2.removeActionListener(aL);
		botao3.removeActionListener(aL);
		botao4.removeActionListener(aL);
	}
	
	public void registrarListenerParaSalvarJogo(ActionListener aL) {
		JFrame bancaFrame = ContrInterface.getContrInterface().getBancaFrame();
		JButton bot = getButton(bancaFrame, "Salvar Jogo");
		ActionListener[] aLsBotao = bot.getActionListeners();
		if (aLsBotao.length != 1) {
			throw new RuntimeException("Inconsistencia");
		}
		SalvarListener salvL = (SalvarListener) aLsBotao[0];
		salvL.setSalvarListener(aL);
	}
	
	public void removerListenerParaSalvarJogo(ActionListener aL) {
		JFrame bancaFrame = ContrInterface.getContrInterface().getBancaFrame();
		JButton bot = getButton(bancaFrame, "Salvar Jogo");
		ActionListener[] aLsBotao = bot.getActionListeners();
		if (aLsBotao.length != 1) {
			throw new RuntimeException("Inconsistencia");
		}
		SalvarListener salvL = (SalvarListener) aLsBotao[0];
		salvL.setSalvarListener(null);
	}
	
	public void registrarListenerParaCarregarJogo(ActionListener aL) {
		JFrame bancaFrame = ContrInterface.getContrInterface().getBancaFrame();
		JButton bot = getButton(bancaFrame, "Load Game");
		ActionListener[] aLsBotao = bot.getActionListeners();
		if (aLsBotao.length != 1) {
			throw new RuntimeException("Inconsistencia");
		}
		LoadListener loadL = (LoadListener) aLsBotao[0];
		loadL.setLoadListener(aL);
	}
	
	public void removerListenerParaCarregarJogo(ActionListener aL) {
		JFrame bancaFrame = ContrInterface.getContrInterface().getBancaFrame();
		JButton bot = getButton(bancaFrame, "Load Game");
		ActionListener[] aLsBotao = bot.getActionListeners();
		if (aLsBotao.length != 1) {
			throw new RuntimeException("Inconsistencia");
		}
		LoadListener loadL = (LoadListener) aLsBotao[0];
		loadL.setLoadListener(null);
	}
	
	public void registrarListenerParaReiniciarRodada(ActionListener aL) {
		registrarListenerParaBotaoBanca(aL, "Reiniciar Rodada");
	}
	
	public void removerListenerParaReiniciarRodada(ActionListener aL) {
		removerListenerParaBotaoBanca(aL, "Reiniciar Rodada");
	}
	
	public void registrarListenerParaApostar(ActionListener aL) {
		JFrame bancaFrame = ContrInterface.getContrInterface().getBancaFrame();
		JButton bot = getButton(bancaFrame, "Apostar");
		ActionListener[] aLsBotao = bot.getActionListeners();
		if (aLsBotao.length != 1) {
			throw new RuntimeException("Inconsistencia");
		}
		ApostarListener apostL = (ApostarListener) aLsBotao[0];
		apostL.setApostarListener(aL);
	}
	
	public void removerListenerParaApostar(ActionListener aL) {
		JFrame bancaFrame = ContrInterface.getContrInterface().getBancaFrame();
		JButton bot = getButton(bancaFrame, "Apostar");
		ActionListener[] aLsBotao = bot.getActionListeners();
		if (aLsBotao.length != 1) {
			throw new RuntimeException("Inconsistencia");
		}
		ApostarListener apostL = (ApostarListener) aLsBotao[0];
		apostL.setApostarListener(null);
	}
	
	public void registrarListenerParaComprar(ActionListener aL) {
		JFrame bancaFrame = ContrInterface.getContrInterface().getBancaFrame();
		JButton bot = getButton(bancaFrame, "Apostar");
		ActionListener[] aLsBotao = bot.getActionListeners();
		if (aLsBotao.length != 1) {
			throw new RuntimeException("Inconsistencia");
		}
		ApostarListener apostL = (ApostarListener) aLsBotao[0];
		apostL.setComprarListener(aL);
	}
	
	public void removerListenerParaComprar(ActionListener aL) {
		JFrame bancaFrame = ContrInterface.getContrInterface().getBancaFrame();
		JButton bot = getButton(bancaFrame, "Apostar");
		ActionListener[] aLsBotao = bot.getActionListeners();
		if (aLsBotao.length != 1) {
			throw new RuntimeException("Inconsistencia");
		}
		ApostarListener apostL = (ApostarListener) aLsBotao[0];
		apostL.setComprarListener(null);
	}
	
	public void registrarListenerParaPedir(ActionListener aL) {
		registrarListenerParaBotaoJogador(aL, "Pedir");
	}
	public void removerListenerParaPedir(ActionListener aL) {
		removerListenerParaBotaoJogador(aL, "Pedir");
	}
	
	public void registrarListenerParaParar(ActionListener aL) {
		registrarListenerParaBotaoJogador(aL, "Parar");
	}
	public void removerListenerParaParar(ActionListener aL) {
		removerListenerParaBotaoJogador(aL, "Parar");
	}
	
	public void registrarListenerParaDobrar(ActionListener aL) {
		registrarListenerParaBotaoJogador(aL, "Dobrar");
	}
	public void removerListenerParaDobrar(ActionListener aL) {
		removerListenerParaBotaoJogador(aL, "Dobrar");
	}
	
	public void registrarListenerParaSeguro(ActionListener aL) {
		registrarListenerParaBotaoJogador(aL, "Seguro");
	}
	public void removerListenerParaSeguro(ActionListener aL) {
		removerListenerParaBotaoJogador(aL, "Seguro");
	}
	
	public void registrarListenerParaRender(ActionListener aL) {
		registrarListenerParaBotaoJogador(aL, "Arregar");
	}
	public void removerListenerParaRender(ActionListener aL) {
		removerListenerParaBotaoJogador(aL, "Arregar");
	}
	
	public void registrarListenerParaSair(ActionListener aL) {
		PainelJogador[] paineisJogs = ContrInterface.getContrInterface().getPaineisJogs();
		for (int i = 1; i <= 4; i++) {
			if (paineisJogs[i-1] != null) {
				JFrame frameJog = (JFrame) paineisJogs[i-1].getTopLevelAncestor();
				WindowListener[] winLs = frameJog.getWindowListeners();
				if (winLs.length != 1) {
					throw new RuntimeException("Inconsistência");
				}
				SaiuListener sL = (SaiuListener) winLs[0];
				sL.setSairListener(aL);
			}
		}
	}
	public void removerListenerParaSair(ActionListener aL) {
		PainelJogador[] paineisJogs = ContrInterface.getContrInterface().getPaineisJogs();
		for (int i = 1; i <= 4; i++) {
			if (paineisJogs[i-1] != null) {
				JFrame frameJog = (JFrame) paineisJogs[i-1].getTopLevelAncestor();
				WindowListener[] winLs = frameJog.getWindowListeners();
				if (winLs.length != 1) {
					throw new RuntimeException("Inconsistência");
				}
				SaiuListener sL = (SaiuListener) winLs[0];
				sL.setSairListener(null);
			}
		}
	}
}
