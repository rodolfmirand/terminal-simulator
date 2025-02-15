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
        if (request.args.length < 2) {
            return new Response("No file specified", request.path);
        }

        Directory currentDir = Application.database.findDirectory(new ArrayList<>(List.of(request.path.split("/"))));

        File file = currentDir.findFile(request.args[1]);

        if (file == null) return new Response("File not found: " + request.args[1], request.path);

        return new Response("File Content:\n" + file.getData(), request.path);
    }
}
