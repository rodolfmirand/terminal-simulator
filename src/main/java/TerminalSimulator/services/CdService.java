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
public class CdService implements CommandService {

    @Override
    public Response execute(Request request) {

        // verifica a ausência de argumentos
        if (request.args.length <= 1) {
            return new Response("No args found", request.path);
        }

        // armazena o argumento do comando
        String commandArg = request.args[1];

        // caso o argumento seja "/", retorna o diretório raíz
        if (commandArg.equals("/")) {
            return new Response("", "root/");
        }

        // armazena o caminho atual
        ArrayList<String> dirs = new ArrayList<>(List.of(request.path.split("/")));

        // busca na árvore o diretório atual
        Directory currentDir = Application.database.findDirectory(dirs);

        // caso o argumento seja "..", retorna quantos diretórios foram requisitados
        if (commandArg.contains("..")) {

            // armazena a quantidade de diretórios a serem retornados
            int dots = request.args[1].split("/").length;

            // retorna os diretórios
            for (int i = dots; i > 0 && currentDir.getParent() != null; i--) {
                currentDir = currentDir.getParent();
            }

            return new Response("", currentDir.getPath());
        }

        // procura no diretório atual o diretório a ser encontrado
        Directory targetDir = currentDir.getDirectories()
                .stream()
                .filter(d -> d.getName().equals(commandArg))
                .findFirst()
                .orElse(null);

        // caso o diretório exista, ele é retornado
        return (targetDir != null)
                ? new Response("", targetDir.getPath())
                : new Response("Directory not found.", currentDir.getPath());
    }
}
