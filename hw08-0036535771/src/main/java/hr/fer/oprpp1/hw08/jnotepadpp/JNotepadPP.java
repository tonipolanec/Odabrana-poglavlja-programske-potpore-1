package hr.fer.oprpp1.hw08.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.nio.file.Path;
import java.text.Collator;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.Timer;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.PlainDocument;

import hr.fer.oprpp1.hw08.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizableAction;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizableLabel;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.model.DefaultMultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.model.interfaces.MultipleDocumentListener;
import hr.fer.oprpp1.hw08.jnotepadpp.model.interfaces.SingleDocumentListener;
import hr.fer.oprpp1.hw08.jnotepadpp.model.interfaces.SingleDocumentModel;

public class JNotepadPP extends JFrame{
	private static final long serialVersionUID = -3738166483318524943L;

	private DefaultMultipleDocumentModel model;
	
	private LocalizableLabel lengthLabel,
							 caretInfoLabel;
	private JLabel clockLabel;
	private Timer clock;
	
	private FormLocalizationProvider flp;
	
	private LocalizableAction 
		fileMenu, 
			createBlankDocument, openDocument, saveDocument, saveAsDocument, closeDocument, 
		languagesMenu, 
			toEnglish, toCroatian, toGerman,
		toolsMenu, 
			changeCaseSubmenu, 
				toUppercase, toLowercase, invertCase,
			sortSubmenu,
				ascending, descending, unique;
	
	private boolean haveSelectedText;
	
	
	public JNotepadPP() {
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setupClosingWindowAdapter();
		
        setLocation(20, 20);
        setSize(700, 400);
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
		flp = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);
		
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		cp.add(model.getVisualComponent(), BorderLayout.CENTER);
		
		
		setupActions();
		
