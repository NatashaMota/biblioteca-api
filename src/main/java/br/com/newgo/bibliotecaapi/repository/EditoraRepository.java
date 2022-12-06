package br.com.newgo.bibliotecaapi.repository;

import br.com.newgo.bibliotecaapi.model.Editora;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EditoraRepository extends JpaRepository<Editora, UUID> {
    public Editora findByNome(String nome);
}
