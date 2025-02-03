package TerminalSimulator.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import TerminalSimulator.Application;
import TerminalSimulator.models.Directory;
import TerminalSimulator.models.File;
import TerminalSimulator.models.dto.request.Request;
import TerminalSimulator.models.dto.response.Response;
import TerminalSimulator.services.interfaces.CommandService;

@Service	
public class GrepService implements CommandService{

	@Override
	public Response execute(Request request) {
		if(request.args.length < 2) {
			return new Response("No args found", request.path);
		}
		
		Directory currentDir = Application.database.findDirectory(new ArrayList<>(List.of(request.path)));
		File file = currentDir.findFile(request.args[2]);
		
		if(file == null) {
			return new Response("The file named as " + request.args[2] + " doesn't exists", request.path);
		}
		
		StringBuilder result = new StringBuilder();
		String searchTerm = request.args[1]; 
		String[] lines = file.getData().replace("\\n", "\n").split("\n");
		
		for (int line = 0; line < lines.length;line++) {
		    if (lines[line].contains(searchTerm)) { 
		        result.append("[" + line +"] " + lines[line]).append("\n"); 
		    }
		}	
		
		return new Response(result.toString(), request.path);
	}
}
