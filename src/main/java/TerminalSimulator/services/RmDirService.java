package TerminalSimulator.services;

import TerminalSimulator.Application;
import TerminalSimulator.models.Directory;
import TerminalSimulator.models.dto.request.Request;
import TerminalSimulator.models.dto.response.Response;
import TerminalSimulator.services.interfaces.CommandService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RmDirService implements CommandService {


    @Override
    public Response execute(Request request) {

        // verifica ausência de argumentos
        if (request.args.length <= 1) {
            return new Response("Empty directory name.", request.path);
        }

        // procura na árvore o diretório atual
        Directory currentDir = Application.database.findDirectory(new ArrayList<>(List.of(request.path.split("/"))));

        // armazena o nome do diretório a ser removido
        String dirName = request.args[1];

        // procura o diretório a ser remvido no diretório atual
        Directory targetDir = currentDir.getDirectories()
                .stream()
                .filter(dir -> dir.getName().equals(dirName))
                .findFirst()
                .orElse(null);

        // caso não encontrado
        if (targetDir == null) {
            return new Response("Directory not found.", request.path);
        }

        // caso encontrado e não esteja vazio, é removido
        if (!targetDir.getFiles().isEmpty() || !targetDir.getDirectories().isEmpty()) {
            return new Response("Directory not empty.", request.path);
        }

        // removido caso encontrado e esteja vazio
        currentDir.removeDirectory(dirName);
        return new Response("Directory deleted.", request.path);
    }
}
