package hr.fer.oprpp1.hw05.shell.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

public class MkdirShellCommand implements ShellCommand {

	public MkdirShellCommand() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		String args[] = arguments.split(" ");
		
		if (args.length == 1 && args[0] != "") { 
				
			File dir = new File(args[0]);
							
			if (dir.exists()) {
				env.writeln("Directory with that name already exists.");
				return ShellStatus.CONTINUE;
			
			} else {
				try {					
					makeDirectories(dir);			
							
				} catch (IOException e) {
					env.writeln("Failed at creating directory.");
					return ShellStatus.CONTINUE;
				}
			}
			
			
		} else {
			env.writeln("Command mkdir takes one argument.");
			return ShellStatus.CONTINUE;
		}
		
		
		
		return ShellStatus.CONTINUE;
	}


	@Override
	public String getCommandName() {
		return "mkdir";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> descriptions = new ArrayList<>();
		
		descriptions.add("Command mkdir:");
		descriptions.add("  Takes one argument: directory name");
		descriptions.add("  Creates directory with given name.");

				
		return Collections.unmodifiableList(descriptions);
	}
	
	
	private void makeDirectories(File dir) throws IOException {
		
		String dirPath = dir.toPath().toString();	
		String[] folders = dirPath.split("\\\\");
		
		String stringPath = "";

		for(int i=0; i<folders.length; i++) {
			
			stringPath += folders[i] + "/";
			Path currentPath = Paths.get(stringPath);

			
			// directory doesn't exist
			if(!currentPath.toFile().exists() || currentPath.toFile().isFile()) {
				Files.createDirectory(currentPath);
			}			
		}

	}
	
	
	
	
	
	
	
	

}
