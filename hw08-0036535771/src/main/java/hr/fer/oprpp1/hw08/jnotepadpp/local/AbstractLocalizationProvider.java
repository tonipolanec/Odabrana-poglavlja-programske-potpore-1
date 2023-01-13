package hr.fer.oprpp1.hw08.jnotepadpp.local;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract Localization provider which implements interface ILocalizationProvider
 * 
 * @author Toni Polanec
 */
public abstract class AbstractLocalizationProvider implements ILocalizationProvider {

	/**
	 * List of currently registered listeners.
	 */
	private List<ILocalizationListener> listeners;
	
	public AbstractLocalizationProvider() {
		listeners = new ArrayList<>();
	}
	

	/*
	 * Registers new Localization listener.
	 */
	@Override
	public void addLocalizationListener(ILocalizationListener l) {
		listeners.add(l);
	}

	/**
	 * Removes registered Localization listener.
	 */
	@Override
	public void removeLocalizationListener(ILocalizationListener l) {
		listeners.remove(l);
	}
	
	/**
	 * For every listener fire localizationChanged method.
	 */
	public void fire() {
		listeners.forEach(l -> l.localizationChanged());
	}



}
