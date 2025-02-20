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
public class StatService implements CommandService {

    @Override
    public Response execute(Request request) {
        // Verifica se o número de argumentos passados no comando é insuficiente (menos de 2 argumentos)
        if (request.args.length < 2) {
            // Retorna uma resposta de uso adequado caso o comando não tenha os argumentos corretos
            return new Response("Usage: stat <name>", request.path);
        }

        // Pega o segundo argumento como o nome do arquivo ou diretório a ser verificado
        String name = request.args[1];

        // Tenta encontrar o diretório atual baseado no caminho fornecido na requisição (request.path)
        Directory currentDir = Application.database.findDirectory(new ArrayList<>(List.of(request.path.split("/"))));

        // Verifica se o diretório atual foi encontrado
        if (currentDir == null) {
            // Retorna uma resposta de erro caso o diretório atual não tenha sido encontrado
            return new Response("Directory not found", request.path);
        }

        // Tenta encontrar o arquivo com o nome fornecido no diretório atual
        File file = currentDir.findFile(name);

        // Tenta encontrar o subdiretório com o nome fornecido no diretório atual
        Directory dir = currentDir.findChildDirectory(name);

        // Verifica se o arquivo foi encontrado
        if (file != null) {
            // Retorna uma resposta com a representação do arquivo no formato 'ls', caso o arquivo seja encontrado
            return new Response(file.toStringLs(), request.path);
        } else if (dir != null) {
            // Caso não tenha encontrado o arquivo, verifica se o diretório foi encontrado
            // Retorna uma resposta com a representação do diretório no formato 'ls', caso o diretório seja encontrado
            return new Response(dir.toStringLs(), request.path);
        } else {
            // Caso nem o arquivo nem o diretório sejam encontrados, retorna uma mensagem de erro
            return new Response("File or directory not found", request.path);
        }

    }
}
