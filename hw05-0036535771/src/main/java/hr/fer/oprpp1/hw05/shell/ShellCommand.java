package hr.fer.oprpp1.hw05.shell;

import java.util.List;

public interface ShellCommand {

	/** Executes command in given environment. */
	ShellStatus executeCommand(Environment env, String arguments);
	
	/** Returns command name. */
	String getCommandName();
	
	/** Returns command description. */
	List<String> getCommandDescription();
	
}
