package com.mobile.service;

import com.mobile.entities.Client;
import com.mobile.repository.ClientRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    ClientRepository userRepository;


    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public ClientService(ClientRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<Client> getAllUsers() {
        return userRepository.findAll();
    }


    public Client addUser(Client client) {
        Client newClient = new Client();
        newClient.setEmail(client.getEmail());
        newClient.setPassword(passwordEncoder.encode(client.getPassword()));
        newClient.setRole(client.getRole());
        newClient.setFirstName(client.getFirstName());
        newClient.setLastName(client.getLastName());
        return userRepository.save(newClient);

    }

    public Client getUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }



    public void deleteUserById(Long userId) {
            userRepository.deleteById(userId);
    }

    public Client getUserByEmail(String email) {
        Optional<Client> clientOptional = userRepository.findByEmail(email);
        if (clientOptional.isPresent()) {
            return clientOptional.get();
        } else {
            throw new RuntimeException("Client not found for email: " + email); // Or handle the exception as needed
        }
    }
    public void deleteUserByEmail(String email) {
        userRepository.deleteByEmail(email);
    }
}