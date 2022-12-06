package br.com.newgo.bibliotecaapi.repository;

import br.com.newgo.bibliotecaapi.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AutorRepository extends JpaRepository<Autor, UUID> {
    boolean existsById(String id);
}
