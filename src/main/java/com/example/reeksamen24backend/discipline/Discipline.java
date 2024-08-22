package com.example.reeksamen24backend.discipline;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Discipline {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String name;
    private Long ApproxDuration;

    public Discipline(String name, Long approxDuration) {
        this.name = name;
        ApproxDuration = approxDuration;
    }
}
