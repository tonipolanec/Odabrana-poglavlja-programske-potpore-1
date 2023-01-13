package hr.fer.oprpp1.hw08.jnotepadpp.local;

import javax.swing.JLabel;

public class LocalizableLabel extends JLabel {
	private static final long serialVersionUID = 703340873513759541L;

	/**
	 * Translations key.
	 */
	private String key;
	
	/**
	 * Localization provider who gives translations.
	 */
	private ILocalizationProvider localProvider;
	
	
	public LocalizableLabel(String key, ILocalizationProvider lp) {
		this.key = key;
		localProvider = lp;
		
		localProvider.addLocalizationListener(() -> setTranslatedText());
		setTranslatedText();
	}

	public void setTranslatedText() {
		setText(localProvider.getString(key));
	}



}
