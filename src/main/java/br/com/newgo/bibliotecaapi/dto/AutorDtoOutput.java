package br.com.newgo.bibliotecaapi.dto;

import br.com.newgo.bibliotecaapi.model.Autor;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public class AutorDtoOutput extends AutorDto{
    @NotBlank
    private UUID id;


    public AutorDtoOutput(Autor autor) {
        super(autor);
        this.id = autor.getId();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "AutorDtoOutput{" +
                "id='" + id + '\'' +
                '}';
    }
}
