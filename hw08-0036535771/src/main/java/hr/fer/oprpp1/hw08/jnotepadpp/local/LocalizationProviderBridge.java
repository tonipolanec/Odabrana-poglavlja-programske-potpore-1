package hr.fer.oprpp1.hw08.jnotepadpp.local;

/**
 * Implementation of AbstractLocalizationProvider.
 * 
 * @author Toni Polanec
 */
public class LocalizationProviderBridge extends AbstractLocalizationProvider {

	private boolean connected;
	
	private ILocalizationListener listener;
	
	private ILocalizationProvider parent;
	
	private String oldParentLanguage;
	
	
	public LocalizationProviderBridge(ILocalizationProvider localProvider) {
		parent = localProvider;
		listener = () -> fire();
		
		oldParentLanguage = parent.getLanguage();
	}

	@Override
	public String getString(String key) {
		return parent.getString(key);
	}

	@Override
	public String getLanguage() {
		return parent.getLanguage();
	}
	
	/**
	 * Connects bridge with parent.
	 */
	public void connect() {
		if(connected) return;
		connected = true;
		parent.addLocalizationListener(listener);
		
		if(!oldParentLanguage.equals(parent.getLanguage())) {
			fire();
			oldParentLanguage = parent.getLanguage();
		}
	}
	
	/**
	 * Disconnects bridge from parent and removes listeners that connected them.
	 */
	public void disconnect() {
		if(!connected) return;
		connected = false;
		parent.removeLocalizationListener(listener);
		
		oldParentLanguage = parent.getLanguage();
		
	}
	
	
	
	

}
