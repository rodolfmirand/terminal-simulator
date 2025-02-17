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
public class CatService implements CommandService {
    @Override
    public Response execute(Request request) {
        // verifica ausência de argumentos
        if (request.args.length < 2) {
            return new Response("No file specified", request.path);
        }

        // encontra o diretório atual a partir do caminho informado
        Directory currentDir = Application.database.findDirectory(
                new ArrayList<>(List.of(request.path.split("/")))
        );

        // busca o arquivo no diretório atual
        File file = currentDir.findFile(request.args[1]);

        // verifica se o arquivo existe
        if (file == null) {
            return new Response("File not found: " + request.args[1], request.path);
        }

        // troca os \n por <br> para poder exibir o conteudo do arquivo
        String formattedContent = file.getData().replace("\\n", "\n").replaceAll("\n", "<br>");

        //retorna o texto
        return new Response("File Content:<br>" + formattedContent, request.path);
    }
}

