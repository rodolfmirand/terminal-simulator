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
        // Verifica se o número de argumentos passados no comando é insuficiente (menos de 2 argumentos)
        if (request.args.length < 2) {
            // Retorna uma resposta de uso adequado caso o comando não tenha os argumentos corretos
            return new Response("Usage: du <directory>", request.path);
        }

        // Pega o segundo argumento como o nome do diretório a ser verificado
        String name = request.args[1];

        // Tenta encontrar o diretório atual baseado no caminho fornecido na requisição (request.path)
        Directory currentDir = Application.database.findDirectory(new ArrayList<>(List.of(request.path.split("/"))));

        // Verifica se o diretório atual foi encontrado
        if (currentDir == null) {
            // Retorna uma resposta de erro caso o diretório atual não tenha sido encontrado
            return new Response("Directory not found", request.path);
        }

        // Tenta encontrar o subdiretório com o nome fornecido (name) dentro do diretório atual
        Directory dir = currentDir.findChildDirectory(name);

        // Verifica se o subdiretório foi encontrado
        if (dir == null) {
            // Retorna uma resposta de erro caso o subdiretório não seja encontrado
            return new Response("Directory not found", request.path);
        }

        // Retorna o tamanho em bytes do subdiretório encontrado, juntamente com a mensagem de sucesso
        return new Response("Size: " + dir.getBytesSize() + " bytes", request.path);

    }
}
