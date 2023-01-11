package hr.fer.oprpp1.hw08.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Container;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchEvent.Modifier;

import javax.swing.JFrame;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.oprpp1.hw08.jnotepadpp.model.DefaultMultipleDocumentModel;

public class JNotepadPP extends JFrame{
	private static final long serialVersionUID = -3738166483318524943L;

	private DefaultMultipleDocumentModel model;
	
	
	public JNotepadPP() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocation(20, 20);
        setSize(700, 700);
        setTitle("JNotepad++");
        
        initGUI();
	}

	private void initGUI() {
		File file = new File("D:\\FER\\5. semestar\\Odabrana poglavlje razvojne programske potpore 1\\Odabrana-poglavlja-programske-potpore-1\\hw08-0036535771\\src\\main\\resources\\hr\\fer\\oprpp1\\hw08\\jnotepadpp\\files\\demo.txt");
		
		model = new DefaultMultipleDocumentModel();
		model.loadDocument(file.toPath());
		
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		cp.add(model.getVisualComponent(), BorderLayout.CENTER);
		
		setupToolBar(cp);
		
	}

	private void setupToolBar(Container cp) {
		JToolBar toolbar = new JToolBar();
		
		cp.add(toolbar, BorderLayout.PAGE_START);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new JNotepadPP().setVisible(true));
	}

}
