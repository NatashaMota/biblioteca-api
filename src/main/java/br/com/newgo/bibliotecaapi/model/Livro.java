package br.com.newgo.bibliotecaapi.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Table(name = "livros")
public class Livro implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(nullable = false)
    private String titulo;
    @Column(length = 10, unique = true)
    private String isbn10;
    @Column(length = 13, unique = true)
    private String isbn13;

    @ManyToOne
    private Editora editora;
    @ManyToOne
    private Idioma idioma;

    @ManyToMany(mappedBy = "livros")
    private Set<Autor> autores;
}
