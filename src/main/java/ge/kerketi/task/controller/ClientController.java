package ge.kerketi.task.controller;

import ge.kerketi.task.model.ClientDto;
import ge.kerketi.task.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ClientController {

    private ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/register-client")
    ResponseEntity<Map<String,String>> registerClient(@Valid @RequestBody ClientDto clientDto) {
        Map<String,String> response = clientService.registerClient(clientDto);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
