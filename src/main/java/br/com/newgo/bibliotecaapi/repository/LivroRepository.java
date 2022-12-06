package br.com.newgo.bibliotecaapi.repository;

import br.com.newgo.bibliotecaapi.model.Autor;
import br.com.newgo.bibliotecaapi.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LivroRepository extends JpaRepository<Livro, UUID> {

    boolean existsByIsbn10(String isbn);

    boolean existsByIsbn13(String isbn);

    Optional<Livro> findById(UUID id);

    Optional<List<Livro>> findByAutores(Autor autor);

}
