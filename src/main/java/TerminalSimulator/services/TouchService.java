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
public class TouchService implements CommandService{

	@Override
	public Response execute(Request request) {
		if (request.args.length < 1) {
            return new Response("No args found", request.path);
        }
		
		Directory currentDir = Application.database.findDirectory(new ArrayList<>(List.of(request.path.split("/"))));
		File emptyFile = new File(request.path, request.args[1], Application.database.getCurrentUser());
		
		currentDir.addFile(emptyFile);
		
		return new Response("Empty file created with success!", request.path);
	}
}
