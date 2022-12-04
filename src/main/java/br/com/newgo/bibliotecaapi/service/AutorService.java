package br.com.newgo.bibliotecaapi.service;

import br.com.newgo.bibliotecaapi.model.Autor;
import br.com.newgo.bibliotecaapi.repository.AutorRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AutorService {
    private final AutorRepository autorRepository;

    public AutorService(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    public Page<Autor> listarTodos(Pageable pageable){
        return autorRepository.findAll(pageable);
    }

    public Optional<Autor> listarPorId(UUID id){
        return autorRepository.findById(id);
    }

    @Transactional
    public Autor salvar(Autor autor){
        return autorRepository.save(autor);
    }

    @Transactional
    public void excluir(Autor autor){
        autorRepository.delete(autor);
    }

    public Autor alterar(Autor autor){
        return autorRepository.save(autor);
    }

}
