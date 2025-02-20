package TerminalSimulator.services;

import TerminalSimulator.Application;
import TerminalSimulator.models.Directory;
import TerminalSimulator.models.File;
import TerminalSimulator.models.dto.request.Request;
import TerminalSimulator.models.dto.response.Response;
import TerminalSimulator.services.interfaces.CommandService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LsService implements CommandService {

    @Override
    public Response execute(Request request) {
        // Verifica se há mais de dois argumentos fornecidos, o que é inválido para o comando "ls"
        if (request.args.length > 2) {
            return new Response("Usage: ls or ls -l", request.path);
        }

        // Divide o caminho fornecido em partes e busca o diretório correspondente no banco de dados
        Directory currentDir = Application.database.findDirectory(new ArrayList<>(List.of(request.path.split("/"))));

        // Se o diretório não for encontrado, retorna uma mensagem de erro
        if (currentDir == null) {
            return new Response("Directory not found", request.path);
        }

        // Verifica se o argumento "-l" foi passado para ativar a exibição detalhada
        boolean detailed = request.args.length > 1 && request.args[1].equals("-l");

        // Lista que armazenará os nomes dos arquivos e diretórios a serem exibidos
        List<String> result = new ArrayList<>();

        // Adiciona os diretórios à listagem, considerando o modo detalhado se necessário
        for (Directory dir : currentDir.getDirectories()) {
            result.add(detailed ? dir.toStringLs() : dir.getName());
        }

        // Adiciona os arquivos à listagem, considerando o modo detalhado se necessário
        for (File file : currentDir.getFiles()) {
            result.add(detailed ? file.toStringLs() : file.getName());
        }

        // Retorna a resposta contendo a listagem formatada como uma string única
        return new Response(String.join("\n", result), request.path);
    }
}