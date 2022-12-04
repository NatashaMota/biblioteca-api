package br.com.newgo.bibliotecaapi.controller;

import br.com.newgo.bibliotecaapi.dto.AutorDto;
import br.com.newgo.bibliotecaapi.model.Autor;
import br.com.newgo.bibliotecaapi.model.Nacionalidade;
import br.com.newgo.bibliotecaapi.service.AutorService;
import br.com.newgo.bibliotecaapi.service.NacionalidadeService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/autor")
public class AutorController {

    private final AutorService autorService;
    private final NacionalidadeService nacionalidadeService;

    public AutorController(AutorService autorService, NacionalidadeService nacionalidadeService) {
        this.autorService = autorService;
        this.nacionalidadeService = nacionalidadeService;
    }

    @GetMapping
    public ResponseEntity<Object> listarTodos(
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(autorService.listarTodos(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> listar(@PathVariable(value = "id") UUID id){
        Optional<Autor> autorOptional = autorService.listarPorId(id);
        if(!autorOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Autor não encontrado!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(autorOptional.get());
    }

    @PostMapping
    public ResponseEntity<Object> registrar(@RequestBody @Valid AutorDto autorDto){
        if(!nacionalidadeService.existePorNome(autorDto.getNacionalidade())
                && autorDto.getNacionalidade() != "" && autorDto.getNacionalidade() != null){
            Nacionalidade novaNacionalidade = new Nacionalidade();
            novaNacionalidade.setNome(autorDto.getNacionalidade());
            nacionalidadeService.salvar(novaNacionalidade);
        }
        Autor autor = new Autor();
        BeanUtils.copyProperties(autorDto, autor, "nacionalidade");
        Optional<Nacionalidade> nacionalidade = nacionalidadeService.acharPorNome(autorDto.getNacionalidade());
        if(nacionalidade.isPresent()){
            autor.setNacionalidade(nacionalidade.get());
        }
        autorService.salvar(autor);
        return ResponseEntity.status(HttpStatus.CREATED).body(autorDto);
    }

}
