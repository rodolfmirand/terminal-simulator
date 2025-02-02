package TerminalSimulator.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import TerminalSimulator.models.dto.request.Request;
import TerminalSimulator.models.dto.response.Response;
import TerminalSimulator.services.EchoService;

@RestController
@RequestMapping(path = "/echo")
public class EchoController {
	@Autowired
	private EchoService service;
	
	@PostMapping
    public ResponseEntity<Response> rmDir(@RequestBody Request request){
        return ResponseEntity.ok(this.service.execute(request));
    }
}
