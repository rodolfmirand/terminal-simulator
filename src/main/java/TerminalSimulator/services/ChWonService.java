package TerminalSimulator.services;

import TerminalSimulator.Application;
import TerminalSimulator.models.Directory;
import TerminalSimulator.models.File;
import TerminalSimulator.models.dto.request.Request;
import TerminalSimulator.models.dto.response.Response;
import TerminalSimulator.services.interfaces.CommandService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChWonService implements CommandService {

    @Override
    public Response execute(Request request){
        // Verifica se o número de argumentos passados no comando é válido
        if (request.args.length < 3) {
            return new Response("Usage: chown <owner> <name>", request.path);
        }

        // Pega o primeiro argumento como o novo dono (owner) e o segundo como o nome do arquivo ou diretório
        String owner = request.args[1];
        String targetName = request.args[2];

        // Busca pelo diretório "root" no banco de dados simulado
        Directory root = Application.database.findDirectory(new ArrayList<>(List.of("root")));

        // Tenta encontrar o diretório do usuário (owner) dentro do diretório "root"
        Directory userDirectory = root.findSubDirectory(owner);
        if (userDirectory == null) {
            // Retorna erro se o diretório do usuário não existir (usuário inválido)
            return new Response("Invalid owner: User '" + owner + "' does not exist.", request.path);
        }

        // Encontra o diretório atual baseado no caminho fornecido na requisição
        Directory currentDir = Application.database.findDirectory(new ArrayList<>(List.of(request.path.split("/"))));

        // Tenta encontrar o arquivo com o nome fornecido no diretório atual
        File file = currentDir.findFile(targetName);
        if (file != null) {
            // Se o arquivo for encontrado, altera o dono do arquivo
            file.setOwner(owner);
            return new Response("Owner changed to " + owner, request.path);
        }

        // Tenta encontrar um subdiretório com o nome fornecido
        Directory directory = currentDir.findSubDirectory(targetName);
        if (directory != null) {
            // Se o diretório for encontrado, altera o dono do diretório
            directory.setOwner(owner);
            return new Response("Owner changed to " + owner, request.path);
        }

        // Se não encontrar nem o arquivo nem o diretório, retorna erro
        return new Response("File or directory not found.", request.path);
    }
}
