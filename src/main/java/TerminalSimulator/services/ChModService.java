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
public class ChModService implements CommandService {

    @Override
    public Response execute(Request request) {
        // Verifica se o número de argumentos passados no comando é válido
        if (request.args.length < 3) {
            return new Response("Usage: chmod <permission> <name>", request.path);
        }

        // Pega o primeiro argumento como a permissão desejada e o segundo como o nome do arquivo ou diretório
        String inputPermission = request.args[1];
        String targetName = request.args[2];

        String permissions;
        // Verifica se a permissão fornecida está no formato octal (3 dígitos numéricos)
        if (inputPermission.matches("\\d{3}")) {
            permissions = convertOctalToSymbolic(inputPermission); // Converte de octal para simbólico
        } else if (isValidPermission(inputPermission)) { // Verifica se a permissão está no formato simbólico válido
            permissions = inputPermission; // Se estiver no formato simbólico, usa a permissão diretamente
        } else {
            return new Response("Invalid permission format.", request.path); // Retorna erro se o formato for inválido
        }

        // Encontra o diretório atual no banco de dados simulando o terminal
        Directory currentDir = Application.database.findDirectory(new ArrayList<>(List.of(request.path.split("/"))));

        // Verifica se o diretório foi encontrado
        if (currentDir == null) {
            return new Response("Directory not found.", request.path); // Retorna erro se o diretório não existir
        }

        // Tenta encontrar o arquivo no diretório atual
        File file = currentDir.findFile(targetName);
        if (file != null) {
            // Se o arquivo for encontrado, altera suas permissões
            file.setPermissions(permissions);
            return new Response("File permissions changed to: " + permissions, request.path);
        }

        // Tenta encontrar um subdiretório com o nome fornecido
        Directory directory = currentDir.findSubDirectory(targetName);
        if (directory != null) {
            // Se o diretório for encontrado, altera suas permissões
            directory.setPermissions(permissions);
            return new Response("Directory permissions changed to: " + permissions, request.path);
        }

        // Se não encontrar nem o arquivo nem o diretório, retorna um erro
        return new Response("File or directory not found.", request.path);
    }

    // Método auxiliar para verificar se a permissão fornecida é válida no formato simbólico
    private boolean isValidPermission(String permissions) {
        return permissions.matches("^[rwx-]{9}$"); // Verifica se tem exatamente 9 caracteres de 'r', 'w', 'x' ou '-'
    }

    // Método auxiliar para converter permissões octais (3 dígitos) em permissões simbólicas (rwx)
    private String convertOctalToSymbolic(String octal) {
        StringBuilder symbolic = new StringBuilder();

        // Para cada caractere no octal, converte para o formato simbólico
        for (char digit : octal.toCharArray()) {
            int value = Character.getNumericValue(digit); // Pega o valor numérico do dígito octal

            // Verifica os bits para determinar quais permissões são 'r', 'w' ou 'x'
            symbolic.append((value & 4) != 0 ? "r" : "-"); // Bit de leitura (r)
            symbolic.append((value & 2) != 0 ? "w" : "-"); // Bit de escrita (w)
            symbolic.append((value & 1) != 0 ? "x" : "-"); // Bit de execução (x)
        }

        return symbolic.toString(); // Retorna a string de permissões simbólicas
    }
}
