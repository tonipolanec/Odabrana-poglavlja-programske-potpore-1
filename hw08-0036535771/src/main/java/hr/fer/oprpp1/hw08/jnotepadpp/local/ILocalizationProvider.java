package hr.fer.oprpp1.hw08.jnotepadpp.local;

/**
 * Interface that defines methods fo LocalizationProvider
 * 
 * @author Toni Polanec
 */
public interface ILocalizationProvider {
	
	/*
	 * Registers new Localization listener.
	 */
	void addLocalizationListener(ILocalizationListener l);
	
	/**
	 * Removes registered Localization listener.
	 */
	void removeLocalizationListener(ILocalizationListener l);
	
	/**
	 * Returns translated word for given key.
	 */
	String getString(String key);

	/**
	 * Return currently choosen language.
	 */
	String getLanguage();
}
