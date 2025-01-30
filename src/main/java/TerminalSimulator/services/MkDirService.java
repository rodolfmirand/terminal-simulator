package TerminalSimulator.services;

import TerminalSimulator.Application;
import TerminalSimulator.models.Directory;
import TerminalSimulator.models.dto.request.Request;
import TerminalSimulator.models.dto.response.Response;
import TerminalSimulator.services.interfaces.CommandService;
import org.springframework.stereotype.Service;

@Service
public class MkDirService implements CommandService {

    @Override
    public Response execute(Request request) {
        String dirName = request.args[1];
        String currentDirName;
        String currentPath = request.path;

        if(currentPath.equals("root/")){
            currentDirName = "root";
        }else{
            String[] path = currentPath.split("/");
            currentDirName = path[path.length - 1];
        }

        Directory currentDir = Application.database.findDirectory(currentDirName);
        Directory newDir = new Directory(currentDir.getPath() + dirName + "/", dirName, currentDir);

        currentDir.addDirectory(newDir);

        return new Response("Directory created '" + newDir.getPath() + "'",  request.path);
    }
}
