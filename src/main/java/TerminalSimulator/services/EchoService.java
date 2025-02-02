package TerminalSimulator.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import TerminalSimulator.Application;
import TerminalSimulator.models.Directory;
import TerminalSimulator.models.File;
import TerminalSimulator.models.dto.request.Request;
import TerminalSimulator.models.dto.response.Response;
import TerminalSimulator.services.interfaces.CommandService;

@Service	
public class EchoService implements CommandService {

	@Override
	public Response execute(Request request) {
		if(request.args.length < 4) {
			return new Response("No args found", request.path);
        }
		
		Directory currentDir = Application.database.findDirectory(new ArrayList<>(List.of(request.args[2])));
		File file = currentDir.findFile(request.args[3]);
		
		if(file == null) {
			return new Response("The file named as " + request.args[3] + " doesn't exists", request.path);
		}
		
		return switch(request.args[2]) {
			case ">" -> this.overwriteFileContent(file, request.args[1], request.path);
			case ">>"->  this.concatContentToFile(file, request.args[1], request.path);
			default -> new Response("The > or >> flag are missing", request.path);
		};
	}
	
	private Response overwriteFileContent(File file, String content, String path) {
		file.setData(content);
		file.setBytesSize();
		
		return new Response("New content added to file", path);
	}
	
	private Response concatContentToFile(File file, String content, String path) {
		file.setData(file.getData() + content);
		file.setBytesSize();
		
		return new Response("New content concatenated to file", path);
	}
}
