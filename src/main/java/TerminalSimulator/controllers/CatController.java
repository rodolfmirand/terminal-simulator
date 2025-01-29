package TerminalSimulator.controllers;

import TerminalSimulator.models.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/cat")
public class CatController {

    @PostMapping
    public ResponseEntity<Response> cat() {
        return ResponseEntity.ok(new Response("cat"));
    }
}
