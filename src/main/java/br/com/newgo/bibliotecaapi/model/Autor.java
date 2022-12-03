package br.com.newgo.bibliotecaapi.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Table(name = "aut_autores")
public class Autor implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(nullable = false, length = 20)
    private String nome;
    @Column(nullable = false, length = 120)
    private String sobrenome;
    private Date dataNascimento;
    @ManyToOne
    private Nacionalidade nacionalidade;

    @ManyToMany
    @JoinTable(name = "autores_livros",
        joinColumns = @JoinColumn(name = "autor_fk"),
        inverseJoinColumns = @JoinColumn(name = "livro_fk"))
    private Set<Livro> livros;
}