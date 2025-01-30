package TerminalSimulator.services;

import TerminalSimulator.Application;
import TerminalSimulator.models.Directory;
import TerminalSimulator.models.dto.request.Request;
import TerminalSimulator.services.interfaces.CommandService;
import org.springframework.stereotype.Service;

@Service
public class CdService implements CommandService {

    @Override
    public String exectute(Request request) {
        String commandArg = request.args[1];
        if(commandArg.equals("/")) return "/";
        if(commandArg.contains("..")){
            String[] dots = request.args[1].split("/");
            String[] dirs = request.path.split("/");
            if(dots.length > dirs.length){
                return "/";
            }
            return Application.database.findDirectory(Application.database.getRoot(), dirs[dirs.length-dots.length]).getName();
        }
        String[] path = request.path.split("/");
        Directory dir = Application.database.findDirectory(Application.database.getRoot(), path[path.length - 1]);
        boolean dirExists = false;
        for(Directory d : dir.getDirectories()){
            if (d.getName().equals(request.args[1])) {
                dirExists = true;
                break;
            }
        }

        return dirExists ? request.path + "/" + dir.getName() : "Directory not found";
    }
}
