package hr.fer.oprpp1.hw08.jnotepadpp.local;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Singleton instance of AbstractLocalizationProvider.
 * 
 * @author Toni Polanec
 */
public class LocalizationProvider extends AbstractLocalizationProvider {

	/** 
	 * Singleton instance of LocalizationProvider.
	 */
	private static final LocalizationProvider localProvider = new LocalizationProvider();
	
	/**
	 * Currently chosen language.
	 */
	private String language;
	
	/**
	 * Currently used resource bundle,
	 */
	private ResourceBundle resourceBundle;
	
	/**
	 * Private constructor for this class.
	 */
	private LocalizationProvider() {
		language = "en";
		resourceBundle = ResourceBundle.getBundle("hr.fer.oprpp1.hw08.jnotepadpp.local.translations", Locale.forLanguageTag(language));
	}
	
	/**
	 * Returns instance of this class.
	 */
	public static LocalizationProvider getInstance() {		
		return localProvider;
	}

	/**
	 * Returns translated word for given key.
	 */
	@Override
	public String getString(String key) {
		return resourceBundle.getString(key);
	}

	/**
	 * Returns currently chosen language.
	 */
	@Override
	public String getLanguage() {
		return language;
	}
	
	/**
	 * Sets new language and fires of every registered listener.
	 */
	public void setLanguage(String language) {
		this.language = language;
		resourceBundle = ResourceBundle.getBundle("hr.fer.oprpp1.hw08.jnotepadpp.local.translations", Locale.forLanguageTag(language));
		
		fire();
	}
	
	
	

}
