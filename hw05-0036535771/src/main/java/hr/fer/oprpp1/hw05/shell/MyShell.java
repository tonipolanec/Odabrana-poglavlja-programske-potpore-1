package hr.fer.oprpp1.hw05.shell;

import java.util.Scanner;
import java.util.SortedMap;

public class MyShell implements Environment{
	
	private char PROMPTSYMBOL;
	private char MORELINESYMBOL;
	private char MULTILINESYMBOL;

	public MyShell() {
		PROMPTSYMBOL = '>';
		MORELINESYMBOL = '\\';
		MULTILINESYMBOL = '|';
		
		
		writeln("Welcome to MyShell v 1.0");
	}

	
	
	public static void main(String[] args) {
		MyShell shell = new MyShell();
		
		ShellStatus status = ShellStatus.CONTINUE;
		
		do {
			
			String line = shell.readLine();
			
			String commandName = ""; // extract from line
			String arguments = ""; // extract from line

			SortedMap<String, ShellCommand> commands = shell.commands();
			ShellCommand command = commands.get(commandName);
			
			status = command.executeCommand(shell, arguments);
			
		} while(status != ShellStatus.TERMINATE);
		

	}
	
	

	@Override
	public String readLine() throws ShellIOException {
		
		try (Scanner scanner = new Scanner(System.in)){
			write("" + PROMPTSYMBOL + " ");
			String line = scanner.nextLine();
			
			return line;
		} catch (Exception e) {
			throw new ShellIOException("Error with reading from console!");
		}
	}

	@Override
	public void write(String text) throws ShellIOException {
		try {
			System.out.print(text);
		} catch (Exception e) {
			throw new ShellIOException("Error with writing on console!");
		}
		
	}

	@Override
	public void writeln(String text) throws ShellIOException {
		try {
			System.out.println(text);
		} catch (Exception e) {
			throw new ShellIOException("Error with writing on console!");
		}
	}

	
	@Override
	public SortedMap<String, ShellCommand> commands() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Character getMultilineSymbol() {
		return MULTILINESYMBOL;
	}

	@Override
	public void setMultilineSymbol(Character symbol) {
		System.out.println("Symbol for MULTILINE changed from '"+ MULTILINESYMBOL +"' to '"+ symbol +"'");
		MULTILINESYMBOL = symbol;
	}

	@Override
	public Character getPromptSymbol() {
		return PROMPTSYMBOL;
	}

	@Override
	public void setPromptSymbol(Character symbol) {
		System.out.println("Symbol for PROMPT changed from '"+ PROMPTSYMBOL +"' to '"+ symbol +"'");
		PROMPTSYMBOL = symbol;
	}

	@Override
	public Character getMorelinesSymbol() {
		return MORELINESYMBOL;
	}

	@Override
	public void setMorelinesSymbol(Character symbol) {
		System.out.println("Symbol for MORELINES changed from '"+ MORELINESYMBOL +"' to '"+ symbol +"'");
		MORELINESYMBOL = symbol;
	}
	

}
