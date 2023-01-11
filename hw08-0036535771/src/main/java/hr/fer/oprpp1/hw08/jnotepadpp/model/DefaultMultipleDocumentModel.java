package hr.fer.oprpp1.hw08.jnotepadpp.model;

import java.awt.Image;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.text.DefaultCaret;

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

            int tabIndex = getSelectedIndex();
            currentDocument = tabIndex != -1 ? documents.get(tabIndex) : null;

            notifyRegisteredListeners(l -> l.currentDocumentChanged(previousDocument, currentDocument));
        });
			
	}



	@Override
	public Iterator<SingleDocumentModel> iterator() {
		return documents.iterator();
	}

	@Override
	public JComponent getVisualComponent() {
		return this;
	}

	@Override
	public SingleDocumentModel createNewDocument() {
		SingleDocumentModel previousModel = currentDocument;
		
		currentDocument = new DefaultSingleDocumentModel(null, "");
		initializeNewDocument(null);
		currentDocument.addSingleDocumentListener(getBasicDocumentListener());
		
		notifyRegisteredListeners(l -> l.currentDocumentChanged(previousModel, currentDocument));
		
		return currentDocument;
	}

	@Override
	public SingleDocumentModel getCurrentDocument() {
		return currentDocument;
	}

	@Override
	public SingleDocumentModel loadDocument(Path path) {
		SingleDocumentModel previousModel = currentDocument;
		
		path = path.toAbsolutePath().normalize();
		
		SingleDocumentModel newModel = null;
		
		for(SingleDocumentModel document : documents) {
			if(path.equals(document.getFilePath())) {
				newModel = document;
			}
		}
		if(newModel != null) currentDocument = newModel;
		else {
			byte[] fileBytes;
			try {
				fileBytes = Files.readAllBytes(path);
			} catch(IOException e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				return null;
			}	
			
			currentDocument = new DefaultSingleDocumentModel(path, new String(fileBytes, StandardCharsets.UTF_8));
			initializeNewDocument(path);		
			
			notifyRegisteredListeners(l -> l.documentAdded(currentDocument));
		}
		
		currentDocument.addSingleDocumentListener(getBasicDocumentListener());
		notifyRegisteredListeners(l -> l.currentDocumentChanged(previousModel, currentDocument));
		
		return currentDocument;		
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
		if(path == null) throw new NullPointerException("Path to document cannot be null!");
		
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
	
	
	/**
	 * Reads image from given path and returns it as an {@code ImageIcon}
	 * @param iconPath path to image
	 * @return icon
	 */
	private ImageIcon getIcon(String iconPath) {
		if(iconPath == null) throw new NullPointerException("Icon path cannot be null!");
		
		InputStream is = this.getClass().getResourceAsStream(iconPath);
		try {
			return new ImageIcon(is.readAllBytes());
			//ImageIcon icon = new Image()// is.readAllBytes()
//			Image imageFromIcon = new ImageIcon(is.readAllBytes()).getImage();
//			return new ImageIcon(imageFromIcon.getScaledInstance(20, 20, Image.SCALE_SMOOTH));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Warning", JOptionPane.WARNING_MESSAGE);
		}
		
		return null;	
	}
	

	/**
	 * Notifies all registered listeners on given action.
	 */
	private void notifyRegisteredListeners(Consumer<MultipleDocumentListener> action) {
        this.listeners.forEach(action);
    }
	
	/**
	 * Creates and return basic DocumentListener.
	 * Listens for modifying and set proper icon.
	 * If file path changes, changes tooltip and title of a tab.
	 * 
	 * @return instance of {@code SingleDocumentListener}
	 */
	private SingleDocumentListener getBasicDocumentListener() {
		return new SingleDocumentListener() {
			@Override
			public void documentModifyStatusUpdated(SingleDocumentModel model) {
				if(model.isModified()) setIconAt(getSelectedIndex(), modifiedIcon);
				else setIconAt(getSelectedIndex(), notModifiedIcon);
			}
			@Override
			public void documentFilePathUpdated(SingleDocumentModel model) {
				setToolTipTextAt(getSelectedIndex(), model.getFilePath().toString());
				setTitleAt(getSelectedIndex(), model.getFilePath().getFileName().toString());
			}
		};
	}
	
	/**
	 * Sets up SingleDocumentModel and adds it like a tab to MultipleDocumentModel
	 */
	private void initializeNewDocument(Path path) {
		boolean noPath = path == null;
		
		documents.add(currentDocument);
		int tabInd = documents.indexOf(currentDocument);
		
		addTab(noPath ? "(unnamed)" : path.getFileName().toString(), new JPanel().add(new JScrollPane(currentDocument.getTextComponent())));
		setToolTipTextAt(tabInd, noPath ? "(unnamed)" : path.toString());
		setIconAt(tabInd, notModifiedIcon);
		setSelectedIndex(tabInd);
		

	}
	
	
	
	
	
	
}
