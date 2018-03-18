package interface_blackjack.acao_listener;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ComprarCreditoListener extends MouseAdapter {
	private Rectangle compra;
	private ApostarListener aL = null;

	public ComprarCreditoListener(ApostarListener aL, int width, int height, int posX, int posY) {
		super();
		this.compra = new Rectangle();
		this.compra.setBounds(posX, posY, width, height);
		this.aL = aL;
	}

	@Override
	public void mouseReleased(MouseEvent me) {
		int x = me.getX();
		int y = me.getY();

		if (compra.contains(x, y)) {
			aL.actionPerformed(new ActionEvent(this, 1, "c"));
		}
	}
}
