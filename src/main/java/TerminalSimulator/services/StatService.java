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
        if (request.args.length < 2) {
            return new Response("Usage: stat <name>", request.path);
        }

        String name = request.args[1];
        Directory currentDir = Application.database.findDirectory(new ArrayList<>(List.of(request.path.split("/"))));

        if (currentDir == null) {
            return new Response("Directory not found", request.path);
        }

        File file = currentDir.findFile(name);
        Directory dir = currentDir.findChildDirectory(name);

        if (file != null) {
            return new Response(file.toStringLs(), request.path);
        } else if (dir != null) {
            return new Response(dir.toStringLs(), request.path);
        } else {
            return new Response("File or directory not found", request.path);
        }
    }
}
