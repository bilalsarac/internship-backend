package com.mobile.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;

@Entity
@Table(name="client")
@Data
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @Email
    @Column(unique = true)
    private String email;
    private String password;

    @Column(name = "role")
    private String role;

    private String firstName;

    private String lastName;



}
