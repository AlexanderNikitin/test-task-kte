package an.kte.controller;

import an.kte.model.Client;
import an.kte.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ClientController {
    @Autowired
    private ClientService clientService;

    @GetMapping("/clients")
    public List<Client> clients() {
        return clientService.findAll();
    }

    @GetMapping("/clients/{id}")
    public Client client(@PathVariable Long id) {
        return clientService.getById(id).orElseThrow();
    }

    @PutMapping(path = "/clients")
    public Client update(@RequestBody Client client) {
        return clientService.save(client);
    }
}
