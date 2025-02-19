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
public class WcService implements CommandService {
    @Override
    public Response execute(Request request) {
        // verifica ausência de argumentos
        if (request.args.length < 2) {
            return new Response("No file specified", request.path);
        }

        // encontra o diretório atual a partir do caminho informado
        Directory currentDir = Application.database.findDirectory(new ArrayList<>(List.of(request.path.split("/"))));

        // busca o arquivo no diretório atual
        File file = currentDir.findFile(request.args[1]);

        // verifica se o arquivo existe
        if (file == null) {
            return new Response("File not found: " + request.args[1], request.path);
        }

        // formata de \n para <br> o conteúdo do arquivo para exibição
        String content = file.getData().replace("\\n", "\n").replaceAll("\n", "<br>");

        // conta linhas, palavras e caracteres
        String[] lines = content.split("<br>");
        int lineCount = lines.length;
        int wordCount = content.replaceAll("<br>", " ").split("\\s+").length;
        int charCount = content.replaceAll("<br>", "").length();

        // gera a saída formatada
        String output = "lines: " + lineCount + "<br>" +
                "words: " + wordCount + "<br>" +
                "characters: " + charCount;

        return new Response(output, request.path);
    }
}