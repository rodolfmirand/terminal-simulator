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
        if (request.args.length < 3) {
            return new Response("Usage: chmod <permission> <name>", request.path);
        }

        String inputPermission = request.args[1];
        String targetName = request.args[2];

        String permissions;
        if (inputPermission.matches("\\d{3}")) {
            permissions = convertOctalToSymbolic(inputPermission);
        } else if (isValidPermission(inputPermission)) {
            permissions = inputPermission;
        } else {
            return new Response("Invalid permission format.", request.path);
        }

        Directory currentDir = Application.database.findDirectory(new ArrayList<>(List.of(request.path.split("/"))));

        if (currentDir == null) {
            return new Response("Directory not found.", request.path);
        }

        File file = currentDir.findFile(targetName);
        if (file != null) {
            file.setPermissions(permissions);
            return new Response("File permissions changed to: " + permissions, request.path);
        }

        Directory directory = currentDir.findSubDirectory(targetName);
        if (directory != null) {
            directory.setPermissions(permissions);
            return new Response("Directory permissions changed to: " + permissions, request.path);
        }

        return new Response("File or directory not found.", request.path);
    }

    private boolean isValidPermission(String permissions) {
        return permissions.matches("^[rwx-]{9}$");
    }

    private String convertOctalToSymbolic(String octal) {
        StringBuilder symbolic = new StringBuilder();

        for (char digit : octal.toCharArray()) {
            int value = Character.getNumericValue(digit);

            symbolic.append((value & 4) != 0 ? "r" : "-");
            symbolic.append((value & 2) != 0 ? "w" : "-");
            symbolic.append((value & 1) != 0 ? "x" : "-");
        }

        return symbolic.toString();
    }
}
