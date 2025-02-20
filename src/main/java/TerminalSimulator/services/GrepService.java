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
		// Verifica se o usuario passou o texto a ser procurado e o caminho do arquivo
		if(request.args.length < 2) {
			return new Response("No args found", request.path);
		}
		
		// Encontra o diretório atual a partir do caminho atual
		Directory currentDir = Application.database.findDirectory(new ArrayList<>(List.of(request.path.split("/"))));
		
		// Busca o arquivo no diretório atual
		File file = currentDir.findFile(request.args[2]);
		
		// Se o arquivo não existir retorna mensagem de erro
		if(file == null) {
			return new Response("The file named as " + request.args[2] + " doesn't exists", request.path);
		}
		
		// Inicializa String que conterá o resultado da filtragem do conteúdo do arquivo
		StringBuilder result = new StringBuilder();
		String searchTerm = request.args[1]; 
		
		// Remove o escapamento do \n para poder quebrar o texto do arquivo em linhas
		String[] lines = file.getData().replace("\\n", "\n").split("\n");
		
		// Percorre cada linha do arquivo, e se ela conter o termo procurado adiciona na variável result
		for (int line = 0; line < lines.length;line++) {
		    if (lines[line].contains(searchTerm)) { 
		        result.append("[" + line +"] " + lines[line]).append("\n"); 
		    }
		}	
		
		return new Response(result.toString(), request.path);
	}
}
