package hr.fer.oprpp1.hw05.shell.commands;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SortedMap;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

public class CharsetsShellCommand implements ShellCommand {
	

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		if (arguments.length() > 0) {
			env.write("Command charsets takes no arguments.");
			return ShellStatus.CONTINUE;
		}
		
		SortedMap<String, Charset> charsets = Charset.availableCharsets();
		charsets.forEach((s,c) -> env.writeln(s));
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "charsets";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> descriptions = new ArrayList<>();
		
		descriptions.add("Command charsets:");
		descriptions.add("  Takes no arguments.");
		descriptions.add("  Lists all supported and available charsets.");	
				
		return Collections.unmodifiableList(descriptions);
	}

}
