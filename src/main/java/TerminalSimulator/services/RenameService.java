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
public class RenameService implements CommandService {
    @Override
    public Response execute(Request request) {
        if(request.args.length <= 1){
            return new Response("No args found.", request.path);
        }

        Directory currentDir = Application.database.findDirectory(new ArrayList<>(List.of(request.path)));

        for (Directory dir : currentDir.getDirectories()) {
            if(dir.getName().equals(request.args[1])){
                String oldName = dir.getName();
                dir.setName(request.args[2]);
                return new Response("Directory + '" + oldName + "' renamed to '" + request.args[2] + "'", request.path);
            }
        }

        return new Response("Directory not found.", request.path);
    }
}
