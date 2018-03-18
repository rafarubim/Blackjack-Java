import blackjack.engine.ControladorJogo;
import interface_blackjack.ContrInterface;

public class Main {
	
	public static void main(String[] args) {
		ControladorJogo cJogo = ControladorJogo.getControladorJogo();
		ContrInterface cInterface = ContrInterface.getContrInterface();
		cInterface.criarInterface();
		cJogo.interfaceCriada();
		cJogo.iniciarJogo();
	}
}
