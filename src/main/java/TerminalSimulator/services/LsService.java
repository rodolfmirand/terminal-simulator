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
public class LsService implements CommandService {

    @Override
    public Response execute(Request request) {
        if (request.args.length > 2) {
            return new Response("Usage: ls or ls -l", request.path);
        }

        Directory currentDir = Application.database.findDirectory(new ArrayList<>(List.of(request.path.split("/"))));

        if (currentDir == null) {
            return new Response("Directory not found", request.path);
        }

        boolean detailed = request.args.length > 1 && request.args[1].equals("-l");

        List<String> result = new ArrayList<>();
        for (Directory dir : currentDir.getDirectories()) {
            result.add(detailed ? dir.toStringLs() : dir.getName());
        }
        for (File file : currentDir.getFiles()) {
            result.add(detailed ? file.toStringLs() : file.getName());
        }

        return new Response(String.join("\n", result), request.path);
    }
}