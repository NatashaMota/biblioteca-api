package br.com.newgo.bibliotecaapi.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
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
    @Column(length = 20)
    private String nacionalidade;
    private Date dataNascimento;

}