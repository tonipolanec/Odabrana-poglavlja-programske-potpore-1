package hr.fer.oprpp1.hw05.shell.commands;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

import hr.fer.oprpp1.hw05.crypto.Util;

public class HexdumpShellCommand implements ShellCommand {

	public HexdumpShellCommand() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		String args[] = arguments.split(" ");
		
		if (args.length == 1 && args[0] != "") { 
			
			File file = new File(args[0]);
			if(!file.exists() || file.isDirectory()) { 
			    env.writeln("Invalid file name given or is given a directory name.");
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
			
			for (int i=0; i<input.length; i+= 16) {
				
				StringBuilder resultLine = new StringBuilder();
				
				resultLine.append(intTo8Hex(i) + ": ");
				
				byte[] first8 = new byte[8];
				byte[] last8 = new byte[8];
				
				for(int j=0; j<16; j++) {
					if (j<8) {
						if (i+j >= input.length) 
							first8[j] = 0;
						else
							first8[j] = input[i+j];
					} else {
						if (i+j >= input.length) 
							last8[j-8] = 0;
						else
							last8[j-8] = input[i+j];	
					}
				}
				
				String hexLine = giveHexLineFor16bytes(first8, last8);
				resultLine.append(hexLine + " | ");
				
				byte[] lineBytes = new byte[16];
				for(int j=i; j<i+16; j++) {
					if (j < input.length) 
						lineBytes[j-i] = input[j];
				}
				
				String standardWriting = "";
				for(int j=0; j<16; j+=2) {
					byte[] duoBytes = new byte[] {lineBytes[j], lineBytes[j+1]};
					
					if (duoBytes[0] < 27 || duoBytes[0] > 127 || duoBytes[1] < 27 || duoBytes[1] > 127)
						standardWriting += ".";
					else
						standardWriting += new String(duoBytes, Charset.defaultCharset());
				}
				
				resultLine.append(standardWriting);
				env.writeln(resultLine.toString());
				
			}
			
			
			
			
			
			
		
		} else {
			env.writeln("Command hexdump takes one argument.");
			return ShellStatus.CONTINUE;
		}
		
		return ShellStatus.CONTINUE;
	}

	private String intTo8Hex(int i) {
		String hex = Integer.toHexString(i);
		int diff = 8 - hex.length();
		
		String res = "0".repeat(diff) + hex;
		return res;
	}

	private String giveHexLineFor16bytes(byte[] first8, byte[] last8) {
		String line = "";
		String hexFirst8 = Util.bytetohex(first8);
		String hexLast8 = Util.bytetohex(last8);
		
		for(int i=0; i<hexFirst8.length(); i++) {
			if (i%2==0) line += " ";
			line += hexFirst8.charAt(i);
		}
		line += " | ";
		for(int i=0; i<hexLast8.length(); i++) {
			if (i%2==0) line += " ";
			line += hexLast8.charAt(i);
		}		
		
		return line;
	}

	@Override
	public String getCommandName() {
		return "hexdump";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> descriptions = new ArrayList<>();
		
		descriptions.add("Command hexdump:");
		descriptions.add("  Takes one argument: file name");
		descriptions.add("  Prints out hex-output of given file.");

				
		return Collections.unmodifiableList(descriptions);
	}

}
