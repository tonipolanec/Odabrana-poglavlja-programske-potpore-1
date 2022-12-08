package hr.fer.oprpp1.hw05.shell;

import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.oprpp1.hw05.shell.commands.CharsetsShellCommand;
import hr.fer.oprpp1.hw05.shell.commands.ExitShellCommand;
import hr.fer.oprpp1.hw05.shell.commands.HexdumpShellCommand;
import hr.fer.oprpp1.hw05.shell.commands.LsShellCommand;
import hr.fer.oprpp1.hw05.shell.commands.MkdirShellCommand;
import hr.fer.oprpp1.hw05.shell.commands.SymbolShellCommand;
import hr.fer.oprpp1.hw05.shell.commands.TreeShellCommand;

public class MyShell implements Environment{
	
	private char PROMPTSYMBOL;
	private char MORELINESYMBOL;
	private char MULTILINESYMBOL;
	
	SortedMap<String, ShellCommand> commands;
	
	Scanner scanner;

	public MyShell() {
		scanner = new Scanner(System.in);
		
		PROMPTSYMBOL = '>';
		MORELINESYMBOL = '\\';
		MULTILINESYMBOL = '|';
		
		commands = commands();
		
		writeln("Welcome to MyShell v 1.0");
		
	}

	
	
	public static void main(String[] args) {
		MyShell shell = new MyShell();
		
		ShellStatus status = ShellStatus.CONTINUE;
		String line;
		
		do {
			
			line = shell.readLine();
			
			String commandName = shell.getCommandFromLine(line);
			String arguments = shell.getArgumentsFromLine(line);

			SortedMap<String, ShellCommand> commands = shell.commands();
			ShellCommand command = commands.get(commandName);
			
			status = command.executeCommand(shell, arguments);
			
		} while(status != ShellStatus.TERMINATE);
		

	}
	
	

	@Override
	public String readLine() throws ShellIOException {
		
		try {
			write("" + PROMPTSYMBOL + " ");
			String line = scanner.nextLine();
			
			return line;
		} catch (Exception e) {
			e.printStackTrace();
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
		SortedMap<String, ShellCommand> commands = new TreeMap<>();
		
		commands.put("charsets", new CharsetsShellCommand());
		commands.put("exit", new ExitShellCommand());
		commands.put("symbol", new SymbolShellCommand());
		commands.put("tree", new TreeShellCommand());
		commands.put("hexdump", new HexdumpShellCommand());
		commands.put("ls", new LsShellCommand());
		commands.put("mkdir", new MkdirShellCommand());
		
		return commands;
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
	
	private String getCommandFromLine(String line) {
		
		int index = line.indexOf(' ');
		if (index > -1) 
			return line.substring(0, index).trim();
		else 
			return line; 
	}
	
	private String getArgumentsFromLine(String line) {
		
		int index = line.indexOf(' ');
		if (index > -1) 
			return line.substring(index, line.length()).trim();
		else 
			return "";
	}
	
	
	
	
	
	
	

}
