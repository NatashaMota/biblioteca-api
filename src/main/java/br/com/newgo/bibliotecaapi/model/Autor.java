package br.com.newgo.bibliotecaapi.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Table(name = "autores")
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


    //@JoinTable(name = "livro_autor", uniqueConstraints = @UniqueConstraint(columnNames = {"livro_id", "autor_id"}),
    //        joinColumns = @JoinColumn(name="autor_id"),
    //        inverseJoinColumns = @JoinColumn(name = "livro_id"))
    @ManyToMany(mappedBy = "autores")
    private Set<Livro> livros = new HashSet<>();
}