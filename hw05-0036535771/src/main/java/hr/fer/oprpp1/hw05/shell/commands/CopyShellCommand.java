package hr.fer.oprpp1.hw05.shell.commands;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

public class CopyShellCommand implements ShellCommand {
	

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] args = arguments.split(" ");
		
		if (args.length != 2) {
			env.writeln("Command copy takes 2 arguments");
			return ShellStatus.CONTINUE;
		}
		
		
		File sourceFile = new File(args[0]);
		File destFile = new File(args[1]);
		
		if(!sourceFile.exists()) {
			env.writeln("Source file doesn't exist.");
			return ShellStatus.CONTINUE;
		}
		if(sourceFile.isDirectory()) {
			env.writeln("Source file cannot be a directory.");
			return ShellStatus.CONTINUE;
		}
		
		boolean success;
		
		if(destFile.exists()) {
			if(destFile.isFile()) { // we need to ask for permission to overwrite
				env.write("Destination file already exists. Permission for overwrite? [y/n]: ");
				String answer = env.readLine();
				
				if(answer.equals("y")) {	
					destFile.delete();
					success = cleanCopy(env, sourceFile, destFile);
				} else {
					env.writeln("Aborting copy.");
					return ShellStatus.CONTINUE;
				}
				
			} else { // destination file is directory
				String fileName = sourceFile.getName();
				Path destPath = destFile.toPath().resolve(fileName);
				
				success =  cleanCopy(env, sourceFile, destPath.toFile());
			}
			
		} else { // file does not exist		
			success = cleanCopy(env, sourceFile, destFile);
		}
		
		if (!success) {
			env.writeln("Error with copying files.");
			return ShellStatus.CONTINUE;
		}
			
		
		return ShellStatus.CONTINUE;
	}
	

	@Override
	public String getCommandName() {
		return "copy";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> descriptions = new ArrayList<>();
		
		descriptions.add("Command copy:");
		descriptions.add("  Takes two arguments: source file name, destination file name");
		descriptions.add("  Copies first file into second, if destination file already exists it will ask for permission to overwrite.");
		descriptions.add("	Second argument can be directory. Then it will copy soruce file in that directory.");
				
		return Collections.unmodifiableList(descriptions);
	}
		
	
	private boolean cleanCopy(Environment env, File source, File dest) {
		InputStream inputStream;
		OutputStream outputStream;
		try {
			inputStream = new BufferedInputStream(Files.newInputStream(source.toPath()));
			outputStream = new BufferedOutputStream(Files.newOutputStream(dest.toPath()));
		} catch (IOException e) {
			env.writeln("Error with opening input and output streams.");
			return false;
		}
		
		try {
			while(inputStream.available() > 0) {
				int data = inputStream.read();			
				outputStream.write(data);
			}
			
			inputStream.close();
			outputStream.close();
		} catch (IOException e) {
			env.writeln("Error with copying files.");
			return false;
		}
		
		return true;
	}
	
	
	
	
	
	
	

}
