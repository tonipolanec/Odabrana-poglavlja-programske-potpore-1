package hr.fer.zemris.java.gui.layouts;

import java.util.Objects;

public class RCPosition {
	
	private final int row;
	private final int column;

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

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	@Override
	public int hashCode() {
		return Objects.hash(column, row);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		
		RCPosition other = (RCPosition) obj;
		return column == other.column && row == other.row;
	}
	
	

}
