package hr.fer.oprpp1.hw05.shell.commands;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

public class TreeShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] args = arguments.split(" ");
		
		if (args.length == 1 && args[0] != "") {

			File dir = new File(args[0]);
			if(!dir.exists() || !dir.isDirectory()) { 
			    System.out.println("Invalid folder name given.");
			    return ShellStatus.CONTINUE;
			}
			goThroughFolder(dir, "");
			
			return ShellStatus.CONTINUE;
		} else {
			System.out.println("Wrong number of arguments (must have only one)");
			return ShellStatus.CONTINUE;
		}
		
	}
	
	
	private void goThroughFolder(File dir, String indent) {
		
		File files[] = dir.listFiles();
	    if (files != null) {
	        for (int i=0; i<files.length; i++) {
	        	String pointer = i==files.length-1 ? "└─" : "├──";
	        	
	            if (files[i].isDirectory()) {
	              System.out.println(indent + pointer + files[i].getName()); 
	              goThroughFolder(files[i], indent+"  ");
	            } else {
	                System.out.println(indent + pointer + files[i].getName().toString());
	            }
	        }
	    }
	    
	}

	@Override
	public String getCommandName() {
		return "tree";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> descriptions = new ArrayList<>();
		
		descriptions.add("Command tree:");
		descriptions.add("  Takes one argument: name of a folder");
		descriptions.add("  Prints tree like structure og given folder.");	

				
		return Collections.unmodifiableList(descriptions);
	}

}
