package br.com.newgo.bibliotecaapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

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
    @NotNull
    private Set<UUID> autores;

    public LivroDto() {
    }

    public LivroDto(String titulo, String isbn10, String isbn13, String editora, String idioma, Set<UUID> autores) {
        this.titulo = titulo;
        this.isbn10 = isbn10;
        this.isbn13 = isbn13;
        this.editora = editora;
        this.idioma = idioma;
        this.autores = autores;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIsbn10() {
        return isbn10;
    }

    public void setIsbn10(String isbn10) {
        this.isbn10 = isbn10;
    }

    public String getIsbn13() {
        return isbn13;
    }

    public void setIsbn13(String isbn13) {
        this.isbn13 = isbn13;
    }

    public String getEditora() {
        return editora;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Set<UUID> getAutores() {
        return autores;
    }

    public void setAutores(Set<UUID> autores) {
        this.autores = autores;
    }

}