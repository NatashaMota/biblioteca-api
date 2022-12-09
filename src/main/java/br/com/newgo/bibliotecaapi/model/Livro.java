package br.com.newgo.bibliotecaapi.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

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
    private Editora editora = new Editora();
    @ManyToOne
    private Idioma idioma = new Idioma();

    @ManyToMany
    @JoinTable(name = "autor_livro", joinColumns = @JoinColumn(name = "livro_id"),
            inverseJoinColumns = @JoinColumn(name = "autor_id"))
    private Set<Autor> autores = new HashSet<>();

    public Livro() {
    }

    public Livro(String titulo, String isbn10, String isbn13) {
        this.titulo = titulo;
        this.isbn10 = isbn10;
        this.isbn13 = isbn13;
    }

    public Livro(UUID id, String titulo, String isbn10, String isbn13, Editora editora, Idioma idioma, Set<Autor> autores) {
        this.id = id;
        this.titulo = titulo;
        this.isbn10 = isbn10;
        this.isbn13 = isbn13;
        this.editora = editora;
        this.idioma = idioma;
        this.autores = autores;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public Editora getEditora() {
        return editora;
    }

    public void setEditora(Editora editora) {
        this.editora = editora;
    }

    public Idioma getIdioma() {
        return idioma;
    }

    public void setIdioma(Idioma idioma) {
        this.idioma = idioma;
    }

    public Set<Autor> getAutores() {
        return autores;
    }

    public void setAutores(Set<Autor> autores) {
        this.autores = autores;
    }

    public Set<UUID> getAutoresUUID(){
        Set<UUID> uuids = new HashSet<>();
        for(Autor autor: this.getAutores()){
            uuids.add(autor.getId());
        }
        return uuids;
    }

    @Override
    public String toString() {
        return "Livro{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", isbn10='" + isbn10 + '\'' +
                ", isbn13='" + isbn13 + '\'' +
                ", editora=" + editora +
                ", idioma=" + idioma +
                ", autores=" + autores +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Livro livro = (Livro) o;
        return id.equals(livro.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
