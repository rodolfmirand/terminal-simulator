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
public class RmService implements CommandService {
    @Override
    public Response execute(Request request) {
        if (request.args.length < 2) {
            return new Response("No file or directory specified.", request.path);
        }

        boolean recursive = false;
        String targetName = request.args[1];

        // Verificar se o argumento -r foi passado
        if (targetName.equals("-r")) {
            if (request.args.length < 3) {
                return new Response("No directory specified for recursive deletion.", request.path);
            }
            recursive = true;
            targetName = request.args[2];
        }

        Directory currentDir = Application.database.findDirectory(new ArrayList<>(List.of(request.path.split("/"))));
        if (currentDir.getName().equals("root") && targetName.equals("root")) {
            return new Response("Cannot remove root directory", request.path);
        }

        File file = currentDir.findFile(targetName);
        Directory dir = currentDir.findSubDirectory(targetName);

        if (file != null) {
            // Remover arquivo
            boolean removed = currentDir.removeFile(targetName);
            if (!removed) {
                return new Response("Failed to remove file: " + targetName, request.path);
            }
            return new Response("File deleted.", request.path);
        } else if (dir != null) {
            // Remover diretório somente se for recursivo
            if (!recursive) {
                return new Response("Cannot remove directory without -r flag", request.path);
            }

            boolean removed = currentDir.removeDirectory(targetName);
            if (!removed) {
                return new Response("Failed to remove directory: " + targetName, request.path);
            }
            return new Response("Directory deleted.", request.path);
        }

        return new Response("File or directory not found: " + targetName, request.path);

    }
}
