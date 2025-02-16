package TerminalSimulator.controllers;

import TerminalSimulator.models.dto.request.Request;
import TerminalSimulator.models.dto.response.Response;
import TerminalSimulator.services.StatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/stat")
public class StatController {

    @Autowired
    private StatService statService;

    @PostMapping()
    public ResponseEntity<Response> stat(@RequestBody Request request) {
        return ResponseEntity.ok(statService.execute(request));
    }
}