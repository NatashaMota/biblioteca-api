package br.com.newgo.bibliotecaapi.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Table(name = "nacionalidades")
public class Nacionalidade implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(nullable = false, unique = true, length = 30)
    private String nome;

    @OneToMany
    private Set<Autor> autores;
}
