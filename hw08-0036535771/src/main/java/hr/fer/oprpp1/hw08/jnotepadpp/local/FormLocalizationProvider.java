package hr.fer.oprpp1.hw08.jnotepadpp.local;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

/**
 * Class that extends LocalizationProviderBridge.
 * Used for separately connecting multiple frames.
 * 
 * @author Toni Polanec
 */
public class FormLocalizationProvider extends LocalizationProviderBridge {

	/**
	 * Creates new FormLocalizationProvider for given frame and connects it as a bridge. 
	 */
	public FormLocalizationProvider(ILocalizationProvider localProvider, JFrame frame) {
		super(localProvider);
		
		frame.addWindowListener(new WindowAdapter() {
			@Override 
			public void windowOpened(WindowEvent e) {
				super.windowOpened(e);
				connect();
			}
			@Override 
			public void windowClosed(WindowEvent e) {
				super.windowOpened(e);
				disconnect();
			}
		});
	}

}