		setupMenuBar(cp);
		setupStatusBar(cp);
		
		
	}
	
	private void setupClosingWindowAdapter() {
        WindowAdapter wa = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exit();
            }
        };
        addWindowListener((wa));
    }
	
	
	/**
	 * Function for closing application.
	 */
	private void exit() {
		
		this.clock.stop();
        this.dispose();
	}


	/**
	 * Sets up listeners for change in document.
	 */
	private void setupMultipleDocumentModelListeners() {
		
		
		MultipleDocumentListener mdl = new MultipleDocumentListener() {
			
			@Override
			public void documentRemoved(SingleDocumentModel model) {}
			
			@Override
			public void documentAdded(SingleDocumentModel model) {
				 model.getTextComponent().getCaret().addChangeListener(e -> {
	                    updateCaretInfoLabel();
	                    updateLengthLabel();
	             });
			}
			
			@Override
			public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
				
				if(currentModel != null) {
					updateCaretInfoLabel();
					updateLengthLabel();
				}
				
			}
		};
		
		model.addMultipleDocumentListener(mdl);
		
	}
		

	private void setupMenuBar(Container cp) {

		JToolBar toolbar = new JToolBar();
		toolbar.setFloatable(true);
		JMenuBar menubar = new JMenuBar();
		
		
		JMenu file = new JMenu(fileMenu);
		file.add(new JMenuItem(createBlankDocument));
		file.add(new JMenuItem(openDocument));
		file.add(new JMenuItem(saveDocument));
		file.add(new JMenuItem(saveAsDocument));
		file.add(new JMenuItem(closeDocument));
		menubar.add(file);

		JMenu languages = new JMenu(languagesMenu);
		languages.add(new JMenuItem(toEnglish));
		languages.add(new JMenuItem(toCroatian));
		languages.add(new JMenuItem(toGerman));
		menubar.add(languages);
		
		JMenu tools = new JMenu(toolsMenu);
		JMenu changeCase = new JMenu(changeCaseSubmenu);
		changeCase.add(new JMenuItem(invertCase));
		changeCase.add(new JMenuItem(toLowercase));
		changeCase.add(new JMenuItem(toUppercase));
		JMenu sort = new JMenu(sortSubmenu);
		sort.add(new JMenuItem(ascending));
		sort.add(new JMenuItem(descending));
		
		tools.add(sort);
		tools.add(changeCase);
		tools.add(new JMenuItem(unique));
		
		menubar.add(tools);
		
		toolbar.add(menubar);
		
		cp.add(toolbar, BorderLayout.PAGE_START);
	}
	
	
	
	/**
	 * Sets up and adds all components of a status bar.
	 */
	private void setupStatusBar(Container cp) {
		JToolBar statusBar = new JToolBar();
		statusBar.setLayout(new BorderLayout());
		statusBar.setFloatable(false);
		
		JPanel docInfoPanel = new JPanel(new GridLayout(1,2));
		lengthLabel = new LocalizableLabel("length", flp);
		caretInfoLabel = new LocalizableLabel("caretInfo", flp);;
		docInfoPanel.add(lengthLabel);
		docInfoPanel.add(caretInfoLabel);
		
		haveSelectedText = false;
		updateLengthLabel();
		updateCaretInfoLabel();

		clockLabel = new JLabel();
		updateClock();
		
		statusBar.add(clockLabel, BorderLayout.LINE_END);
		statusBar.add(docInfoPanel, BorderLayout.LINE_START);
		cp.add(statusBar, BorderLayout.PAGE_END);
	}
	
	/** 
	 * Sets up all needed actions.
	 */
	private void setupActions() {
		

		//						FILE MENU		
		//fileMenu, createBlankDocument, openDocument, saveDocument, saveAsDocument, 
		
		fileMenu = new LocalizableAction("fileMenu", flp) {
			private static final long serialVersionUID = 1L;
			@Override
			public void actionPerformed(ActionEvent e) {				
			}
		};
		fileMenu.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_F);
		
		
		createBlankDocument = new LocalizableAction("createBlank", flp) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				model.createNewDocument();
			}
		};
		createBlankDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control N"));
		createBlankDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);
		
		openDocument = new LocalizableAction("open", flp) {
			private static final long serialVersionUID = 1L;
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.showOpenDialog(JNotepadPP.this);
				File file = fileChooser.getSelectedFile();
				if(file != null)
					model.loadDocument(file.toPath());
			}
		}; 
		openDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
		openDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
		
		saveDocument = new LocalizableAction("save", flp) {
			private static final long serialVersionUID = 1L;
			@Override
			public void actionPerformed(ActionEvent e) {
				save(false);
			}
		}; 
		saveDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
		saveDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		
		saveAsDocument = new LocalizableAction("saveAs", flp) {
			private static final long serialVersionUID = 1L;
			@Override
			public void actionPerformed(ActionEvent e) {
				save(true);
			}
		}; 
		saveAsDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control shift S"));
		saveAsDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);
		
		closeDocument = new LocalizableAction("close", flp) {
			private static final long serialVersionUID = 1L;
			@Override
			public void actionPerformed(ActionEvent e) {
				model.closeDocument(model.getCurrentDocument());
			}
		}; 
		closeDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control shift X"));
		closeDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
		
		

		//						LANGUAGES MENU
		//  toEnglish, toCroatian, toDeutsch
		
		languagesMenu = new LocalizableAction("languages", flp) {
			private static final long serialVersionUID = 1L;
			@Override
			public void actionPerformed(ActionEvent e) {				
			}
		};
		languagesMenu.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_L);

		
		toEnglish = new LocalizableAction("english", flp) {
			private static final long serialVersionUID = 1L;
			@Override
			public void actionPerformed(ActionEvent e) {
				LocalizationProvider.getInstance().setLanguage("en");
			}
		};
		toEnglish.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("alt E"));
		toEnglish.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_E);
		
		toCroatian = new LocalizableAction("croatian", flp) {
			private static final long serialVersionUID = 1L;
			@Override
			public void actionPerformed(ActionEvent e) {
				LocalizationProvider.getInstance().setLanguage("hr");
			}
		};
		toCroatian.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("alt C"));
		toCroatian.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
		
		toGerman = new LocalizableAction("german", flp) {
			private static final long serialVersionUID = 1L;
			@Override
			public void actionPerformed(ActionEvent e) {
				LocalizationProvider.getInstance().setLanguage("de");
			}
		};
		toGerman.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("alt G"));
		toGerman.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_G);
		
		
		
		//                     TOOLS MENU
		// toolsMenu, changeCaseSubmenu, toUppercase, toLowercase, invertCase;
		
		toolsMenu = new LocalizableAction("tools", flp) {
			private static final long serialVersionUID = 1L;
			@Override
			public void actionPerformed(ActionEvent e) {				
			}
		};
		toolsMenu.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_T);
		
		changeCaseSubmenu = new LocalizableAction("changeCase", flp) {
			private static final long serialVersionUID = 1L;
			@Override
			public void actionPerformed(ActionEvent e) {				
			}
		};
		changeCaseSubmenu.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
		
		invertCase = new LocalizableAction("invertCase", flp) {
			private static final long serialVersionUID = 1L;
			@Override
			public void actionPerformed(ActionEvent e) {
				JTextArea editor = model.getCurrentDocument().getTextComponent();
				Document doc = editor.getDocument();
				
				int len = Math.abs(editor.getCaret().getDot()-editor.getCaret().getMark());
				int offset = 0;
				if(len!=0) 
					offset = Math.min(editor.getCaret().getDot(),editor.getCaret().getMark());
				else 
					len = doc.getLength();
				
				try {
					String text = doc.getText(offset, len);
					text = changeCase(text, "invert");
					doc.remove(offset, len);
					doc.insertString(offset, text, null);
				} catch(BadLocationException ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		};
		invertCase.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control I"));
		invertCase.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_I);
		
		toLowercase = new LocalizableAction("toLowercase", flp) {
			private static final long serialVersionUID = 1L;
			@Override
			public void actionPerformed(ActionEvent e) {
				JTextArea editor = model.getCurrentDocument().getTextComponent();
				Document doc = editor.getDocument();
				
				int len = Math.abs(editor.getCaret().getDot()-editor.getCaret().getMark());
				int offset = 0;
				if(len!=0) 
					offset = Math.min(editor.getCaret().getDot(),editor.getCaret().getMark());
				else
					len = doc.getLength();
				
				try {
					String text = doc.getText(offset, len);
					text = changeCase(text, "lower");
					doc.remove(offset, len);
					doc.insertString(offset, text, null);
				} catch(BadLocationException ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		};
		toLowercase.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control L"));
		toLowercase.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_L);
		
		toUppercase = new LocalizableAction("toUppercase", flp) {
			private static final long serialVersionUID = 1L;
			@Override
			public void actionPerformed(ActionEvent e) {
				JTextArea editor = model.getCurrentDocument().getTextComponent();
				Document doc = editor.getDocument();
				
				int len = Math.abs(editor.getCaret().getDot()-editor.getCaret().getMark());
				int offset = 0;
				if(len!=0) 
					offset = Math.min(editor.getCaret().getDot(),editor.getCaret().getMark());
				else 
					len = doc.getLength();
				
				try {
					String text = doc.getText(offset, len);
					text = changeCase(text, "upper");
					doc.remove(offset, len);
					doc.insertString(offset, text, null);
				} catch(BadLocationException ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		};
		toUppercase.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control U"));
		toUppercase.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_U);
		
		// sortSubmenu, ascending, descending, unique
	
		sortSubmenu = new LocalizableAction("sortSubmenu", flp) {
			private static final long serialVersionUID = 1L;
			@Override
			public void actionPerformed(ActionEvent e) {				
			}
		};
		sortSubmenu.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		
		ascending = new LocalizableAction("ascending", flp) {
			private static final long serialVersionUID = 1L;
			@Override
			public void actionPerformed(ActionEvent e) {	
				sortLines(true);
			}	
		};
		ascending.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control A"));
		ascending.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);
		
		descending = new LocalizableAction("descending", flp) {
			private static final long serialVersionUID = 1L;
			@Override
			public void actionPerformed(ActionEvent e) {
				sortLines(false);
			}
		};
		descending.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control D"));
		descending.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_D);
		
		unique = new LocalizableAction("unique", flp) {
			private static final long serialVersionUID = 1L;
			@Override
			public void actionPerformed(ActionEvent e) {	
				getUniqueLines();
			}
		};
		unique.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control shift U"));
		unique.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_U);
		
	}
	
	
	
	/*
	 * For caret in JTextArea finds its position and puts it in caretInfoLabel.
	 */
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

            column = caretpos - editArea.getLineStartOffset(row) +1;
            row++;
        }
        catch(Exception ex) { 
        	System.out.println("err");
        }
        
        Caret c = editArea.getCaret();
        if(c.getDot() != c.getMark()) haveSelectedText = true;
        else haveSelectedText = false;
        String selectedInfo = haveSelectedText ? ""+Math.abs(c.getDot() - c.getMark()) : "-";
       
        changeCaseSubmenu.setEnabled(haveSelectedText);
        sortSubmenu.setEnabled(haveSelectedText);
        unique.setEnabled(haveSelectedText);
		
		// Ln : #  Col : $  Sel : %
		String info = flp.getString("caretInfo");
		info = info.replace("#", ""+row);
		info = info.replace("$", ""+column);
		info = info.replace("%", selectedInfo);
		
		//String.format("Ln : %d  Col : %d  Sel : %d", row, column+1, 0);
		caretInfoLabel.setText(info);
	}

	/**
	 * Finds length of currently opened document and puts its length in length label in status bar.
	 */
	private void updateLengthLabel() {
		SingleDocumentModel currModel = model.getCurrentDocument();
		
		String info = flp.getString("length");
		if(currModel == null) {
			info += "-";
			return;
		}
		info += currModel.getTextComponent().getText().length();
		
		lengthLabel.setText(info);		
	}
	
	/**
	 * Updates clock every 500ms (0.5sec).
	 */
	private void updateClock() {
		   DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
		   
		   clock = new javax.swing.Timer(500, e -> clockLabel.setText(dtf.format(LocalDateTime.now())));
		   clock.start();
	}
	
	
	/**
	 * Saves current document to disk.
	 * 
	 * @param saveAs if we want to save to new path or not
	 */
	private void save(boolean saveAs) {
		SingleDocumentModel currentDocument = model.getCurrentDocument();
		
		if(!saveAs && currentDocument.getFilePath() != null) {
			model.saveDocument(currentDocument, null);
			return;
		}
		
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.showOpenDialog(JNotepadPP.this);
		File file = fileChooser.getSelectedFile();
		if(file == null)
			return;
		
		Path newPath = file.toPath();
		
		try {
            model.saveDocument(currentDocument, newPath);
            currentDocument.setFilePath(newPath);
        } catch (NullPointerException e1) {
            return;
        }
		
	}
	
	
	
	/**
	 * Changes case for given string and returns it.
	 * 
	 * @param text we want to change case
	 * @param wantedCase [upper, lower, *], if * then inverts case
	 */
	private String changeCase(String text, String wantedCase) {
		
		char[] symbols = text.toCharArray();
		
		for(int i = 0; i < symbols.length; i++) {
			char c = symbols[i];
			
			switch (wantedCase){
				case "upper":
					symbols[i] = Character.toUpperCase(c);
					break;
				case "lower":
					symbols[i] = Character.toLowerCase(c);
					break;
				default:
					if(Character.isLowerCase(c)) 
						symbols[i] = Character.toUpperCase(c);
					else if(Character.isUpperCase(c))
						symbols[i] = Character.toLowerCase(c);	
			}		
		}
		return new String(symbols);
	}

	/**
	 * Sorts highlighted lines.
	 * @param ascending if {@code true} sorts ascending, otherwise descending
	 */
	private void sortLines(boolean ascending) {
		Collator collator = Collator.getInstance(new Locale(LocalizationProvider.getInstance().getLanguage()));
		Comparator<String> comparator = new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return collator.compare(o1, o2);
			}
		};
		if (!ascending) comparator = comparator.reversed();
		
		JTextArea editor = model.getCurrentDocument().getTextComponent();
		PlainDocument doc = (PlainDocument) editor.getDocument();
		Element root = doc.getDefaultRootElement();
		
		int startSelect = editor.getSelectionStart();
		int endSelect = editor.getSelectionEnd();
		
		int start = root.getElement(root.getElementIndex(startSelect)).getStartOffset();
		int end = root.getElement(root.getElementIndex(endSelect)).getEndOffset();
		end = Math.min(end, doc.getLength());
		
		int len = end - start;

		try {
			String text = doc.getText(start, len);
			String[] lines = text.split("\\r?\\n");
			Arrays.sort(lines, comparator);
			
			text = String.join("\n", lines) + '\n';
			
			doc.remove(start, len);
			doc.insertString(start, text, null);
			
		} catch(BadLocationException ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}

	}
	
	/**
	 * From a selection of lines, remove all duplicates.
	 */
	private void getUniqueLines() {
		JTextArea editor = model.getCurrentDocument().getTextComponent();
		PlainDocument doc = (PlainDocument) editor.getDocument();
		Element root = doc.getDefaultRootElement();
		
		int startSelect = editor.getSelectionStart();
		int endSelect = editor.getSelectionEnd();
		
		int start = root.getElement(root.getElementIndex(startSelect)).getStartOffset();
		int end = root.getElement(root.getElementIndex(endSelect)).getEndOffset();
		end = Math.min(end, doc.getLength());
		
		int len = end - start;

		try {
			String text = doc.getText(start, len);
			String[] lines = text.split("\\r?\\n");
			
			Set<String> uniqueLines = new LinkedHashSet<>(Arrays.asList(lines));
			
			text = "";
			for(String l : uniqueLines)
				text += l + '\n';
			
			doc.remove(start, len);
			doc.insertString(start, text, null);
			
		} catch(BadLocationException ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	

	
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new JNotepadPP().setVisible(true));
	}

	
}
