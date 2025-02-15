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
public class HeadService implements CommandService {
    @Override
    public Response execute(Request request) {
        if (request.args.length < 2) {
            return new Response("No file specified", request.path);
        }

        int linesToShow;

        try {
            linesToShow = Integer.parseInt(request.args[2]);
            if (linesToShow < 1) {
                return new Response("Invalid number of lines", request.path);
            }
        } catch (NumberFormatException e) {
            return new Response("Invalid number format", request.path);
        }

        Directory currentDir = Application.database.findDirectory(new ArrayList<>(List.of(request.path.split("/"))));
        File file = currentDir.findFile(request.args[1]);
        if (file == null) {
            return new Response("File not found: " + request.args[1], request.path);
        }

        String[] lines = file.getData().split("\n");
        StringBuilder output = new StringBuilder("Head of file: " + request.args[1] + "\n");
        for (int i = 0; i < Math.min(linesToShow, lines.length); i++) {
            output.append(lines[i]).append("\n");
        }

        return new Response(output.toString(), request.path);
    }
}