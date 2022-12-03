package br.com.newgo.bibliotecaapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class AutorDto {

    @NotBlank
    @Size(max=20)
    private String nome;
    @NotBlank
    @Size(max=120)
    private String sobrenome;
    private Date dataNascimento;
    private UUID idNascionalidade;
}
