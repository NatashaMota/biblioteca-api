package br.com.newgo.bibliotecaapi.controller;

import br.com.newgo.bibliotecaapi.model.Autor;
import br.com.newgo.bibliotecaapi.service.AutorService;
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

    public AutorController(AutorService autorService) {
        this.autorService = autorService;
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

}
