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
		// Verifica se o usuário passou o diretorio, -name, e nome do arquivo
		if(request.args.length < 4) {
			return new Response("No args found", request.path);
        }
		
		// Verifica se a flag -name foi mandada na requisição, pois ela é obrigatória
		if(!request.args[2].equals("-name")) {
			return new Response("The -name flag is mandatory" , request.path);
		}
		
		// Encontra o diretório atual a partir do caminho informado
		Directory currentDir = Application.database.findDirectory(new ArrayList<>(List.of(request.args[1].split("/"))));
		
		// Procura pelo arquivo solicitado pelo usuário no diretório 
		File file = currentDir.findFile(request.args[3]);
		
		// Caso não exista o arquivo retorna mensagem
		if(file == null) {
			return new Response("The file named as " + request.args[3] + " doesn't exists", request.path);
		}
		
		return new Response("The file named as " + request.args[3] + " was found", request.path);
	}
}
