package hr.fer.oprpp1.hw05.shell.commands;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SortedMap;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

public class CatShellCommand implements ShellCommand {


	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {	
		String[] args = arguments.split(" ");
		
		if ((args.length != 1 || args[0].equals("")) && args.length != 2) {
			env.writeln("Command copy takes one or two arguments.");
			return ShellStatus.CONTINUE;
		}
		
		File file = new File(args[0]);
		if(!file.exists() || file.isDirectory()) { 
		    env.writeln("File doesn't exist or is given a directory name.");
		    return ShellStatus.CONTINUE;
		}
		
		byte[] input;
		try {
			InputStream inputStream = Files.newInputStream(file.toPath());
			input = inputStream.readAllBytes();
			
		} catch (IOException e) {
			env.writeln("Error while reading file.");
			return ShellStatus.CONTINUE;
		}
		
		String contents = "";
		if (args.length == 1) { // Only 1 argument
			
			contents = new String(input, Charset.defaultCharset()); 
			
		} else if(args.length == 2) { // 2 arguments
		
			SortedMap<String, Charset> charsets = Charset.availableCharsets();
			
			if (!charsets.containsKey(args[1])) {
				env.writeln("Given charset is not available.");
				return ShellStatus.CONTINUE;
			}
			
			contents = new String(input, Charset.forName(args[1]));
		}
		
		env.writeln(contents);
		return ShellStatus.CONTINUE;
		
	}

	@Override
	public String getCommandName() {
		return "cat";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> descriptions = new ArrayList<>();
		
		descriptions.add("Command cat:");
		descriptions.add("  Takes one or two arguments: [file path, (charset name)]");
		descriptions.add("  First argument is mandatory, second is optional (if no charset is given, uses standard one)");
		descriptions.add("	Opens given file and writes its contents to console.");

				
		return Collections.unmodifiableList(descriptions);
	}

}
