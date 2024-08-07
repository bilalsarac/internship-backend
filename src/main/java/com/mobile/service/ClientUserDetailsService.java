package com.mobile.service;

import com.mobile.entities.Client;
import com.mobile.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class ClientUserDetailsService implements UserDetailsService {
    @Autowired
    private ClientRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Client> clientOptional = repository.findByEmail(email);
        if (clientOptional.isPresent()) {
            Client client = clientOptional.get();
            return new User(email, client.getPassword(), Set.of(new SimpleGrantedAuthority(client.getRole())));
        } else {
            throw new UsernameNotFoundException("Client not found with email: " + email);
        }
    }
}