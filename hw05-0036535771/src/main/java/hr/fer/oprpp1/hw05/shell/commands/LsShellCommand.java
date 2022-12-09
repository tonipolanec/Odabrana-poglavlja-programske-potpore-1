package hr.fer.oprpp1.hw05.shell.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

public class LsShellCommand implements ShellCommand {

	public LsShellCommand() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String args[] = arguments.split(" ");
		
		if (args.length == 1 && args[0] != "") { 
			
			File dir = new File(args[0]);
			if(!dir.exists() || !dir.isDirectory()) { 
			    env.writeln("Invalid directory name or it this isn't directory.");
			    return ShellStatus.CONTINUE;
			}
			
			File files[] = dir.listFiles();
			
			for(File file : files) {
				
				Path path = file.toPath();
				BasicFileAttributeView faView = Files.getFileAttributeView(path, BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS);
				BasicFileAttributes attributes;
				try {
					attributes = faView.readAttributes();
				} catch (IOException e) {
					env.writeln("Error reading file attributes.");
					return ShellStatus.CONTINUE;
				}
				
				
				// directory readable writable executable
				char d = attributes.isDirectory() ? 'd' : '-';
				char r = file.canRead() ? 'r' : '-';
				char w = file.canWrite() ? 'w' : '-';
				char x = file.canExecute() ? 'x' : '-';
				
				// size in bytes
				long size = 0;
				if (file.isDirectory())
					try {
						size += Files.walk(file.toPath())
						        .map(f -> f.toFile())
						        .filter(f -> f.isFile())
						        .mapToLong(f -> f.length())
						        .sum();
						
					} catch (IOException e) {
						env.writeln("Error while calculating folder size.");
						return ShellStatus.CONTINUE;
					}
				else
					size += file.length();
				
				String sizeString = "" + size;
				
				// date and time
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				FileTime fileTime = attributes.creationTime();
				String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));
				
				// file name
				String fileName = file.getName();
				
				
				StringBuilder resultLine = new StringBuilder();
				resultLine.append(d);
				resultLine.append(r);
				resultLine.append(w);
				resultLine.append(x);
				
				int diff = 10 - sizeString.length() + 1;
				resultLine.append(" ".repeat(diff));
				resultLine.append(sizeString + " ");
				
				resultLine.append(formattedDateTime + " ");
				
				resultLine.append(fileName);
				
				
				env.writeln(resultLine.toString());
			}
			
		
		} else {
			env.writeln("Command ls takes one argument.");
			return ShellStatus.CONTINUE;
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "ls";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> descriptions = new ArrayList<>();
		
		descriptions.add("Command ls:");
		descriptions.add("  Takes one argument: directory name");
		descriptions.add("  Prints out detailed list of all files and directories in that directory.");

				
		return Collections.unmodifiableList(descriptions);
	}
	
}
