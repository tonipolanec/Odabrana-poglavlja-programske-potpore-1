package hr.fer.zemris.java.gui.layouts;

public class RCPosition {
	
	public final int row;
	public final int column;

	public RCPosition(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
	public static RCPosition parse(String text) {
		String rowS = text.substring(0, text.indexOf(','));
		String columnS = text.substring(text.indexOf(',')+1);
		
		int row, column;
		try {
			row = Integer.parseInt(rowS);
			column = Integer.parseInt(columnS);
		} catch(NumberFormatException e) {
			throw new CalcLayoutException();
		}
		
		return new RCPosition(row, column);
	}
	
	

}
