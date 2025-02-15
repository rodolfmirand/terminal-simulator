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
public class DuService implements CommandService {

    @Override
    public Response execute(Request request) {
        if (request.args.length < 2) {
            return new Response("Usage: du <directory>", request.path);
        }

        String name = request.args[1];
        Directory currentDir = Application.database.findDirectory(new ArrayList<>(List.of(request.path.split("/"))));

        if (currentDir == null) {
            return new Response("Directory not found", request.path);
        }

        Directory dir = currentDir.findChildDirectory(name);
        if (dir == null) {
            return new Response("Directory not found", request.path);
        }

        return new Response("Size: " + dir.getBytesSize() + " bytes", request.path);
    }
}
