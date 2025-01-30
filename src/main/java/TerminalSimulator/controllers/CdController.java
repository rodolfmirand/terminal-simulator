package TerminalSimulator.controllers;

import TerminalSimulator.models.dto.request.Request;
import TerminalSimulator.services.CdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/cd")
public class CdController {

    @Autowired
    private CdService service;

    @PostMapping(path = "/cd")
    public ResponseEntity<String> cdController(@RequestBody Request request){
        return ResponseEntity.ok(this.service.exectute(request));
    }
}
