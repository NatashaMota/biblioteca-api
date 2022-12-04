package br.com.newgo.bibliotecaapi.repository;

import br.com.newgo.bibliotecaapi.model.Nacionalidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface NacionalidadeRepository extends JpaRepository<Nacionalidade, UUID> {
    Nacionalidade findByNome(String nome);
    boolean existsByNome(String nome);
}