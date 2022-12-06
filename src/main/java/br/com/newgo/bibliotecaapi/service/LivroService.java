package br.com.newgo.bibliotecaapi.service;

import br.com.newgo.bibliotecaapi.model.Autor;
import br.com.newgo.bibliotecaapi.model.Livro;
import br.com.newgo.bibliotecaapi.repository.LivroRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LivroService {
    private final LivroRepository livroRepository;

    public LivroService(LivroRepository livroRepository) {
        this.livroRepository = livroRepository;
    }

    public Page<Livro> listarLivros(Pageable pageable){
        return livroRepository.findAll(pageable);
    }

    @Transactional
    public Livro salvar(Livro livro){
        return livroRepository.save(livro);
    }

    @Transactional
    public void excluir(Livro livro){
        livroRepository.delete(livro);
    }

    public boolean existerPorIsbn10(String isbn){
        return livroRepository.existsByIsbn10(isbn);
    }

    public boolean existerPorIsbn13(String isbn){
        return livroRepository.existsByIsbn13(isbn);
    }

    public Optional<Livro> acharPorId(UUID id){
        return livroRepository.findById(id);
    }

    public Optional<List<Livro>> acharPorAutor(Autor autor) {
        return livroRepository.findByAutores(autor);
    }
}
