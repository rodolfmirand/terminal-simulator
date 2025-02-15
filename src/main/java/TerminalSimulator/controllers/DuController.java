package TerminalSimulator.controllers;

import TerminalSimulator.models.dto.request.Request;
import TerminalSimulator.models.dto.response.Response;
import TerminalSimulator.services.DuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/du")
public class DuController {

    @Autowired
    private DuService duService;

    @PostMapping()
    public ResponseEntity<Response> du(@RequestBody Request request) {
        return ResponseEntity.ok(duService.execute(request));
    }
}