package hr.fer.zemris.java.gui.prim;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class PrimDemo extends JFrame{
	
	PrimListModel model;

	public PrimDemo() {
		model = new PrimListModel();
		
		setLocation(300, 100);
		setSize(250, 200);
		setTitle("Lists");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		initGUI();
	}

	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		JButton button = new JButton("Sljedeći");
		button.addActionListener(e -> {
			model.next();
		});
		cp.add(button, BorderLayout.PAGE_END);
	
		JPanel panelLists = new JPanel(new GridLayout(1,0));
		JList<Integer> first = new JList<>(model);
		JList<Integer> second = new JList<>(model);
		
		panelLists.add(new JScrollPane(first));
		panelLists.add(new JScrollPane(second));
		
		cp.add(panelLists, BorderLayout.CENTER);
		
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			JFrame frame = new PrimDemo();
			frame.setVisible(true);
		});

	}

}
