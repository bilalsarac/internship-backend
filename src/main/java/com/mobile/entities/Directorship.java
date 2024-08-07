package com.mobile.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Table(name="directorship")
@Data
public class Directorship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "directorship", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Tax> taxes;

}
