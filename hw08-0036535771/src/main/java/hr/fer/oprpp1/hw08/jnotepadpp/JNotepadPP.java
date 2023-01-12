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
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.Caret;

import hr.fer.oprpp1.hw08.jnotepadpp.model.DefaultMultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.model.interfaces.MultipleDocumentListener;
import hr.fer.oprpp1.hw08.jnotepadpp.model.interfaces.SingleDocumentListener;
import hr.fer.oprpp1.hw08.jnotepadpp.model.interfaces.SingleDocumentModel;

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
        setupMultipleDocumentModelListeners();
        
        File file = new File("D:\\FER\\5. semestar\\Odabrana poglavlje razvojne programske potpore 1\\Odabrana-poglavlja-programske-potpore-1\\hw08-0036535771\\src\\main\\resources\\hr\\fer\\oprpp1\\hw08\\jnotepadpp\\files\\demo.txt");
		model.loadDocument(file.toPath());
	}

	/**
	 * Initializes all GUI components.
	 */
	private void initGUI() {
		
		model = new DefaultMultipleDocumentModel();
		
		
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		cp.add(model.getVisualComponent(), BorderLayout.CENTER);
		
		setupToolBar(cp);
		setupStatusBar(cp);
		
		
	}
	
	/**
	 * Sets up listeners for change in document.
	 */
	private void setupMultipleDocumentModelListeners() {
		
		SingleDocumentListener currentSingleDocListener = new SingleDocumentListener() {	
			@Override
			public void documentModifyStatusUpdated(SingleDocumentModel model) {
				
			}
			@Override
			public void documentFilePathUpdated(SingleDocumentModel model) {
				// TODO Auto-generated method stub
				
			}
		}; 
		
		MultipleDocumentListener mdl = new MultipleDocumentListener() {
			
			@Override
			public void documentRemoved(SingleDocumentModel model) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void documentAdded(SingleDocumentModel model) {
				 model.getTextComponent().getCaret().addChangeListener(e -> {
//	                    Caret caret = model.getTextComponent().getCaret();
//	                    setDocumentModificationActionEnablement(caret.getDot() != caret.getMark());
					 	System.out.println("caret changed");
	                    updateCaretInfoLabel();
	                    updateLengthLabel();
	             });
				
			}
			
			@Override
			public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
//				if(previousModel != null)
//					previousModel.removeSingleDocumentListener(currentSingleDocListener);
//				if(currentModel != null)
//					currentModel.addSingleDocumentListener(currentSingleDocListener);
				
				if(currentModel != null) {
					System.out.println("update when changed");
					updateCaretInfoLabel();
					updateLengthLabel();
				}
				
			}
		};
		
		model.addMultipleDocumentListener(mdl);
		
	}

	private void setupToolBar(Container cp) {
		JToolBar toolbar = new JToolBar();
		//Action a = new Action();
		
		cp.add(toolbar, BorderLayout.PAGE_START);
	}
	
	private void setupStatusBar(Container cp) {
		JToolBar statusBar = new JToolBar();
		statusBar.setLayout(new GridLayout(1,2));
		statusBar.setFloatable(false);
		
		lengthLabel = new JLabel();
		caretInfoLabel = new JLabel();
		
		statusBar.add(lengthLabel);
		statusBar.add(caretInfoLabel);
		updateLengthLabel();
		updateCaretInfoLabel();
		
		cp.add(statusBar, BorderLayout.PAGE_END);
	}
	

	private void updateCaretInfoLabel() {
		SingleDocumentModel currModel = model.getCurrentDocument();
	
		if(currModel == null) {
			lengthLabel.setText("-");
			return;
		}
		
		JTextArea editArea = currModel.getTextComponent();

        int row = 1;
        int column = 1;
        try {
            int caretpos = editArea.getCaretPosition();
            row = editArea.getLineOfOffset(caretpos);

            column = caretpos - editArea.getLineStartOffset(row);
            row++;
        }
        catch(Exception ex) { 
        	System.out.println("err");
        }
		
		
		String info = String.format("Ln : %d  Col : %d  Sel : %d", row, column, 0);
		System.out.println(info);
		lengthLabel.setText(info);
	}

	private void updateLengthLabel() {
		SingleDocumentModel currModel = model.getCurrentDocument();
		
		String info = "length : ";
		info += currModel != null ? currModel.getTextComponent().toString().length() : "-";
		
		lengthLabel.setText(info);		
	}

	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new JNotepadPP().setVisible(true));
	}

}
