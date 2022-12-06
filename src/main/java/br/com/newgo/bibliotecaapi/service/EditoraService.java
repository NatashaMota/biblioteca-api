package br.com.newgo.bibliotecaapi.service;

import br.com.newgo.bibliotecaapi.model.Editora;
import br.com.newgo.bibliotecaapi.repository.EditoraRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class EditoraService {

    private final EditoraRepository editoraRepository;

    public EditoraService(EditoraRepository editoraRepository) {
        this.editoraRepository = editoraRepository;
    }
    @Transactional
    public Editora salvar(Editora editora){
        return editoraRepository.save(editora);
    }

    public Optional<Editora> acharPorNome(String nome){
        return Optional.ofNullable(editoraRepository.findByNome(nome));
    }

}
