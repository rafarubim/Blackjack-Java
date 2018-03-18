package interface_blackjack.acao_listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class LoadListener implements ActionListener {
    private ActionListener carregar = null;
    private final String defaultExtension = "gdboi";
    
    public void setLoadListener(ActionListener aL) {
    	carregar = aL;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
    	
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Arquivos ."+defaultExtension, defaultExtension);
        chooser.setFileFilter(filter);
        chooser.setCurrentDirectory(new File(System.getProperty("user.dir") + "/MemoryCard"));
        chooser.setSelectedFile(new File("save."+defaultExtension));
        int returnVal = chooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
        	String filePath = chooser.getSelectedFile().getPath();
        	if (filePath.endsWith("."+defaultExtension)) {
        		if (carregar != null) {
        			carregar.actionPerformed(new ActionEvent(this, 1, filePath));
        		}
        	}
        	else {
        		JOptionPane.showMessageDialog(null, "Este não é um arquivo ."+defaultExtension+" válido!");
        	}
        }
    }
}