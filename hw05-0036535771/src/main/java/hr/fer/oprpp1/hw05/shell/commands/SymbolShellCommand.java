package hr.fer.oprpp1.hw05.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

public class SymbolShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] args = arguments.split(" ");
		
		if (args.length == 1) { // Only one argument
			switch (args[0]) {
				case "PROMPT":
					System.out.println("Symbol for PROMPT is '"+ env.getPromptSymbol() +"'");
					break;
				case "MORELINES":
					System.out.println("Symbol for MORELINES is '"+ env.getMorelinesSymbol() +"'");
					break;
				case "MULTILINE":
					System.out.println("Symbol for MULTILINE is '"+ env.getMultilineSymbol() +"'");
					break;
				default:
					System.out.println("Invalid symbol requested.");
			}
		} else if(args.length == 2) { // 2 arguments
			
			if (args[1].length() > 1) {
				System.out.println("Invalid new symbol given.");
				return ShellStatus.CONTINUE;
			}
			char newSymbol = args[1].charAt(0);
			
			switch (args[0]) {
			case "PROMPT":
				env.setPromptSymbol(newSymbol);
				break;
			case "MORELINES":
				env.setMorelinesSymbol(newSymbol);
				break;
			case "MULTILINE":
				env.setMultilineSymbol(newSymbol);
				break;
			default:
				System.out.println("Invalid type of symbol given.");
			}
			
		} else {
			System.out.println("Too many arguments.");
			return ShellStatus.CONTINUE;
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "symbol";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> descriptions = new ArrayList<>();
		
		descriptions.add("Command symbol:");
		descriptions.add("  Takes one argument or two.");
		descriptions.add("  First argument: [PROMPT, MORELINES, MULTILINE]");
		descriptions.add("  	Gives character used for given type of symbol.");	
		descriptions.add("  Second argument: [any character]");
		descriptions.add("  	If given second argument it changes symbol to the new one.");
				
		return Collections.unmodifiableList(descriptions);
	}

}
