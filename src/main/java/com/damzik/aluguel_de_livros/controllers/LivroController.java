package com.damzik.aluguel_de_livros.controllers;

import com.damzik.aluguel_de_livros.DTOs.request.LivroRequestDTO;
import com.damzik.aluguel_de_livros.DTOs.response.LivroResponseDTO;
import com.damzik.aluguel_de_livros.entities.Livro;
import com.damzik.aluguel_de_livros.services.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/livros")
@RequiredArgsConstructor
public class LivroController {

    private final LivroService livroService;

    // Listar Livros
    @GetMapping
    public ResponseEntity<List<LivroResponseDTO>> listarLivros(){
        return ResponseEntity.ok().body(livroService.listarLivros());
    }

    // Find Livro by id
    @GetMapping("/{id}")
    public ResponseEntity<LivroResponseDTO> getLivroById(@PathVariable Long id){
        return ResponseEntity.ok().body(livroService.findLivroById(id));
    }

    // Cadastrar Livro
    @PostMapping
    public ResponseEntity<LivroResponseDTO> cadastrarLivro(@RequestBody @Valid LivroRequestDTO livroRequestDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(livroService.cadastrarLivro(livroRequestDTO));
    }

    // Deletar Livro
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLivro(@PathVariable Long id){
        livroService.deletarLivro(id);

        return ResponseEntity.noContent().build();
    }
}
