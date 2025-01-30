package TerminalSimulator.services;

import TerminalSimulator.Application;
import TerminalSimulator.models.Directory;
import TerminalSimulator.models.dto.request.Request;
import TerminalSimulator.models.dto.response.Response;
import TerminalSimulator.services.interfaces.CommandService;
import org.springframework.stereotype.Service;

@Service
public class CdService implements CommandService {

    @Override
    public Response execute(Request request) {
        if(request.args.length <= 1){
            return new Response("No args found", request.path);
        }

        String commandArg = request.args[1];

        if(commandArg.equals("/")) return new Response("", "/");

        if(commandArg.contains("..")){
            String[] dots = request.args[1].split("/");
            String[] dirs = request.path.split("/");
            if(dots.length >= dirs.length){
                return new Response("", "root/");
            }

            Directory currentDir = Application.database.findDirectory(dirs[dirs.length - 1]);
            for (int i = dots.length; i > 0; i--){
                currentDir = currentDir.getParent();
            }
            return new Response("", currentDir.getPath());
        }

        Directory dir;
        if(request.path.equals("/")){
            dir = Application.database.getRoot();
        }else{
            String[] path = request.path.split("/");
            dir = Application.database.findDirectory(path[path.length - 1]);
        }

        for(Directory d : dir.getDirectories()){
            if (d.getName().equals(request.args[1])) {
                return new Response("", d.getPath());
            }
        }
        return new Response("", dir.getPath());
    }
}
