package TerminalSimulator.controllers;

import TerminalSimulator.Application;
import TerminalSimulator.models.dto.request.Request;
import TerminalSimulator.models.dto.response.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/tree")
public class TreeController {

    @PostMapping
    public ResponseEntity<String> tree() {
        return ResponseEntity.ok().body(Application.database.tree());
    }
}
