package hr.fer.zemris.java.gui.layouts;

import java.awt.Color;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class Demo extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	public Demo() {
		super();
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Demo");
		setLocation(20, 20);
		setSize(500, 500);
		initGUI();
		pack();
	}

	private void initGUI() {
		/*Container cp = getContentPane();
		cp.setLayout(new CalcLayout(5));
		this.add(new JLabel("x"), new RCPosition(1,1));
		this.add(new JLabel("y"), new RCPosition(2,3));
		this.add(new JLabel("z"), new RCPosition(2,7));
		this.add(new JLabel("w"), new RCPosition(4,2));
		this.add(new JLabel("a"), new RCPosition(4,5));
		this.add(new JLabel("b"), new RCPosition(4,7));
		*/
		Container cp = getContentPane();
		cp.setLayout(new CalcLayout(3));
		cp.add(l("tekst 1"), new RCPosition(1,1));
		cp.add(l("tekst 2"), new RCPosition(2,3));
		cp.add(l("tekst stvarno najdulji"), new RCPosition(2,7));
		cp.add(l("tekst kraći"), new RCPosition(4,2));
		cp.add(l("tekst srednji"), new RCPosition(4,5));
		cp.add(l("tekst"), new RCPosition(4,7));

	}
	
	private JLabel l(String text) {
		JLabel l = new JLabel(text);
		l.setBackground(Color.YELLOW);
		l.setOpaque(true);
		return l;
		}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				Demo prozor = new Demo();
				prozor.setVisible(true);
			}
		});

	}

}
