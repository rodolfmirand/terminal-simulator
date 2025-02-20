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
		// Se tiver apenas um argumento exibe a mensagem no terminal
		if(request.args.length == 2) {
			return new Response(request.args[1], request.path);
		}
		
		// Caso contrário se faz necessário 3 argumentos: Texto, (> ou >>), caminho do arquivo
		if(request.args.length < 4) {
			return new Response("No args found", request.path);
        }
		
		// Encontra o diretório atual a partir do caminho atual
		Directory currentDir = Application.database.findDirectory(new ArrayList<>(List.of(request.path.split("/"))));
		
		// Busca o arquivo no diretório atual
		File file = currentDir.findFile(request.args[3]);
		
		// Se o arquivo não existir retorna mensagem de erro
		if(file == null) {
			return new Response("The file named as " + request.args[3] + " doesn't exists", request.path);
		}
		
		// Com base na flag (> ou >>) irá adicionar ou concatenar o texto no arquivo
		return switch(request.args[2]) {
			case ">" -> this.overwriteFileContent(file, request.args[1], request.path);
			case ">>"->  this.concatContentToFile(file, request.args[1], request.path);
			default -> new Response("The > or >> flag are missing", request.path);
		};
	}
	
	private Response overwriteFileContent(File file, String content, String path) {
		// Atualiza o conteúdo e tamanho do arquivo
		file.setData(content);
		file.setBytesSize();
		
		return new Response("New content added to file", path);
	}
	
	private Response concatContentToFile(File file, String content, String path) {
		// Atualiza o conteúdo e tamanho do arquivo
		file.setData(file.getData() + content);
		file.setBytesSize();
		
		return new Response("New content concatenated to file", path);
	}
}
