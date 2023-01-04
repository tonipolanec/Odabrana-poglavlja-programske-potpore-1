package hr.fer.zemris.java.gui.layouts;

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
		setSize(500, 200);
		initGUI();
	}

	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new CalcLayout(5));
		this.add(new JLabel("x"), new RCPosition(1,1));
		this.add(new JLabel("y"), new RCPosition(2,3));
		this.add(new JLabel("z"), new RCPosition(2,7));
		this.add(new JLabel("w"), new RCPosition(4,2));
		this.add(new JLabel("a"), new RCPosition(4,5));
		this.add(new JLabel("b"), new RCPosition(4,7));
		
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
