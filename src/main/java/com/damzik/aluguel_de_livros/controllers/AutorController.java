package com.damzik.aluguel_de_livros.controllers;

import com.damzik.aluguel_de_livros.entities.Autor;
import com.damzik.aluguel_de_livros.entities.Usuario;
import com.damzik.aluguel_de_livros.services.AutorService;
import com.damzik.aluguel_de_livros.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/autores")
@RequiredArgsConstructor
public class AutorController {

    private final AutorService autorService;

    // Listar Autores
    @GetMapping
    public ResponseEntity<List<Autor>> listarAutores(){
        return ResponseEntity.ok().body(autorService.listarAutores());
    }

    // Find autor by id
    @GetMapping("/{id}")
    public ResponseEntity<Autor> getAutorById(@PathVariable Long id){
        return ResponseEntity.ok().body(autorService.findAutorById(id));
    }

    // Cadastrar Autor
    @PostMapping()
    public ResponseEntity<Autor> cadastrarAutor(@RequestBody Autor autor){
        return ResponseEntity.status(HttpStatus.CREATED).body(autorService.cadastrarAutor(autor));
    }

    // Deletar Autor
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAutor(@PathVariable Long id){
        autorService.deleteAutor(id);
        return ResponseEntity.noContent().build();
    }
}