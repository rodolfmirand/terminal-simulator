package TerminalSimulator.controllers;

import TerminalSimulator.models.dto.request.Request;
import TerminalSimulator.models.dto.response.Response;
import TerminalSimulator.services.LsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/ls")
public class LsController {

    @Autowired
    private LsService lsService;

    @PostMapping
    public ResponseEntity<Response> ls(@RequestBody Request request) {
        return ResponseEntity.ok(lsService.execute(request));
    }
}
