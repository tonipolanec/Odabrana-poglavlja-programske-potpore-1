package hr.fer.oprpp1.hw08.jnotepadpp.model;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import hr.fer.oprpp1.hw08.jnotepadpp.model.interfaces.SingleDocumentListener;
import hr.fer.oprpp1.hw08.jnotepadpp.model.interfaces.SingleDocumentModel;

public class DefaultSingleDocumentModel implements SingleDocumentModel {

	private JTextArea textAreaComponent;
	private Path path;
	private boolean modified;
	private List<SingleDocumentListener> listeners;
	private String originalContent;
	
	public DefaultSingleDocumentModel(Path path, String textContent) {
		if(path != null)
			this.path = path.toAbsolutePath().normalize();
		
		if(textContent == null) textContent = "";
		textAreaComponent = new JTextArea(textContent);
		originalContent = textContent;
		
		listeners = new ArrayList<>();
		textAreaComponent.getDocument().addDocumentListener(new DocumentListener() {
			private boolean isModified = !originalContent.equals(textAreaComponent.getText());
	
	        @Override
	        public void insertUpdate(DocumentEvent e) {
                setModified(isModified);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                setModified(isModified);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                setModified(isModified);
            }
        });
		
	}

	@Override
	public JTextArea getTextComponent() {
		return textAreaComponent;
	}

	@Override
	public Path getFilePath() {
		return path;
	}

	@Override
	public void setFilePath(Path path) {
		this.path = path.toAbsolutePath().normalize();
		notifyRegisteredListeners(l -> l.documentFilePathUpdated(this));
	}

	@Override
	public boolean isModified() {
		return modified;
	}

	@Override
	public void setModified(boolean modified) {
		this.modified = modified;
		if (!modified) originalContent = textAreaComponent.getText();
		
		notifyRegisteredListeners(l -> l.documentModifyStatusUpdated(this));
	}

	@Override
	public void addSingleDocumentListener(SingleDocumentListener l) {
		if (l == null) throw new NullPointerException("Given listener cannot be null!");
		listeners.add(l);

	}

	@Override
	public void removeSingleDocumentListener(SingleDocumentListener l) {
		listeners.remove(l);
	}
	
	private void notifyRegisteredListeners(Consumer<SingleDocumentListener> action) {
        this.listeners.forEach(action);
    }

	@Override
	public int hashCode() {
		return Objects.hash(path);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DefaultSingleDocumentModel other = (DefaultSingleDocumentModel) obj;
		return Objects.equals(path, other.path);
	}
	
	

}
