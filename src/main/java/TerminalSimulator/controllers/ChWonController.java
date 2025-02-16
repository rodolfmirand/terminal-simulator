package TerminalSimulator.controllers;

import TerminalSimulator.models.dto.request.Request;
import TerminalSimulator.models.dto.response.Response;
import TerminalSimulator.services.ChWonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/cmwon")
public class ChWonController {
    @Autowired
    private ChWonService service;

    @PostMapping()
    public ResponseEntity<Response> chwon (@RequestBody Request request){
        return ResponseEntity.ok(this.service.execute(request));
    }
}
