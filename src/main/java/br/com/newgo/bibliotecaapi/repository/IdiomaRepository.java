package br.com.newgo.bibliotecaapi.repository;

import br.com.newgo.bibliotecaapi.model.Idioma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IdiomaRepository extends JpaRepository<Idioma, UUID> {

    Idioma findByNome(String nome);

}
