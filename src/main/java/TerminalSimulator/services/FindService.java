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
public class FindService implements CommandService {

	@Override
	public Response execute(Request request) {
		if(request.args.length < 4) {
			return new Response("No args found", request.path);
        }
		
		if(request.args[2] != "-name") {
			return new Response("The -name flag is mandatory" , request.path);
		}
		
		Directory currentDir = Application.database.findDirectory(new ArrayList<>(List.of(request.args[1])));
		File file = currentDir.findFile(request.args[3]);
		
		if(file == null) {
			return new Response("The file named as " + request.args[3] + "doesn't exists", null);
		}
		
		return new Response("The file named as " + request.args[3] + "was found", null);
	}
}
