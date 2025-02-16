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

        // verifica a ausência de argumentos
        if(request.args.length < 1){
            return new Response("Empty directory name.", request.path);
        }

        // armazena o nome do diretório
        String newDirName = request.args[1];

        // busca na árvore o diretório atual
        Directory currentDir = Application.database.findDirectory(new ArrayList<>(List.of(request.path.split("/"))));

        // verifica se já existe um diretório com o mesmo nome
        for (Directory child : currentDir.getDirectories()){
            if(child.getName().equals(newDirName)){
                return new Response("Directory already exists!", request.path);
            }
        }

        // cria um novo objeto diretório
        Directory newDir = new Directory(newDirName, currentDir);

        // adiciona o novo diretório na árvore
        currentDir.addDirectory(newDir);

        return new Response("Directory created '" + newDir.getPath() + "'.",  request.path);
    }
}
