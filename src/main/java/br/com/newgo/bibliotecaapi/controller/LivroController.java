package br.com.newgo.bibliotecaapi.controller;

import br.com.newgo.bibliotecaapi.dto.LivroDto;
import br.com.newgo.bibliotecaapi.dto.LivroDtoOutput;
import br.com.newgo.bibliotecaapi.model.Autor;
import br.com.newgo.bibliotecaapi.model.Editora;
import br.com.newgo.bibliotecaapi.model.Idioma;
import br.com.newgo.bibliotecaapi.model.Livro;
import br.com.newgo.bibliotecaapi.service.AutorService;
import br.com.newgo.bibliotecaapi.service.EditoraService;
import br.com.newgo.bibliotecaapi.service.IdiomaService;
import br.com.newgo.bibliotecaapi.service.LivroService;
import jakarta.validation.Valid;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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
    public ResponseEntity<List<LivroDtoOutput>> listarLivros(){
        List<LivroDtoOutput> livros = new ArrayList<>();
        for(Livro livro: livroService.listarLivros()){
            livros.add(new LivroDtoOutput(livro));
        }
        return ResponseEntity.status(HttpStatus.OK).body(livros);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> listarPorId(@PathVariable UUID id){
        Optional<Livro> livroOptional = livroService.acharPorId(id);
        if(livroOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Livro nao encontrado.");
        }

        return ResponseEntity.status(HttpStatus.OK).body(new LivroDtoOutput(livroOptional.get()));
    }

    @GetMapping("/autor/{id}")
    public ResponseEntity<Object> listarPorAutor(@PathVariable UUID id){
        if(!autorService.existePorId(id)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Autor n??o encontrado");

        }
        Optional<Autor> autorOptional = autorService.listarPorId(id);
        Optional<List<Livro>> livroOptional = livroService.acharPorAutor(autorOptional.get());

        List<LivroDtoOutput> livroDtoOutputs = new ArrayList<>();
        for(Livro livro: livroService.listarLivros()){
            livroDtoOutputs.add(new LivroDtoOutput(livro));
        }

        return livroOptional.<ResponseEntity<Object>>map(livros -> ResponseEntity.status(HttpStatus.OK).body(livroDtoOutputs)).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum livro encontrado"));
    }

    @PostMapping
    public ResponseEntity<Object> registrar(@RequestBody @Valid @NotNull LivroDto livroDto){
        if(livroService.existerPorIsbn10(livroDto.getIsbn10())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("ISBN10 j?? cadastrado.");
        }
        if(livroService.existerPorIsbn13(livroDto.getIsbn13())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("ISBN13 j?? cadastrado.");
        }
        if(livroDto.getAutores().isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Livro precisa ter autor");
        }

        for(UUID autor: livroDto.getAutores()){
            if(Objects.equals(autor, "") || autor == null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Livro precisa ter autor");
            }
            if(!autorService.existePorId(autor)){
                 return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Autor n??o encontrado.");
            }

        }

        Livro novoLivro = new Livro();
        BeanUtils.copyProperties(livroDto, novoLivro, "editora", "idioma", "autores");

        novoLivro = setEditora(novoLivro, livroDto.getEditora());
        novoLivro = setIdioma(novoLivro, livroDto.getIdioma());


        Set<Autor> autores = autorService.autoresPorId(livroDto.getAutores());
        novoLivro.setAutores(autores);
        livroService.salvar(novoLivro);
        return ResponseEntity.status(HttpStatus.CREATED).body(new LivroDtoOutput(novoLivro));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> excluir(@PathVariable UUID id){
        Optional<Livro> livroOptional = livroService.acharPorId(id);
        if(livroOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Livro n??o encontrado.");
        }
        livroService.excluir(livroOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Livro excluido com sucesso");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> alterar(@PathVariable UUID id, @RequestBody @Valid LivroDto livroDto){
        Optional<Livro> livroBD = livroService.acharPorId(id);
        if(livroBD.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Livro n??o encontrado.");
        }
        Livro livroAlterado = new Livro();
        BeanUtils.copyProperties(livroDto, livroAlterado, "id", "editora", "idioma", "autores");
        livroAlterado.setId(livroBD.get().getId());
        livroAlterado = setEditora(livroAlterado, livroDto.getEditora());
        livroAlterado = setIdioma(livroAlterado, livroDto.getIdioma());

        livroAlterado.setAutores(autorService.autoresPorId(livroDto.getAutores()));


        livroService.salvar(livroAlterado);
        return ResponseEntity.status(HttpStatus.OK).body(new LivroDtoOutput(livroAlterado));

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
