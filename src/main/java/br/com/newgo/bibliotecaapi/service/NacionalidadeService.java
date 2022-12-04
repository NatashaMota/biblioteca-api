package br.com.newgo.bibliotecaapi.service;

import br.com.newgo.bibliotecaapi.model.Nacionalidade;
import br.com.newgo.bibliotecaapi.repository.NacionalidadeRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NacionalidadeService {
    private final NacionalidadeRepository nacionalidadeRepository;

    public NacionalidadeService(NacionalidadeRepository nacionalidadeRepository) {
        this.nacionalidadeRepository = nacionalidadeRepository;
    }

    public boolean existePorNome(String nome){
        return nacionalidadeRepository.existsByNome(nome);
    }

    public Optional<Nacionalidade> acharPorNome(String nome){
        return Optional.ofNullable(nacionalidadeRepository.findByNome(nome));
    }

    @Transactional
    public Nacionalidade salvar(Nacionalidade nacionalidade){
        return nacionalidadeRepository.save(nacionalidade);
    }

    @Transactional
    public Nacionalidade salvarPorNome(String nome){
        Nacionalidade novaNacionalidade = new Nacionalidade();
        novaNacionalidade.setNome(nome);
        return salvar(novaNacionalidade);
    }
}
