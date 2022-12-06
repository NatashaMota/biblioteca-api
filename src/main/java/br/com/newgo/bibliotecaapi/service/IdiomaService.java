package br.com.newgo.bibliotecaapi.service;

import br.com.newgo.bibliotecaapi.model.Idioma;
import br.com.newgo.bibliotecaapi.repository.IdiomaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IdiomaService {

    private final IdiomaRepository idiomaRepository;

    public IdiomaService(IdiomaRepository idiomaRepository) {
        this.idiomaRepository = idiomaRepository;
    }

    public Optional<Idioma> acharPorNome(String nome){
        return Optional.ofNullable(idiomaRepository.findByNome(nome));
    }

    public Idioma salvar(Idioma idioma){
        return idiomaRepository.save(idioma);
    }
}
