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
        if (request.args.length < 2) {
            return new Response("No file specified", request.path);
        }

        Directory currentDir = Application.database.findDirectory(new ArrayList<>(List.of(request.path.split("/"))));
        File file = currentDir.findFile(request.args[1]);
        if (file == null) {
            return new Response("File not found: " + request.args[1], request.path);
        }

        String content = file.getData().replace("\\n", "\n").replaceAll("\r\n|\n", "<br>");
        String[] lines = content.split("<br>");
        int lineCount = lines.length;
        int wordCount = content.replaceAll("<br>", " ").split("\\s+").length;
        int charCount = content.replaceAll("<br>", "").length();

        String output = "lines: " + lineCount + "<br>" +
                "words: " + wordCount + "<br>" +
                "characters: " + charCount;

        return new Response(output, request.path);
    }
}