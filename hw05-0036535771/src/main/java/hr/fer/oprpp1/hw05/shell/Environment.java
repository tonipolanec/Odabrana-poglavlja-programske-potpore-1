package hr.fer.oprpp1.hw05.shell;

import java.util.SortedMap;

public interface Environment {
	
	/** Reads line from console. */
	String readLine() throws ShellIOException;
	
	/** Writes given string on console. */
	void write(String text) throws ShellIOException;
	
	/** Writes given string on console and adds newline. */
	void writeln(String text) throws ShellIOException;
	
	SortedMap<String, ShellCommand> commands();
	
	/** Returns MULTILINE symbol. */
	Character getMultilineSymbol();
	
	/** Sets new MULTILINE symbol and logs the change on console. */
	void setMultilineSymbol(Character symbol);
	
	/** Returns PROMPT symbol. */
	Character getPromptSymbol();
	
	/** Sets new PROMPT symbol and logs the change on console. */
	void setPromptSymbol(Character symbol);
	
	/** Returns MORELINES symbol. */
	Character getMorelinesSymbol();
	
	/** Sets new MORELINES symbol and logs the change on console. */
	void setMorelinesSymbol(Character symbol);

}
