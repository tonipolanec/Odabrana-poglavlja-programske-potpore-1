package hr.fer.oprpp1.hw08.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
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

import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.text.Caret;

import hr.fer.oprpp1.hw08.jnotepadpp.model.DefaultMultipleDocumentModel;

public class JNotepadPP extends JFrame{
	private static final long serialVersionUID = -3738166483318524943L;

	private DefaultMultipleDocumentModel model;
	
	private JLabel lengthLabel;
	private JLabel caretInfoLabel;
	
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
		setupStatusBar(cp);
		
	}

	private void setupToolBar(Container cp) {
		JToolBar toolbar = new JToolBar();
		//Action a = new Action();
		
		cp.add(toolbar, BorderLayout.PAGE_START);
	}
	
	private void setupStatusBar(Container cp) {
		JToolBar statusBar = new JToolBar();
		statusBar.setLayout(new GridLayout(1,0));
		statusBar.setFloatable(false);
		
		lengthLabel = new JLabel();
		caretInfoLabel = new JLabel();
		
		updateLengthLabel();
		updateCaretInfoLabel();
		
		statusBar.add(lengthLabel);
		statusBar.add(caretInfoLabel);
		
		cp.add(statusBar, BorderLayout.PAGE_END);
	}
	

	private void updateCaretInfoLabel() {
		Caret caret = model.getCurrentDocument().getTextComponent().getCaret();
	
		int dot = caret.getDot();
		int mark = caret.getMark();
		
		if(dot == mark) {
			
		}
		//String info = String.format("Ln : %d  Col : %d  Sel : %d", caretPos.y, caretPos.y, 0);
		
	}

	private void updateLengthLabel() {
		int length = model.getCurrentDocument().getTextComponent().toString().length();
		lengthLabel.setText("length : " + length);		
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new JNotepadPP().setVisible(true));
	}

}
