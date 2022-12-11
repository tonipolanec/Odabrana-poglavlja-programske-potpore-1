package hr.fer.oprpp1.hw05.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

public class HelpShellCommand implements ShellCommand {


	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String args[] = arguments.split(" ");
	
		SortedMap<String, ShellCommand> commands = env.commands();
		
		if (arguments.length() == 0) { // No arguments given
			
			Set<String> names = commands.keySet();
			for(String n : names) 
				env.writeln(n);
			
		}
		else if (args.length == 1 && args[0] != "") { 
			if (commands.containsKey(args[0])) {
			
				List<String> list = commands.get(args[0]).getCommandDescription();	
				for(String l : list) 
					env.writeln(l);
				
			} else {
				env.writeln("Not supported command entered.");
			}
			
		} else {
			env.writeln("Command help takes none or one argument.");
			return ShellStatus.CONTINUE;
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "help";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> descriptions = new ArrayList<>();
		
		descriptions.add("Command help:");
		descriptions.add("  Takes none or one argument: command name");
		descriptions.add("  Writes out all commands or description of given command.");
				
		return Collections.unmodifiableList(descriptions);
	}

}
