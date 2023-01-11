package hr.fer.oprpp1.hw08.jnotepadpp;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import hr.fer.oprpp1.hw08.jnotepadpp.model.DefaultMultipleDocumentModel;

public class JNotepadPP extends JFrame{
	private static final long serialVersionUID = -3738166483318524943L;

	private DefaultMultipleDocumentModel multipleDocumentModel;
	
	
	public JNotepadPP() {
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setLocation(20, 20);
        setSize(700, 700);
        setTitle("JNotepad++");
        
        initGUI();
	}

	private void initGUI() {
		// TODO Auto-generated method stub
		
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new JNotepadPP().setVisible(true));
	}

}
