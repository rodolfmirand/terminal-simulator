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
            linesToShow = Integer.parseInt(request.args[1]);
            if (linesToShow < 1) {
                return new Response("Invalid number of lines", request.path);
            }
        } catch (NumberFormatException e) {
            return new Response("Invalid number format", request.path);
        }

        Directory currentDir = Application.database.findDirectory(new ArrayList<>(List.of(request.path.split("/"))));
        File file = currentDir.findFile(request.args[2]);
        if (file == null) {
            return new Response("File not found.", request.path);
        }

        String formattedContent = file.getData().replace("\\n", "\n").replaceAll("\r\n|\n", "<br>");
        String[] lines = formattedContent.split("<br>");

        StringBuilder output = new StringBuilder();
        for (int i = 0; i < Math.min(linesToShow, lines.length); i++) {
            output.append(lines[i]).append("\n");
        }

        return new Response(output.toString(), request.path);
    }
}