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

        // verifica a ausência de argumetnos
        if(request.args.length <= 1){
            return new Response("No args found.", request.path);
        }

        // busca na árvore o diretório atual
        Directory currentDir = Application.database.findDirectory(new ArrayList<>(List.of(request.path)));

        // procura no diretório atual se o diretório a ser alterado o nome existe
        for (Directory dir : currentDir.getDirectories()) {
            if(dir.getName().equals(request.args[1])){
                
                // altera o nome do diretório
                String oldName = dir.getName();
                dir.setName(request.args[2]);
                return new Response("Directory + '" + oldName + "' renamed to '" + request.args[2] + "'", request.path);
            }
        }

        // resposta caso o diretório a ser alterado o nome não exista
        return new Response("Directory not found.", request.path);
    }
}
