package br.com.newgo.bibliotecaapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;

@Data
public class AutorDto {

    @NotBlank
    @Size(max=20)
    private String nome;
    @NotBlank
    @Size(max=120)
    private String sobrenome;
    private Date dataNascimento;
    @Size(max=30)
    private String nacionalidade;
}
