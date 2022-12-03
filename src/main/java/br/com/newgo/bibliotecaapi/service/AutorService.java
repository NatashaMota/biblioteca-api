package br.com.newgo.bibliotecaapi.service;

import br.com.newgo.bibliotecaapi.repository.AutorRepository;
import org.springframework.stereotype.Service;

@Service
public class AutorService {
    private final AutorRepository autorRepository;

    public AutorService(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }
}
