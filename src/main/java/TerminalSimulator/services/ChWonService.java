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
        if (request.args.length < 3) {
            return new Response("Usage: chown <owner> <name>", request.path);
        }

        String owner = request.args[1];
        String targetName = request.args[2];

        Directory root = Application.database.findDirectory(new ArrayList<>(List.of("root")));

        Directory userDirectory = root.findSubDirectory(owner);
        if (userDirectory == null) {
            return new Response("Invalid owner: User '" + owner + "' does not exist.", request.path);
        }

        Directory currentDir = Application.database.findDirectory(new ArrayList<>(List.of(request.path.split("/"))));
        File file = currentDir.findFile(targetName);

        if (file != null) {
            file.setOwner(owner);
            return new Response("Owner changed to " + owner, request.path);
        }

        Directory directory = currentDir.findSubDirectory(targetName);
        if (directory != null) {
            directory.setOwner(owner);
            return new Response("Owner changed to " + owner, request.path);
        }

        return new Response("File or directory not found.", request.path);
    }
}
