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
public class TailService implements CommandService {
    @Override
    public Response execute(Request request) {
        // verifica ausência de argumentos
        if (request.args.length < 2) {
            return new Response("No file specified", request.path);
        }

        //criação da variavel para armazenar qtde de linhas a mostrar
        int linesToShow;

        try {
            linesToShow = Integer.parseInt(request.args[1]);
            if (linesToShow < 1) {
                return new Response("Invalid number of lines", request.path);
            }
        } catch (NumberFormatException e) {
            return new Response("Invalid number format", request.path);
        }

        // encontra o diretório atual a partir do caminho informado
        Directory currentDir = Application.database.findDirectory(new ArrayList<>(List.of(request.path.split("/"))));

        // busca o arquivo no diretório atual
        File file = currentDir.findFile(request.args[2]);

        // verifica se o arquivo existe
        if (file == null) {
            return new Response("File not found.", request.path);
        }

        // troca os \n por <br> para poder exibir o conteudo do arquivo
        String formattedContent = file.getData().replace("\\n", "\n").replaceAll("\n", "<br>");

        // divide o conteúdo em linhas
        String[] lines = formattedContent.split("<br>");

        // gera a saída com as últimas linhas
        StringBuilder output = new StringBuilder();
        int startIndex = Math.max(0, lines.length - linesToShow);
        for (int i = startIndex; i < lines.length; i++) {
            output.append(lines[i]).append("\n");
        }

        return new Response(output.toString(), request.path);
    }
}
