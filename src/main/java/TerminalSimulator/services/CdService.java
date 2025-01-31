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
        if (request.args.length <= 1) {
            return new Response("No args found", request.path);
        }

        String commandArg = request.args[1];

        if (commandArg.equals("/")) return new Response("", "/");

        if (commandArg.contains("..")) {
            ArrayList<String> dots = new ArrayList<>(List.of(request.args[1].split("/")));
            ArrayList<String> dirs = new ArrayList<>(List.of(request.path.split("/")));
            if (dots.size() >= dirs.size()) {
                return new Response("", "root/");
            }

            Directory currentDir = Application.database.findDirectory(dirs);
            for (int i = dots.size(); i > 0; i--) {
                currentDir = currentDir.getParent();
            }
            return new Response("", currentDir.getPath());
        }

        Directory dir;
        if (request.path.equals("/")) {
            dir = Application.database.getRoot();
        } else {
            ArrayList<String> path = new ArrayList<>(List.of(request.path.split("/")));
            dir = Application.database.findDirectory(path);
        }

        for (Directory d : dir.getDirectories()) {
            if (d.getName().equals(request.args[1])) {
                return new Response("", d.getPath());
            }
        }
        return new Response("", dir.getPath());
    }
}
