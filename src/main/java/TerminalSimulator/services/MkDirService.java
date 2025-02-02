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
public class MkDirService implements CommandService {

    @Override
    public Response execute(Request request) {
        if(request.args.length < 1){
            return new Response("Empty directory name.", request.path);
        }

        String newDirName = request.args[1];

        if(newDirName.isEmpty()){
            return new Response("Empty directory name.", request.path);
        }

        Directory currentDir = Application.database.findDirectory(new ArrayList<>(List.of(request.path.split("/"))));

        for (Directory child : currentDir.getDirectories()){
            if(child.getName().equals(newDirName)){
                return new Response("Directory already exists!", request.path);
            }
        }

        Directory newDir = new Directory(newDirName, currentDir);

        currentDir.addDirectory(newDir);

        return new Response("Directory created '" + newDir.getPath() + "'.",  request.path);
    }
}
