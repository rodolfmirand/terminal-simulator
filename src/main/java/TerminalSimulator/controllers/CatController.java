package TerminalSimulator.controllers;

<<<<<<< HEAD
=======

>>>>>>> 35d2b868b8d06258500369b656521fbd9199da14
import TerminalSimulator.models.dto.request.Request;
import TerminalSimulator.models.dto.response.Response;
import TerminalSimulator.services.CatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/cat")
public class CatController {
    @Autowired
    private CatService service;

    @PostMapping
        public ResponseEntity<Response> cat(@RequestBody Request request) {
        return ResponseEntity.ok(this.service.execute(request));
    }
}
