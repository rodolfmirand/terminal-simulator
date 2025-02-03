package TerminalSimulator.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import TerminalSimulator.models.dto.request.Request;
import TerminalSimulator.models.dto.response.Response;
import TerminalSimulator.services.GrepService;

@RestController
@RequestMapping(path = "/grep")
public class GrepController {
	@Autowired
	private GrepService service;
	
	@PostMapping()
    public ResponseEntity<Response> cd(@RequestBody Request request){
        return ResponseEntity.ok(this.service.execute(request));
    }
}
