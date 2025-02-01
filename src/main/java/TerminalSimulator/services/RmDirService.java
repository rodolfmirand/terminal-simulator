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
public class RmDirService implements CommandService {


    @Override
    public Response execute(Request request) {
        if(request.args.length <= 1){
            return new Response("Empty directory name.", request.path);
        }

        Directory currentDir = Application.database.findDirectory(new ArrayList<>(List.of(request.path.split("/"))));
        for (Directory dir : currentDir.getDirectories()) {
            if(dir.getName().equals(request.args[1])){
                if (dir.getFiles().isEmpty() && dir.getDirectories().isEmpty()) {
                    currentDir.removeDirectory(dir.getName());
                    return new Response("Directory deleted.", request.path);
                }else{
                    return new Response("Directory not empty.", request.path);
                }
            }
        }

        return new Response("Directory not found.", request.path);
    }
}
