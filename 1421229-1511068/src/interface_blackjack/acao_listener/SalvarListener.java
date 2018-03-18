package interface_blackjack.acao_listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class SalvarListener implements ActionListener {
	private ActionListener salvar = null;
	private String defaultExtension = "gdboi";
	
	public void setSalvarListener(ActionListener aL) {
		salvar = aL;
	}
	
	@Override
	public void actionPerformed (ActionEvent ae) {
		
		
        JFileChooser chooser = new JFileChooser();
        
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Somente arquivos ."+defaultExtension, defaultExtension);
        chooser.setFileFilter(filter);
        chooser.setCurrentDirectory(new File(System.getProperty("user.dir")+"\\MemoryCard"));
        chooser.setSelectedFile(new File("save."+defaultExtension));
        int returnVal = chooser.showSaveDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
        	String filePath = chooser.getSelectedFile().getPath();
        	if (!filePath.endsWith("."+defaultExtension)) {
        		filePath += "."+defaultExtension;
        	}
        	if (salvar != null) {
        		salvar.actionPerformed(new ActionEvent(this, 1, filePath));
        	}
        }
	}
}
