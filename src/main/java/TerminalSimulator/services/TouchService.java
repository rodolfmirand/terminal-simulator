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
		// Verifica se o usuário passou o nome do arquivo a ser criado
		if (request.args.length < 2) {
            return new Response("No args found", request.path);
        }
		
		// Encontra o diretório atual a partir do caminho atual
		Directory currentDir = Application.database.findDirectory(new ArrayList<>(List.of(request.path.split("/"))));
		
		// Procura se existe um arquivo com o nome informado pelo usuário no diretório
		boolean fileExists = currentDir.findFile(request.args[1]) != null;
		
		// Caso exista, retornar mensagem de erro
		if(fileExists) return new Response("Already exists a file named as " + request.args[1], request.path);
		
		// Caso contrário, cria um novo arquivo no diretório
		File emptyFile = new File(currentDir, request.args[1], Application.database.getCurrentUser());
		
		currentDir.addFile(emptyFile);
		
		return new Response("Empty file created with success!", request.path);
	}
}
