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

import java.util.Objects;
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
    public ResponseEntity<Object> listarTodos(){
        return ResponseEntity.status(HttpStatus.OK).body(autorService.listarTodos());
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
        Autor autor = new Autor();
        BeanUtils.copyProperties(autorDto, autor, "nacionalidade");

        Optional<Nacionalidade> nacionalidadeBD = nacionalidadeService.acharPorNome(autorDto.getNacionalidade());
        if(nacionalidadeBD.isEmpty()){
            if(Objects.equals(autorDto.getNacionalidade(), "")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Nacionalidade invalida.");
            }
            if(autorDto.getNacionalidade() != null){
                Nacionalidade novaNacionalidade = nacionalidadeService.salvarPorNome(autorDto.getNacionalidade());
                autor.setNacionalidade(novaNacionalidade);
            }
        } else{
            autor.setNacionalidade(nacionalidadeBD.get());
        }
        autorService.salvar(autor);
        return ResponseEntity.status(HttpStatus.CREATED).body(autorDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> excluir(@PathVariable(value = "id") UUID id){
        Optional<Autor> autor = autorService.listarPorId(id);
        if(!autor.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Autor não encontrado.");
        }
        autorService.excluir(autor.get());
        return ResponseEntity.status(HttpStatus.OK).body("Autor excluido com sucesso.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> alterar(@PathVariable UUID id, @RequestBody @Valid AutorDto autorDto){
        Optional<Autor> autorBD = autorService.listarPorId(id);
        if(!autorBD.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Autor não encontrado.");
        }
        Autor autor = new Autor();
        BeanUtils.copyProperties(autorDto, autor, "nacionalidade");
        autor.setId(autorBD.get().getId());
        autor = setNacionalidade(autor, autorDto.getNacionalidade());
        autorService.salvar(autor);
        return ResponseEntity.status(HttpStatus.OK).body(autor);
    }

    private Autor setNacionalidade(Autor autor, String nacionalidade){
        if(nacionalidade == null || Objects.equals(nacionalidade, "")) {
            return autor;
        }

        Optional<Nacionalidade> nacionalidadeBD = nacionalidadeService.acharPorNome(nacionalidade);
        if(nacionalidadeBD.isPresent()){
            autor.setNacionalidade(nacionalidadeBD.get());
            return autor;
        }
        Nacionalidade novaNacionalidade = nacionalidadeService.salvarPorNome(nacionalidade);
        autor.setNacionalidade(novaNacionalidade);
        return autor;
    }

}
