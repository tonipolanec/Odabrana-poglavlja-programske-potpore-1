package hr.fer.oprpp1.hw08.jnotepadpp.local;

import javax.swing.AbstractAction;
import javax.swing.Action;

public abstract class LocalizableAction extends AbstractAction {
	private static final long serialVersionUID = 703340879513759541L;

	/**
	 * Translations key.
	 */
	private String key;
	
	/**
	 * Localization provider who gives translations.
	 */
	private ILocalizationProvider localProvider;
	
	
	public LocalizableAction(String key, ILocalizationProvider lp) {
		this.key = key;
		localProvider = lp;
		
		localProvider.addLocalizationListener(() -> putValues());
		putValues();
	}
	
	private void putValues() {
		putValue(Action.NAME, localProvider.getString(key));
	}

	



}
