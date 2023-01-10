package hr.fer.oprpp1.hw08.jnotepadpp;

import java.nio.file.Path;

import javax.swing.JTextArea;

public class DefaultSingleDocumentModel implements SingleDocumentModel {

	private JTextArea textArea;
	
	
	public DefaultSingleDocumentModel(Path path, String textContent) {
		textArea = new JTextArea(textContent);
	}

	@Override
	public JTextArea getTextComponent() {
		return textArea;
	}

	@Override
	public Path getFilePath() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setFilePath(Path path) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isModified() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setModified(boolean modified) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addSingleDocumentListener(SingleDocumentListener l) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeSingleDocumentListener(SingleDocumentListener l) {
		// TODO Auto-generated method stub

	}

}
