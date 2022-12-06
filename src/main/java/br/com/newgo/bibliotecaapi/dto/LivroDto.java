package br.com.newgo.bibliotecaapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
public class LivroDto {

    @NotBlank
    private String titulo;
    @NotBlank
    @Size(max = 10)
    private String isbn10;
    @Size(max = 13)
    private String isbn13;
    @NotBlank
    @Size(max = 150)
    private String editora;
    @NotBlank
    @Size(max = 30)
    private String idioma;
    private Set<UUID> autores;

}
