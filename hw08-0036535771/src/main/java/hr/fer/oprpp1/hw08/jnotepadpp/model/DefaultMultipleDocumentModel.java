package hr.fer.oprpp1.hw08.jnotepadpp.model;

import java.awt.Image;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;

import hr.fer.oprpp1.hw08.jnotepadpp.model.interfaces.MultipleDocumentListener;
import hr.fer.oprpp1.hw08.jnotepadpp.model.interfaces.MultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.model.interfaces.SingleDocumentListener;
import hr.fer.oprpp1.hw08.jnotepadpp.model.interfaces.SingleDocumentModel;

public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel {
	private static final long serialVersionUID = -2609991042835234007L;
	
	private List<SingleDocumentModel> documents;
	private SingleDocumentModel currentDocument;
	private List<MultipleDocumentListener> listeners;
	
	private static final String NOT_MODIFIED_ICON_PATH = "../../icons/modifiedGreen.png";
	private static final String MODIFIED_ICON_PATH = "../../icons/modifiedRed.png";
	private final ImageIcon notModifiedIcon;
	private final ImageIcon modifiedIcon;

	public DefaultMultipleDocumentModel() {
		super();
		
		documents = new ArrayList<>();
		currentDocument = null;
		listeners = new ArrayList<>();
		
		notModifiedIcon = getIcon(NOT_MODIFIED_ICON_PATH);
		modifiedIcon = getIcon(MODIFIED_ICON_PATH);
	
		addChangeListener(e -> {
            SingleDocumentModel previousDocument = currentDocument;

            int newTabIndex = getSelectedIndex();
            this.currentDocument = newTabIndex != -1 ? documents.get(newTabIndex) : null;

            this.notifyRegisteredListeners(listener -> listener.currentDocumentChanged(previousDocument, currentDocument));
        });
			
	}



	@Override
	public Iterator<SingleDocumentModel> iterator() {
		return documents.iterator();
	}

	@Override
	public JComponent getVisualComponent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SingleDocumentModel createNewDocument() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SingleDocumentModel getCurrentDocument() {
		return currentDocument;
	}

	@Override
	public SingleDocumentModel loadDocument(Path path) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveDocument(SingleDocumentModel model, Path newPath) {
		if (model == null) throw new NullPointerException("Given model cannot be null!");
		
		if (newPath == null)
			newPath = model.getFilePath();
		
		else {
			newPath = newPath.toAbsolutePath().normalize();
			for(SingleDocumentModel document : documents) {
				if(newPath.equals(document.getFilePath())) {
					JOptionPane.showMessageDialog(null, "Document with this file path is currently opened!", "Warning", JOptionPane.WARNING_MESSAGE);
					return;
				}
			}
		}
		
		try {
			if(newPath == null) throw new NullPointerException("Document path cannot be null!");
            Files.writeString(newPath, model.getTextComponent().getText());
        } catch (IOException e) {
        	JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			return;
        }
		
		notifyRegisteredListeners(l -> l.currentDocumentChanged(model, currentDocument));
		model.setFilePath(newPath);
		model.setModified(false);
		setIconAt(getSelectedIndex(), notModifiedIcon);
	}

	@Override
	public void closeDocument(SingleDocumentModel model) {
		int indexOfClosingDoc = documents.indexOf(model);
		
		documents.remove(model);		
		notifyRegisteredListeners(l -> l.documentRemoved(model));
		
		removeTabAt(indexOfClosingDoc);
		
		if(documents.isEmpty()) 
			currentDocument = null;
		else 
			currentDocument = documents.get(0);
		notifyRegisteredListeners(l -> l.currentDocumentChanged(model, currentDocument));
	}

	@Override
	public void addMultipleDocumentListener(MultipleDocumentListener l) {
		if (l == null) throw new NullPointerException("Given listener cannot be null!");
		listeners.add(l);
	}

	@Override
	public void removeMultipleDocumentListener(MultipleDocumentListener l) {
		listeners.remove(l);
	}

	@Override
	public int getNumberOfDocuments() {
		return documents.size();
	}

	@Override
	public SingleDocumentModel getDocument(int index) {
		return documents.get(index);
	}

	@Override
	public SingleDocumentModel findForPath(Path path) {
		for(SingleDocumentModel document : documents) {
			if(document.getFilePath().equals(path.toAbsolutePath().normalize()))
				return document;
		}
		return null;
	}

	@Override
	public int getIndexOfDocument(SingleDocumentModel doc) {
		return documents.indexOf(doc);
	}
	
	private ImageIcon getIcon(String iconPath) {
		if(iconPath == null) throw new NullPointerException("Icon path cannot be null!");
		
		InputStream is = this.getClass().getResourceAsStream(iconPath);
		try {
			Image imageFromIcon = new ImageIcon(is.readAllBytes()).getImage();
			return new ImageIcon(imageFromIcon.getScaledInstance(20, 20, Image.SCALE_SMOOTH));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Warning", JOptionPane.WARNING_MESSAGE);
		}
		
		return null;
		
	}
	

	private void notifyRegisteredListeners(Consumer<MultipleDocumentListener> action) {
        this.listeners.forEach(action);
    }
	
	
	
	
	
	
	
}
