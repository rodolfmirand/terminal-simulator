package TerminalSimulator.controllers;

import TerminalSimulator.models.dto.request.Request;
import TerminalSimulator.models.dto.response.Response;
import TerminalSimulator.services.RenameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/rename")
public class RenameController {

    @Autowired
    private RenameService service;

    @PostMapping
    public ResponseEntity<Response> rename(@RequestBody Request request){
        return ResponseEntity.ok(this.service.execute(request));
    }
}
