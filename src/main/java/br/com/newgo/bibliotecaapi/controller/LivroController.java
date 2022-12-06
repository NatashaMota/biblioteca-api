package br.com.newgo.bibliotecaapi.controller;

import br.com.newgo.bibliotecaapi.dto.LivroDto;
import br.com.newgo.bibliotecaapi.model.Autor;
import br.com.newgo.bibliotecaapi.model.Editora;
import br.com.newgo.bibliotecaapi.model.Idioma;
import br.com.newgo.bibliotecaapi.model.Livro;
import br.com.newgo.bibliotecaapi.service.AutorService;
import br.com.newgo.bibliotecaapi.service.EditoraService;
import br.com.newgo.bibliotecaapi.service.IdiomaService;
import br.com.newgo.bibliotecaapi.service.LivroService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/livro")
public class LivroController {

    private final LivroService livroService;
    private final AutorService autorService;
    private final EditoraService editoraService;

    private final IdiomaService idiomaService;

    public LivroController(LivroService livroService, AutorService autorService,
                           EditoraService editoraService, IdiomaService idiomaService) {
        this.livroService = livroService;
        this.autorService = autorService;
        this.editoraService = editoraService;
        this.idiomaService = idiomaService;
    }

    @GetMapping
    public ResponseEntity<Page<Livro>> listarLivros(
            @PageableDefault(page = 0, size = 10, sort = "titulo", direction = Sort.Direction.ASC) Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(livroService.listarLivros(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> listarPorId(@PathVariable UUID id){
        Optional<Livro> livroOptional = livroService.acharPorId(id);
        if(livroOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Livro nao encontrado.");
        }
        Livro livro = livroOptional.get();
       return ResponseEntity.status(HttpStatus.OK).body(livro);
    }

    @GetMapping("/autor/{id}")
    public ResponseEntity<Object> listarPorAutor(@PathVariable UUID id){
        if(!autorService.existePorId(id)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Autor não encontrado");

        }
        Optional<Autor> autorOptional = autorService.listarPorId(id);
        Optional<List<Livro>> livroOptional = livroService.acharPorAutor(autorOptional.get());
        if(!livroOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum livro encontrado");
        }
        return ResponseEntity.status(HttpStatus.FOUND).body(livroOptional.get());
    }

    @PostMapping
    public ResponseEntity<Object> registrar(@RequestBody @Valid LivroDto livroDto){
        if(livroService.existerPorIsbn10(livroDto.getIsbn10())){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ISBN10 já cadastrado.");
        }
        if(livroService.existerPorIsbn13(livroDto.getIsbn13())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("ISBN13 já cadastrado.");
        }
        for(UUID autor: livroDto.getAutores()){
            if(!autorService.existePorId(autor)){
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Autor não encontrado.");
            }
        }

        Livro novoLivro = new Livro();
        BeanUtils.copyProperties(livroDto, novoLivro, "editora", "idioma", "autores");

        novoLivro = setEditora(novoLivro, livroDto.getEditora());
        novoLivro = setIdioma(novoLivro, livroDto.getIdioma());

        Set<Autor> autores = autorService.autoresPorId(livroDto.getAutores());
        for(Autor autor : autores){
          autor.getLivros().add(novoLivro);
            autorService.salvar(autor);
        }

        novoLivro.setAutores(autores);
        livroService.salvar(novoLivro);

        return ResponseEntity.status(HttpStatus.OK).body(novoLivro);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> excluir(@PathVariable UUID id){
        Optional<Livro> livroOptional = livroService.acharPorId(id);
        if(livroOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Livro não encontrado.");
        }
        livroService.excluir(livroOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Livro excluido com sucesso");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> alterar(@PathVariable UUID id, @RequestBody @Valid LivroDto livroDto){
        Optional<Livro> livroBD = livroService.acharPorId(id);
        if(livroBD.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Livro não encontrado.");
        }
        Livro livroAlterado = new Livro();
        BeanUtils.copyProperties(livroDto, livroAlterado, "id", "editora", "idioma", "autores");
        livroAlterado.setId(livroBD.get().getId());
        livroAlterado = setEditora(livroAlterado, livroDto.getEditora());
        livroAlterado = setIdioma(livroAlterado, livroDto.getIdioma());

        livroAlterado.setAutores(autorService.autoresPorId(livroDto.getAutores()));


        livroService.salvar(livroAlterado);
        return ResponseEntity.status(HttpStatus.OK).body("Livro alterado com sucesso.");

    }

    private Livro setEditora(Livro livro, String editoraNome){
        Optional<Editora> editora = editoraService.acharPorNome(editoraNome);
        if(editora.isPresent()){
            livro.setEditora(editora.get());
            return livro;
        }
        Editora novaEditora = new Editora();
        novaEditora.setNome(editoraNome);
        editoraService.salvar(novaEditora);

        livro.setEditora(novaEditora);
        return livro;
    }

    private Livro setIdioma(Livro livro, String idiomaNome){
        Optional<Idioma> idiomaOptional = idiomaService.acharPorNome(idiomaNome);
        if(idiomaOptional.isPresent()){
            livro.setIdioma(idiomaOptional.get());
            return livro;
        }
        Idioma novoIdioma = new Idioma();
        novoIdioma.setNome(idiomaNome);
        idiomaService.salvar(novoIdioma);

        livro.setIdioma(novoIdioma);
        return livro;

    }

}
