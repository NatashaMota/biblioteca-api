package br.com.newgo.bibliotecaapi.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
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
    @Column(nullable = false ,length = 10, unique = true)
    private String isbn10;
    @Column(length = 13, unique = true)
    private String isbn13;

    @ManyToOne
    private Editora editora;
    @ManyToOne
    private Idioma idioma;

    @ManyToMany
    @JoinTable(name = "autor_livro", joinColumns = @JoinColumn(name = "livro_id"),
            inverseJoinColumns = @JoinColumn(name = "autor_id"))
    private Set<Autor> autores = new HashSet<>();
}
