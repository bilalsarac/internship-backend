package com.mobile.controllers;

import com.mobile.entities.Client;
import com.mobile.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/v1")
public class ClientController {

    @Autowired
    private ClientService service;

    @GetMapping("/client")
    public Iterable<Client> findAll() {
        return service.getAllUsers();
    }

    @GetMapping("/client/{email}")
    public Client findByEmail(@PathVariable String email) {
        return service.getUserByEmail(email); // Use "email" parameter to find user by email
    }

    @PostMapping("/client")
    public Client save(@RequestBody Client client) {
        return service.addUser(client);
    }

    @DeleteMapping("/client/{email}")
    public void delete(@PathVariable String email) {
        service.deleteUserByEmail(email);
    }
}
